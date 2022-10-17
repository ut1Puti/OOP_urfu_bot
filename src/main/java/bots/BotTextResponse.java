package bots;

/**
 * Класс возможных ответов на сообщения пользователя
 *
 * @author Кедровских Олег
 * @author Щеголев Андрей
 * @version 1.6
 */
public class BotTextResponse {
    /**
     * Поле сообщения о боте и его возможностях
     */
    public static final String HELP_INFO = """
            Привет, я бот помогающий взаимодействовать с музыкальной индустрией.
            Я могу найти страницу исполнителя, выдав в качестве результата id или ссылку на его группу в вк.
            Чтобы пройти верификацию и получить доступ к использованию команд связанных с вк,
            используйте команду "/auth". Также ее можно использовать при повторной верификации.
            Чтобы получить ссылку на подтвержденного исполнителя используйте команду
            "/link имя артиста или его псевдоним".
            Чтобы получить id на подтвержденного исполнителя используйте команду
            "/id имя артиста или его псевдоним".
            Чтобы подписаться на исполнителя используте команду
            "/subscribe имя артиста или его псевдоним"
            Чтобы получить последние пять постов используйте команду
            "/get_five_posts имя артиста или его псевдоним"
            Чтобы остановить бота используйте
            "/stop".
            Вы можете вызвать это сообщение еще раз использовав
            "/help".""";
    /**
     * Поле сообщения об успешной авторизации
     */
    public static final String AUTH_SUCCESS = "Вы успешно прошли авторизацию";
    /**
     * Поле сообщения об ошибке при аутентификации
     */
    public static final String AUTH_ERROR = "Ошибка при аутентификации. Повторите позже.";
    /**
     * Поле сооббщения о необходимости обновить токен
     */
    public static final String UPDATE_TOKEN = "Продлите токен с помощью команды /auth.";
    /**
     * Поле сообщения об остановке бота
     */
    public static final String STOP_INFO = "Остановлен.";
    /**
     * Поле сообщения с просьбой перейти по ссылке
     */
    public static final String AUTH_GO_VIA_LINK = "Перейдите по ссылке, чтобы пройти аутентификацию, ссылка действительна одну минуту:\n";
    /**
     * Поле сообщения с просьбой пройти аутентификации через vk
     */
    public static final String NOT_AUTHED_USER = "Сначала пройдите аутентификацию с помощью вк. Для этого используйте /auth";
    /**
     * Поле сообщения неизвестной команды
     */
    public static final String UNKNOWN_COMMAND = "Неизвестная команда. Используйте /help, чтобы увидеть доступные";
    /**
     * Поле сообщения подписки на группу
     */
    public static final String SUBSCRIBE = "Вы успешно подписаны на группу!";
    /**
     * Поле сообщения о том, что пользователь уже подписан
     */
    public static final String ALREADY_SUBSCRIBER = "Вы уже подписаны на эту группу";
    /**
     * Поле сообщения, о том что группа закрыта
     */
    public static final String GROUP_IS_CLOSED = "Не могу подписать так как группа закрыта";
    /**
     * Поле сообщения о том, что в группе отсутствуют посты
     */
    public static final String NO_POSTS_IN_GROUP = "В этой группе нет постов";
    /**
     * Поле сообщения об ошибке на стороны сервера
     */
    public static final String SERVER_ERROR = """
            Возникла ошибка при получении данных с сервера.
            Попробуйте повторить позже""";
    /**
     * Поле сообщения об ошибке при обращении к vk api
     */
    public static final String VK_API_ERROR = """
            Возникла ошибка при обращении к вк.
            Попробуйте еще раз, если ошибка возникнет еще раз, попробуйте позже.""";
}
