package com.loneliness.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Picture implements Entity {
    @Positive(message = "id MUST_BE_POSITIVE")
    private final int id;
    private final byte [] byteImage;
    @Length(min = 1,message = "name MUST_HAVE_MORE_SYMBOLS")
    private final String name;
    @PastOrPresent(message = "last_update MUST_BE_NOT_IN_FUTURE")
    private final LocalDate lastUpdate;

    private Picture(Builder builder) {
        this.id = builder.id;
        this.byteImage = builder.byteImage;
        this.name = builder.name;
        this.lastUpdate = builder.lastUpdate;
    }

    public int getId() {
        return id;
    }

    public byte[] getByteImage() {
        return byteImage;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return id == picture.id &&
                Arrays.equals(byteImage, picture.byteImage) &&
                name.equals(picture.name) &&
                lastUpdate.equals(picture.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, lastUpdate);
        result = 31 * result + Arrays.hashCode(byteImage);
        return result;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", byteImage=" + Arrays.toString(byteImage) +
                ", name='" + name + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static class Builder{
        private int id=0;
        private byte [] byteImage=new byte[1];
        private String name="";
        private LocalDate lastUpdate=LocalDate.now();

        public Builder(Picture picture) {
            this.id = picture.id;
            this.byteImage = picture.byteImage;
            this.name = picture.name;
            this.lastUpdate = picture.lastUpdate;
        }

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setByteImage(byte[] byteImage) {
            this.byteImage = byteImage;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLastUpdate(LocalDate lastUpdate) {
            this.lastUpdate = lastUpdate;
            return this;
        }

        public int getId() {
            return id;
        }

        public byte[] getByteImage() {
            return byteImage;
        }

        public String getName() {
            return name;
        }

        public LocalDate getLastUpdate() {
            return lastUpdate;
        }

        public Picture build(){
            return new Picture(this);
        }
    }
}
