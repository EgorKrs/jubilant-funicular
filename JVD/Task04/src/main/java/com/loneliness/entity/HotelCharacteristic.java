package com.loneliness.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;
@XmlRootElement
@XmlType(propOrder = { "hotelId", "numberOfStars","feed","other" })
public class HotelCharacteristic {
    @XmlAttribute
    private String hotelId;
    private int numberOfStars;
    private boolean feed;
    private String other;

    private HotelCharacteristic(){

    }
    public static Builder newBuilder() {
        return new HotelCharacteristic().new Builder();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelCharacteristic that = (HotelCharacteristic) o;
        return hotelId.equals(that.hotelId) &&
                numberOfStars == that.numberOfStars &&
                feed == that.feed &&
                other.equals(that.other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, numberOfStars, feed, other);
    }

    public String getHotelId() {
        return hotelId;
    }



    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public boolean isFeed() {
        return feed;
    }

    public void setFeed(boolean feed) {
        this.feed = feed;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "HotelCharacteristic{" +
                " \nhotelId='" + hotelId + '\'' +
                ",\nnumberOfStars=" + numberOfStars +
                ",\nfeed=" + feed +
                ",\nother='" + other + '\'' +
                "}\n";
    }

    public class Builder{
        private Builder(){

        }


        public Builder setId(String id) {
            HotelCharacteristic.this.hotelId = id;
            return this;
        }

        public Builder setNumberOfStars(int numberOfStars) {
            HotelCharacteristic.this.numberOfStars = numberOfStars;
            return this;
        }

        public Builder setFeed(boolean feed) {
            HotelCharacteristic.this.feed = feed;
            return this;
        }

        public Builder setOther(String other) {
            HotelCharacteristic.this.other = other;
            return this;
        }
        public HotelCharacteristic build(){
            return HotelCharacteristic.this;
        }
    }
}
