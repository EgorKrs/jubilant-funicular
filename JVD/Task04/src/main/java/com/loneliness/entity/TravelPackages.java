package com.loneliness.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Objects;

@XmlType(propOrder = { "id", "type","country","numberOfDay","hotelCharacteristic","price" })
@XmlRootElement
public class TravelPackages {

    private String id;
    private Type type;
    private String country;
    private int numberOfDay;
    private HotelCharacteristic hotelCharacteristic;
    private BigDecimal price;

    public String getId(){
        return id;
    }
    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(int numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    public HotelCharacteristic getHotelCharacteristic() {
        return hotelCharacteristic;
    }

    public void setHotelCharacteristic(HotelCharacteristic hotelCharacteristic) {
        this.hotelCharacteristic = hotelCharacteristic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setPrice(String price) {
        this.price = new BigDecimal(price);
    }

    public static Builder newBuilder(){
        return new TravelPackages().new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelPackages that = (TravelPackages) o;
        return id.equals(that.id) &&
                numberOfDay == that.numberOfDay &&
                that.price.equals( price) &&
                type == that.type &&
                country.equals(that.country) &&
                hotelCharacteristic.equals(that.hotelCharacteristic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, country, numberOfDay, hotelCharacteristic, price);
    }

    @Override
    public String toString() {
        return "TravelPackages{" +
                "  \nid='" + id + '\'' +
                ", \ntype=" + type +
                ", \ncountry='" + country + '\'' +
                ", \nnumberOfDay=" + numberOfDay +
                ", \nhotelCharacteristic=" + hotelCharacteristic +
                ", \nprice=" + price +
                "}\n";
    }

    public class Builder{
        public Builder setId(String id) {
            TravelPackages.this.id = id;
            return this;
        }

        public Builder setType(Type type) {
            TravelPackages.this.type = type;
            return this;
        }

        public Builder setCountry(String country) {
            TravelPackages.this.country = country;
            return this;
        }

        public Builder setNumberOfDay(int numberOfDay) {
            TravelPackages.this.numberOfDay = numberOfDay;
            return this;
        }

        public Builder setHotelCharacteristic(HotelCharacteristic hotelCharacteristic) {
            TravelPackages.this.hotelCharacteristic = hotelCharacteristic;
            return this;
        }


        public Builder setPrice(BigDecimal price) {
            TravelPackages.this.price = price;
            return this;
        }
        public Builder setPrice(String price) {
            TravelPackages.this.price = new BigDecimal(price);
            return this;
        }
        public TravelPackages build(){
            return TravelPackages.this;
        }
    }
}
