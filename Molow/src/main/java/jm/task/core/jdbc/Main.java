package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
      /*UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Name1", "LastName1", (byte) 20);
        userService.saveUser("Name2", "LastName2", (byte) 25);
        userService.saveUser("Name3", "LastName3", (byte) 31);
        userService.saveUser("Name4", "LastName4", (byte) 38);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeConnection();*/
        // Получаем SessionFactory
        SessionFactory sessionFactory = Util.getSessionFactory();

        // Открываем сессию
        Session session = sessionFactory.openSession();

// Создаем объект транзакции
        Transaction transaction = session.beginTransaction();

// Загружаем существующего пользователя по идентификатору
        User existingUser = session.get(User.class, 1L);

        if (existingUser != null) {
            // Обновляем существующую запись
            existingUser.setName("Новое имя");
            existingUser.setLastName("Новая фамилия");
            session.update(existingUser);
        } else {
            // Создаем новую запись
            User newUser = new User();
            newUser.setId(1L);
            newUser.setName("Misha");
            newUser.setLastName("Molotov");
            newUser.setAge((byte) 25);
            session.save(newUser);
        }

// Завершаем транзакцию и закрываем сессию
        transaction.commit();
        session.close();
    }
}
