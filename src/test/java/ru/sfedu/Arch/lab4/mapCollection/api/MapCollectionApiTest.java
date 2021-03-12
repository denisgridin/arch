package ru.sfedu.Arch.lab4.mapCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab4.mapCollection.model.Presentation;
import ru.sfedu.Arch.lab4.mapCollection.model.Slide;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MapCollectionApiTest {

    private MapCollectionApi api = new MapCollectionApi();


    /**
     * Add default test presentation
     * @return Result - result of execution
     */
    private Result addPresentation () {
        Presentation presentation = new Presentation();
        presentation.setName("Presentation name");
        presentation.setFontFamily("Roboto");
        presentation.setFillColor("yellow");

        Map slides = new HashMap();
        Slide slide = new Slide();
        slide.setName("Slide1");
        slide.setIndex(1);

        Slide slide2 = new Slide();
        slide2.setName("Slide2");
        slide2.setIndex(2);


        slides.put(String.valueOf(UUID.randomUUID()), slide.getName());
        slides.put(String.valueOf(UUID.randomUUID()), slide2.getName());


        presentation.setSlides(slides);

        System.out.println("Presentation: " + presentation);

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
            }
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

            Map<String, String> slideList = presentation.getSlides();
            String[] slideIds = slideList.keySet().toArray(new String[slideList.size()]);
            String slide = slideIds[0];

            Result result = api.removePresentationSlide(UUID.fromString(slide), presentation.getId());

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

            Map slidesMap = presentation.getSlides();
            String[] keys = presentation.getSlides().keySet().toArray(new String[presentation.getSlides().keySet().size()]);
            String slideId = keys[0];
            String name = "Slide edited name";

            slidesMap.put(slideId, name);

            HashMap args = new HashMap();
            args.put(Constants.FIELD_NAME, name);

            Result result = api.updatePresentationSlide(UUID.fromString(slideId), presentation.getId(), args);
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

            Map slidesMap = presentation.getSlides();
            String[] keys = (String[]) slidesMap.keySet().toArray(new String[slidesMap.keySet().size()]);
            String slideId = keys[0];

            Result result = api.getSlideById(UUID.fromString(slideId), presentation.getId());

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
