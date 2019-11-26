package com.loneliess.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class Cone {
    @Positive(message = "id должен быть положительным")
    private int id;
    @NotNull(message = "Обязательно должна быть задана длина образующей конуса")
    @Positive(message = "Длина образующей конуса должна быть положительна. ")
    private double l;
    @NotNull(message = "Обязательно должна быть задана длина радиуса окружности")
    @Positive(message = "Длина радиуса окружности основания должна быть положительна. ")
    private double r;
    @NotNull(message = "Обязательно должна быть задана высота конуса")
    @Positive(message = "Высота конуса должна быть положительна. ")
    private double h;


    private Point coordinateTheCenterOfCircle;
    private Point coordinateBorderLineCircle;

    public Cone(int id,
                @NotNull(message = "Обязательно должна быть задана длина образующей конуса")
                @Positive(message = "Длина образующей конуса должна быть положительна. ") double l,
                @NotNull(message = "Обязательно должна быть задана длина радиуса окружности")
                @Positive(message = "Длина радиуса окружности основания должна быть положительна. ")
                        double r, @NotNull(message = "Обязательно должна быть задана высота конуса")
                @Positive(message = "Высота конуса должна быть положительна. ") double h,
                double x1,double y1,double z1,double x2,double y2,double z2) {
        this.id = id;
        this.l = l;
        this.r = r;
        this.h = h;
        this.coordinateTheCenterOfCircle = new Point(x1,y1,z1);
        this.coordinateBorderLineCircle = new Point(x2,y2,z2);
    }
    public Cone(){}

    public Cone(int id, @NotNull(message = "Обязательно должна быть задана длина образующей конуса") @Positive(message = "Длина образующей конуса должна быть положительна. ") double l, @NotNull(message = "Обязательно должна быть задана длина радиуса окружности") @Positive(message = "Длина радиуса окружности основания должна быть положительна. ") double r, @NotNull(message = "Обязательно должна быть задана высота конуса") @Positive(message = "Высота конуса должна быть положительна. ") double h) {
        this.id = id;
        this.l = l;
        this.r = r;
        this.h = h;
    }

    public Cone(@NotNull(message = "Обязательно должна быть задана длина образующей конуса") @Positive(message = "Длина образующей конуса должна быть положительна. ") double l, @NotNull(message = "Обязательно должна быть задана длина радиуса окружности") @Positive(message = "Длина радиуса окружности основания должна быть положительна. ") double r, @NotNull(message = "Обязательно должна быть задана высота конуса") @Positive(message = "Высота конуса должна быть положительна. ") double h) {
        id=-1;
        this.l = l;
        this.r = r;
        this.h = h;
    }

    public int getId() {
        return id;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public Point getCoordinateTheCenterOfCircle() {
        return coordinateTheCenterOfCircle;
    }

    public void setCoordinateTheCenterOfCircle(Point coordinateTheCenterOfCircle) {
        this.coordinateTheCenterOfCircle = coordinateTheCenterOfCircle;
    }

    public Point getCoordinateBorderLineCircle() {
        return coordinateBorderLineCircle;
    }

    public void setCoordinateBorderLineCircle(Point coordinateBorderLineCircle) {
        this.coordinateBorderLineCircle = coordinateBorderLineCircle;
    }
    public void setAll(int id,
                       @NotNull(message = "Обязательно должна быть задана длина образующей конуса")
                       @Positive(message = "Длина образующей конуса должна быть положительна. ") double l,
                       @NotNull(message = "Обязательно должна быть задана длина радиуса окружности")
                       @Positive(message = "Длина радиуса окружности основания должна быть положительна. ")
                               double r, @NotNull(message = "Обязательно должна быть задана высота конуса")
                       @Positive(message = "Высота конуса должна быть положительна. ") double h,
                       double x1, double y1, double z1, double x2, double y2, double z2) {
        this.id = id;
        this.l = l;
        this.r = r;
        this.h = h;
        this.coordinateTheCenterOfCircle = new Point(x1,y1,z1);
        this.coordinateBorderLineCircle = new Point(x2,y2,z2);

    }

    @Override
    public String toString() {
        return "Cone{" +
                "id=" + id +
                ", l=" + l +
                ", r=" + r +
                ", h=" + h +
                ", coordinateTheCenterOfCircle=" + coordinateTheCenterOfCircle +
                ", coordinateBorderLineCircle=" + coordinateBorderLineCircle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cone cone = (Cone) o;
        return id == cone.id &&
                Double.compare(cone.l, l) == 0 &&
                Double.compare(cone.r, r) == 0 &&
                Double.compare(cone.h, h) == 0 &&
                coordinateTheCenterOfCircle.equals(cone.coordinateTheCenterOfCircle) &&
                coordinateBorderLineCircle.equals(cone.coordinateBorderLineCircle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, l, r, h, coordinateTheCenterOfCircle, coordinateBorderLineCircle);
    }
}
