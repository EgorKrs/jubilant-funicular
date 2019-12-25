package com.loneliness.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;


    public  class HotelCharacteristicBuilder {
        private final String hotelId;
        private int numberOfStars = 1;
        private boolean feed = false;
        private String other = "no";

        public HotelCharacteristicBuilder(String hotelId) {
            this.hotelId = hotelId;
        }

        public HotelCharacteristicBuilder setNumberOfStars(int numberOfStars) {
            this.numberOfStars = numberOfStars;
            return this;
        }

        public HotelCharacteristicBuilder setFeed(boolean feed) {
            this.feed = feed;
            return this;
        }

        public HotelCharacteristicBuilder setOther(String other) {
            this.other = other;
            return this;
        }

        public HotelCharacteristic build() {
            return new HotelCharacteristic(this);
        }


        @XmlRootElement
        @XmlType(propOrder = {"hotelId", "numberOfStars", "feed", "other"})
        public static class HotelCharacteristic {
            @XmlAttribute
            private String hotelId;
            private int numberOfStars;
            private boolean feed;
            private String other;

            private HotelCharacteristic(HotelCharacteristicBuilder builder) {
                this.hotelId = builder.hotelId;
                this.numberOfStars = builder.numberOfStars;
                this.feed = builder.feed;
                this.other = builder.other;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                HotelCharacteristic that = (HotelCharacteristic) o;
                return numberOfStars == that.numberOfStars &&
                        feed == that.feed &&
                        hotelId.equals(that.hotelId) &&
                        other.equals(that.other);
            }

            @Override
            public int hashCode() {
                return Objects.hash(hotelId, numberOfStars, feed, other);
            }

            @Override
            public String toString() {
                return "HotelCharacteristic{" +
                        "hotelId='" + hotelId + '\'' +
                        ", numberOfStars=" + numberOfStars +
                        ", feed=" + feed +
                        ", other='" + other + '\'' +
                        '}';
            }

            public String getHotelId() {
                return hotelId;
            }

            public int getNumberOfStars() {
                return numberOfStars;
            }

            public boolean isFeed() {
                return feed;
            }

            public String getOther() {
                return other;
            }
        }
    }

