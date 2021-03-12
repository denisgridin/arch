package ru.sfedu.Arch.lab4.setCollection.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.EntityApi;
import ru.sfedu.Arch.lab4.setCollection.model.Presentation;
import ru.sfedu.Arch.lab4.setCollection.model.Slide;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.Messages;

import java.util.*;
import java.util.stream.Collectors;


public class SetCollectionApi extends EntityApi {
    private static final Logger logger = LogManager.getLogger(SetCollectionApi.class);
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
                    Set<Slide> slideSet = presentation.getSlides();
                    slideSet.add(slide);
                    presentation.setSlides(slideSet);
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

                Set<Slide> slideList = presentation.getSlides();
                Set<Slide> updatedList = slideList.stream().filter(slide -> !slide.getId().equals(slideId)).collect(Collectors.toSet());

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

                Set<Slide> slideList = presentation.getSlides();
                Optional<Slide> updatingSlide = slideList.stream().filter(el -> el.getId().equals(slideId)).findFirst();
                if (updatingSlide.isPresent()) {
                    Set<Slide> updatedSlides = slideList.stream().map(el -> {
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
                    }).collect(Collectors.toSet());
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

                Set<Slide> slideList = presentation.getSlides();
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
