package com.loneliness.parser;

import com.loneliness.Validation;
import com.loneliness.entity.HotelCharacteristic;
import com.loneliness.entity.TouristVouchers;
import com.loneliness.entity.TravelPackages;
import com.loneliness.entity.Type;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.events.Attribute;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

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
        private HotelCharacteristic.Builder hotelCharacteristicBuilder=HotelCharacteristic.newBuilder();
        private TravelPackages.Builder travelPackagesBuilder=TravelPackages.newBuilder();

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
                hotelCharacteristicBuilder.setId(attributes.getValue("hotelId"));
            } else if (qName.equalsIgnoreCase("type")) {
                type = true;
            } else if (qName.equalsIgnoreCase("country")) {
                country = true;
            } else if (qName.equalsIgnoreCase("numberOfDay")) {
                numberOfDay = true;
            } else if (qName.equalsIgnoreCase("feed")) {
                feed = true;
            } else if (qName.equalsIgnoreCase("other")) {
                other = true;
            } else if (qName.equalsIgnoreCase("price")) {
                price = true;
            } else if (qName.equalsIgnoreCase("numberOfStars")) {
                numberOfStars = true;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (type) {
                    travelPackagesBuilder.setType(Type.valueOf(information));
                    type = false;
                } else if (country) {
                    travelPackagesBuilder.setCountry(information);
                    country = false;
                } else if (numberOfDay) {
                    travelPackagesBuilder.setNumberOfDay(Integer.parseInt(information));
                    numberOfDay = false;
                } else if (price) {
                    travelPackagesBuilder.setPrice(information);
                    price = false;
                } else if (feed) {
                    hotelCharacteristicBuilder.setFeed(Boolean.parseBoolean(information));
                    feed = false;
                } else if (other) {
                    hotelCharacteristicBuilder.setOther(information);
                    other = false;
                } else if (numberOfStars) {
                    hotelCharacteristicBuilder.setNumberOfStars(Integer.parseInt(information));
                    numberOfStars = false;
                }
            }
        }


        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equalsIgnoreCase("TravelPackages")) {
                vouchers.add(travelPackagesBuilder.build());
                travelPackagesBuilder=TravelPackages.newBuilder();
            }
            else if(qName.equalsIgnoreCase("hotelCharacteristic")) {
               travelPackagesBuilder.setHotelCharacteristic(hotelCharacteristicBuilder.build());
                hotelCharacteristicBuilder=HotelCharacteristic.newBuilder();
            }
        }
    }
}
