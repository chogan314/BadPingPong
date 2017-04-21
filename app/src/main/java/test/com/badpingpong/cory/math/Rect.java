package test.com.badpingpong.cory.math;

/**
 * Created by Cory on 4/17/2017.
 */

public class Rect {
    private Vec2 pos;
    private float width;
    private float height;

    public Rect(Vec2 pos, float width, float height) {
        this.pos = pos.cpy();
        this.width = width;
        this.height = height;
    }

    public Rect(float width, float height) {
        this(new Vec2(0, 0), width, height);
    }

    public Rect copy() {
        return new Rect(pos, width, height);
    }

    public boolean containsPoint(Vec2 point) {
        return point.getX() >= pos.getX() &&
                point.getY() >= pos.getY() &&
                point.getX() <= pos.getX() + width &&
                point.getY() <= pos.getY() + height;
    }

    public boolean overlapsX(Rect other) {
        return (pos.getX() <= other.pos.getX() + other.width
                && pos.getX() >= other.pos.getX())
                || (pos.getX() + width <= other.pos.getX() + other.width
                && pos.getX() + width >= other.pos.getX());
    }

    public boolean overlapsY(Rect other) {
        return (pos.getY() <= other.pos.getY() + other.height
                && pos.getY() >= other.pos.getY())
                || (pos.getY() + height <= other.pos.getY() + other.height
                && pos.getY() + height >= other.pos.getY());
    }

    public boolean overlaps(Rect other) {
        return overlapsX(other) && overlapsY(other);
    }

    public boolean contains(Rect other) {
        return pos.getX() <= other.pos.getX()
                && pos.getX() + width >= other.pos.getX() + other.width
                && pos.getY() <= other.pos.getY()
                && pos.getY() + height >= other.pos.getY() + other.height;
    }

    public void centerXWith(Rect other) {
        float widthDiff = other.width - width;
        pos.setX(other.pos.getX() + widthDiff / 2);
    }

    public void centerYWith(Rect other) {
        float heightDiff = other.height - height;
        pos.setY(other.pos.getY() + heightDiff / 2);
    }

    public void centerWith(Rect other) {
        centerXWith(other);
        centerYWith(other);
    }

    public void alignLeftWith(Rect other) {
        pos.setX(other.pos.getX());
    }

    public void alignRightWith(Rect other) {
        float widthDiff = other.width - width;
        pos.setX(other.pos.getX() + widthDiff);
    }

    public void alignTopWith(Rect other) {
        this.pos.setY(other.pos.getY());
    }

    public void alignBottomWith(Rect other) {
        float heightDiff = other.height - height;
        pos.setY(other.pos.getY() + heightDiff);
    }

    public boolean extendsLeftOf(Rect other) {
        return pos.getX() < other.pos.getX();
    }

    public boolean extendsRightOF(Rect other) {
        return pos.getX() + width > other.pos.getX() + other.width;
    }

    public boolean extendsAbove(Rect other) {
        return pos.getY() < other.pos.getY();
    }

    public boolean extendsBelow(Rect other) {
        return pos.getY() + height > other.pos.getY() + other.height;
    }

    public Vec2 getCenter() {
        return pos.cpy().add(width / 2, height / 2);
    }

    public Vec2 getPosition() {
        return pos;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}

