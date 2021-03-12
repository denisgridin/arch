package ru.sfedu.Arch.lab3.TablePerClass.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.TablePerClass.model.Comment;
import ru.sfedu.Arch.providers.ITablePerClassProvider;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.HibernateUtil;
import ru.sfedu.Arch.utils.Messages;

import java.sql.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class Api3 implements ITablePerClassProvider {


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
     * Save comment bean in data source
     * @param comment Entity bean for data source
     * @return Long - id of element
     */
    @Override
    public Result saveComment (Comment comment) {
        try {
            event.info(1, Messages.SAVE_BEAN);
            Session session = this.getSession();
            event.debug(1, Messages.BEGIN_TRANSACTION);
            Transaction transaction = session.beginTransaction();
            UUID id = (UUID) session.save(comment);
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
     * Get comment bean by id from data source
     * @param comment Class of entity
     * @param id identifier of searching entity
     * @return Optional - optional comment element
     */
    @Override
    public Result getCommentById(Class<Comment> comment, UUID id) {
        try {
            event.info(1, Messages.FIND_ENTITY_BY_ID);
            Session session = this.getSession();
            Comment item = session.get(comment, id);
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
     * Update comment
     * @param comment - updated comment bean
     * @return Result - result of execution
     */
    @Override
    public Result updateComment(Comment comment) {
        try {
            event.info(1, Messages.UPDATE_BEAN);

            Session session = this.getSession();

            if (session != null) {
                Transaction transaction = session.beginTransaction();
                event.debug(1, String.format(Messages.UPDATE_BEAN_FORMAT, comment));
                session.update(comment);
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


    /**
     * Delete comment
     * @param comment - comment bean for removing
     * @return Result - result of execution
     */
    public Result deleteComment(Comment comment) {
        try {
            event.info(1, Messages.UPDATE_BEAN);

            Session session = this.getSession();

            Result result = getCommentById(Comment.class, comment.getId());

            if (result.getStatus() == Enums.STATUS.success) {
                if (session != null) {
                    Transaction transaction = session.beginTransaction();
                    event.debug(1, String.format(Messages.DELETE_BEAN_FORMAT, comment));
                    session.delete(comment);
                    transaction.commit();
                    return new Result(Enums.STATUS.success, Messages.SUCCESS_BEAN_DELETED);
                } else {
                    event.error(1, Messages.ERROR_GET_SESSION);
                    return new Result(Enums.STATUS.error, Messages.ERROR_GET_SESSION);
                }
            } else {
                event.error(1, Messages.ERROR_GET_BEAN);
                return new Result(Enums.STATUS.error, Messages.ERROR_GET_BEAN);
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_REMOVE_BEAN);
            return new Result(Enums.STATUS.error, Messages.ERROR_REMOVE_BEAN);
        }
    }


    /**
     * Method of comment creation from cli
     * @param args - HashMap of arguments
     * @return Result - result of execution
     */
    public Result buildAndSaveComment(HashMap args) {
        try {
            Comment comment = new Comment();

            comment.setRole(Enums.Role.valueOf((String) args.get(Constants.FIELD_ROLE)));
            comment.setDatetime(String.valueOf(new Date(System.currentTimeMillis())));
            comment.setText((String) args.get(Constants.FIELD_TEXT));
            comment.setPresentationId((UUID) args.get(Constants.FIELD_PRESENTATION_ID));

            return this.saveComment(comment);
        } catch (Exception error) {
            return new Result(Enums.STATUS.error, error);
        }
    }


    /**
     * Method of comment updating from cli
     * @param args - HashMap of arguments
     * @return Result - result of execution
     */
    public Result buildAndUpdateComment(HashMap args) {
        try {
            Result resultGetComment = getCommentById(Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
            if (resultGetComment.getStatus() == Enums.STATUS.success) {
                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
                Comment comment = optionalComment.get();

                comment.setPresentationId(UUID.fromString(String.valueOf(args.getOrDefault(Constants.FIELD_PRESENTATION_ID, comment.getPresentationId()))));
                comment.setRole(Enums.Role.valueOf(String.valueOf(args.getOrDefault(Constants.FIELD_ROLE, comment.getRole()))));
                comment.setText((String) args.getOrDefault(Constants.FIELD_TEXT, comment.getText()));
                return updateComment(comment);
            } else {
                return new Result(Enums.STATUS.error, Messages.ERROR_GET_BEAN);
            }

        } catch (Exception error) {
            event.error(1, error);
            return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
        }
    }


    /**
     * Method of comment removing from cli
     * @param args - HashMap of arguments
     * @return Result - result of execution
     */
    public Result buildAndDeleteComment(HashMap args) {
        try {
            Result resultGetComment = getCommentById(Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
            if (resultGetComment.getStatus() == Enums.STATUS.success) {
                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
                Comment comment = optionalComment.get();
                return deleteComment(comment);
            } else {
                return new Result(Enums.STATUS.error, Messages.ERROR_GET_BEAN);
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_REMOVE_BEAN);
            return new Result(Enums.STATUS.error, Messages.ERROR_REMOVE_BEAN);
        }
    }
}
