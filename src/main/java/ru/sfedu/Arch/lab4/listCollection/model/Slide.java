package ru.sfedu.Arch.lab4.listCollection.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Table(name = Constants.TABLE_SLIDES_LIST_COLLECTION)
public class Slide implements Serializable {

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = Constants.FIELD_NAME)
    private String name;

    @Column(name = Constants.FIELD_INDEX)
    private int index;

//    @Column(name = Constants.FIELD_PRESENTATION_ID, columnDefinition="uniqueidentifier")
//    @Type(type = "uuid-char")
//    private UUID presentationId;


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

//    public UUID getPresentationId() {
//        return presentationId;
//    }
//
//    public void setPresentationId(UUID presentationId) {
//        this.presentationId = presentationId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slide slide = (Slide) o;
        return index == slide.index &&
                Objects.equals(id, slide.id) &&
                Objects.equals(name, slide.name);
//                &&
//                Objects.equals(presentationId, slide.presentationId);
    }

    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, index, presentationId);
//    }
    public int hashCode() {
        return Objects.hash(id, name, index);
    }

    @Override
    public String toString() {
        return "Slide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", index=" + index +
//                ", presentationId=" + presentationId +
                '}';
    }
}
