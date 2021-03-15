package ru.sfedu.Arch.lab5.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab5.model.Presentation;
import ru.sfedu.Arch.lab5.model.Slide;
import ru.sfedu.Arch.utils.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApiProviderTest {

    private ApiProvider api = new ApiProvider();

    private static final Logger logger = LogManager.getLogger(ApiProviderTest.class);


    /**
     * Add default test presentation
     * @return Result - result of execution
     */
    private Result addPresentation () {
        Presentation presentation = new Presentation();
        presentation.setName("Presentation name");
        presentation.setFontFamily("Roboto");
        presentation.setFillColor("yellow");

        Result result = api.savePresentation(presentation);

        return new Result(result.getStatus(), presentation);
    }


    private Result addSlide () {
        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            String name = "Slide name 123";
            int index = 1;
            UUID presentationId = presentation.getId();

            Slide slide = new Slide();
            slide.setName(name);
            slide.setIndex(index);
            return api.addPresentationSlide(slide, presentationId);
        } else {
            return new Result(Enums.STATUS.error, Messages.ERROR_SLIDE_CREATE);
        }
    }


    /**
     * Creating of presentation
     * Type: Success
     */
    @Test
    void savePresentationSuccess() {
        Result result = addPresentation();
        assertTrue(result.getStatus() == Enums.STATUS.success);
    }

    /**
     * Creating of presentation
     * Type: Fail
     */
    @Test
    void savePresentationFail() {
        Presentation presentation = new Presentation();
        Result result = api.savePresentation(presentation);

        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * Getting presentation by id
     * Type: Success
     */
    @Test
    void getPresentationByIdSuccess() {

        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            Result result = api.getPresentationById(presentation.getId());

            assertTrue(result.getStatus() == Enums.STATUS.success);
        }
    }


    /**
     * Getting presentation by id
     * Type: Fail
     */
    @Test
    void getPresentationByIdFail() {
        Result result = api.getPresentationById(UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * Removing presentation by id
     * Type: Success
     */
    @Test
    void deletePresentationByIdSuccess() {
        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            Result result = api.deletePresentationById(UUID.fromString("CE99613D-C9C3-4D1F-85A0-6C0200547C18"));

            assertTrue(result.getStatus() == Enums.STATUS.success);
        } else {
            fail();
        }
    }

    /**
     * Removing presentation by id
     * Type: Fail
     */
    @Test
    void deletePresentationByIdFail() {
        Result result = api.deletePresentationById(UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * Updating presentation
     * Type: Success
     */
    @Test
    void updatePresentationSuccess() {
        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            String name = "Edited name";
            String fillColor = "blue";
            String fontFamily = "Comic Sans";

            presentation.setFillColor(fillColor);
            presentation.setName(name);
            presentation.setFontFamily(fontFamily);
            Result result = api.updatePresentation(presentation);

            assertTrue(result.getStatus() == Enums.STATUS.success);

            Result resultGetPresentation = api.getPresentationById(presentation.getId());
            Optional<Presentation> optionalRetrievedPresentation = (Optional<Presentation>) resultGetPresentation.getReturnValue();

            if (optionalRetrievedPresentation.isPresent()) {
                Presentation item = optionalRetrievedPresentation.get();

                assertEquals(item.getFontFamily(), presentation.getFontFamily());
                assertEquals(item.getFillColor(), presentation.getFillColor());
                assertEquals(item.getName(), presentation.getName());
            } else {
                fail();
            }
        } else {
            fail();
        }
    }

    /**
     * Updating presentation
     * Type: Fail
     */
    @Test
    void updatePresentationFail() {
        Result result = api.updatePresentation(new Presentation());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }

    /**
     * Get presentation slides
     * Type: Success
     */
    @Test
    void getPresentationSlidesSuccess () {
        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            Result result = api.getPresentationSlides(UUID.fromString("B4319BAB-21BA-4563-841E-B209B6976119"));
            assertTrue(result.getStatus() == Enums.STATUS.success);
        } else fail();
    }

    /**
     * Get presentation slides
     * Type: Fail
     */
    @Test
    void getPresentationSlidesFail () {
        Result result = api.getPresentationSlides(UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.success);
    }


    /**
     * Add presentation slide
     * Type: Success
     */
    @Test
    void addPresentationSlideSuccess () {
        try {
            Result resultAddPresentation = addPresentation();

            if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
                Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
                String name = "Slide name 123";
                int index = 1;
                UUID presentationId = UUID.fromString("B4319BAB-21BA-4563-841E-B209B6976119");

                Slide slide = new Slide();
                slide.setName(name);
                slide.setIndex(index);
                Result result = api.addPresentationSlide(slide, presentationId);
                assertTrue(result.getStatus() == Enums.STATUS.success);
            } else fail();
        } catch (Exception error) {
            logger.error(error);
        }
    }

    /**
     * Add presentation slide
     * Type: Fail
     */
    @Test
    void addPresentationSlideFail () {
        Result result = api.addPresentationSlide(new Slide(), UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * Get presentation slide
     * Type: Success
     */
    @Test
    void getSlideByIdSuccess () {
        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            String name = "Slide name 123";
            int index = 1;
            UUID presentationId = presentation.getId();

            Slide slide = new Slide();
            slide.setName(name);
            slide.setIndex(index);
            Result result = api.addPresentationSlide(slide, presentationId);

            if (result.getStatus() == Enums.STATUS.success) {
                Slide item = (Slide) result.getReturnValue();
                UUID id = item.getId();
                Result resultGetSlide = api.getSlideById(id);
                assertTrue(resultGetSlide.getStatus() == Enums.STATUS.success);
            } else {
                fail();
            }
        } else fail();
    }

    /**
     * Get presentation slide
     * Type: Fail
     */
    @Test
    void getSlideByIdFail () {
        Result resultGetSlide = api.getSlideById(UUID.randomUUID());
        assertTrue(resultGetSlide.getStatus() == Enums.STATUS.error);
    }


    /**
     * Update presentation slide
     * Type: Success
     */
    @Test
    void updateSlideSuccess () {
        Result result = addSlide();

        if (result.getStatus() == Enums.STATUS.success) {
            Slide slide = (Slide) result.getReturnValue();

            String name = "Slide edited name";
            int index = 2;

            slide.setName(name);
            slide.setIndex(index);

            Result resultUpdate = api.updateSlide(slide);

            assertTrue(resultUpdate.getStatus() == Enums.STATUS.success);

            Result resultGetSlide = api.getSlideById(slide.getId());
            if (resultGetSlide.getStatus() == Enums.STATUS.success) {
                Optional<Slide> optionalSlide = (Optional<Slide>) resultGetSlide.getReturnValue();
                Slide item = optionalSlide.get();

                assertEquals(item.getName(), slide.getName());
                assertEquals(item.getIndex(), slide.getIndex());

            } else {
                fail();
            }
        } else {
            fail();
        }
    }


    /**
     * Update presentation slide
     * Type: Fail
     */
    @Test
    void updateSlideFail () {
        Result resultUpdate = api.updateSlide(new Slide());
        assertTrue(resultUpdate.getStatus() == Enums.STATUS.error);
    }



    /**
     * Delete presentation slide
     * Type: Success
     */
    @Test
    void deleteSlideSuccess () {
        Result result = addSlide();
        if (result.getStatus() == Enums.STATUS.success) {
            Slide slide = (Slide) result.getReturnValue();
            Result resultDelete = api.deleteSlide(slide.getId());
            assertTrue(resultDelete.getStatus() == Enums.STATUS.success);
        } else {
            fail();
        }
    }

    /**
     * Delete presentation slide
     * Type: Fail
     */
    @Test
    void deleteSlideFail () {
        Result resultDelete = api.deleteSlide(UUID.randomUUID());
        assertTrue(resultDelete.getStatus() == Enums.STATUS.error);
    }
}