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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class MapCollectionApi extends EntityApi {
    private static final Logger logger = LogManager.getLogger(MapCollectionApi.class);
    private static final EventWrapper event = new EventWrapper(logger);

    private final Session session = getSession();

    public Result savePresentation (Presentation presentation) {
        try {
            return saveBean(presentation);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_PRESENTATION_SAVE);
            return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_SAVE);
        }
    }

    public Result getPresentationById (UUID id) {
        try {
            return getBeanById(Presentation.class, id);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_GET_PRESENTATION);
            return new Result(Enums.STATUS.error, Messages.ERROR_GET_PRESENTATION);
        }
    }


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

    public Result getSlideById (UUID id) {
        try {
            return getBeanById(Presentation.class, id);
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_GET_SLIDE);
            return new Result(Enums.STATUS.error, Messages.ERROR_GET_SLIDE);
        }
    }
}
