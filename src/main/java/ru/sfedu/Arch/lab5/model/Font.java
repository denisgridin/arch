package ru.sfedu.Arch.lab5.model;

import ru.sfedu.Arch.Enums;

import java.io.Serializable;
import java.util.Objects;

public class Font implements Serializable {
    private String fontFamily;

    private String fontSize;

    private String letterSpacing;

    private String lineSpacing;

    private Enums.FontCase fontCase;

    public Font () { }

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

