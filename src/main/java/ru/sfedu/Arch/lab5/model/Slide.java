package ru.sfedu.Arch.lab5.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    @Column(name = Constants.FIELD_NAME, nullable = false)
    private String name;

    @Column(name = Constants.FIELD_INDEX, nullable = false)
    private int index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.FIELD_PRESENTATION_ID)
    protected Presentation presentation;


    @OneToMany(mappedBy = Constants.FIELD_SLIDE, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Element> elements = new ArrayList<Element>();


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


    public void setElements (List<Element> items) {
        elements = items;
    }
    public List<Element> getElements () {
        return elements;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
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
                ", presentationId=" + presentation.getId() +
                ", elements=" + elements +
                '}';
    }
}
