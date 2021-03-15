package ru.sfedu.Arch.lab5.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.EntityApi;
import ru.sfedu.Arch.lab5.model.Presentation;
import ru.sfedu.Arch.lab5.model.Slide;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.Messages;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Result updateSlide (Slide slide) {
        try {
            return updateBean(slide);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_UPDATE);
        }
    }

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
}
