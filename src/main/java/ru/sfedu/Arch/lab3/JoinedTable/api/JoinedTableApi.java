//package ru.sfedu.Arch.lab3.JoinedTable.api;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import ru.sfedu.Arch.Constants;
//import ru.sfedu.Arch.Enums;
//import ru.sfedu.Arch.Result;
//import ru.sfedu.Arch.lab3.JoinedTable.model.Comment;
//import ru.sfedu.Arch.providers.IJoinedTableProvider;
//import ru.sfedu.Arch.utils.EventWrapper;
//import ru.sfedu.Arch.utils.HibernateUtil;
//import ru.sfedu.Arch.utils.Messages;
//
//import java.sql.Date;
//import java.util.HashMap;
//import java.util.Optional;
//import java.util.UUID;
//import ru.sfedu.Arch.lab3.EntityApi;
//
//public class JoinedTableApi extends EntityApi implements IJoinedTableProvider {
//
//
//    private static final Logger logger = LogManager.getLogger(Api3.class);
//    private static final EventWrapper event = new EventWrapper(logger);
//
//
//    /**
//     * Connect to Hibernate and connect to session
//     * @return Session object or null value
//     */
//    private Session getSession () {
//        try {
//            event.info(1, Messages.GET_HIBERNATE_SESSION);
//            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//            event.debug(1, Messages.SESSION_SUCCESS);
//            return sessionFactory.openSession();
//        } catch (Exception error) {
//            event.error(1, error);
//            event.error(2, Messages.ERROR_GET_SESSION);
//            return null;
//        }
//    }
//
//
//    /**
//     * Save comment bean in data source
//     * @param comment Entity bean for data source
//     * @return Long - id of element
//     */
//    @Override
//    public Result saveComment (Comment comment) {
//        try {
//            return saveBean(comment);
//        } catch (Exception e) {
//            event.error(1, e);
//            event.error(2, Messages.ERROR_SAVE_BEAN);
//            return new Result(Enums.STATUS.error, Messages.ERROR_SAVE_BEAN);
//        }
//    }
//
//    /**
//     * Get comment bean by id from data source
//     * @param comment Class of entity
//     * @param id identifier of searching entity
//     * @return Optional - optional comment element
//     */
//    @Override
//    public Result getCommentById(Class<Comment> comment, UUID id) {
//        try {
//            return getBeanById(comment, id);
//        } catch (Exception error) {
//            event.error(1, error);
//            event.error(2, Messages.ERROR_GET_ENTITY_BY_ID);
//            return new Result(Enums.STATUS.error, Messages.ERROR_GET_ENTITY_BY_ID);
//        }
//    }
//
//    @Override
//    public Result updateComment(Comment comment) {
//        try {
//            return updateBean(comment);
//        } catch (Exception error) {
//            event.error(1, error);
//            event.error(2, Messages.ERROR_UPDATE_BEAN);
//            return new Result(Enums.STATUS.error, Messages.ERROR_UPDATE_BEAN);
//        }
//    }
//
//    @Override
//    public Result deleteComment(Comment comment) {
//        try {
//            return deleteBean(comment);
//        } catch (Exception error) {
//            event.error(1, error);
//            event.error(2, Messages.ERROR_REMOVE_BEAN);
//            return new Result(Enums.STATUS.error, Messages.ERROR_REMOVE_BEAN);
//        }
//    }
//}
