//package service;
//
//import com.github.javafaker.Faker;
//import com.loneliness.dao.DAOException;
//import com.loneliness.dao.sql_dao_impl.AccountDAO;
//import com.loneliness.dao.sql_dao_impl.UserDAO;
//import com.loneliness.entity.Account;
//import com.loneliness.entity.User;
//import com.loneliness.command.Command;
//import com.loneliness.service.ServiceException;
//import com.loneliness.command.common_comand.Create;
//import com.loneliness.command.common_comand.Delete;
//import com.loneliness.command.common_comand.ReceiveAll;
//import com.loneliness.command.common_comand.Update;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.Locale;
//
//public class AccountServiceTest {
//    private static Account validAccount;
//    private static Faker faker;
//    private static AccountDAO dao;
//    private static Command<Integer,Integer, Account> commonCommand;
//
//    @BeforeClass
//    public static void initialize() throws DAOException, ServiceException {
//        dao=new AccountDAO();
//        faker=new Faker(new Locale("ru"));
//        commonCommand =new Create<>(new <Account>AccountDAO());
//        Account.Builder builder=new Account.Builder(createValidAccount());
//        builder.setId(commonCommand.execute(builder.build()));
//        validAccount=builder.build();
//
//
//    }
//
//    @Test
//    public void createTest() throws DAOException, ServiceException {
//        Account account=createValidAccount();
//        Account created=dao.receive(commonCommand.execute(account));
//        Account.Builder builder=new Account.Builder(account);
//        builder.setId(created.getId());
//        account=builder.build();
//        Assert.assertEquals(account,created);
//        created=dao.receive(commonCommand.undo());
//        Assert.assertNotEquals(account,created);
//    }
//    @Test
//    public void updateTest() throws DAOException, ServiceException {
//
//        Account profile=dao.receive(validAccount);
//        commonCommand =new Update<>(new <Account>AccountDAO()).setData(profile);
//        Account.Builder builder=new Account.Builder(profile);
//        builder.setNumber(faker.finance().creditCard());
//        Account changedProfile=builder.build();
//        commonCommand.execute(changedProfile);
//        Assert.assertNotEquals(profile,dao.receive(changedProfile));
//        commonCommand.undo();
//        Assert.assertEquals(profile,dao.receive(changedProfile));
//    }
//    @Test
//    public void deleteTest() throws DAOException, ServiceException {
//        Account profile=dao.receive(validAccount);
//        boolean similar=false;
//        commonCommand =new Delete<>(new <Account>AccountDAO());
//        commonCommand.execute(profile);
//        Assert.assertNotEquals(profile,dao.receive(profile));
//
//        Account pr=dao.receive(commonCommand.undo());
//        if(     profile.getUserID() == pr.getUserID() &&
//                profile.getCreditCardNumber().equals(pr.getCreditCardNumber()) &&
//                profile.getSumOfMoney().equals(pr.getSumOfMoney()))
//            similar=true;
//        Assert.assertTrue(similar);
//    }
//    @Test
//    public void receiveTest() throws DAOException, ServiceException {
//        Command<Collection<Account>, Collection<Account>,Account> commonAccountCommand =new ReceiveAll<>(new <Account>AccountDAO());
//        Collection<Account> left=commonAccountCommand.execute( new Account.Builder().build());
//        Assert.assertArrayEquals(left.toArray(),dao.receiveAll().toArray());
//    }
//    @AfterClass
//    public static void clean() throws DAOException {
//        dao.delete(validAccount);
//    }
//
//
//
//
//    private static User getUser() throws  DAOException {
//        UserDAO userDAO=new UserDAO();
//        return userDAO.receiveAll(new int[]{0,1}).iterator().next();
//    }
//    private static Account createValidAccount() throws DAOException {
//        Account.Builder builder = new Account.Builder();
//        User user = getUser();
//        builder.setUserID(user.getId())
//                .setLastUpdate(LocalDate.now())
//                .setSumOfMoney(new BigDecimal(faker.number().randomDouble(2,10,10000)).setScale(3, RoundingMode.HALF_DOWN))
//                .setNumber(faker.finance().creditCard());
//        return builder.build();
//    }
//}
