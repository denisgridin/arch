package ru.sfedu.Arch.lab5.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.NativeQuery;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.EntityApi;
import ru.sfedu.Arch.lab5.model.*;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.Messages;

import java.util.*;

public class ApiProvider extends EntityApi {
    private static final Logger logger = LogManager.getLogger(ApiProvider.class);
    private static final EventWrapper event = new EventWrapper(logger);

    private final Session session = getSession();

    /**
     * Save presentation in data source
     * @return Result - result of execution
     */
    public Result savePresentation (Presentation presentation) {
        try {
            event.info(1, presentation);
            return saveBean(presentation);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_PRESENTATION_SAVE);
            return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_SAVE);
        }
    }

    /**
     * Get presentation by id from data source
     * @param id - identifier of presentation to search
     * @return Result - result of execution
     */
    public Result getPresentationById (UUID id) {
        try {
            return getBeanById(Presentation.class, id);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_GET_PRESENTATION);
            return new Result(Enums.STATUS.error, Messages.ERROR_GET_PRESENTATION);
        }
    }

    /**
     * Get presentation by id from data source
     * @param id - identifier of presentation to search
     * @return Result - result of execution
     */
    public Result deletePresentationById (UUID id) {
        try {
            Result result = getPresentationById(id);
            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) result.getReturnValue();
                Presentation presentation = optionalPresentation.get();
                event.info(1, String.format(Messages.ATTEMPT_REMOVE_PRESENTATION, presentation));
                return deleteBean(presentation);
            } else {
                event.error(1, Messages.ERROR_PRESENTATION_GET);
                return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_GET);
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_GET_PRESENTATION);
            return new Result(Enums.STATUS.error, Messages.ERROR_GET_PRESENTATION);
        }
    }

    /**
     * Update presentation in data source
     * @param presentation - updated presentation
     * @return Result - result of execution
     */
    public Result updatePresentation (Presentation presentation) {
        try {
            return updateBean(presentation);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_GET_PRESENTATION);
            return new Result(Enums.STATUS.error, Messages.ERROR_GET_PRESENTATION);
        }
    }

    /**
     * Retrieve slides from data source
     * @param presentationId - presentation identifier
     * @return Result - result of execution
     */
    public Result getPresentationSlides (UUID presentationId) {
        try {
            Result resultGetPresentation = getPresentationById(presentationId);
            if (resultGetPresentation.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) resultGetPresentation.getReturnValue();
                Presentation presentation = optionalPresentation.get();

                return new Result(Enums.STATUS.success, presentation.getSlides());
            } else {
                return resultGetPresentation;
            }
        } catch (Exception error) {
            event.error( 1, error);
            event.error(2, Messages.ERROR_SLIDES_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDES_GET);
        }
    }



    /**
     * Add slides in data source
     * @param slide - Slide bean
     * @param presentationId - presentation identifier
     * @return Result - result of execution
     */
    public Result addPresentationSlide (Slide slide, UUID presentationId) {
        try {

            event.info(2, String.format(Messages.SHOW_BEAN, presentationId));

            Result result = getPresentationById(presentationId);

            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) result.getReturnValue();
                Presentation presentation = optionalPresentation.get();
                slide.setPresentation(presentation);

                Transaction transaction = session.beginTransaction();
                event.debug(1, slide);
                session.persist(slide);
                transaction.commit();
                session.close();

                return new Result(Enums.STATUS.success, slide);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_CREATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_CREATE);
        }
    }


    /**
     * Retrieve slide data source by id
     * @param slideId - Slide id
     * @return Result - result of execution
     */
    public Result getSlideById (UUID slideId) {
        try {
            event.info(2, String.format(Messages.SHOW_BEAN, slideId));
            return getBeanById(Slide.class, slideId);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_GET);
        }
    }

    /**
     * Updating slide in data source
     * @param slide - updated Slide bean
     * @return Result - result of execution
     */
    public Result updateSlide (Slide slide) {
        try {
            return updateBean(slide);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_UPDATE);
        }
    }


    /**
     * Remove slide from data source
     * @param slideId - slide id
     * @return Result - result of execution
     */
    public Result deleteSlide (UUID slideId) {
        try {
            Result result = getSlideById(slideId);

            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Slide> optionalSlide = (Optional<Slide>) result.getReturnValue();
                Slide slide = optionalSlide.get();
                return deleteBean(slide);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_DELETE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_DELETE);
        }
    }


    /*              comments section                */

    /**
     * Retrieve slides from data source
     * @param presentationId - presentation identifier
     * @return Result - result of execution
     */
    public Result getPresentationComments (UUID presentationId) {
        try {
            Result resultGetPresentation = getPresentationById(presentationId);
            if (resultGetPresentation.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) resultGetPresentation.getReturnValue();
                Presentation presentation = optionalPresentation.get();

                return new Result(Enums.STATUS.success, presentation.getComments());
            } else {
                return resultGetPresentation;
            }
        } catch (Exception error) {
            event.error( 1, error);
            event.error(2, Messages.ERROR_COMMENTS_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_COMMENTS_GET);
        }
    }


    /**
     * Add comment in data source
     * @param comment - Comment bean
     * @param presentationId - presentation identifier
     * @return Result - result of execution
     */
    public Result addPresentationComment (Comment comment, UUID presentationId) {
        try {

            event.info(2, String.format(Messages.SHOW_BEAN, presentationId));

            Result result = getPresentationById(presentationId);

            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) result.getReturnValue();
                Presentation presentation = optionalPresentation.get();
                comment.setPresentation(presentation);
                event.debug(1, String.format(Messages.SHOW_BEAN, comment));
                Transaction transaction = session.beginTransaction();
                event.debug(1, comment);
                session.persist(comment);
                transaction.commit();
                session.close();

                return new Result(Enums.STATUS.success, comment);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_COMMENT_CREATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_COMMENT_CREATE);
        }
    }


    /**
     * Get comment from data source
     * @param commentId - Comment id
     * @return Result - result of execution
     */
    public Result getCommentById (UUID commentId) {
        try {
            event.info(2, String.format(Messages.SHOW_BEAN, commentId));
            return getBeanById(Comment.class, commentId);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_GET);
        }
    }


    /**
     * Updating comment in data source
     * @param comment - Comment bean
     * @return Result - result of execution
     */
    public Result updateComment (Comment comment) {
        try {
            return updateBean(comment);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_COMMENT_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_COMMENT_UPDATE);
        }
    }


    /**
     * Remove comment from data source
     * @param commentId - Comment id
     * @return Result - result of execution
     */
    public Result deleteComment (UUID commentId) {
        try {
            Result result = getCommentById(commentId);

            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Comment> optionalComment = (Optional<Comment>) result.getReturnValue();
                Comment comment = optionalComment.get();
                return deleteBean(comment);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_COMMENT_DELETE);
            return new Result(Enums.STATUS.error, Messages.ERROR_COMMENT_DELETE);
        }
    }

    /*        Shape section          */


    /**
     * Get slide elements from data source
     * @param slideId - Slide id
     * @return Result - result of execution
     */
    public Result getSlideElements (UUID slideId) {
        try {
            Result resultGetSlide = getSlideById(slideId);
            if (resultGetSlide.getStatus() == Enums.STATUS.success) {
                Optional<Slide> optionalSlide = (Optional<Slide>) resultGetSlide.getReturnValue();
                Slide slide = optionalSlide.get();

                return new Result(Enums.STATUS.success, slide.getElements());
            } else {
                return resultGetSlide;
            }
        } catch (Exception error) {
            event.error( 1, error);
            event.error(2, Messages.ERROR_ELEMENTS_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_ELEMENTS_GET);
        }
    }

    /**
     * Add shape in slide
     * @param shape - Shape bean
     * @param slideId - parent Slide id
     * @return Result - result of execution
     */
    public Result addSlideShape(Shape shape, UUID slideId) {
        try {
            event.info(2, String.format(Messages.SHOW_BEAN, slideId));
            Result result = getSlideById(slideId);
            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Slide> optionalSlide = (Optional<Slide>) result.getReturnValue();
                Slide slide = optionalSlide.get();
                shape.setSlide(slide);
                Session s = getSession();
                Transaction transaction = s.beginTransaction();
                event.debug(1, slide);
                s.persist(shape);
                transaction.commit();
                s.close();

                return new Result(Enums.STATUS.success, shape);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_ELEMENT_CREATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_ELEMENT_CREATE);
        }
    }

    /**
     * Add content in slide
     * @param content - Content bean
     * @param slideId - parent Slide id
     * @return Result - result of execution
     */
    public Result addSlideContent(Content content, UUID slideId) {
        try {
            event.info(2, String.format(Messages.SHOW_BEAN, slideId));
            Result result = getSlideById(slideId);
            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Slide> optionalSlide = (Optional<Slide>) result.getReturnValue();
                Slide slide = optionalSlide.get();
                content.setSlide(slide);
                Session s = getSession();
                Transaction transaction = s.beginTransaction();
                event.debug(1, slide);
                s.persist(content);
                transaction.commit();
                s.close();

                return new Result(Enums.STATUS.success, content);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_ELEMENT_CREATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_ELEMENT_CREATE);
        }
    }


    /**
     * Add content in slide
     * @param element - Element bean
     * @param slideId - parent Slide id
     * @return Result - result of execution
     */
    public Result updateSlideElement (Element element, UUID slideId) {
        try {
            return updateBean(element);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_COMMENT_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_COMMENT_UPDATE);
        }
    }


    /**
     * Get slide element
     * @param id - element id
     * @return Result - result of execution
     */
    public Result getSlideElementById (UUID id) {
        try {
            event.info(1, String.format(Messages.SHOW_BEAN, id));
            return getBeanById(Element.class, id);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_GET);
        }
    }


    /**
     * Remove element from slide
     * @param id - id of element
     * @return Result - result of execution
     */
    public Result deleteSlideElement (UUID id) {
        try {
            Result result = getSlideElementById(id);
            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Element> optionalElement = (Optional<Element>) result.getReturnValue();
                Element element = optionalElement.get();
                return deleteBean(element);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_COMMENT_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_COMMENT_UPDATE);
        }
    }


    /*                Assessments section                   */

    /**
     * Generate assessment
     * @param presentationId - id of presentation
     * @return Result - result of execution
     */
    public Result generateAssessments (UUID presentationId) {
        try {
            Result result = getPresentationById(presentationId);
            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) result.getReturnValue();
                Presentation presentation = optionalPresentation.get();

                Session s = getSession();
                Transaction transaction = s.beginTransaction();
                Set<Assessment> assessments = presentation.getAssessments();

                Assessment assessment = new Assessment();
                assessment.setMark(Enums.Mark.bad);
                assessment.setRole(Enums.Role.editor);

                Set<Presentation> presentationSet = new HashSet();
                presentationSet.add(presentation);
                assessments.add(assessment);

                assessment.setPresentations(presentationSet);
                presentation.setAssessments(assessments);
                s.persist(assessment);
                s.merge(presentation);
                transaction.commit();
                s.close();
                return new Result(Enums.STATUS.success, assessment);
            } else {
                return result;
            }

        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_ADD_ENTITIES);
            return new Result(Enums.STATUS.error, Messages.ERROR_ADD_ENTITIES);
        }
    }


    /**
     * Get assessment by id
     * @param id - id of assessment
     * @return Result - result of execution
     */
    public Result getAssessmentById (UUID id) {
        try {
            event.info(1, String.format(Messages.SHOW_BEAN, id));
            return getBeanById(Assessment.class, id);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_ASSESSMENT_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_ASSESSMENT_GET);
        }
    }

    /**
     * Update assessment
     * @param assessment - Assessment bean
     * @return Result - result of execution
     */
    public Result updateAssessment (Assessment assessment) {
        try {
            return updateBean(assessment);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_ASSESSMENT_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_ASSESSMENT_UPDATE);
        }
    }


    /**
     * Remove assessment
     * @param id - Assessment id
     * @return Result - result of execution
     */
    public Result deleteAssessment (UUID id) {
        try {
            Result result = getAssessmentById(id);
            if (result.getStatus() == Enums.STATUS.success) {
                Optional<Assessment> optionalAssessment = (Optional<Assessment>) result.getReturnValue();
                Assessment assessment = optionalAssessment.get();
                return deleteBean(assessment);
            } else {
                return result;
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_ASSESSMENT_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_ASSESSMENT_UPDATE);
        }
    }

    public Result getSummaryCriteria(){
        try{
            int startTime = (int) System.currentTimeMillis();
            Session session = this.getSession();
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Presentation.class);
            criteria.setProjection(Projections.count(Constants.FIELD_ID));
            List totalCount = criteria.list();
            transaction.commit();
            event.debug(1, totalCount);
            int methodTime = (int) (System.currentTimeMillis() - startTime);
            return new Result(Enums.STATUS.success, String.format(Messages.SHOW_SUMMARY_INFO, totalCount.get(0), methodTime));
        } catch (Exception e) {
            event.error(1, e);
            return new Result(Enums.STATUS.error, e);
        }
    }

    public Result getSummaryHQL(){
        try{
            int startTime = (int) System.currentTimeMillis();
            Session session = this.getSession();
            Query query = session.createQuery(Constants.HQL_ALL_PRESENTATIONS);
            List presentationList = query.list();
            event.debug(1, presentationList.size());
            int methodTime = (int) (System.currentTimeMillis() - startTime);
            return new Result(Enums.STATUS.success, String.format(Messages.SHOW_SUMMARY_INFO, presentationList.size(), methodTime));
        } catch (Exception e) {
            event.error(1, e);
            return new Result(Enums.STATUS.error, e);
        }
    }

    public Result getSummaryNative () {
        try {
            int startTime = (int) System.currentTimeMillis();
            Session session = getSession();
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_PRESENTATIONS);
            List resList = query.getResultList();
            session.close();
            int methodTime = (int) (System.currentTimeMillis() - startTime);
            return new Result(Enums.STATUS.success, String.format(Messages.SHOW_SUMMARY_INFO, resList.size(), methodTime));
        } catch (Exception error) {
            event.error(1, error);
            return new Result(Enums.STATUS.error, error);
        }
    }

}
