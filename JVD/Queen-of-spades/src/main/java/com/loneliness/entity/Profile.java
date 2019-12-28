package com.loneliness.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Profile implements Entity {
    @Positive(message = "id MUST_BE_POSITIVE")
    private final int id;
    @Positive(message = "userID MUST_BE_POSITIVE")
    private final int userID;
    @NotNull(message = "language MUST_BE_SET")
    private final Language language;
    @PositiveOrZero(message = "rating MUST_BE_POSITIVE")
    private final int rating;
    private final String telegram;
    private final String instagram;
    private final String about;
    @PastOrPresent(message = "last_update MUST_BE_NOT_IN_FUTURE")
    private final LocalDate lastUpdate;
    @PositiveOrZero(message = "numberOfVictories MUST_BE_POSITIVE")
    private final AtomicInteger numberOfVictories;
    @PositiveOrZero(message = "numberOfDefeats MUST_BE_POSITIVE")
    private final AtomicInteger numberOfDefeats;
    @PositiveOrZero(message = "numberOfGame MUST_BE_POSITIVE")
    private final AtomicInteger numberOfGame;

    private Profile(Builder builder) {
        this.id = builder.id;
        this.userID = builder.userID;
        this.language = builder.language;
        this.rating = builder.rating;
        this.telegram = builder.telegram;
        this.instagram = builder.instagram;
        this.about = builder.about;
        this.lastUpdate = builder.lastUpdate;
        this.numberOfDefeats=new AtomicInteger(builder.numberOfDefeats.get());
        this.numberOfVictories=new AtomicInteger(builder.numberOfVictories.get());
        this.numberOfGame=new AtomicInteger(builder.numberOfGame.get());
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

    public AtomicInteger getNumberOfVictories() {
        return numberOfVictories;
    }

    public AtomicInteger getNumberOfDefeats() {
        return numberOfDefeats;
    }

    public AtomicInteger getNumberOfGame() {
        return numberOfGame;
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
                lastUpdate.equals(profile.lastUpdate) &&
                numberOfVictories.equals(profile.numberOfVictories) &&
                numberOfDefeats.equals(profile.numberOfDefeats) &&
                numberOfGame.equals(profile.numberOfGame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, language, rating, telegram, instagram, about, lastUpdate, numberOfVictories, numberOfDefeats, numberOfGame);
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
                ", numberOfVictories=" + numberOfVictories +
                ", numberOfDefeats=" + numberOfDefeats +
                ", numberOfGame=" + numberOfGame +
                '}';
    }

    public static class Builder{
        private int id=0;
        private int userID=0;
        private Language language= Language.EN;
        private int rating=0;
        private String telegram="";
        private String instagram="";
        private String about="";
        private LocalDate lastUpdate=LocalDate.now();
        private AtomicInteger numberOfVictories=new AtomicInteger(0);
        private AtomicInteger numberOfDefeats=new AtomicInteger(0);
        private AtomicInteger numberOfGame=new AtomicInteger(0);

        public Builder(Profile profile) {
            this.id = profile.id;
            this.userID = profile.userID;
            this.language = profile.language;
            this.rating = profile.rating;
            this.telegram = profile.telegram;
            this.instagram = profile.instagram;
            this.about = profile.about;
            this.lastUpdate = profile.lastUpdate;
            this.numberOfDefeats=profile.numberOfDefeats;
            this.numberOfVictories=profile.numberOfVictories;
            this.numberOfGame=profile.numberOfGame;
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

        public Builder setNumberOfVictories(AtomicInteger numberOfVictories) {
            this.numberOfVictories = numberOfVictories;
            return this;
        }

        public Builder setNumberOfDefeats(AtomicInteger numberOfDefeats) {
            this.numberOfDefeats = numberOfDefeats;
            return this;
        }

        public Builder setNumberOfGame(int numberOfGame) {
            this.numberOfGame.set( numberOfGame);
            return this;
        }
        public Builder setNumberOfVictories(int numberOfVictories) {
            this.numberOfVictories.set(numberOfVictories);
            return this;
        }

        public Builder setNumberOfDefeats(int numberOfDefeats) {
            this.numberOfDefeats.set(numberOfDefeats);
            return this;
        }

        public Builder setNumberOfGame(AtomicInteger numberOfGame) {
            this.numberOfGame = numberOfGame;
            return this;
        }

        public AtomicInteger getNumberOfVictories() {
            return numberOfVictories;
        }

        public AtomicInteger getNumberOfDefeats() {
            return numberOfDefeats;
        }

        public AtomicInteger getNumberOfGame() {
            return numberOfGame;
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
