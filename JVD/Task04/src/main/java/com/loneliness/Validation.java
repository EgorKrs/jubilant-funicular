package com.loneliness;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;


import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Validation {
    private Set<String> errors=new HashSet<>();
    private Logger logger = LogManager.getLogger();
    public boolean validate(String xmlFile) throws SAXException, IOException {

        // 1. Поиск и создание экземпляра фабрики для языка XML Schema
        SchemaFactory factory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        // 2. Компиляция схемы
        // Схема загружается в объект типа java.io.File, но вы также можете использовать
        // классы java.net.URL и javax.xml.transform.Source
        File schemaLocation = new File("Data\\data.xsd");
        Schema schema = factory.newSchema(schemaLocation);

        // 3. Создание валидатора для схемы
        Validator validator = schema.newValidator();

        // 4. Разбор проверяемого документа
        Source source = new StreamSource(xmlFile);

        // 5. Валидация документа
        try {
            validator.validate(source);
            logger.info(xmlFile+" is valid");
            return true;
        }
        catch (SAXException ex) {
            logger.info(xmlFile+" is not valid");
            logger.info(ex.getMessage());
            errors.add(ex.getMessage());
            return false;
        }

    }

}
