package ru.sfedu.Arch.lab5.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Entity
public class Layout implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "property", value = "element"))
    @Type(type = "uuid-char")
    protected UUID id;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    protected Element element;

    @Column(name = Constants.FIELD_X, nullable = false)
    private int x;

    @Column(name = Constants.FIELD_Y, nullable = false)
    private int y;

    @Column(name = Constants.FIELD_WIDTH, nullable = false)
    private int width;

    @Column(name = Constants.FIELD_HEIGHT, nullable = false)
    private int height;

    @Column(name = Constants.FIELD_ROTATION, nullable = false)
    private int rotation;


    public Layout () { }

    public void setElement (Element element) {
        this.element = element;
    }

    public Element getElement () {
        return element;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Layout layout = (Layout) o;
        return x == layout.x &&
                y == layout.y &&
                width == layout.width &&
                height == layout.height &&
                rotation == layout.rotation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, rotation);
    }

    @Override
    public String toString() {
        return "Layout{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", rotation=" + rotation +
                '}';
    }
}
