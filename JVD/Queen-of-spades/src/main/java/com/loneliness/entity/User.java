package com.loneliness.entity;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

public class User {
    public enum Type{
        ADMIN,GAMER
    }
    private final int id;
    private final String login;
    private final String password;
    private final Type type;
    private final LocalDate lastUpdate;
    private final LocalDate createDate;
    private final int avatarId;

    private User(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.type = builder.type;
        this.lastUpdate = builder.lastUpdate;
        this.createDate = builder.createDate;
        this.avatarId = builder.avatarId;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Type getType() {
        return type;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public int getAvatarId() {
        return avatarId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                avatarId == user.avatarId &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                type == user.type &&
                lastUpdate.equals(user.lastUpdate) &&
                createDate.equals(user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, type, lastUpdate, createDate, avatarId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", lastUpdate=" + lastUpdate +
                ", createDate=" + createDate +
                ", avatarId=" + avatarId +
                '}';
    }

    public static class Builder{
        private int id=0;
        private String login="";
        private String password="";
        private Type type=Type.GAMER;
        private LocalDate lastUpdate=LocalDate.now();
        private LocalDate createDate=LocalDate.now();
        private int avatarId=0;

        public Builder (User user){
            this.id = user.id;
            this.login = user.login;
            this.password = user.password;
            this.type = user.type;
            this.lastUpdate = user.lastUpdate;
            this.createDate = user.createDate;
            this.avatarId = user.avatarId;
        }
        public Builder(){}

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setLastUpdate(LocalDate lastUpdate) {
            this.lastUpdate = lastUpdate;
            return this;
        }

        public Builder setCreateDate(LocalDate createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder setAvatarId(int avatarId) {
            this.avatarId = avatarId;
            return this;
        }

        public int getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public Type getType() {
            return type;
        }

        public LocalDate getLastUpdate() {
            return lastUpdate;
        }

        public LocalDate getCreateDate() {
            return createDate;
        }

        public int getAvatarId() {
            return avatarId;
        }

        public User build(){
            return new User(this);
        }
    }

}
