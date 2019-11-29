package com.loneliess.servise;

import com.loneliess.entity.Point;

public class PointService {

    public Point difference(Point p1, Point p2) {
        return new Point(Math.abs(p1.getCoordinateX() - p2.getCoordinateX()),
                Math.abs(p1.getCoordinateY() - p2.getCoordinateY()), Math.abs(p1.getCoordinateZ() - p2.getCoordinateZ()));
    }

    public boolean isLiesOnThePlane(Point point) {
        if (point.getCoordinateY() == 0) {
            return true;
        } else if (point.getCoordinateX() == 0) {
            return true;
        } else return point.getCoordinateZ() == 0;
    }

    public boolean isEquals(Point point, Point point1) {
        return point.equals(point1);
    }
}
