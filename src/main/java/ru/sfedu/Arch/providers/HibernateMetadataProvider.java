package ru.sfedu.Arch.providers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.utils.HibernateUtil;
import ru.sfedu.Arch.utils.Messages;


public class HibernateMetadataProvider implements IMetadataProvider {

    private static Logger log = LogManager.getLogger(HibernateMetadataProvider.class);

    private Session getSession () {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.openSession();
    }

    public Optional<List> getAllTables() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_TABLES);
            List resList = query.getResultList();
            session.close();
            if (resList != null) {
                log.info(Messages.TABLES_SIZE, resList.size());
            } else {
                log.error(Messages.NO_SCHEMAS_SIZE);
            }
            log.debug(resList);
            return Optional.ofNullable(resList);
        } catch (RuntimeException e) {
            log.error(Messages.SQL_ERROR);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List> getAllPrivileges() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_PRIVILEGES);
            List resList = query.getResultList();
            session.close();
            if (resList != null) {
                log.info(Messages.PRIVILEGES_SIZE, resList.size());
            } else {
                log.error(Messages.NO_SCHEMAS_SIZE);
            }
            log.debug(resList);
            return Optional.ofNullable(resList);
        } catch (RuntimeException e) {
            log.error(e);
            log.error(Messages.SQL_ERROR);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List> getAllDomains() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_DOMAINS);
            List resList = query.getResultList();
            session.close();
            if (resList != null) {
                log.info(Messages.DOMAINS_SIZE, resList.size());
            } else {
                log.error(Messages.NO_SCHEMAS_SIZE);
            }
            log.debug(resList);
            return Optional.ofNullable(resList);
        } catch (RuntimeException e) {
            log.error(e);
            log.error(Messages.SQL_ERROR);
            return Optional.empty();
        }
    }

    public Optional<List> getAllSchemas() {
        try {
            Session session = this.getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_SCHEMAS);
            List resList = query.getResultList();
            session.close();
            if (resList != null) {
                log.info(Messages.SCHEMAS_SIZE, resList.size());
            } else {
                log.error(Messages.NO_SCHEMAS_SIZE);
            }
            return Optional.ofNullable(resList);
        } catch (RuntimeException e) {
            log.error(e);
            log.error(Messages.SQL_ERROR);
            return Optional.empty();
        }
    }
}
