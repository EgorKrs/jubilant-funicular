import com.github.javafaker.Faker;
import com.loneliness.entity.HotelCharacteristicBuilder;
import com.loneliness.entity.TouristVouchers;
import com.loneliness.entity.TravelPackagesBuilder;
import com.loneliness.entity.Type;
import com.loneliness.parser.XmlDomParser;
import com.loneliness.parser.XmlJaxbParser;
import com.loneliness.parser.XmlSAXParser;
import com.loneliness.parser.XmlStAXParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class XmlParserTest {
    private static Faker faker;
    private static XmlJaxbParser xmlJaxbParser;
    private static XmlDomParser xmlDomParser;
    private static XmlStAXParser xmlStAXParser;
    private static XmlSAXParser xmlSAXParser;
    @BeforeClass
    public static void init(){
        faker=new Faker(new Locale("ru"));
        xmlJaxbParser =new XmlJaxbParser();
        xmlDomParser=new XmlDomParser();
        xmlStAXParser=new XmlStAXParser();
        xmlSAXParser=new XmlSAXParser();
    }
    private TravelPackagesBuilder.TravelPackages createValid(){
        HotelCharacteristicBuilder.HotelCharacteristic hotelCharacteristic= new HotelCharacteristicBuilder(faker.idNumber().valid()).
                setFeed(faker.random().nextBoolean()).setNumberOfStars(faker.number().numberBetween(1,5)).setOther(faker.
                commerce().productName()).build();
        TravelPackagesBuilder builder= new TravelPackagesBuilder().setCountry(faker.country().name()).
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
    private TravelPackagesBuilder.TravelPackages createInValid(){
        HotelCharacteristicBuilder.HotelCharacteristic hotelCharacteristic= new HotelCharacteristicBuilder(faker.idNumber().invalid()).setFeed(faker.random().nextBoolean()).
                setNumberOfStars(faker.number().numberBetween(5,10)).setOther(faker.commerce().productName()).build();
        TravelPackagesBuilder builder= new TravelPackagesBuilder().setCountry(faker.country().name()).
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

//    @Test
//    public void ParserJaxbTest(){
//        TouristVouchers startVouchers=new TouristVouchers();
//        for (int i = 0; i < 5; i++) {
//            startVouchers.vouchers.add(createValid());
//        }
//        xmlJaxbParser.marshall("Data\\data.xml",startVouchers);
//        Assert.assertEquals(startVouchers, xmlJaxbParser.unMarshall("Data\\data.xml"));
//    }
//    @Test
//    public void validationJaxbParserTest(){
//        TouristVouchers invalidVouchers=new TouristVouchers();
//        for (int i = 0; i < 5; i++) {
//            invalidVouchers.vouchers.add(createInValid());
//        }
//        xmlJaxbParser.marshall("Data\\data.xml",invalidVouchers);
//        xmlJaxbParser.unMarshall("Data\\data.xml");
//        Assert.assertFalse(xmlJaxbParser.getErrors().isEmpty());
//    }

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
    public void StAXParserTest(){
        TouristVouchers vouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            vouchers.vouchers.add(createValid());
        }
        xmlStAXParser.marshall("Data\\data.xml",vouchers);
        Assert.assertEquals(vouchers,xmlStAXParser.unMarshall("Data\\data.xml"));
    }
    @Test
    public void SAXParserTest(){
        TouristVouchers vouchers=new TouristVouchers();
        for (int i = 0; i < 5; i++) {
            vouchers.vouchers.add(createValid());
        }
        xmlStAXParser.marshall("Data\\data.xml",vouchers);
        Assert.assertEquals(vouchers,xmlSAXParser.unMarshall("Data\\data.xml"));
    }


}
