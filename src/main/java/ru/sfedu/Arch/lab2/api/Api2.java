package ru.sfedu.Arch.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.lab2.model.Specialization;
import ru.sfedu.Arch.lab2.model.TestEntity;
import ru.sfedu.Arch.providers.ITestEntityProvider;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.HibernateUtil;
import ru.sfedu.Arch.utils.Messages;


import java.sql.Date;
import java.util.HashMap;
import java.util.Optional;

public class Api2 implements ITestEntityProvider {

    private static final Logger logger = LogManager.getLogger(Api2.class);
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
            event.error(2, Messages.ERROR_GET_SESSION);
            return null;
        }
    }


    /**
     * Save bean in data source
     * @param entity Entity bean for data source
     * @return Long - id of element
     */
    @Override
    public Long save(TestEntity entity) {
        try {
            event.info(1, Messages.SAVE_BEAN);
            Session session = this.getSession();
            event.debug(1, Messages.BEGIN_TRANSACTION);
            Transaction transaction = session.beginTransaction();
            Long id = (Long) session.save(entity);
            event.debug(2, String.format(Messages.COMMIT_BEAN, id));
            transaction.commit();
            return id;
        } catch (Exception e) {
            event.error(1, e);
            event.error(2, Messages.ERROR_SAVE_BEAN);
            return null;
        }
    }


    /**
     * Update bean in data source
     * @param updatedEntity Entity bean for updating
     */
    @Override
    public void update(TestEntity updatedEntity) {
        try {
            event.info(1, Messages.UPDATE_BEAN);

            Session session = this.getSession();

            if (session != null) {
                Transaction transaction = session.beginTransaction();
                event.debug(1, String.format(Messages.UPDATE_BEAN_FORMAT, updatedEntity));
                session.update(updatedEntity);
                transaction.commit();
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
            }

        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_UPDATE_BEAN);
        }
    }


    /**
     * Delete bean from data source
     * @param entity Entity bean for removing
     */
    @Override
    public void delete(TestEntity entity) {
        try {
            event.info(1, Messages.UPDATE_BEAN);

            Session session = this.getSession();

            if (session != null) {
                Transaction transaction = session.beginTransaction();
                event.debug(1, String.format(Messages.DELETE_BEAN_FORMAT, entity));
                session.delete(entity);
                transaction.commit();
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
            }

        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_UPDATE_BEAN);
        }
    }

    /**
     * Get bean by id from data source
     * @param entity Class of entity
     * @param id identifier of searching entity
     * @return Long - id of element
     */
    @Override
    public Optional<TestEntity> getById(Class<TestEntity> entity, Long id) {
       try {
           event.info(1, Messages.FIND_ENTITY_BY_ID);
           Session session = this.getSession();
           TestEntity testEntity = session.get(entity, id);
           event.debug(1, String.format(Messages.ENTITY_FOUND, testEntity));
           session.close();
           return Optional.of(testEntity);
       } catch (Exception error) {
           event.error(1, error);
           event.error(2, Messages.ERROR_GET_ENTITY_BY_ID);
           return Optional.empty();
       }
    }

    public void updateTestBean (HashMap args) {
        if (args.get(Constants.FIELD_ID) != null) {
            Long id = Long.valueOf( (String) args.get(Constants.FIELD_ID) );
            Optional<TestEntity> optionalTestEntity = this.getById(TestEntity.class, id);
            if (optionalTestEntity.isPresent()) {
                TestEntity testEntity = optionalTestEntity.get();
                testEntity.setName((String) args.getOrDefault(Constants.FIELD_NAME, testEntity.getName()));
                testEntity.setDescription((String) args.getOrDefault(Constants.FIELD_DESCRIPTION, testEntity.getDescription()));
                boolean isCheck = Boolean.getBoolean((String) args.getOrDefault(Constants.FIELD_CHECK, testEntity.isCheck()));
                testEntity.setCheck(isCheck);

                Date date = Date.valueOf(String.valueOf(args.getOrDefault(Constants.FIELD_DATE_CREATED, testEntity.getDateCreated())));
                testEntity.setDateCreated(date);

                Specialization specialization = testEntity.getSpecialization();
                int years = Integer.parseInt((String) args.getOrDefault(Constants.FIELD_SPECIALIZATION_YEARS, specialization.getYears()));
                specialization.setYears(years);

                specialization.setLevel((String) args.getOrDefault(Constants.FIELD_SPECIALIZATION_LEVEL, specialization.getLevel()));

                specialization.setName((String) args.getOrDefault(Constants.FIELD_SPECIALIZATION_NAME, specialization.getName()));

                testEntity.setSpecialization(specialization);
                event.debug(1, String.format(Messages.UPDATED_BEAN, testEntity));

                this.update(testEntity);
            }
        } else {
            event.error(1, Messages.ID_NOT_PROVIDED);
        }
    }

    public void saveTestEntity(HashMap args) {
        TestEntity testEntity = new TestEntity();

        testEntity.setName((String) args.getOrDefault(Constants.FIELD_NAME, null));

        boolean isCheck = Boolean.getBoolean((String) args.getOrDefault(Constants.FIELD_CHECK, null));
        testEntity.setCheck(isCheck);

        Date date = new Date(System.currentTimeMillis());
        testEntity.setDateCreated(date);

        testEntity.setDescription((String) args.getOrDefault(Constants.FIELD_DESCRIPTION, null));


        Specialization specialization = new Specialization();
        specialization.setName((String) args.getOrDefault(Constants.FIELD_SPECIALIZATION_NAME, null));
        specialization.setLevel((String) args.getOrDefault(Constants.FIELD_SPECIALIZATION_LEVEL, null));

        int years = Integer.parseInt((String) args.getOrDefault(Constants.FIELD_SPECIALIZATION_YEARS, null));
        specialization.setYears(years);
        testEntity.setSpecialization(specialization);

        this.save(testEntity);
    }

    public void deleteTestBean (HashMap args) {
        if (args.get(Constants.FIELD_ID) != null) {
            Long id = Long.valueOf( (String) args.get(Constants.FIELD_ID) );
            Optional<TestEntity> optionalTestEntity = this.getById(TestEntity.class, id);

            if (optionalTestEntity.isPresent()) {
                this.delete(optionalTestEntity.get());
            } else {
                event.error(1, Messages.ERROR_ENTITY_NOT_FOUND);
            }
        } else {
            event.error(1, Messages.ID_NOT_PROVIDED);
        }
    }

    public Optional<TestEntity> getTestBeanById(HashMap args) {
        if (args.get(Constants.FIELD_ID) != null) {
            Long id = Long.valueOf( (String) args.get(Constants.FIELD_ID) );
            return this.getById(TestEntity.class, id);
        } else {
            event.error(1, Messages.ID_NOT_PROVIDED);
            return Optional.empty();
        }
    }
}

