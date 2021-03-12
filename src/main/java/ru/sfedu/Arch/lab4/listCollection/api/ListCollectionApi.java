package ru.sfedu.Arch.lab4.listCollection.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.EntityApi;
import ru.sfedu.Arch.lab4.listCollection.model.Presentation;
import ru.sfedu.Arch.lab4.listCollection.model.Slide;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.Messages;

import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.CheckedOutputStream;


public class ListCollectionApi extends EntityApi {
    private static final Logger logger = LogManager.getLogger(ListCollectionApi.class);
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
                    List<Slide> slideList = presentation.getSlides();
                    slideList.add(slide);
                    presentation.setSlides(slideList);
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

                List<Slide> slideList = presentation.getSlides();
                List<Slide> updatedList = slideList.stream().filter(slide -> !slide.getId().equals(slideId)).collect(Collectors.toList());

                if (updatedList.size() != slideList.size()) {
                    Transaction transaction = session.beginTransaction();
                    presentation.setSlides(updatedList);
                    event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_SLIDES, updatedList));
                    session.merge(presentation);
                    transaction.commit();
                    return new Result(Enums.STATUS.success, Messages.SUCCESS_SLIDE_REMOVED);
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

                List<Slide> slideList = presentation.getSlides();
                Optional<Slide> updatingSlide = slideList.stream().filter(el -> el.getId().equals(slideId)).findFirst();
                if (updatingSlide.isPresent()) {
                    List<Slide> updatedSlides = slideList.stream().map(el -> {
                        if (el.getId().equals(slideId)) {
                            Result result = updateSlideBean(el, args);
                            if (result.getStatus() == Enums.STATUS.success) {
                                return (Slide) result.getReturnValue();
                            } else {
                                event.error(1, Messages.ERROR_SLIDE_UPDATE);
                                return el;
                            }
                        } else {
                            return el;
                        }
                    }).collect(Collectors.toList());
                    Transaction transaction = session.beginTransaction();
                    presentation.setSlides(updatedSlides);
                    event.debug(2, String.format(Messages.SET_FIELD, Constants.FIELD_SLIDES, updatedSlides));
                    session.merge(presentation);
                    transaction.commit();
                    return new Result(Enums.STATUS.success, Messages.SUCCESS_SLIDE_UPDATED);
                } else {
                    return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_GET);
                }
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
     * Update slide bean
     * @param slide - Slide bean, that will be added to presentation
     * @param args - parameters of updating fields
     * @return Result - result of execution
     */
    private Result updateSlideBean (Slide slide, HashMap args) {
        try {
            int index = Integer.valueOf(String.valueOf(args.getOrDefault(Constants.FIELD_INDEX, slide.getIndex())));
            String name = (String) args.getOrDefault(Constants.FIELD_NAME, slide.getName());
            slide.setIndex(index);
            slide.setName(name);
            return new Result(Enums.STATUS.success, slide);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_UPDATE);
        }
    }

    public Result getSlideById (UUID slideId, UUID presentationId) {
        try {
            Result resultGetPresentation = getPresentationById(presentationId);

            if (resultGetPresentation.getStatus() == Enums.STATUS.success) {
                Optional<Presentation> optionalPresentation = (Optional<Presentation>) resultGetPresentation.getReturnValue();
                Presentation presentation = optionalPresentation.get();

                List<Slide> slideList = presentation.getSlides();
                Optional<Slide> optionalSlide = slideList.stream().filter(el -> el.getId().equals(slideId)).findFirst();

                if (optionalSlide.isPresent()) {
                    event.info(1, optionalSlide);
                    return new Result(Enums.STATUS.success, optionalSlide);
                } else {
                    event.error(1, Messages.ERROR_SLIDE_GET);
                    return new Result(Enums.STATUS.success, Messages.ERROR_SLIDE_GET);
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
