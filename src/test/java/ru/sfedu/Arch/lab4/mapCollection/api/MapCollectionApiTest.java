package ru.sfedu.Arch.lab4.mapCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab4.mapCollection.api.MapCollectionApi;
import ru.sfedu.Arch.lab4.mapCollection.model.Presentation;
import ru.sfedu.Arch.lab4.mapCollection.model.Slide;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MapCollectionApiTest {

    private MapCollectionApi api = new MapCollectionApi();

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

    @Test
    void savePresentationSuccess() {

        Result result = addPresentation();

        assertTrue(result.getStatus() == Enums.STATUS.success);
    }

    @Test
    void getPresentationByIdSuccess() {

        Result resultAddPresentation = addPresentation();

        if (resultAddPresentation.getStatus() == Enums.STATUS.success) {
            Presentation presentation = (Presentation) resultAddPresentation.getReturnValue();
            Result result = api.getPresentationById(presentation.getId());

            assertTrue(result.getStatus() == Enums.STATUS.success);
        }
    }

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


}
