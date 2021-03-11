package ru.sfedu.Arch.lab3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.TablePerClass.api.Api3;
import ru.sfedu.Arch.lab3.TablePerClass.model.Comment;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.HibernateUtil;
import ru.sfedu.Arch.utils.Messages;

import java.util.Optional;
import java.util.UUID;

public class EntityApi {

    private static final Logger logger = LogManager.getLogger(Api3.class);
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
     * @param bean Entity bean for data source
     * @return Result - result of execution
     */
    public <T> Result saveBean (T bean) {
        try {
            event.info(1, Messages.SAVE_BEAN);
            Session session = this.getSession();
            event.debug(1, Messages.BEGIN_TRANSACTION);
            Transaction transaction = session.beginTransaction();
            UUID id = (UUID) session.save(bean);
            event.debug(2, String.format(Messages.COMMIT_BEAN, id));
            transaction.commit();
            return new Result(Enums.STATUS.success, id);
        } catch (Exception e) {
            event.error(1, e);
            event.error(2, Messages.ERROR_SAVE_BEAN);
            return new Result(Enums.STATUS.error, Messages.ERROR_SAVE_BEAN);
        }
    }

    /**
     * Get bean by id from data source
     * @param bean Class of entity
     * @param id identifier of searching entity
     * @return Result - result of execution
     */
    public <T> Result getBeanById(Class<T> bean, UUID id) {
        try {
            event.info(1, Messages.FIND_ENTITY_BY_ID);
            Session session = this.getSession();
            T item = session.get(bean, id);
            event.debug(1, String.format(Messages.ENTITY_FOUND, item));
            session.close();
            if (item != null) {
                return new Result(Enums.STATUS.success, Optional.of(item));
            } else {
                return new Result(Enums.STATUS.error, Messages.ERROR_GET_BEAN);
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_GET_ENTITY_BY_ID);
            return new Result(Enums.STATUS.error, Messages.ERROR_GET_ENTITY_BY_ID);
        }
    }


    /**
     * Update bean by id from data source
     * @param bean Class of entity
     * @return Result - result of execution
     */
    public <T> Result updateBean(T bean) {
        try {
            event.info(1, Messages.UPDATE_BEAN);

            Session session = this.getSession();

            if (session != null) {
                Transaction transaction = session.beginTransaction();
                event.debug(1, String.format(Messages.UPDATE_BEAN_FORMAT, bean));
                session.update(bean);
                transaction.commit();
                return new Result(Enums.STATUS.success, Messages.SUCCESS_BEAN_UPDATED);
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
                return new Result(Enums.STATUS.error, Messages.ERROR_GET_SESSION);
            }

        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_UPDATE_BEAN);
            return new Result(Enums.STATUS.error, Messages.ERROR_UPDATE_BEAN);
        }
    }


    public <T> Result deleteBean(T bean) {
        try {
            event.info(1, Messages.UPDATE_BEAN);

            Session session = this.getSession();

            if (session != null) {
                Transaction transaction = session.beginTransaction();
                event.debug(1, String.format(Messages.DELETE_BEAN_FORMAT, bean));
                session.delete(bean);
                transaction.commit();
                return new Result(Enums.STATUS.success, Messages.SUCCESS_BEAN_DELETED);
            } else {
                event.error(1, Messages.ERROR_GET_SESSION);
                return new Result(Enums.STATUS.error, Messages.ERROR_GET_SESSION);
            }

        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_REMOVE_BEAN);
            return new Result(Enums.STATUS.error, Messages.ERROR_REMOVE_BEAN);
        }
    }
}
