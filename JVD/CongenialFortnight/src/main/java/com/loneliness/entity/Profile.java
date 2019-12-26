package com.loneliness.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Profile {
    private final int id;
    private final int userID;
    private final Language language;
    private final int rating;
    private final String telegram;
    private final String instagram;
    private final String about;
    private final LocalDate lastUpdate;

    private Profile(Builder builder) {
        this.id = builder.id;
        this.userID = builder.userID;
        this.language = builder.language;
        this.rating = builder.rating;
        this.telegram = builder.telegram;
        this.instagram = builder.instagram;
        this.about = builder.about;
        this.lastUpdate = builder.lastUpdate;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public Language getLanguage() {
        return language;
    }

    public int getRating() {
        return rating;
    }

    public String getTelegram() {
        return telegram;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getAbout() {
        return about;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id &&
                userID == profile.userID &&
                rating == profile.rating &&
                language == profile.language &&
                telegram.equals(profile.telegram) &&
                instagram.equals(profile.instagram) &&
                about.equals(profile.about) &&
                lastUpdate.equals(profile.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, language, rating, telegram, instagram, about, lastUpdate);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userID=" + userID +
                ", language=" + language +
                ", rating=" + rating +
                ", telegram='" + telegram + '\'' +
                ", instagram='" + instagram + '\'' +
                ", about='" + about + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static class Builder{
        private int id=0;
        private int userID=0;
        private Language language=Language.EN;
        private int rating=0;
        private String telegram="";
        private String instagram="";
        private String about="";
        private LocalDate lastUpdate=LocalDate.now();

        public Builder(Profile profile) {
            this.id = profile.id;
            this.userID = profile.userID;
            this.language = profile.language;
            this.rating = profile.rating;
            this.telegram = profile.telegram;
            this.instagram = profile.instagram;
            this.about = profile.about;
            this.lastUpdate = profile.lastUpdate;
        }

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setLanguage(Language language) {
            this.language = language;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setTelegram(String telegram) {
            this.telegram = telegram;
            return this;
        }

        public Builder setInstagram(String instagram) {
            this.instagram = instagram;
            return this;
        }

        public Builder setAbout(String about) {
            this.about = about;
            return this;
        }

        public Builder setLastUpdate(LocalDate lastUpdate) {
            this.lastUpdate = lastUpdate;
            return this;
        }

        public int getId() {
            return id;
        }

        public int getUserID() {
            return userID;
        }

        public Language getLanguage() {
            return language;
        }

        public int getRating() {
            return rating;
        }

        public String getTelegram() {
            return telegram;
        }

        public String getInstagram() {
            return instagram;
        }

        public String getAbout() {
            return about;
        }

        public LocalDate getLastUpdate() {
            return lastUpdate;
        }
        public Profile build(){
            return new Profile(this);
        }
    }
}
