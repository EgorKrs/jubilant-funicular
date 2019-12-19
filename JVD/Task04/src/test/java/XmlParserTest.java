import com.github.javafaker.Faker;
import entity.HotelCharacteristic;
import entity.TouristVouchers;
import entity.TravelPackages;
import entity.Type;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class XmlParserTest {
    private static Faker faker;
    private static XmlParser xmlParser;
    @BeforeClass
    public static void init(){
        faker=new Faker(new Locale("ru"));
        xmlParser=new XmlParser();
    }
    private TravelPackages createValid(){
        HotelCharacteristic hotelCharacteristic=HotelCharacteristic.newBuilder().setFeed(faker.random().nextBoolean()).
                setId(faker.idNumber().valid()).setNumberOfStars(faker.number().numberBetween(1,5)).setOther(faker.commerce().productName()).build();
        TravelPackages.Builder builder=TravelPackages.newBuilder().setCountry(faker.country().name()).
                setHotelCharacteristic(hotelCharacteristic).setId(faker.idNumber().valid()).setNumberOfDay(faker.number().
                randomDigit()).setPrice(String.valueOf(faker.number().randomDouble(5,10,100000)));
        switch (faker.number().numberBetween(0,3)){
            case 0:
                builder.setType(Type.DAY_OFF);
                break;
            case 1:
                builder.setType(Type.PILGRIMAGE);
                break;
            default:
                builder.setType(Type.SIGHTSEEING);
        }
        return builder.build();
    }
    public TravelPackages createInValid(){
        HotelCharacteristic hotelCharacteristic=HotelCharacteristic.newBuilder().setFeed(faker.random().nextBoolean()).
                setNumberOfStars(faker.number().numberBetween(5,10)).setOther(faker.commerce().productName()).build();
        TravelPackages.Builder builder=TravelPackages.newBuilder().setCountry(faker.country().name()).
                setHotelCharacteristic(hotelCharacteristic).setNumberOfDay(faker.number().
                randomDigit()).setPrice(String.valueOf(faker.number().randomDouble(5,10,100000)));
        switch (faker.number().numberBetween(0,3)){
            case 0:
                builder.setType(Type.DAY_OFF);
                break;
            case 1:
                builder.setType(Type.PILGRIMAGE);
                break;
            default:
                builder.setType(Type.SIGHTSEEING);
        }
        return builder.build();
    }

    @Test
    public void ParserTest(){
        TouristVouchers startVouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            startVouchers.vouchers.add(createValid());
        }
        xmlParser.marshall(startVouchers,"Data\\data.xml");
        Assert.assertEquals(startVouchers,xmlParser.unMarshall("Data\\data.xml"));
    }
    @Test
    public void validationParserTest(){
        TouristVouchers invalidVouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            invalidVouchers.vouchers.add(createInValid());
        }
        xmlParser.marshall(invalidVouchers,"Data\\data.xml");
        xmlParser.unMarshall("Data\\data.xml");
        Assert.assertFalse(xmlParser.getErrors().isEmpty());
    }


}
