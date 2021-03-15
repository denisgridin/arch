package ru.sfedu.Arch.lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab4.listCollection.api.ListCollectionApi;
import ru.sfedu.Arch.lab4.mapCollection.api.MapCollectionApi;
import ru.sfedu.Arch.lab4.setCollection.api.SetCollectionApi;
import ru.sfedu.Arch.utils.EventWrapper;
import ru.sfedu.Arch.utils.Messages;

import java.util.HashMap;
import java.util.UUID;

public class CollectionApi {
    public CollectionApi () {}

    private static final Logger logger = LogManager.getLogger(CollectionApi.class);
    private static final EventWrapper event = new EventWrapper(logger);

    public Result buildAndSavePresentation (Enums.COLLECTION_STRATEGY strategy, HashMap args) {
        try {
            switch (strategy) {
                case SET: {
                    SetCollectionApi api = new SetCollectionApi();
                    event.info(1, Messages.ATTEMPT_SAVE_PRESENTATION);
                    ru.sfedu.Arch.lab4.setCollection.model.Presentation presentation = new ru.sfedu.Arch.lab4.setCollection.model.Presentation();
                    presentation.setName((String) args.get(Constants.FIELD_NAME));
                    presentation.setFillColor((String) args.get(Constants.FIELD_FILL_COLOR));
                    presentation.setFontFamily((String) args.get(Constants.FIELD_FONT_FAMILY));
                    event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, presentation));
                    return api.savePresentation(presentation);
                }
                case LIST: {
                    ListCollectionApi api = new ListCollectionApi();
                    event.info(1, Messages.ATTEMPT_SAVE_PRESENTATION);
                    ru.sfedu.Arch.lab4.listCollection.model.Presentation presentation = new ru.sfedu.Arch.lab4.listCollection.model.Presentation();
                    presentation.setName((String) args.get(Constants.FIELD_NAME));
                    presentation.setFillColor((String) args.get(Constants.FIELD_FILL_COLOR));
                    presentation.setFontFamily((String) args.get(Constants.FIELD_FONT_FAMILY));
                    event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, presentation));
                    return api.savePresentation(presentation);
                }
                case MAP: {
                    MapCollectionApi api = new MapCollectionApi();
                    event.info(1, Messages.ATTEMPT_SAVE_PRESENTATION);
                    ru.sfedu.Arch.lab4.mapCollection.model.Presentation presentation = new ru.sfedu.Arch.lab4.mapCollection.model.Presentation();
                    presentation.setName((String) args.get(Constants.FIELD_NAME));
                    presentation.setFillColor((String) args.get(Constants.FIELD_FILL_COLOR));
                    presentation.setFontFamily((String) args.get(Constants.FIELD_FONT_FAMILY));
                    event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, presentation));
                    return api.savePresentation(presentation);
                }
                default: {
                    event.error(1, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                    return new Result(Enums.STATUS.error, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                }
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_PRESENTATION_SAVE);
            return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_SAVE);
        }
    }

    public Result retrievePresentation (Enums.COLLECTION_STRATEGY strategy, HashMap args) {
        try {
            switch (strategy) {
                case SET: {
                    SetCollectionApi api = new SetCollectionApi();
                    event.info(1, Messages.ATTEMPT_GET_PRESENTATION);
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    if (id != null) {
                        event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, id));
                        return api.getPresentationById(id);
                    } else {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                }
                case LIST: {
                    ListCollectionApi api = new ListCollectionApi();
                    event.info(1, Messages.ATTEMPT_GET_PRESENTATION);
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    if (id != null) {
                        event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, id));
                        return api.getPresentationById(id);
                    } else {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                }
                case MAP: {
                    MapCollectionApi api = new MapCollectionApi();
                    event.info(1, Messages.ATTEMPT_GET_PRESENTATION);
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    if (id != null) {
                        event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, id));
                        return api.getPresentationById(id);
                    } else {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                }
                default: {
                    event.error(1, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                    return new Result(Enums.STATUS.error, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                }
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_PRESENTATION_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_PRESENTATION_GET);
        }
    }

    public Result addSlideInPresentation (Enums.COLLECTION_STRATEGY strategy, HashMap args) {
        try {
            switch (strategy) {
                case SET: {
                    SetCollectionApi api = new SetCollectionApi();
                    event.info(1, Messages.ATTEMPT_ADD_SLIDE);
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));
                    ru.sfedu.Arch.lab4.setCollection.model.Slide slide = new ru.sfedu.Arch.lab4.setCollection.model.Slide();
                    if (presentationId != null) {
                        event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, presentationId));
                        slide.setIndex(Integer.parseInt((String) args.get(Constants.FIELD_INDEX)));
                        slide.setName((String) args.get(Constants.FIELD_NAME));
                        slide.setId(UUID.randomUUID());
                        event.debug(2, String.format(Messages.FIELD_SLIDE, slide));
                        return api.addPresentationSlide(slide, presentationId);
                    } else {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                }
                case LIST: {
                    ListCollectionApi api = new ListCollectionApi();
                    event.info(1, Messages.ATTEMPT_ADD_SLIDE);
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));
                    ru.sfedu.Arch.lab4.listCollection.model.Slide slide = new ru.sfedu.Arch.lab4.listCollection.model.Slide();
                    if (presentationId != null) {
                        event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, presentationId));
                        slide.setIndex(Integer.parseInt((String) args.get(Constants.FIELD_INDEX)));
                        slide.setName((String) args.get(Constants.FIELD_NAME));
                        slide.setId(UUID.randomUUID());
                        event.debug(2, String.format(Messages.FIELD_SLIDE, slide));
                        return api.addPresentationSlide(slide, presentationId);
                    } else {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                }
                case MAP: {
                    MapCollectionApi api = new MapCollectionApi();
                    event.info(1, Messages.ATTEMPT_ADD_SLIDE);
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));
                    ru.sfedu.Arch.lab4.mapCollection.model.Slide slide = new ru.sfedu.Arch.lab4.mapCollection.model.Slide();
                    if (presentationId != null) {
                        event.debug(1, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION, presentationId));
                        slide.setIndex(Integer.parseInt((String) args.get(Constants.FIELD_INDEX)));
                        slide.setName((String) args.get(Constants.FIELD_NAME));
                        event.debug(2, String.format(Messages.FIELD_SLIDE, slide));
                        return api.addPresentationSlide(slide, presentationId);
                    } else {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                }
                default: {
                    event.error(1, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                    return new Result(Enums.STATUS.error, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                }
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_SAVE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_SAVE);
        }
    }

    public Result updatePresentationSlide (Enums.COLLECTION_STRATEGY strategy, HashMap args) {
        try {
            switch (strategy) {
                case SET: {
                    SetCollectionApi api = new SetCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.updatePresentationSlide(slideId, presentationId, args);
                }
                case LIST: {
                    ListCollectionApi api = new ListCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.updatePresentationSlide(slideId, presentationId, args);
                }
                case MAP: {
                    MapCollectionApi api = new MapCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.updatePresentationSlide(slideId, presentationId, args);
                }
                default: {
                    event.error(1, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                    return new Result(Enums.STATUS.error, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                }
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_UPDATE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_UPDATE);
        }
    }

    public Result getSlideById (Enums.COLLECTION_STRATEGY strategy, HashMap args) {
        try {
            switch (strategy) {
                case SET: {
                    SetCollectionApi api = new SetCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.getSlideById(slideId, presentationId);
                }
                case LIST: {
                    ListCollectionApi api = new ListCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.getSlideById(slideId, presentationId);
                }
                case MAP: {
                    MapCollectionApi api = new MapCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.getSlideById(slideId, presentationId);
                }
                default: {
                    event.error(1, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                    return new Result(Enums.STATUS.error, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                }
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_GET);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_GET);
        }
    }

    public Result removeSlide (Enums.COLLECTION_STRATEGY strategy, HashMap args) {
        try {
            switch (strategy) {
                case SET: {
                    SetCollectionApi api = new SetCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.removePresentationSlide(slideId, presentationId);
                }
                case LIST: {
                    ListCollectionApi api = new ListCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.removePresentationSlide(slideId, presentationId);
                }
                case MAP: {
                    MapCollectionApi api = new MapCollectionApi();
                    event.info(1, Messages.ATTEMPT_UPDATE_SLIDE);
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));

                    if (slideId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_ID));
                    }
                    if (presentationId == null) {
                        event.error(1, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                        return new Result(Enums.STATUS.error, String.format(Messages.ERROR_FIELD_NOT_SET, Constants.FIELD_PRESENTATION_ID));
                    }
                    event.info(1, String.format(Messages.SET_FIELD, Constants.FIELD_ID, slideId));
                    event.info(2, String.format(Messages.SET_FIELD, Constants.FIELD_PRESENTATION_ID, presentationId));
                    return api.removePresentationSlide(slideId, presentationId);
                }
                default: {
                    event.error(1, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                    return new Result(Enums.STATUS.error, Messages.ERROR_UNABLE_COLLECTION_STRATEGY);
                }
            }
        } catch (Exception error) {
            event.error(1, error);
            event.error(2, Messages.ERROR_SLIDE_DELETE);
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_DELETE);
        }
    }
}
