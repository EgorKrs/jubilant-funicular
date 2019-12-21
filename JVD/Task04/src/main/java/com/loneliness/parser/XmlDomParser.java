package com.loneliness.parser;

import com.loneliness.Validation;
import com.loneliness.entity.HotelCharacteristic;
import com.loneliness.entity.TouristVouchers;
import com.loneliness.entity.TravelPackages;
import com.loneliness.entity.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.w3c.dom.Document;

public class XmlDomParser implements Parser{
    private Logger logger = LogManager.getLogger();
    public TouristVouchers unMarshall(String fileName){
        Validation validation =new Validation();

        TouristVouchers touristVouchers=new TouristVouchers();
        try {
            if(validation.validate(fileName)) {
                File inputFile = new File(fileName);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();
                NodeList travelPackages = doc.getElementsByTagName("TravelPackages");

                for (int temp = 0; temp < travelPackages.getLength(); temp++) {
                    Element element = (Element) travelPackages.item(temp);
                    Node node = travelPackages.item(temp);
                    TravelPackages.Builder builder = TravelPackages.newBuilder();
                    builder.setType(Type.valueOf(element.getElementsByTagName("type").item(0).getTextContent()));
                    builder.setPrice(element.getElementsByTagName("price").item(0).getTextContent());
                    builder.setCountry(element.getElementsByTagName("country").item(0).getTextContent());
                    builder.setNumberOfDay(Integer.parseInt(element.getElementsByTagName("numberOfDay").item(0).
                            getTextContent()));
                    builder.setId(node.getAttributes().getNamedItem("id").getNodeValue());

                    Element hotelCharacteristicElem = (Element) element.getElementsByTagName("hotelCharacteristic").item(0);
                    HotelCharacteristic.Builder hotelBuilder = HotelCharacteristic.newBuilder();
                    hotelBuilder.setFeed(Boolean.parseBoolean(hotelCharacteristicElem.getElementsByTagName("feed").
                            item(0).getTextContent()));
                    hotelBuilder.setOther(hotelCharacteristicElem.getElementsByTagName("other").item(0).
                            getTextContent());
                    hotelBuilder.setNumberOfStars(Integer.parseInt(hotelCharacteristicElem.
                            getElementsByTagName("numberOfStars").item(0).getTextContent()));
                    hotelBuilder.setId(hotelCharacteristicElem.getAttribute("hotelId"));
                    builder.setHotelCharacteristic(hotelBuilder.build());
                    touristVouchers.vouchers.add(builder.build());

                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.catching(e);
        }
        return touristVouchers;
    }

    public boolean marshall(String fileName, TouristVouchers vouchers){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try ( FileOutputStream fos = new FileOutputStream(fileName)){
            builder = factory.newDocumentBuilder();
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            Document document = builder.newDocument();

            Element root=document.createElement("touristVouchers");
            document.appendChild(root);
            Element voucher=document.createElement("vouchers");
            root.appendChild(voucher);
            for (TravelPackages packages :
                    vouchers.vouchers) {

                voucher.appendChild(createNode(document,packages));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
            return true;
        } catch (TransformerException | ParserConfigurationException | IOException e) {
            logger.catching(e);
        }
        return false;
    }


    private  Node createNode(Document doc, TravelPackages packages) {
        Element travelPackage = doc.createElement("TravelPackages");
        Element hotelCharacteristic=doc.createElement("hotelCharacteristic");

        travelPackage.setAttribute("id", packages.getId());
        hotelCharacteristic.setAttribute("hotelId",packages.getHotelCharacteristic().getHotelId());

        hotelCharacteristic.appendChild(getElement(doc,hotelCharacteristic,"numberOfStars",
                String.valueOf(packages.getHotelCharacteristic().getNumberOfStars())));
        hotelCharacteristic.appendChild(getElement(doc,hotelCharacteristic,"feed",String.valueOf(packages.
                getHotelCharacteristic().isFeed())));
        hotelCharacteristic.appendChild(getElement(doc,hotelCharacteristic,"other",String.valueOf(packages.
                getHotelCharacteristic().getOther())));

        travelPackage.appendChild(getElement(doc, travelPackage, "type", packages.getType().
                toString()));
        travelPackage.appendChild(getElement(doc, travelPackage, "country", packages.getCountry()));
        travelPackage.appendChild(getElement(doc, travelPackage, "numberOfDay", String.valueOf(
                packages.getNumberOfDay())));
        travelPackage.appendChild(hotelCharacteristic);
        travelPackage.appendChild(getElement(doc, travelPackage, "price", packages.getPrice().
                toString()));
        return travelPackage;
    }


    // утилитный метод для создание нового узла XML-файла
    private  Node getElement(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
