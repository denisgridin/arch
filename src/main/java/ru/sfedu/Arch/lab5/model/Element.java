package ru.sfedu.Arch.lab5.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity(name = Constants.ENTITY_ELEMENT_MAIN)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Element {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constants.FIELD_SLIDE, nullable = false)
    private Slide slide;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = Constants.FIELD_NAME, nullable = false)
    private String name;

    @Column(name = Constants.FIELD_ELEMENT_TYPE, nullable = false)
    private Enums.ElementType elementType;

    @OneToOne(mappedBy = "element", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    protected Layout layout;

    protected Element() { }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Slide getSlide() {
        return slide;
    }

    public void setSlide(Slide slide) {
        this.slide = slide;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enums.ElementType getElementType() {
        return elementType;
    }

    public void setElementType(Enums.ElementType elementType) {
        this.elementType = elementType;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return id == element.id &&
                Objects.equals(name, element.name) &&
                Objects.equals(elementType, element.elementType) &&
                Objects.equals(layout, element.layout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, elementType, layout);
    }
}
