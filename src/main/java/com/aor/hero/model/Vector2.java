package com.aor.hero.model;

import java.util.Objects;

import static java.lang.Math.*;

public class Vector2 implements Cloneable {
    private double x;
    private double y;

    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 RIGHT = new Vector2(1, 0);
    public static final Vector2 LEFT = new Vector2(-1, 0);

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vector2) {
        this.x = vector2.x;
        this.y = vector2.y;
    }

    public Vector2 add(Vector2 vector2)
    {
        return new Vector2(x + vector2.x, y + vector2.y);
    }

    public Vector2 multiplyByScalar(double scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 negative(){
        return new Vector2(-x, -y);
    }

    public Vector2 getLeft() {
        return new Vector2(x - 1, y);
    }

    public Vector2 getRight() {
        return new Vector2(x + 1, y);
    }

    public Vector2 getUp() {
        return new Vector2(x, y - 1);
    }

    public Vector2 getDown() {
        return new Vector2(x, y + 1);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double dist(Vector2 vector2) {
        return sqrt(pow(this.x - vector2.x, 2) + pow(this.y - vector2.y, 2));
    }

    public double magnitude()
    {
        return sqrt(x * x + y * y);
    }

    public Vector2 normalized() {
        if(x == 0 && y == 0) return new Vector2(0, 0);

        double magnitude = magnitude();

        return new Vector2(x / magnitude, y / magnitude);
    }

    public Vector2 rotateRad(double angle) {
        return new Vector2(x * Math.cos(angle) - y * Math.sin(angle), y * Math.cos(angle) + x * Math.sin(angle));
    }

    public Vector2 rotateDeg(double angle) {
        return rotateRad(Math.toRadians(angle));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2 vector2)) return false;
        return x == vector2.x && y == vector2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Vector2{" + "x=" + x + ", y=" + y + '}';
    }
}
