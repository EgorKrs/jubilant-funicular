package com.loneliness.parser;

import com.loneliness.entity.TouristVouchers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class XmlJaxbParser implements Parser{
    private Set<String> errors=new HashSet<>();

    public boolean marshall(String fileName,TouristVouchers object) {
        try( BufferedWriter writer=new BufferedWriter(new FileWriter(fileName))) {
            JAXBContext context = JAXBContext.newInstance(TouristVouchers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, writer);
            return true;
        } catch (JAXBException | IOException exception) {
           logger.catching(exception);
           return false;
        }
    }
    public TouristVouchers unMarshall(String fileName){
        try {

        JAXBContext context = JAXBContext.newInstance(TouristVouchers.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("Data\\data.xsd"));

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new ValidationHandler());
        Object o = unmarshaller.unmarshal(reader);

            return (TouristVouchers) o;
    }  catch (JAXBException | IOException | SAXException exception) {
            logger.catching(exception);
            return new TouristVouchers();
        }
    }
    class ValidationHandler implements ValidationEventHandler {
        @Override
        public boolean handleEvent(ValidationEvent event) {
            logger.info(event.getMessage());
            errors.add(event.getMessage());
            return true;
        }
    }

    public Set<String> getErrors() {
        return errors;
    }
}
