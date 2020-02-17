//package dao;
//
//import com.github.javafaker.Faker;
//import com.loneliness.dao.DAOException;
//import com.loneliness.dao.sql_dao_impl.AccountDAO;
//import com.loneliness.dao.sql_dao_impl.UserDAO;
//import com.loneliness.entity.Account;
//import com.loneliness.entity.User;
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
//public class AccountDAOTest {
//    private static Faker faker;
//    private static AccountDAO dao;
//
//
//    @BeforeClass
//    public static void initialize() throws DAOException {
//        dao=new AccountDAO();
//        faker=new Faker(new Locale("ru"));
//        dao.create(createValidAccount());
//
//
//    }
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
//                .setSumOfMoney(new BigDecimal(faker.number().randomDouble(2,10,10000)).setScale(3,RoundingMode.HALF_DOWN))
//                .setNumber(faker.finance().creditCard().replaceAll("-",""));
//        return builder.build();
//    }
//    @Test
//    public void createTest() throws  DAOException {
//        Account account=createValidAccount();
//        Account.Builder builder=new Account.Builder(account);
//        builder.setId(dao.create(account));
//        account=builder.build();
//        Assert.assertEquals(account,dao.receive(account));
//    }
//    @Test
//    public void updateTest()throws  DAOException{
//        Account profile=dao.receiveAll(new int[]{0,1}).iterator().next();
//        Account.Builder builder=new Account.Builder(profile);
//        builder.setNumber(faker.finance().creditCard());
//        Account changedProfile=builder.build();
//        dao.update(changedProfile);
//        Assert.assertNotEquals(profile,dao.receive(changedProfile));
//    }
//    @Test
//    public void deleteTest()throws  DAOException{
//        Account profile=dao.receiveAll(new int[]{0,1}).iterator().next();
//        dao.delete(profile);
//        Assert.assertNotEquals(profile,dao.receive(profile));
//    }
//    @Test
//    public void receiveTest()throws  DAOException{
//        Collection<Account> left=dao.receiveAll();
//        Assert.assertArrayEquals(left.toArray(),dao.receiveAll(new int[]{0,left.size()}).toArray());
//    }
//}
