package com.loneliness.parser;

import com.loneliness.Validation;
import com.loneliness.entity.HotelCharacteristicBuilder;
import com.loneliness.entity.TouristVouchers;
import com.loneliness.entity.TravelPackagesBuilder;
import com.loneliness.entity.Type;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XmlSAXParser implements Parser{
    private Validation validation = new Validation();
    @Override
    public TouristVouchers unMarshall(String fileName) {
        XMLHandler handler = new XMLHandler();
            try {
                if (validation.validate(fileName)) {

                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();


                    parser.parse(new File(fileName), handler);
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
               logger.catching(e);
            }
            return handler.getVouchers();

    }


    @Override
    public boolean marshall(String fileName, TouristVouchers vouchers) {
        return false;
    }



    private static class XMLHandler extends DefaultHandler {
        private TouristVouchers vouchers=new TouristVouchers();
        private HotelCharacteristicBuilder hotelCharacteristicBuilder;
        private TravelPackagesBuilder travelPackagesBuilder=new TravelPackagesBuilder();
        private String information;
        private boolean type=false;
        private boolean country=false;
        private boolean numberOfDay=false;
        private boolean price = false;
        private boolean numberOfStars = false;
        private boolean feed = false;
        private boolean other = false;

        TouristVouchers getVouchers() {
            return vouchers;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equalsIgnoreCase("TravelPackages")) {
                travelPackagesBuilder.setId(attributes.getValue("id"));
            } else if (qName.equalsIgnoreCase("hotelCharacteristic")) {
                hotelCharacteristicBuilder = new HotelCharacteristicBuilder(attributes.getValue("hotelId"));
            }

        }

        @Override
        public void characters(char[] ch, int start, int length) {
            information = new String(ch, start, length);

            information = information.replace("\n", "").trim();
        }


        @Override
        public void endElement(String uri, String localName, String qName) {
            if (!information.isEmpty()) {
                if (qName.equalsIgnoreCase("type")) {
                    travelPackagesBuilder.setType(Type.valueOf(information));
                } else if (qName.equalsIgnoreCase("country")) {
                    travelPackagesBuilder.setCountry(information);
                } else if (qName.equalsIgnoreCase("numberOfDay")) {
                    travelPackagesBuilder.setNumberOfDay(Integer.parseInt(information));
                } else if (qName.equalsIgnoreCase("feed")) {
                    hotelCharacteristicBuilder.setFeed(Boolean.parseBoolean(information));
                } else if (qName.equalsIgnoreCase("other")) {
                    hotelCharacteristicBuilder.setOther(information);
                } else if (qName.equalsIgnoreCase("price")) {
                    travelPackagesBuilder.setPrice(information);
                } else if (qName.equalsIgnoreCase("numberOfStars")) {
                    hotelCharacteristicBuilder.setNumberOfStars(Integer.parseInt(information));
                }
            }
            if (qName.equalsIgnoreCase("TravelPackages")) {
                vouchers.add(travelPackagesBuilder.build());
                travelPackagesBuilder= new TravelPackagesBuilder();
            }
            else if(qName.equalsIgnoreCase("hotelCharacteristic")) {
               travelPackagesBuilder.setHotelCharacteristic(hotelCharacteristicBuilder.build());
              // hotelCharacteristicBuilder= new HotelCharacteristicBuilder.newBuilder();
            }

        }
    }
}
