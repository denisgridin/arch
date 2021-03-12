package ru.sfedu.Arch.lab4.listCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab4.listCollection.model.Presentation;
import ru.sfedu.Arch.lab4.listCollection.model.Slide;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ListCollectionApiTest {

    private ListCollectionApi api = new ListCollectionApi();


    /**
     * Add default test presentation
     * @return Result - result of execution
     */
    private Result addPresentation () {
        Presentation presentation = new Presentation();
        presentation.setName("Presentation name");
        presentation.setFontFamily("Roboto");
        presentation.setFillColor("yellow");

        List slides = new ArrayList();
        Slide slide = new Slide();
        slide.setId(UUID.randomUUID());
        slide.setName("Slide 1");
        slide.setIndex(1);

        Slide slide2 = new Slide();
        slide2.setId(UUID.randomUUID());
        slide2.setName("Slide 2");
        slide2.setIndex(2);
        slides.add(slide);
        slides.add(slide2);

        presentation.setSlides(slides);

        Result result = api.savePresentation(presentation);

        return new Result(result.getStatus(), presentation);
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
     * Add slide in presentation
     * Type: Success
     */
    @Test
    void addPresentationSlideSuccess () {
        Result resultCreatePresentation = addPresentation();
        if (resultCreatePresentation.getStatus() == Enums.STATUS.success) {
            Slide slide = new Slide();
            slide.setIndex(3);
            slide.setName("Slide 3");
            slide.setId(UUID.randomUUID());

            Presentation presentation = (Presentation) resultCreatePresentation.getReturnValue();

            Result resultAddSlide = api.addPresentationSlide(slide, presentation.getId());

            assertTrue(resultAddSlide.getStatus() == Enums.STATUS.success);
        } else {
            fail();
        }
    }


    /**
     * Add slide in presentation
     * Type: Fail
     */
    @Test
    void addPresentationSlideFail () {
        Result result = api.addPresentationSlide(new Slide(), UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * Getting presentation slides
     * Type: Success
     */
    @Test
    void getPresentationSlidesSuccess() {

        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            Result result = api.getPresentationById(presentation.getId());

            assertTrue(result.getStatus() == Enums.STATUS.success);

            Optional<Presentation> optionalPresentation = (Optional<Presentation>) result.getReturnValue();

            if (optionalPresentation.isPresent()) {
                Presentation foundPresentation = optionalPresentation.get();
                assertTrue(foundPresentation.getSlides().size() == 2);
            } else {
                fail();
            }
        } else {
            fail();
        }
    }


    /**
     * Add slide in presentation
     * Type: Fail
     */
    @Test
    void getPresentationSlidesFail() {
        Result result = api.getPresentationById(UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }



    /**
     * Removing presentation slide
     * Type: Success
     */
    @Test
    void removePresentationSlideSuccess() {
        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();

            List<Slide> slideList = presentation.getSlides();
            Slide slide = slideList.get(0);

            Result result = api.removePresentationSlide(slide.getId(), presentation.getId());

            assertTrue(result.getStatus() == Enums.STATUS.success);
        } else {
            fail();
        }
    }



    /**
     * Removing presentation slide
     * Type: Fail
     */
    @Test
    void removePresentationSlideFail() {
        Result result = api.removePresentationSlide(UUID.randomUUID(), UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * Updating presentation slide
     * Type: Success
     */
    @Test
    void updatePresentationSlideSuccess () {

        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            Slide slide = presentation.getSlides().get(0);
            HashMap args = new HashMap();

            String name = "Slide edited name";
            int index = 3;

            args.put(Constants.FIELD_NAME, name);
            args.put(Constants.FIELD_INDEX, index);

            Result result = api.updatePresentationSlide(slide.getId(), presentation.getId(), args);
            assertTrue(result.getStatus() == Enums.STATUS.success);
        } else {
            fail();
        }
    }


    /**
     * Updating presentation slide
     * Type: Fail
     */
    @Test
    void updatePresentationSlideFail () {
        Result result = api.updatePresentationSlide(UUID.randomUUID(), UUID.randomUUID(), new HashMap());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }

    /**
     * Getting slide by id
     * Type: Success
     */
    @Test
    void getSlideByIdSuccess () {
        Result resultCreatePresentation = addPresentation();

        if (resultCreatePresentation.getStatus() == Enums.STATUS.success) {

            Presentation presentation = (Presentation) resultCreatePresentation.getReturnValue();
            Slide slide = presentation.getSlides().get(0);

            Result result = api.getSlideById(slide.getId(), presentation.getId());

            assertTrue(result.getStatus() == Enums.STATUS.success);
        } else {
            fail();
        }
    }

    /**
     * Getting slide by id
     * Type: Fail
     */
    @Test
    void getSlideByIdFail () {
        Result result = api.getSlideById(UUID.randomUUID(), UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }
}
