import com.github.javafaker.Faker;
import com.loneliness.*;
import com.loneliness.entity.HotelCharacteristic;
import com.loneliness.entity.TouristVouchers;
import com.loneliness.entity.TravelPackages;
import com.loneliness.entity.Type;
import com.loneliness.parser.XmlDomParser;
import com.loneliness.parser.XmlJaxbParser;
import com.loneliness.parser.XmlStAXParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

public class XmlParserTest {
    private static Faker faker;
    private static XmlJaxbParser xmlJaxbParser;
    private static XmlDomParser xmlDomParser;
    private static XmlStAXParser xmlStAXParser;
    @BeforeClass
    public static void init(){
        faker=new Faker(new Locale("ru"));
        xmlJaxbParser =new XmlJaxbParser();
        xmlDomParser=new XmlDomParser();
        xmlStAXParser=new XmlStAXParser();
    }
    private TravelPackages createValid(){
        HotelCharacteristic hotelCharacteristic= HotelCharacteristic.newBuilder().setFeed(faker.random().nextBoolean()).
                setId(faker.idNumber().valid()).setNumberOfStars(faker.number().numberBetween(1,5)).setOther(faker.
                commerce().productName()).build();
        TravelPackages.Builder builder=TravelPackages.newBuilder().setCountry(faker.country().name()).
                setHotelCharacteristic(hotelCharacteristic).setId(faker.idNumber().valid()).setNumberOfDay(faker.number().
                randomDigitNotZero()).setPrice(String.valueOf(faker.number().randomDouble(5,10,100000)));
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
    public void ParserJaxbTest(){
        TouristVouchers startVouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            startVouchers.vouchers.add(createValid());
        }
        xmlJaxbParser.marshall("Data\\data.xml",startVouchers);
        Assert.assertEquals(startVouchers, xmlJaxbParser.unMarshall("Data\\data.xml"));
    }
    @Test
    public void validationJaxbParserTest(){
        TouristVouchers invalidVouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            invalidVouchers.vouchers.add(createInValid());
        }
        xmlJaxbParser.marshall("Data\\data.xml",invalidVouchers);
        xmlJaxbParser.unMarshall("Data\\data.xml");
        Assert.assertFalse(xmlJaxbParser.getErrors().isEmpty());
    }

    @Test
    public void ValidationTest(){
        TouristVouchers invalidVouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            invalidVouchers.vouchers.add(createInValid());
        }
        xmlDomParser.marshall("Data\\data.xml",invalidVouchers);
        TouristVouchers vouchers=xmlDomParser.unMarshall("Data\\data.xml");
        Assert.assertEquals(0,vouchers.vouchers.size());
    }

    @Test
    public void DomParserTest(){
        TouristVouchers vouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            vouchers.vouchers.add(createValid());
        }
        xmlDomParser.marshall("Data\\data.xml",vouchers);
        Assert.assertEquals(vouchers, xmlDomParser.unMarshall("Data\\data.xml"));
    }
    @Test
    public void StAX(){
        TouristVouchers vouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            vouchers.vouchers.add(createValid());
        }
        xmlStAXParser.marshall("Data\\data.xml",vouchers);
        Assert.assertEquals(vouchers,xmlStAXParser.unMarshall("Data\\data.xml"));
    }


}
