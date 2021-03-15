package ru.sfedu.Arch.lab5.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Entity(name = Constants.ENTITY_FONT_MAIN)
public class Font implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = Constants.FIELD_CONTENT_ID)
    protected Content content;

    @Column(name = Constants.FIELD_FONT_FAMILY, nullable = false)
    private String fontFamily;

    @Column(name = Constants.FIELD_FONT_SIZE, nullable = false)
    private String fontSize;

    @Column(name = Constants.FIELD_LETTER_SPACING, nullable = false)
    private String letterSpacing;

    @Column(name = Constants.FIELD_LINE_SPACING, nullable = false)
    private String lineSpacing;

    @Column(name = Constants.FIELD_FONT_CASE, nullable = false)
    private Enums.FontCase fontCase;

    public Font () { }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getLetterSpacing() {
        return letterSpacing;
    }

    public void setLetterSpacing(String letterSpacing) {
        this.letterSpacing = letterSpacing;
    }

    public String getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(String lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public Enums.FontCase getFontCase() {
        return fontCase;
    }

    public void setFontCase(Enums.FontCase fontCase) {
        this.fontCase = fontCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Font font = (Font) o;
        return Objects.equals(fontFamily, font.fontFamily) &&
                Objects.equals(fontSize, font.fontSize) &&
                Objects.equals(letterSpacing, font.letterSpacing) &&
                Objects.equals(lineSpacing, font.lineSpacing) &&
                fontCase == font.fontCase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fontFamily, fontSize, letterSpacing, lineSpacing, fontCase);
    }

    @Override
    public String toString() {
        return "Font{" +
                "fontFamily='" + fontFamily + '\'' +
                ", fontSize='" + fontSize + '\'' +
                ", letterSpacing='" + letterSpacing + '\'' +
                ", lineSpacing='" + lineSpacing + '\'' +
                ", fontCase=" + fontCase +
                '}';
    }
}

