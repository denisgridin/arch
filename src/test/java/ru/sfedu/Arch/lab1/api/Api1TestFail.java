package ru.sfedu.Arch.lab1.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Constants;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class Api1TestFail {

    private static Logger log = LogManager.getLogger(Api1TestSuccess.class);
    private Api1 api = new Api1();

    /**
     * Set empty path of Hibernate config to system variables
     */
    @BeforeAll
    static void setConfig () {
        System.setProperty(Constants.PROPERTY_CUSTOM_HIBERNATE_CONFIG_PATH, Constants.EMPTY_VALUE);
    }

    /**
     * Fail test function from retrieving database schemas
     * Type: Fail
     */
    @Test
    public void getAllSchemasFail () {
        log.info("{ getAllSchemasFail } START");

        Optional<List> result = api.getAllSchemas();
        assertFalse(result.isPresent());

        log.info("{ getAllSchemasFail } END");
    }


    /**
     * Fail test function from retrieving database tables
     * Type: Fail
     */
    @Test
    public void getAllTablesFail () {
        log.info("{ getAllTablesFail } START");
        Optional<List> result = api.getAllTables();
        assertFalse(result.isPresent());

        log.info("{ getAllTablesFail } END");
    }

    /**
     * Fail test function from retrieving database domains
     * Type: Fail
     */
    @Test
    public void getAllDomainsFail () {
        log.info("{ getAllDomainsFail } START");
        Optional<List> result = api.getAllDomains();
        assertFalse(result.isPresent());

        log.info("{ getAllDomainsFail } END");
    }

    /**
     * Fail test function from retrieving database privileges
     * Type: Fail
     */
    @Test
    public void getAllPrivilegesFail () {
        log.info("{ getAllPrivilegesFail } START");
        Optional<List> result = api.getAllPrivileges();
        assertFalse(result.isPresent());

        log.info("{ getAllPrivilegesFail } END");
    }
}
