package com.loneliness.entity;

import javax.validation.constraints.Positive;
import java.util.Objects;

public class PicturesInNews implements Entity {
    @Positive(message = "id MUST_BE_POSITIVE")
    private final int id;
    @Positive(message = "newsID MUST_BE_POSITIVE")
    private final int newsID;
    @Positive(message = "pictureID MUST_BE_POSITIVE")
    private final int pictureID;

    private PicturesInNews(Builder builder) {
        this.id = builder.id;
        this.newsID = builder.newsID;
        this.pictureID = builder.pictureID;
    }

    public int getId() {
        return id;
    }

    public int getNewsID() {
        return newsID;
    }

    public int getPictureID() {
        return pictureID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PicturesInNews that = (PicturesInNews) o;
        return id == that.id &&
                newsID == that.newsID &&
                pictureID == that.pictureID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newsID, pictureID);
    }

    @Override
    public String toString() {
        return "PicturesInNews{" +
                "id=" + id +
                ", newsID=" + newsID +
                ", pictureID=" + pictureID +
                '}';
    }

    public static class Builder{
        private int id;
        private int newsID;
        private int pictureID;

        public Builder(PicturesInNews picturesInNews) {
            this.id = picturesInNews.id;
            this.newsID = picturesInNews.newsID;
            this.pictureID = picturesInNews.pictureID;
        }

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setNewsID(int newsID) {
            this.newsID = newsID;
            return this;
        }

        public Builder setPictureID(int pictureID) {
            this.pictureID = pictureID;
            return this;
        }

        public int getId() {
            return id;
        }

        public int getNewsID() {
            return newsID;
        }

        public int getPictureID() {
            return pictureID;
        }
        public PicturesInNews build(){
            return new PicturesInNews(this);
        }
    }
}
