package ru.sfedu.Arch.lab1.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Constants;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Api1TestSuccess {

    private static Logger log = LogManager.getLogger(Api1TestSuccess.class);
    private Api1 api = new Api1();


    /**
     * Set default path of Hibernate config to system variables
     * Type: Success
     */
    @BeforeAll
    static void setConfig () {
        System.setProperty(Constants.PROPERTY_CUSTOM_HIBERNATE_CONFIG_PATH, Constants.DEFAULT_HIBERNATE_CONFIG_PATH);
    }

    /**
     * Success test function from retrieving database schemas
     * Type: Success
     */
    @Test
    public void getAllSchemasSuccess () {
        log.info("{ getAllSchemasSuccess } START");
        Optional<List> result = api.getAllSchemas();
        assertTrue(result.isPresent());

        log.info("{ getAllSchemasSuccess } END");
    }


    /**
     * Success test function from retrieving database tables
     * Type: Success
     */
    @Test
    public void getAllTablesSuccess () {
        log.info("{ getAllTablesSuccess } START");
        Optional<List> result = api.getAllTables();
        assertTrue(result.isPresent());

        log.info("{ getAllTablesSuccess } END");
    }

    /**
     * Success test function from retrieving database domains
     * Type: Success
     */
    @Test
    public void getAllDomainsSuccess () {
        log.info("{ getAllDomainsSuccess } START");
        Optional<List> result = api.getAllDomains();
        assertTrue(result.isPresent());

        log.info("{ getAllDomainsSuccess } END");
    }

    /**
     * Success test function from retrieving database privileges
     * Type: Success
     */
    @Test
    public void getAllPrivilegesSuccess () {
        log.info("{ getAllPrivilegesSuccess } START");
        Optional<List> result = api.getAllPrivileges();
        assertTrue(result.isPresent());

        log.info("{ getAllPrivilegesSuccess } END");
    }

}