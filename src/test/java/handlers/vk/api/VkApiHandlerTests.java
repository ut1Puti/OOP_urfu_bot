package handlers.vk.api;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import handlers.vk.api.groups.NoGroupException;
import org.junit.jupiter.api.Test;
import user.User;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирующий методы класса VkApiHandler
 * @author Кедровских Олег
 * @version 1.0
 */
public class VkApiHandlerTests {
    /** Поле объекта обрабатывающего запросы к вк апи */
    private VkApiHandler vk = new VkApiHandler("src/test/resources/anonsrc/vkconfig.properties");
    /** Поле тестового пользователя */
    private User testUser;

    /**
     * Метод проверяет работу аутентификации пользователя
     */
    @Test
    public void authTest() {
        assertNotNull(vk.getAuthURL());
        createUser();
        assertNotNull(testUser);

        boolean error = false;
        try {
            VkApiHandler incorrectVk = new VkApiHandler("src/test/resources/testanonsrc/unknownfile.properties");
        } catch (RuntimeException e) {
            error = true;
        }
        assertTrue(error);
    }

    /**
     * Метод проверяющий работу метода getGroupUrl
     * @throws ApiException - возникает при ошибке обращения к vk api со стороны vk
     * @throws ClientException - возникает при ошибке обращения к vk api со стороны клиента
     */
    @Test
    public void getGroupUrlTest() throws ClientException, ApiException {
        createUser();
        try {
            assertEquals(vk.getGroupURL("triplesixdelete", testUser), "https://vk.com/triplesixdelete");
            vk.getGroupURL("jklfjdksljfkl", testUser);
        } catch (NoGroupException e) {
            assertEquals(e.getMessage(), "Группы с названием jklfjdksljfkl не существует");
        }
    }

    /**
     * Метод тестирующий работу метода getGroupId
     * @throws ApiException - возникает при ошибке обращения к vk api со стороны vk
     * @throws ClientException - возникает при ошибке обращения к vk api со стороны клиента
     */
    @Test
    public void getGroupId() throws ClientException, ApiException {
        createUser();
        try {
            assertEquals(vk.getGroupId("lida", testUser), "147725517");
            vk.getGroupId("fdhjkshgjkdshzfhkldjsz", testUser);
        } catch (NoGroupException e) {
            assertEquals(e.getMessage(), "Группы с названием fdhjkshgjkdshzfhkldjsz не существует");
        }
    }

    /**
     * Метод создающий пользователя
     */
    private void createUser() {
        System.out.println(vk.getAuthURL());
        testUser = vk.createUser();
    }
}
