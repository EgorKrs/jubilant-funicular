package com.loneliness.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Account {
    private final int id;
    private final int userID;
    private final String number;
    private final BigDecimal sumOfMoney;
    private final LocalDate lastUpdate;

    private Account(Builder builder) {
        this.id = builder.id;
        this.userID = builder.userID;
        this.number = builder.number;
        this.sumOfMoney = builder.sumOfMoney;
        this.lastUpdate = builder.lastUpdate;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getSumOfMoney() {
        return sumOfMoney;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                userID == account.userID &&
                number.equals(account.number) &&
                sumOfMoney.equals(account.sumOfMoney) &&
                lastUpdate.equals(account.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, number, sumOfMoney, lastUpdate);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userID=" + userID +
                ", number='" + number + '\'' +
                ", sumOfMoney=" + sumOfMoney +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public static class Builder{
        private int id=0;
        private int userID=0;
        private String number="";
        private BigDecimal sumOfMoney=new BigDecimal("0");
        private LocalDate lastUpdate=LocalDate.now();

        public Builder(Account account) {
            this.id = account.id;
            this.userID = account.userID;
            this.number = account.number;
            this.sumOfMoney = account.sumOfMoney;
            this.lastUpdate = account.lastUpdate;
        }
        public Builder(){}

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setSumOfMoney(BigDecimal sumOfMoney) {
            this.sumOfMoney = sumOfMoney;
            return this;
        }

        public Builder setLastUpdate(LocalDate lastUpdate) {
            this.lastUpdate = lastUpdate;
            return this;
        }
        public Account build(){
            return new Account(this);
        }

        public int getId() {
            return id;
        }

        public int getUserID() {
            return userID;
        }

        public String getNumber() {
            return number;
        }

        public BigDecimal getSumOfMoney() {
            return sumOfMoney;
        }

        public LocalDate getLastUpdate() {
            return lastUpdate;
        }
    }
}
