package com.loneliness.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

public class News implements Entity{
    @Positive(message = "id MUST_BE_POSITIVE")
    private final int id;
    @Length(min = 1,message = "text must be ")
    private final String text;
    @PastOrPresent(message = "last_update MUST_BE_NOT_IN_FUTURE")
    private final LocalDate last_update;

    private News(Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.last_update=builder.last_update;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDate getLast_update() {
        return last_update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                text.equals(news.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    public static class Builder{
        private int id=0;
        private String text="";
        private LocalDate last_update=LocalDate.now();

        public Builder() {
        }


        public Builder(News news) {
            this.id = news.id;
            this.text = news.text;
            this.last_update=news.last_update;
        }

        public Builder setLast_update(LocalDate last_update) {
            this.last_update = last_update;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public LocalDate getLast_update() {
            return last_update;
        }

        public News build(){
            return new News(this);
        }
    }
}
