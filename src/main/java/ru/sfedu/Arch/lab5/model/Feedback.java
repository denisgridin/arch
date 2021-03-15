package ru.sfedu.Arch.lab5.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = Constants.ENTITY_FEEDBACK_MAIN)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Feedback {
    private Enums.Role role;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier", nullable = false)
    @Type(type = "uuid-char")
    private UUID id;


    public Feedback() {}

    public Enums.Role getRole() {
        return role;
    }

    public void setRole(Enums.Role role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
