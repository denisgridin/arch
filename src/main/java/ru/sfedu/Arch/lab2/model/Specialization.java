package ru.sfedu.Arch.lab2.model;


import ru.sfedu.Arch.Constants;

import javax.persistence.*;

@Embeddable
public class Specialization {
    @Column(name = Constants.FIELD_SPECIALIZATION_NAME)
    private String name;

    @Column(name = Constants.FIELD_SPECIALIZATION_YEARS)
    private int years;

    @Column(name = Constants.FIELD_SPECIALIZATION_LEVEL)
    private String level;

    public Specialization () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
