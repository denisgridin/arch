package ru.sfedu.Arch.lab1.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.lab2.model.Specialization;
import ru.sfedu.Arch.lab2.model.TestEntity;
import ru.sfedu.Arch.providers.IMetadataProvider;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.HibernateUtil;
import ru.sfedu.Arch.utils.Messages;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class Api1 implements IMetadataProvider {
    private static final Logger logger = LogManager.getLogger(Api1.class);
    private static final EventWrapper event = new EventWrapper(logger);


    /**
     * Connect to Hibernate and connect to session
     * @return Session object or null value
     */
    private Session getSession () {
        try {
            event.info(1, Messages.GET_HIBERNATE_SESSION);
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            event.debug(1, Messages.SESSION_SUCCESS);
            return sessionFactory.openSession();
        } catch (Exception error) {
            event.error(1, error);
            return null;
        }
    }

    /**
     * Get all tables from connected database
     * @return Optional<List> - list of tables in connected database
     */
    public Optional<List> getAllTables() {
        try {
            Session session = this.getSession();
            if (session != null) {
                NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_TABLES);
                List resList = query.getResultList();
                session.close();
                if (resList != null) {
                    String msg = String.format(Messages.TABLES_SIZE, resList.size());
                    event.info(1, msg);
                } else {
                    event.error(1, Messages.NO_SCHEMAS_SIZE);
                }
                event.debug(1, resList);
                return Optional.ofNullable(resList);
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            event.error(1, Messages.SQL_ERROR);
            return Optional.empty();
        }
    }


    /**
     * Get all privileges from connected database
     * @return Optional<List> - list of privileges in connected database
     */
    @Override
    public Optional<List> getAllPrivileges() {
        try {
            Session session = this.getSession();

            if (session != null) {
                NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_PRIVILEGES);
                List resList = query.getResultList();
                session.close();
                if (resList != null) {
                    String msg = String.format(Messages.PRIVILEGES_SIZE, resList.size());
                    event.info(1, msg);
                } else {
                    event.error(1, Messages.NO_SCHEMAS_SIZE);
                }
                event.debug(1, resList);
                return Optional.ofNullable(resList);
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            event.error(1, e);
            event.error(2, Messages.SQL_ERROR);
            return Optional.empty();
        }
    }


    /**
     * Get all domains from connected database
     * @return Optional<List> - list of domains in connected database
     */
    @Override
    public Optional<List> getAllDomains() {
        try {
            Session session = this.getSession();

            if (session != null) {
                NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_DOMAINS);
                List resList = query.getResultList();
                session.close();
                if (resList != null) {
                    String msg = String.format(Messages.DOMAINS_SIZE, resList.size());
                    event.info(1, msg);
                } else {
                    event.error(1, Messages.NO_SCHEMAS_SIZE);
                }
                event.debug(1, resList);
                return Optional.ofNullable(resList);
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            event.error(1, e);
            event.error(2, Messages.SQL_ERROR);
            return Optional.empty();
        }
    }


    /**
     * Get all schemas from connected database
     * @return Optional<List> - list of schemas in connected database
     */
    public Optional<List> getAllSchemas() {
        try {
            Session session = this.getSession();

            if (session != null) {
                NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_SCHEMAS);
                List resList = query.getResultList();
                session.close();
                if (resList != null) {
                    String msg = String.format(Messages.SCHEMAS_SIZE, resList.size());
                    event.info(1, msg);
                } else {
                    event.error(1, Messages.NO_SCHEMAS_SIZE);
                }
                return Optional.ofNullable(resList);
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            event.error(1, e);
            event.error(2, Messages.SQL_ERROR);
            return Optional.empty();
        }
    }
}
