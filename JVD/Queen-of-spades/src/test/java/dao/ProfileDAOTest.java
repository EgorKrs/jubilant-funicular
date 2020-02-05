package dao;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.ProfileDAO;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.Account;
import com.loneliness.entity.Language;
import com.loneliness.entity.Profile;
import com.loneliness.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;

public class ProfileDAOTest {
    private static Faker faker;
    private static ProfileDAO dao;

    @BeforeClass
    public static void initialize() throws DAOException {
        faker=new Faker(new Locale("ru"));
        dao=new ProfileDAO();
        dao.create(createValidProfile());
    }


    @Test
    public void createTest() throws  DAOException {
        Profile profile=createValidProfile();
        Profile.Builder builder=new Profile.Builder(profile);
        builder.setId(dao.create(profile));
        profile=builder.build();
        Assert.assertEquals(profile,dao.receive(profile));
    }
    @Test
    public void updateTest()throws  DAOException{
        Profile profile=dao.receiveAll(new int[]{0,1}).iterator().next();
        Profile.Builder builder=new Profile.Builder(profile);
        if (builder.getLanguage() == Language.EN) {
            builder.setLanguage(Language.RUS);
        } else {
            builder.setLanguage(Language.EN);
        }
        Profile changedProfile=builder.build();
        dao.update(changedProfile);
        Assert.assertNotEquals(profile,dao.receive(changedProfile));
    }
    @Test
    public void deleteTest()throws  DAOException{
        Profile profile=dao.receiveAll(new int[]{0,1}).iterator().next();
        dao.delete(profile);
        Assert.assertNotEquals(profile,dao.receive(profile));
    }
    @Test
    public void receiveTest()throws  DAOException{
        Collection<Profile> left=dao.receiveAll();
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll(new int[]{0,left.size()}).toArray());
    }

    private static User getUser()throws  DAOException {
        UserDAO userDAO=new UserDAO();
        return userDAO.receiveAll(new int[]{0,1}).iterator().next();
    }

    private static Profile createValidProfile() throws  DAOException {
        Profile.Builder builder = new Profile.Builder();
        User user=getUser();
        builder.setUserID(user.getId());
        Language language;
        switch (faker.number().numberBetween(0, 3)) {
            case 0:
                language = Language.RUS;
                break;
            case 1:
                language = Language.BY;
                break;
            default:
                language = Language.EN;
        }
        builder.setRating(faker.number().randomDigitNotZero());
        builder.setLanguage(language);
        builder.setLastUpdate(LocalDate.now());
        builder.setTelegram(user.getLogin());
        builder.setInstagram(user.getLogin());
        builder.setAbout("about");
        return builder.build();
    }
}