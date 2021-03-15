package ru.sfedu.Arch.lab5.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Entity(name = Constants.ENTITY_STYLE_MAIN)
public class Style implements Serializable {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;


    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = Constants.FIELD_SHAPE_ID)
    protected Shape shape;

    @Column(name = Constants.FIELD_FILL_COLOR, nullable = false)
    private String fillColor;

    @Column(name = Constants.FIELD_BOX_SHADOW, nullable = false)
    private String boxShadow;

    @Column(name = Constants.FIELD_OPACITY, nullable = false)
    private String opacity;

    @Column(name = Constants.FIELD_BORDER_COLOR, nullable = false)
    private String borderColor;

    @Column(name = Constants.FIELD_BORDER_RADIUS, nullable = false)
    private String borderRadius;

    @Column(name = Constants.FIELD_BORDER_WIDTH, nullable = false)
    private String borderWidth;

    @Column(name = Constants.FIELD_BORDER_STYLE  , nullable = false)
    private Enums.BorderStyle borderStyle;

    public Style() { }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape () {
        return shape;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getBoxShadow() {
        return boxShadow;
    }

    public void setBoxShadow(String boxShadow) {
        this.boxShadow = boxShadow;
    }

    public String getOpacity() {
        return opacity;
    }

    public void setOpacity(String opacity) {
        this.opacity = opacity;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(String borderRadius) {
        this.borderRadius = borderRadius;
    }

    public String getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(String borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Enums.BorderStyle getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(Enums.BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Style style = (Style) o;
        return opacity == style.opacity &&
                Objects.equals(fillColor, style.fillColor) &&
                Objects.equals(boxShadow, style.boxShadow) &&
                Objects.equals(borderColor, style.borderColor) &&
                Objects.equals(borderRadius, style.borderRadius) &&
                Objects.equals(borderWidth, style.borderWidth);
//                borderStyle == style.borderStyle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fillColor, boxShadow, opacity, borderColor, borderRadius, borderWidth);
    }

    @Override
    public String toString() {
        return "Style{" +
                "fillColor='" + fillColor + '\'' +
                ", boxShadow='" + boxShadow + '\'' +
                ", opacity=" + opacity +
                ", borderColor='" + borderColor + '\'' +
                ", borderRadius='" + borderRadius + '\'' +
                ", borderWidth='" + borderWidth + '\'' +
//                ", borderStyle=" + borderStyle +
                '}';
    }
}
