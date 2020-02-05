package com.loneliness.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Objects;

public class Card implements Entity{
    @Positive(message = "id MUST_BE_POSITIVE")
    private final int id;
    @JsonProperty("lear")
    @Length(min = 1,message = "lear MUST_BE_SET ")
    private final String lear; // масть
    @JsonProperty("par")
    @Length(min = 1,message = "par MUST_BE_SET ")
    private final String par;  // номинал
    @PositiveOrZero(message = "decksOfCardsID MUST_BE_MORE_THAN_NULL ")
    private final int decksOfCardsID;
    @PastOrPresent(message = "last_update MUST_BE_NOT_IN_FUTURE ")
    private final LocalDate lastUpdate;

    private Card(Builder builder) {
        this.id = builder.id;
        this.lear = builder.lear;
        this.par = builder.par;
        this.lastUpdate=builder.lastUpdate;
        this.decksOfCardsID=builder.decksOfCardsID;
    }

    @JsonCreator
    public Card(@JsonProperty("lear") final String lear,
                @JsonProperty("par") final String par) {
        Objects.requireNonNull(lear);
        Objects.requireNonNull(par);
        id = 0;
        this.lear = lear;
        this.par = par;
        decksOfCardsID = 0;
        lastUpdate = LocalDate.now();
    }


    public int getId() {
        return id;
    }

    public String getLear() {
        return lear;
    }

    public String getPar() {
        return par;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public int getDecksOfCardsID() {
        return decksOfCardsID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                lear.equals(card.lear) &&
                par.equals(card.par);
    }

    public boolean lightEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return lear.equals(card.lear) &&
                par.equals(card.par);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, lear, par);
        result = 31 * result ;
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", lear='" + lear + '\'' +
                ", par='" + par + '\'' +
                '}';
    }

    public static class Builder{
        private int id=0;
        private String lear="";
        private String par="";
        private LocalDate lastUpdate=LocalDate.now();
        private int decksOfCardsID=0;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLear(String lear) {
            this.lear = lear;
            return this;
        }

        public Builder setPar(String par) {
            this.par = par;
            return this;
        }

        public int getDecksOfCardsID() {
            return decksOfCardsID;
        }

        public Builder setDecksOfCardsID(int decksOfCardsID) {
            this.decksOfCardsID = decksOfCardsID;
            return this;
        }

        public int getId() {
            return id;
        }

        public String getLear() {
            return lear;
        }

        public String getPar() {
            return par;
        }

        public LocalDate getLastUpdate() {
            return lastUpdate;
        }

        public Builder setLastUpdate(LocalDate lastUpdate) {
            this.lastUpdate = lastUpdate;
            return this;
        }

        public Builder() {
        }

        public Builder(Card card) {
            this.id = card.id;
            this.lear = card.lear;
            this.par = card.par;
            this.decksOfCardsID=card.decksOfCardsID;
        }
        public Card build(){
            return new Card(this);
        }
    }

}
