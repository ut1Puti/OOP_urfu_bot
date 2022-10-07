package handlers.vk.api.groups;

import com.vk.api.sdk.actions.Groups;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.Fields;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import handlers.vk.api.VkApiConsts;
import user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для взаимодействиями с группами через vk api
 *
 * @author Кедровских Олег
 * @version 1.0
 */
public class VkApiGroups extends Groups {
    /**
     * Конструктор унаследованный от родительского класс
     * @param client - клиент vk
     */
    public VkApiGroups(VkApiClient client) {
        super(client);
    }

    /**
     * Метод, который ищет все группы по запросу
     *
     * @param groupName - запрос
     * @param callingUser - пользователь сделавший запрос
     * @return список групп полученных по запросу
     * @throws ApiException - возникает при ошибке обращения к vk api со стороны vk
     * @throws NoGroupException - возникает если не нашлась группа по заданной подстроке
     * @throws ClientException - возникает при ошибке обращения к vk api со стороны клиента
     */
    public List<Group> searchGroups(String groupName, User callingUser) throws NoGroupException, ApiException, ClientException {
        List<Group> userFindGroups = search(callingUser, groupName)
                .offset(VkApiConsts.DEFAULT_OFFSET).count(VkApiConsts.DEFAULT_GROUPS_NUMBER)
                .execute()
                .getItems();

        if (userFindGroups.isEmpty()){
            throw new NoGroupException(groupName);
        }

        return userFindGroups;
    }

    /**
     * Метод, который ищет подтвержденные группы по запросу
     *
     * @param groupName - запрос
     * @param callingUser - пользователь сделавший запрос
     * @return верифицированную группу
     *         если групп оказалось больше одной возвращает с большим числом подписчиков
     *         если верифицированная группа не нашлась, возвращает null
     * @throws ApiException - возникает при ошибке обращения к vk api со стороны vk
     * @throws NoGroupException - возникает если не нашлась группа по заданной подстроке
     * @throws ClientException - возникает при ошибке обращения к vk api со стороны клиента
     */
    public Group searchGroup(String groupName, User callingUser) throws ApiException, NoGroupException, ClientException {
        List<Group> userFindGroups = searchGroups(groupName, callingUser);

        int maxMembersCount = Integer.MIN_VALUE;
        Group resultGroup = null;
        for (Group userFindGroup : userFindGroups) {
            List<GetByIdObjectLegacyResponse> userFindByIdGroups;
            try {
                userFindByIdGroups = getByIdObjectLegacy(callingUser)
                        .groupId(String.valueOf(userFindGroup.getId()))
                        .fields(Fields.MEMBERS_COUNT)
                        .execute();
            } catch (ApiException | ClientException e) {
                continue;
            }

            if (userFindByIdGroups.isEmpty()) {
                continue;
            }

            GetByIdObjectLegacyResponse userFindByIdGroup = userFindByIdGroups.get(VkApiConsts.FIRST_ELEMENT_INDEX);
            String[] foundByIdGroupNames = userFindByIdGroup.getName().split("[/|]");
            for (String foundByIdGroupName : foundByIdGroupNames) {

                if (isNameDifferent(groupName, foundByIdGroupName)) {
                    continue;
                }

                if (userFindByIdGroup.getMembersCount() > maxMembersCount) {
                    maxMembersCount = userFindByIdGroup.getMembersCount();
                    resultGroup = userFindByIdGroup;
                }

            }
        }

        if (resultGroup == null) {
            throw new NoGroupException(groupName);
        }

        return resultGroup;
    }

    /**
     * Метод проверяет есть ли разница между двумя строками
     *
     * @param baseName - изначальное имя
     * @param findName - имя поиска
     * @return true - если разница хотя бы в одном слове больше 50%
     *         false - если разница в обоих словах меньше 50%
     */
    private boolean isNameDifferent(String baseName, String findName) {
        String lowerCaseBaseName = baseName.toLowerCase();
        String lowerCaseUserFindName = findName.toLowerCase();

        Pair<String> diffPair = stringDifference(lowerCaseBaseName, lowerCaseUserFindName);

        int baseNameDiff = (int)((double) diffPair.first.length() / (double) baseName.length() * 100);
        int searchNameDiff = (int)((double) diffPair.second.length() / (double) findName.length() * 100);

        return baseNameDiff > 50 || searchNameDiff > 50;
    }

    /**
     * Метод, который ищет буквы в которых отличаются две строки
     *
     * @param firstString - первая строка
     * @param secondString - вторая строка
     * @return пару, элементы которой это строки состоящие из букв в которых слова различаются
     */
    private Pair<String> stringDifference(String firstString, String secondString) {
        return diffSearcher(firstString, secondString, new HashMap<>());
    }

    /**
     * Метод рекурсивно ищущий несовпадающие элементы
     *
     * @param firstString - первая строка
     * @param secondString - вторая строка
     * @param lookup - Map хранящий не совпавшие элементы
     * @return пару строк состоящих из не совпавших символов
     */
    private Pair<String> diffSearcher(String firstString, String secondString, Map<Long, Pair<String>> lookup) {
        long key = ((long) firstString.length()) | secondString.length();
        if (!lookup.containsKey(key)) {
            Pair<String> value;

            if (firstString.isEmpty() || secondString.isEmpty()) {
                value = new Pair<>(firstString, secondString);
            } else if (firstString.charAt(0) == secondString.charAt(0)) {
                value = diffSearcher(firstString.substring(1), secondString.substring(1), lookup);
            } else {
                Pair<String> firstStringDifferences = diffSearcher(firstString.substring(1), secondString, lookup);
                Pair<String> secondStringDifferences = diffSearcher(firstString, secondString.substring(1), lookup);

                if (firstStringDifferences.first.length() + firstStringDifferences.second.length() <
                        secondStringDifferences.first.length() + secondStringDifferences.second.length()) {
                    value = new Pair<>(firstString.charAt(0) + firstStringDifferences.first, firstStringDifferences.second);
                } else {
                    value = new Pair<>(secondStringDifferences.first, secondString.charAt(0) + secondStringDifferences.second);
                }

            }

            lookup.put(key, value);
        }
        return lookup.get(key);
    }

    /**
     * Хранит элементы пары
     *
     * @param first - первый элемент
     * @param second - второй элемент
     * @param <T> - тип хранимых элементов
     */
    private record Pair<T>(T first, T second){}
}