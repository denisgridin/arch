package ru.sfedu.Arch.lab5.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(name = Constants.ENTITY_SLIDE_MAIN)
@Table(name = Constants.TABLE_ENTITY_SLIDE_MAIN)
public class Slide implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = Constants.FIELD_NAME)
    private String name;

    @Column(name = Constants.FIELD_INDEX)
    private int index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.FIELD_PRESENTATION_ID, nullable = false)
    private Presentation presentation;


    public Slide () {}

    public static Logger log = LogManager.getLogger(Slide.class);

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Presentation getPresentationId() {
        return presentation;
    }

    public void setPresentationId(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slide slide = (Slide) o;
        return index == slide.index &&
                Objects.equals(id, slide.id) &&
                Objects.equals(name, slide.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, index);
    }

    @Override
    public String toString() {
        return "Slide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
