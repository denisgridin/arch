package ru.sfedu.Arch;

import java.math.BigDecimal;

public class Enums {

    public enum STATUS {
        error,
        success
    }

    public enum INHERITANCE_STRATEGY {
        JOINED,
        SINGLE_TABLE,
        MAPPED_SUPERCLASS,
        TABLE_PER_CLASS
    }

    public enum METHODS_LAB_3 {
        saveComment,
        getCommentById,
        updateComment,
        deleteComment,
        saveAssessment,
        getAssessmentById,
        updateAssessment,
        deleteAssessment
    };

    public enum METHODS_LAB_4 {
        savePresentation,
        getPresentationById,
        addPresentationSlide,
        removePresentationSlide,
        updatePresentationSlide,
        getSlideById
    }

    public enum COLLECTION_STRATEGY {
        LIST,
        SET,
        MAP
    }

    public enum ElementType {
        content,
        shape
    }

    public enum FontCase {
        uppercase,
        lowercase,
        normal
    }


    public enum Mark {
        awful,
        bad,
        normal,
        good,
        excellent
    }

    public enum LAB {
        LAB1,
        LAB2,
        LAB3,
        LAB4,
        LAB5
    }

    public enum METHODS_LAB_1 {
        getAllTables,
        getAllPrivileges,
        getAllDomains,
        getAllSchemas
    }

    public enum METHODS_LAB_2 {
        save,
        getById,
        delete,
        update
    }

    public enum GENERATOR_TYPE {
        INCREMENT("increment");
        private final String value;
        GENERATOR_TYPE (String value) {
            this.value = value;
        }

        public String getValue() { return this.value; }
    }

    public enum Role {
        editor,
        guest
    }

}
