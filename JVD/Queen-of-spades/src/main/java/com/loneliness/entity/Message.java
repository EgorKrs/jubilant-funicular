package com.loneliness.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

public class Message implements Entity {
    @Positive(message = "id MUST_BE_POSITIVE")
    private final int id;
    @Positive(message = "toUser MUST_BE_POSITIVE")
    private final int toUser;
    @Positive(message = "fromUser MUST_BE_POSITIVE")
    private final int fromUser;
    @Length(min = 1,message = "message MUST_BE ")
    private final String message;
    @PastOrPresent(message = "date MUST_BE_NOT_IN_FUTURE")
    private final LocalDate date;

    private Message(Builder builder) {
        this.id = builder.id;
        this.toUser = builder.toUser;
        this.fromUser = builder.fromUser;
        this.message = builder.message;
        this.date = builder.date;
    }

    public int getId() {
        return id;
    }

    public int getToUser() {
        return toUser;
    }

    public int getFromUser() {
        return fromUser;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id == message1.id &&
                toUser == message1.toUser &&
                fromUser == message1.fromUser &&
                message.equals(message1.message) &&
                date.equals(message1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, toUser, fromUser, message, date);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", toUser=" + toUser +
                ", fromUser=" + fromUser +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }

    public static class Builder{
        private int id=0;
        private int toUser=0;
        private int fromUser=0;
        private String message="";
        private LocalDate date=LocalDate.now();

        public Builder(){}

        public Builder(Message otherMessage) {
            this.id = otherMessage.id;
            this.toUser = otherMessage.toUser;
            this.fromUser = otherMessage.fromUser;
            this.message = otherMessage.message;
            this.date = otherMessage.date;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setToUser(int toUser) {
            this.toUser = toUser;
            return this;
        }

        public Builder setFromUser(int fromUser) {
            this.fromUser = fromUser;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public int getId() {
            return id;
        }

        public int getToUser() {
            return toUser;
        }

        public int getFromUser() {
            return fromUser;
        }

        public String getMessage() {
            return message;
        }

        public LocalDate getDate() {
            return date;
        }

        public Message build(){
            return new Message(this);
        }
    }
}
