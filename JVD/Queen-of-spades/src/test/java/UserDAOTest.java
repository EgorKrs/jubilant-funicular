import com.github.javafaker.Faker;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.util.Locale;

public class UserDAOTest {
    private static Faker faker;
    private static UserDAO dao;
    @BeforeClass
    public static void initialize() throws PropertyVetoException {
        faker=new Faker(new Locale("ru"));
        dao=new UserDAO();
    }
    private User createValidUser(){
        User.Builder builder=new User.Builder();
        builder.setLogin(faker.name().username());
        builder.setPassword(faker.internet().password());
        User.Type type;
        if (faker.number().numberBetween(0, 1) == 0) {
            type = User.Type.ADMIN;
        } else {
            type = User.Type.GAMER;
        }
        builder.setType(type);
        builder.setLastUpdate(LocalDate.now());
        builder.setCreateDate(LocalDate.now());
        builder.setAvatarId(0);
        return builder.build();
    }
    @Test
    public void createTest(){
        User user=createValidUser();
        User.Builder builder=new User.Builder(user);
        builder.setId(dao.create(user));
        user=builder.build();
        Assert.assertEquals(user,dao.receive(user));
    }
    @Test
    public void updateTest(){
        User user=dao.receiveAll(new int[]{0,1}).iterator().next();
        User.Builder builder=new User.Builder(user);
        if(builder.getType().equals(User.Type.GAMER)){
            builder.setType(User.Type.ADMIN);
        }
        else  builder.setType(User.Type.GAMER);
        User changedUser=builder.build();
        dao.update(changedUser);
        Assert.assertNotEquals(user,dao.receive(changedUser));
    }
    @Test
    public void deleteTest(){
        User user=dao.receiveAll(new int[]{0,1}).iterator().next();
        dao.delete(user);
        Assert.assertNotEquals(user,dao.receive(user));
    }
    @Test
    public void receiveTest(){
        int lengthAll=dao.receiveAll().size();
        int lengthInLim=dao.receiveAll(new int[]{0,lengthAll}).size();
        Assert.assertEquals(lengthAll,lengthInLim);
    }









//    @Test
//    public void img() throws SQLException, IOException, PropertyVetoException {
//        Connection con=SQLConnection.getInstance().getConnection();
//        Statement st = con.createStatement();
//        File imgfile = new File("data\\Anonymous.png");
//        FileInputStream fin = new FileInputStream(imgfile);
//        PreparedStatement pre = con.prepareStatement("insert into pictures (image) values(?)");
//        pre.setBinaryStream(1, fin, (int) imgfile.length());
//        pre.executeUpdate();
//        System.out.println("Inserting Successfully!");
//        pre.close();
//        fin.close();
//    }
}
