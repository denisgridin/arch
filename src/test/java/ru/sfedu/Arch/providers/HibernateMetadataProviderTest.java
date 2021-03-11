package ru.sfedu.Arch.providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Constants;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateMetadataProviderTest {
    private static Logger log = LogManager.getLogger(HibernateMetadataProviderTest.class);

    @BeforeAll
    static void setConfig () {
        System.setProperty(Constants.PROPERTY_CUSTOM_HIBERNATE_CONFIG_PATH, Constants.DEFAULT_HIBERNATE_CONFIG_PATH);
    }

    @Test
    public void getAllSchemasSuccess () {
        log.info("{ getAllSchemasSuccess } START");

        HibernateMetadataProvider instance = new HibernateMetadataProvider();
        Optional<List> result = instance.getAllSchemas();
        assertTrue(result.isPresent());

        log.info("{ getAllSchemasSuccess } END");
    }

    @Test
    public void getAllTablesSuccess () {
        log.info("{ getAllTablesSuccess } START");

        HibernateMetadataProvider instance = new HibernateMetadataProvider();
        Optional<List> result = instance.getAllTables();
        assertTrue(result.isPresent());

        log.info("{ getAllTablesSuccess } END");
    }

    @Test
    public void getAllDomainsSuccess () {
        log.info("{ getAllDomainsSuccess } START");

        HibernateMetadataProvider instance = new HibernateMetadataProvider();
        Optional<List> result = instance.getAllDomains();
        assertTrue(result.isPresent());

        log.info("{ getAllDomainsSuccess } END");
    }

    @Test
    public void getAllPrivilegesSuccess () {
        log.info("{ getAllPrivilegesSuccess } START");

        HibernateMetadataProvider instance = new HibernateMetadataProvider();
        Optional<List> result = instance.getAllPrivileges();
        assertTrue(result.isPresent());

        log.info("{ getAllPrivilegesSuccess } END");
    }
}