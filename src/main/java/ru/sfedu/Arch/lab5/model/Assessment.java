package ru.sfedu.Arch.lab5.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = Constants.ENTITY_ASSESSMENT_MAIN)
@Table(name = Constants.TABLE_ASSESSMENT_MAIN)
public class Assessment extends Feedback implements Serializable {

    @Column(name = Constants.FIELD_MARK, nullable = false)
    private Enums.Mark mark;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(Set<Presentation> presentations) {
        this.presentations = presentations;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = Constants.TABLE_PRESENTATION_ASSESSMENT,
            joinColumns = @JoinColumn(name = Constants.FIELD_ASSESSMENT_ID),
            inverseJoinColumns = @JoinColumn(name = Constants.FIELD_PRESENTATION_ID)
    )
    protected Set<Presentation> presentations = new HashSet<>();

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

