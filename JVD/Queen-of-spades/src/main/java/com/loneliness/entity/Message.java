package com.loneliness.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


    public  final class Message implements Entity{

        @Positive(message = "id MUST_BE_POSITIVE")
        private final int id;
        @NotNull(message = "userName must be set")
        @Length(min = 3,message = "login MUST_HAVE_MORE_SYMBOLS_THEN 3")
        @JsonProperty("username")
        private final String userName;
        @Length(min = 1,message = "message MUST_BE ")
        @JsonProperty
        private final String message;
        @NotNull(message = "date must be set")
        @PastOrPresent(message = "date MUST_BE_NOT_IN_FUTURE")
        private final LocalDateTime date;

        @JsonCreator
        public Message(@JsonProperty("username") final String userName,
                       @JsonProperty("message") final String message) {
            Objects.requireNonNull(userName);
            Objects.requireNonNull(message);
            id=0;
            this.userName = userName;
            this.message = message;
            date=LocalDateTime.now();
        }

        public String getUserName() {
            return this.userName;
        }

        public String getMessage() {
            return this.message;
        }

        public LocalDateTime getDate(){return date;}

        public int getId() {
            return id;
        }

        private Message(Builder builder) {
            this.id = builder.id;
            this.message = builder.message;
            this.userName=builder.userName;
            this.date = builder.date;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    ", message='" + message + '\'' +
                    ", date=" + date +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Message message1 = (Message) o;
            return id == message1.id &&
                    userName.equals(message1.userName) &&
                    message.equals(message1.message) &&
                    date.equals(message1.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, userName, message, date);
        }

        public static class Builder{
            private int id=0;
            private String message="";
            private LocalDateTime date=LocalDateTime.now();
            private String userName="";
            public Builder(){}

            public Builder(Message otherMessage) {
                this.id = otherMessage.id;
                this.message = otherMessage.message;
                this.date = otherMessage.date;
                this.userName=otherMessage.userName;
            }

            public Builder setId(int id) {
                this.id = id;
                return this;
            }

            public String getUserName() {
                return userName;
            }

            public Builder setUserName(String userName) {
                this.userName = userName; return this;
            }


            public Builder setMessage(String message) {
                this.message = message;
                return this;
            }

            public Builder setDate(LocalDateTime date) {
                this.date = date;
                return this;
            }

            public int getId() {
                return id;
            }

            public String getMessage() {
                return message;
            }

            public LocalDateTime getDate() {
                return date;
            }

            public Message build(){
                return new Message(this);
            }
        }
    }

