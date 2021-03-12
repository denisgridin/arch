package ru.sfedu.Arch.lab4.listCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab4.listCollection.api.ListCollectionApi;
import ru.sfedu.Arch.lab4.listCollection.model.Presentation;
import ru.sfedu.Arch.lab4.listCollection.model.Slide;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ListCollectionApiTest {

    private ListCollectionApi api = new ListCollectionApi();

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

    @Test
    void savePresentationSuccess() {
        Presentation presentation = new Presentation();
        Result result = api.savePresentation(presentation);

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
            slide.setId(UUID.randomUUID());

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
