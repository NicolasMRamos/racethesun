package com.aor.hero.model;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2Test {

    @Property
    void getLeft(@ForAll double x, @ForAll double y) {
        assertEquals(x - 1, new Vector2(x, y).getLeft().getX());
        assertEquals(y, new Vector2(x, y).getLeft().getY());
    }

    @Property
    void getRight(@ForAll double x, @ForAll double y) {
        assertEquals(x + 1, new Vector2(x, y).getRight().getX());
        assertEquals(y, new Vector2(x, y).getRight().getY());
    }

    @Property
    void getUp(@ForAll double x, @ForAll double y) {
        assertEquals(x, new Vector2(x, y).getUp().getX());
        assertEquals(y - 1, new Vector2(x, y).getUp().getY());
    }

    @Property
    void getDown(@ForAll double x, @ForAll double y) {
        assertEquals(x, new Vector2(x, y).getDown().getX());
        assertEquals(y + 1, new Vector2(x, y).getDown().getY());
    }

    @Test
    void testConstructor() {
        Vector2 vector = new Vector2(3.5, 2.5);
        assertEquals(3.5, vector.getX());
        assertEquals(2.5, vector.getY());
    }

    @Test
    void testCopyConstructor() {
        Vector2 original = new Vector2(4.2, -1.5);
        Vector2 copy = new Vector2(original);
        assertEquals(original.getX(), copy.getX());
        assertEquals(original.getY(), copy.getY());
    }

    @Test
    void testAdd() {
        Vector2 vector1 = new Vector2(1, 2);
        Vector2 vector2 = new Vector2(3, 4);
        Vector2 result = vector1.add(vector2);
        assertEquals(4, result.getX());
        assertEquals(6, result.getY());
    }

    @Test
    void testMultiplyByScalar() {
        Vector2 vector = new Vector2(2, 3);
        Vector2 result = vector.multiplyByScalar(2.5);
        assertEquals(5, result.getX());
        assertEquals(7.5, result.getY());
    }

    @Test
    void testNegative() {
        Vector2 vector = new Vector2(3, -4);
        Vector2 result = vector.negative();
        assertEquals(-3, result.getX());
        assertEquals(4, result.getY());
    }

    @Test
    void testMagnitude() {
        Vector2 vector = new Vector2(3, 4);
        assertEquals(5, vector.magnitude(), 0.001);
    }

    @Test
    void testNormalized() {
        Vector2 vector = new Vector2(3, 4);
        Vector2 normalized = vector.normalized();
        assertEquals(0.6, normalized.getX(), 0.001);
        assertEquals(0.8, normalized.getY(), 0.001);
    }

    @Test
    void testDist() {
        Vector2 vector1 = new Vector2(1, 1);
        Vector2 vector2 = new Vector2(4, 5);
        assertEquals(5, vector1.dist(vector2), 0.001);
    }

    @Test
    void testRotateRad() {
        Vector2 vector = new Vector2(1, 0);
        Vector2 rotated = vector.rotateRad(Math.PI / 2);
        assertEquals(0, rotated.getX(), 0.001);
        assertEquals(1, rotated.getY(), 0.001);
    }

    @Test
    void testRotateDeg() {
        Vector2 vector = new Vector2(1, 0);
        Vector2 rotated = vector.rotateDeg(90);
        assertEquals(0, rotated.getX(), 0.001);
        assertEquals(1, rotated.getY(), 0.001);
    }

    @Test
    void testEqualsAndHashCode() {
        Vector2 vector1 = new Vector2(2, 3);
        Vector2 vector2 = new Vector2(2, 3);
        Vector2 vector3 = new Vector2(3, 2);

        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);

        assertEquals(vector1.hashCode(), vector2.hashCode());
        assertNotEquals(vector1.hashCode(), vector3.hashCode());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        Vector2 original = new Vector2(5, 7);
        Vector2 clone = (Vector2) original.clone();
        assertEquals(original, clone);
        assertNotSame(original, clone);
    }

    @Test
    void testToString() {
        Vector2 vector = new Vector2(3, 4);
        assertEquals("Vector2{x=3.0, y=4.0}", vector.toString());
    }
}
