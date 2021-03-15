package ru.sfedu.Arch.lab5.model;

import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = Constants.ENTITY_ASSESSMENT_MAPPED_SUPER_CLASS)
@Table(name = Constants.TABLE_ASSESSMENT_MAPPED_SUPER_CLASS)
public class Assessment extends Feedback implements Serializable {
    private Enums.Mark mark;

    public Assessment () { }

    @Column(name = Constants.FIELD_MARK, nullable = false)
    public Enums.Mark getMark() {
        return mark;
    }

    public void setMark(Enums.Mark mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Assessment that = (Assessment) o;
        return mark == that.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mark);
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + getId() +
                ";mark=" + mark +
                '}';
    }
}

