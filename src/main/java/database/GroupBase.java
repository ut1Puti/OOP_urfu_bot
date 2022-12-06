package database;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс базы данных групп
 *
 * @author Кедровских Олег
 * @version 1.0
 */
public interface GroupBase {
    /**
     * Метод добавления информации о подписчиках группы
     *
     * @param groupScreenName короткое имя группы
     * @param userSubscribedToGroupId id пользователя
     * @return {@code true} - если пользователь был добавлен в базу данных подписчиков группы,
     * {@code false} - если пользователь не был добавлен в базу данных
     */
    boolean addInfoToGroup(String groupScreenName, String userSubscribedToGroupId);

    /**
     * Метод удаления пользователь из подписчиков группы
     *
     * @param groupScreenName короткое имя группы
     * @param userSubscribedToGroupId id пользователя
     * @return {@code true} - если пользователь был удален, {@code false} - если пользователь не был удален
     */
    boolean deleteInfoFromGroup(String groupScreenName, String userSubscribedToGroupId);

    /**
     * Метод получающий множество все групп из базы данных
     *
     * @return множество групп из базы данных
     */
    Set<String> getGroups();

    /**
     * Метод получающий список всех подписчиков группы
     *
     * @param groupScreenName короткое имя группы
     * @return список всех подписчиков группы
     */
    List<String> getSubscribedToGroupUsersId(String groupScreenName);

    /**
     * Метод получающий множество все групп, на которые подписан пользователь
     *
     * @param userId id пользователя
     * @return множество групп на которые подписан пользователь
     */
    Set<String> getUserSubscribedGroups(String userId);

    /**
     * Метод получающий дату последнего поста группы
     *
     * @param groupScreenName короткое имя группы
     * @return {@code Optional.empty} если групп нет в базе данных,
     * {@code Optional.of(groupLastPost)} если есть группа в базе данных
     */
    Optional<Long> getGroupLastPostDate(String groupScreenName);

    /**
     * Метод обновляющий дату последнего поста
     *
     * @param groupScreenName короткое имя группы
     * @param newLastPostDate новая дата последнего поста
     */
    void updateGroupLastPost(String groupScreenName, long newLastPostDate);

    /**
     * Метод проверяющий есть ли группа в базе данных
     *
     * @param groupScreenName короткое имя группы
     * @return {@code true} - если группа есть в базе данных, {@code false} - если группы нет в базе данных
     */
    boolean containsGroup(String groupScreenName);
}
