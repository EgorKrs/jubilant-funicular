package dao;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;

public class UserDAOTest {
    private static Faker faker;
    private static UserDAO dao;

    @BeforeClass
    public static void initialize() throws DAOException {
        faker = new Faker(new Locale("ru"));
        dao = new UserDAO();
        dao.create(createValidUser());
    }

    private static User createValidUser() {
        User.Builder builder = new User.Builder();
        builder.setLogin(faker.name().username());
        builder.setPassword(faker.internet().password());
        User.Type type;
        if (faker.number().numberBetween(0, 1) == 0) {
            type = User.Type.ADMIN;
        } else {
            type = User.Type.USER;
        }
        builder.setType(type);
        builder.setLastUpdate(LocalDate.now());
        builder.setCreateDate(LocalDate.now());
        builder.setAvatarId(0);
        return builder.build();
    }

    @Test
    public void createTest() throws DAOException {
        User user = createValidUser();
        User.Builder builder = new User.Builder(user);
        builder.setId(dao.create(user));
        user = builder.build();
        Assert.assertEquals(user, dao.receive(user));
    }

    @Test
    public void updateTest() throws DAOException {
        User user = dao.receiveAll(new Integer[]{0, 1}).iterator().next();
        User.Builder builder = new User.Builder(user);
        if (builder.getType().equals(User.Type.USER)) {
            builder.setType(User.Type.ADMIN);
        } else builder.setType(User.Type.USER);
        User changedUser = builder.build();
        dao.update(changedUser);
        Assert.assertNotEquals(user, dao.receive(changedUser));
    }

    @Test
    public void deleteTest() throws DAOException {
        User user = dao.receiveAll(new Integer[]{0, 1}).iterator().next();
        dao.delete(user);
        Assert.assertNotEquals(user, dao.receive(user));
    }

    @Test
    public void receiveTest() throws DAOException {
        Collection<User> left = dao.receiveAll();
        Assert.assertArrayEquals(left.toArray(), dao.receiveAll(new Integer[]{0, left.size()}).toArray());

    }
}
