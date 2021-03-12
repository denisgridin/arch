package ru.sfedu.Arch.lab4.mapCollection.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.EntityApi;
import ru.sfedu.Arch.lab4.mapCollection.model.Presentation;
import ru.sfedu.Arch.lab4.mapCollection.model.Slide;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.Messages;

import java.util.*;
import java.util.stream.Collectors;


public class MapCollectionApi extends EntityApi {
    private static final Logger logger = LogManager.getLogger(MapCollectionApi.class);
    private static final EventWrapper event = new EventWrapper(logger);

    private final Session session = getSession();

    /**
     * Save presentation in data source
     * @return Result - result of execution
     */
    public Result savePresentation (Presentation presentation) {
        try {
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
     * @param slide - Slide bean, that will be added to presentation
     * @param presentationId - identifier of presentation to search
     * @return Result - result of execution
     */
    public Result addPresentationSlide (Slide slide, UUID presentationId) {
        try {
            Result resultFindPresentation = getPresentationById(presentationId);

            if (resultFindPresentation.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) resultFindPresentation.getReturnValue();

                if (optionalPresentation.isPresent()) {
                    Presentation presentation = optionalPresentation.get();
                    event.debug(1, String.format(Messages.PRESENTATION_FOUND, presentation.toString()));

                    Transaction transaction = session.beginTransaction();
                    Map slideMap = presentation.getSlides();
                    slideMap.put(String.valueOf(UUID.randomUUID()), slide.getName());
                    session.merge(presentation);
                    event.debug(2, String.format(Messages.SET_FIELD, Constants.FIELD_SLIDES, presentation.getSlides()));
                    transaction.commit();

                    return new Result(Enums.STATUS.success, Messages.SUCCESS_SLIDES_ADDED);
                } else {
                    return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_GET);
                }
            } else {
                return new Result(Enums.STATUS.error, Messages.ERROR_ADD_SLIDES);
            }

        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_SAVE);
            return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_SAVE);
        }
    }

    /**
     * Get presentation by id from data source
     * @param slideId - Slide bean, that will be added to presentation
     * @param presentationId - identifier of presentation to search
     * @return Result - result of execution
     */
    public Result removePresentationSlide (UUID slideId, UUID presentationId) {
        try {
            Result resultGetPresentation = getPresentationById(presentationId);

            if (resultGetPresentation.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) resultGetPresentation.getReturnValue();
                Presentation presentation = optionalPresentation.get();

                Map<String, String> slidesMap = presentation.getSlides();
                Map map = slidesMap;
                slidesMap.remove(String.valueOf(slideId));
                return new Result(Enums.STATUS.success, Messages.SUCCESS_SLIDE_REMOVED);

            } else {
                event.error(1, Messages.ERROR_PRESENTATION_GET);
                return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_GET);
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_REMOVE_BEAN);
            return new Result(Enums.STATUS.error, Messages.ERROR_REMOVE_BEAN);
        }
    }


    /**
     * Get presentation by id from data source
     * @param slideId - Slide id, that will be updated
     * @param presentationId - identifier of presentation to search
     * @param args - HashMap of updating fields
     * @return Result - result of execution
     */
    public Result updatePresentationSlide (UUID slideId, UUID presentationId, HashMap args) {
        try {
            Result resultGetPresentation = getPresentationById(presentationId);

            if (resultGetPresentation.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) resultGetPresentation.getReturnValue();
                Presentation presentation = optionalPresentation.get();

                Map<String, String> slidesMap = presentation.getSlides();

                String name = (String) args.getOrDefault(Constants.FIELD_NAME, slidesMap.get(String.valueOf(slideId)));
                slidesMap.put(String.valueOf(slideId), name);
                event.debug(1, slidesMap);
                Transaction transaction = session.beginTransaction();
                presentation.setSlides(slidesMap);
                event.debug(2, String.format(Messages.SET_FIELD, Constants.FIELD_SLIDES, slidesMap));
                session.merge(presentation);
                transaction.commit();
                return new Result(Enums.STATUS.success, Messages.SUCCESS_SLIDE_UPDATED);

            } else {
                event.error(1, Messages.ERROR_PRESENTATION_GET);
                return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_GET);
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_UPDATE);
        }
    }


    /**
     * Get slide by id
     * @param slideId - Slide id
     * @param presentationId - Presentation id
     * @return Result - result of execution
     */
    public Result getSlideById (UUID slideId, UUID presentationId) {
        try {
            Result resultGetPresentation = getPresentationById(presentationId);

            if (resultGetPresentation.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) resultGetPresentation.getReturnValue();
                Presentation presentation = optionalPresentation.get();

                Map<String, String> slidesMap = presentation.getSlides();

                String name = slidesMap.get(String.valueOf(slideId));

                if (name != null) {
                    event.debug(1, name);
                    return new Result(Enums.STATUS.success, name);
                } else {
                    event.error(1, Messages.ERROR_SLIDE_GET);
                    return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_GET);
                }

            } else {
                event.error(1, Messages.ERROR_PRESENTATION_GET);
                return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_GET);
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_GET);
        }
    }
}
