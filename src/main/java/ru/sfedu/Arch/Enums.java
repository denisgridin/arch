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
        deleteComment
    };

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
