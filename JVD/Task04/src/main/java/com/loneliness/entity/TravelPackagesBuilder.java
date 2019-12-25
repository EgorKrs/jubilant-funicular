package com.loneliness.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Objects;



    public class TravelPackagesBuilder {

        private String id;
        private Type type;
        private String country;
        private int numberOfDay;
        private HotelCharacteristicBuilder.HotelCharacteristic hotelCharacteristic;
        private BigDecimal price;

        public TravelPackagesBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public TravelPackagesBuilder setType(Type type) {
            this.type = type;
            return this;
        }

        public TravelPackagesBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public TravelPackagesBuilder setNumberOfDay(int numberOfDay) {
            this.numberOfDay = numberOfDay;
            return this;
        }

        public TravelPackagesBuilder setHotelCharacteristic(HotelCharacteristicBuilder.HotelCharacteristic hotelCharacteristic) {
            this.hotelCharacteristic = hotelCharacteristic;
            return this;
        }


        public TravelPackagesBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public TravelPackagesBuilder setPrice(String price) {
            this.price = new BigDecimal(price);
            return this;
        }

        public TravelPackages build() {
            return new TravelPackages(this);
        }

        @XmlType(propOrder = {"id", "type", "country", "numberOfDay", "hotelCharacteristic", "price"})
        @XmlRootElement
        public class TravelPackages {
            @XmlAttribute
            private String id;
            private Type type;
            private String country;
            private int numberOfDay;
            private HotelCharacteristicBuilder.HotelCharacteristic hotelCharacteristic;
            private BigDecimal price;

            public TravelPackages(TravelPackagesBuilder builder) {
                this.id = builder.id;
                this.type = builder.type;
                this.country = builder.country;
                this.numberOfDay = builder.numberOfDay;
                this.hotelCharacteristic = builder.hotelCharacteristic;
                this.price = builder.price;
            }

            public String getId() {
                return id;
            }


            public Type getType() {
                return type;
            }


            public String getCountry() {
                return country;
            }


            public int getNumberOfDay() {
                return numberOfDay;
            }


            public HotelCharacteristicBuilder.HotelCharacteristic getHotelCharacteristic() {
                return hotelCharacteristic;
            }


            public BigDecimal getPrice() {
                return price;
            }


            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TravelPackages that = (TravelPackages) o;
                return numberOfDay == that.numberOfDay &&
                        id.equals(that.id) &&
                        type == that.type &&
                        country.equals(that.country) &&
                        hotelCharacteristic.equals(that.hotelCharacteristic) &&
                        price.equals(that.price);
            }

            @Override
            public int hashCode() {
                return Objects.hash(id, type, country, numberOfDay, hotelCharacteristic, price);
            }

            @Override
            public String toString() {
                return "TravelPackages{" +
                        "id='" + id + '\'' +
                        ", type=" + type +
                        ", country='" + country + '\'' +
                        ", numberOfDay=" + numberOfDay +
                        ", hotelCharacteristic=" + hotelCharacteristic +
                        ", price=" + price +
                        '}';
            }
        }
    }

