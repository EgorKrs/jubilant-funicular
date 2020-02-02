package com.loneliness.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    private final int rating;
    private final String about;
    @PastOrPresent(message = "last_update MUST_BE_NOT_IN_FUTURE")
    private final LocalDate lastUpdate;
    @NotNull(message = "score MUST_BE_SET")
    private final BigDecimal score;
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
        this.score = builder.score;
        this.about = builder.about;
        this.lastUpdate = builder.lastUpdate;
        this.numberOfDefeats=new AtomicInteger(builder.numberOfDefeats.get());
        this.numberOfVictories=new AtomicInteger(builder.numberOfVictories.get());
        this.numberOfGame=new AtomicInteger(builder.numberOfGame.get());
    }

    public BigDecimal getScore() {
        return score;
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
                about.equals(profile.about) &&
                lastUpdate.equals(profile.lastUpdate) &&
                numberOfVictories.equals(profile.numberOfVictories) &&
                numberOfDefeats.equals(profile.numberOfDefeats) &&
                numberOfGame.equals(profile.numberOfGame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, language, rating, about, lastUpdate, numberOfVictories, numberOfDefeats, numberOfGame);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userID=" + userID +
                ", language=" + language +
                ", rating=" + rating +
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
        private BigDecimal score = new BigDecimal("0");
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

        public Builder addOneVictory() {
            this.numberOfVictories.incrementAndGet();
            this.numberOfGame.incrementAndGet();
            return this;
        }

        public Builder addOneDefeat() {
            this.numberOfDefeats.incrementAndGet();
            this.numberOfGame.incrementAndGet();
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

        public Builder addScore(int delta) {
            this.score = new BigDecimal(String.valueOf(delta));
            return this;
        }

        public Builder addScore(BigDecimal delta) {
            this.score = this.score.add(delta);
            return this;
        }

        public Builder minusScore(int delta) {
            BigDecimal deltaScore = new BigDecimal(String.valueOf(delta));
            this.score = this.score.min(deltaScore);
            return this;
        }

        public Builder minusScore(BigDecimal delta) {
            this.score = this.score.min(delta);
            return this;
        }

        public Builder addRating(int delta) {
            this.rating += delta;
            return this;
        }


        public String getAbout() {
            return about;
        }

        public LocalDate getLastUpdate() {
            return lastUpdate;
        }

        public BigDecimal getScore() {
            return score;
        }

        public void setScore(BigDecimal score) {
            this.score = score;
        }

        public Builder setScore(int score) {
            this.score = new BigDecimal(String.valueOf(score));
            return this;
        }

        public Profile build(){
            return new Profile(this);
        }
    }
}
