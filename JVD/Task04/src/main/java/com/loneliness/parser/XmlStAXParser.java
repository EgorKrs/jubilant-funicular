package com.loneliness.parser;

import com.loneliness.Validation;
import com.loneliness.entity.HotelCharacteristic;
import com.loneliness.entity.TouristVouchers;
import com.loneliness.entity.TravelPackages;
import com.loneliness.entity.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.Iterator;

public class XmlStAXParser implements Parser{
    private Logger logger = LogManager.getLogger();

    public TouristVouchers unMarshall(String fileName) {
        boolean type = false;
        boolean country = false;
        boolean numberOfDay = false;
        boolean price = false;
        boolean numberOfStars = false;
        boolean feed = false;
        boolean other = false;
        Validation validation = new Validation();
        TouristVouchers vouchers = new TouristVouchers();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            if (validation.validate(fileName)) {
                XMLInputFactory factory = XMLInputFactory.newInstance();
                XMLEventReader eventReader = factory.createXMLEventReader(reader);

                TravelPackages.Builder travelPackagesBuilder = TravelPackages.newBuilder();
                HotelCharacteristic.Builder hotelCharacteristicBuilder = HotelCharacteristic.newBuilder();

                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();

                    switch (event.getEventType()) {
                        case XMLStreamConstants.START_ELEMENT:
                            StartElement startElement = event.asStartElement();
                            String qName = startElement.getName().getLocalPart();

                            if (qName.equalsIgnoreCase("TravelPackages")) {
                                Iterator<Attribute> attributes = startElement.getAttributes();
                                travelPackagesBuilder.setId(attributes.next().getValue());
                            } else if (qName.equalsIgnoreCase("hotelCharacteristic")) {
                                Iterator<Attribute> attributes = startElement.getAttributes();
                                hotelCharacteristicBuilder.setId(attributes.next().getValue());
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
                            break;

                        case XMLStreamConstants.CHARACTERS:
                            Characters characters = event.asCharacters();
                            if (type) {
                                travelPackagesBuilder.setType(Type.valueOf(characters.getData()));
                                type = false;
                            } else if (country) {
                                travelPackagesBuilder.setCountry(characters.getData());
                                country = false;
                            } else if (numberOfDay) {
                                travelPackagesBuilder.setNumberOfDay(Integer.parseInt(characters.getData()));
                                numberOfDay = false;
                            } else if (price) {
                                travelPackagesBuilder.setPrice(characters.getData());
                                price = false;
                            } else if (feed) {
                                hotelCharacteristicBuilder.setFeed(Boolean.parseBoolean(characters.getData()));
                                feed = false;
                            } else if (other) {
                                hotelCharacteristicBuilder.setOther(characters.getData());
                                other = false;
                            } else if (numberOfStars) {
                                hotelCharacteristicBuilder.setNumberOfStars(Integer.parseInt(characters.getData()));
                                numberOfStars = false;
                            }
                            break;

                        case XMLStreamConstants.END_ELEMENT:
                            EndElement endElement = event.asEndElement();
                            if (endElement.getName().getLocalPart().equalsIgnoreCase("hotelCharacteristic")) {
                                travelPackagesBuilder.setHotelCharacteristic(hotelCharacteristicBuilder.build());
                                hotelCharacteristicBuilder = HotelCharacteristic.newBuilder();

                            } else if (endElement.getName().getLocalPart().equalsIgnoreCase("TravelPackages")) {
                                vouchers.add(travelPackagesBuilder.build());
                                travelPackagesBuilder = TravelPackages.newBuilder();
                            }
                            break;
                    }
                }
            }
        } catch (SAXException | IOException | XMLStreamException e) {
            logger.catching(e);
        }
        return vouchers;
    }

    public boolean marshall(String fileName,TouristVouchers vouchers) {
        try (BufferedWriter writer =new BufferedWriter(new FileWriter(fileName))){

            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(writer);

            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeStartElement("touristVouchers");

            xMLStreamWriter.writeStartElement("vouchers");
            for (TravelPackages packages :
                    vouchers.getVouchers()) {
               write(packages,xMLStreamWriter);

            }
            xMLStreamWriter.writeEndDocument();

            xMLStreamWriter.flush();
            xMLStreamWriter.close();
            return true;

        } catch (XMLStreamException | IOException e) {
           logger.catching(e);
        }
        return false;
    }

    private void write(TravelPackages packages,XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        xMLStreamWriter.writeStartElement("TravelPackages");
        xMLStreamWriter.writeAttribute("id", packages.getId());
        writeElem("type",packages.getType().toString(),xMLStreamWriter);
        writeElem("country",packages.getCountry(),xMLStreamWriter);
        writeElem("numberOfDay",String.valueOf(packages.getNumberOfDay()),xMLStreamWriter);
        write(packages.getHotelCharacteristic(),xMLStreamWriter);
        writeElem("price",packages.getPrice().toString(),xMLStreamWriter);
        xMLStreamWriter.writeEndElement();
    }
    private void write(HotelCharacteristic hotelCharacteristic,XMLStreamWriter xMLStreamWriter)throws XMLStreamException{
        xMLStreamWriter.writeStartElement("hotelCharacteristic");
        xMLStreamWriter.writeAttribute("hotelId", hotelCharacteristic.getHotelId());
        writeElem("numberOfStars",String.valueOf(hotelCharacteristic.getNumberOfStars()),xMLStreamWriter);
        writeElem("feed",String.valueOf(hotelCharacteristic.isFeed()),xMLStreamWriter);
        writeElem("other",(hotelCharacteristic.getOther()),xMLStreamWriter);
        xMLStreamWriter.writeEndElement();
    }
    private void writeElem(String name,String value,XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        xMLStreamWriter.writeStartElement(name);
        xMLStreamWriter.writeCharacters(value);
        xMLStreamWriter.writeEndElement();
    }
}
