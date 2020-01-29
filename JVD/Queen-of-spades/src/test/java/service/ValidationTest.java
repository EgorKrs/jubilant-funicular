package service;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.*;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import com.loneliness.command.common_comand.Validation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

public class ValidationTest {
    private static Faker faker;

    @BeforeClass
    public static void init() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    public void accountValidationTest() throws DAOException, ServiceException {
        Account.Builder builder = new Account.Builder();
        Command<Account, Set<ConstraintViolation<Account>>, Account> commonCommand = new Validation<>();
        builder.setUserID(3)
                .setId(1)
                .setLastUpdate(LocalDate.now())
                .setSumOfMoney(new BigDecimal(faker.number().randomDouble(2, 10, 10000))
                        .setScale(3, RoundingMode.HALF_DOWN))
                .setNumber(faker.finance().creditCard().replaceAll("-", ""));
        Account valid = builder.build();
        builder.setUserID(2)
                .setLastUpdate(LocalDate.now().plusMonths(1))
                .setSumOfMoney(new BigDecimal(faker.number().randomDouble(2, 10, 10000)).setScale(3, RoundingMode.HALF_DOWN))
                .setNumber(faker.finance().bic());
        Account inValid = builder.build();

        Assert.assertEquals(0, commonCommand.execute(valid).size());
        Assert.assertEquals(valid, commonCommand.undo());
        Assert.assertNotEquals(0, commonCommand.execute(inValid).size());
        Assert.assertEquals(inValid, commonCommand.undo());
    }

    @Test
    public void messageValidationTest() throws DAOException, ServiceException {
        Message.Builder builder = new Message.Builder();
        builder.setToUser(1)
                .setDate(LocalDate.now())
                .setId(1)
                .setFromUser(2)
                .setMessage(faker.hipster().word());
        Message valid = builder.build();
        builder.setToUser(0)
                .setDate(LocalDate.now().plusMonths(1))
                .setFromUser(-1)
                .setMessage("");
        Message inValid = builder.build();
        Command<Message, Set<ConstraintViolation<Message>>, Message> commonCommand = new Validation<>();


        Assert.assertEquals(0, commonCommand.execute(valid).size());
        Assert.assertEquals(valid, commonCommand.undo());
        Assert.assertNotEquals(0, commonCommand.execute(inValid).size());
        Assert.assertEquals(inValid, commonCommand.undo());
    }

    @Test
    public void newsValidationTest() throws ServiceException {
        News valid = new News.Builder()
                .setId(1)
                .setLast_update(LocalDate.now())
                .setText(faker.hipster().word())
                .build();
        Command<News, Set<ConstraintViolation<News>>, News> commonCommand = new Validation<>();

        News inValid = new News.Builder()
                .setLast_update(LocalDate.now().plusDays(1))
                .setText("")
                .build();

        Assert.assertEquals(0, commonCommand.execute(valid).size());
        Assert.assertEquals(valid, commonCommand.undo());
        Assert.assertNotEquals(0, commonCommand.execute(inValid).size());
        Assert.assertEquals(inValid, commonCommand.undo());
    }

    @Test
    public void pictureValidationTest() throws ServiceException, IOException {
        Picture.Builder builder = new Picture.Builder();
        builder.setName("Anonymous.png")
                .setLastUpdate(LocalDate.now())
                .setId(1);
        FileInputStream stream = new FileInputStream(new File("data\\" + builder.getName()));
        builder.setByteImage(stream.readAllBytes());
        Picture valid = builder.build();
        Picture inValid = new Picture.Builder()
                .setId(-1)
                .setName("")
                .setLastUpdate(LocalDate.now().plusWeeks(1))
                .build();
        Command<Picture, Set<ConstraintViolation<Picture>>, Picture> commonCommand = new Validation<>();


        Assert.assertEquals(0, commonCommand.execute(valid).size());
        Assert.assertEquals(valid, commonCommand.undo());
        Assert.assertNotEquals(0, commonCommand.execute(inValid).size());
        Assert.assertEquals(inValid, commonCommand.undo());
    }
    @Test
    public void pictureInNewsValidationTest() throws ServiceException, IOException {
       PicturesInNews valid=new PicturesInNews.Builder().setId(1).setNewsID(2).setPictureID(3).build();
       PicturesInNews inValid=new PicturesInNews.Builder().setId(-1).setNewsID(-2).setPictureID(0).build();
        Command<PicturesInNews, Set<ConstraintViolation<PicturesInNews>>, PicturesInNews> commonCommand = new Validation<>();


        Assert.assertEquals(0, commonCommand.execute(valid).size());
        Assert.assertEquals(valid, commonCommand.undo());
        Assert.assertNotEquals(0, commonCommand.execute(inValid).size());
        Assert.assertEquals(inValid, commonCommand.undo());
    }
    @Test
    public void profileInNewsValidationTest() throws ServiceException, IOException {
        Profile.Builder builder = new Profile.Builder();
        builder.setUserID(1).setId(1);
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
        builder.setTelegram(faker.name().username());
        builder.setInstagram(faker.name().username());
        builder.setAbout(faker.hipster().word());
        builder.setNumberOfDefeats(1);
        builder.setNumberOfVictories(1);
        builder.setNumberOfGame(1);
        Profile valid=builder.build();


        Command<Profile, Set<ConstraintViolation<Profile>>, Profile> commonCommand = new Validation<>();
        Profile inValid=builder.setNumberOfGame(-1).setNumberOfVictories(0).setNumberOfDefeats(-121323).build();

        Assert.assertEquals(0, commonCommand.execute(valid).size());
        Assert.assertEquals(valid, commonCommand.undo());
        Assert.assertNotEquals(0, commonCommand.execute(inValid).size());
        Assert.assertEquals(inValid, commonCommand.undo());
    }

}
