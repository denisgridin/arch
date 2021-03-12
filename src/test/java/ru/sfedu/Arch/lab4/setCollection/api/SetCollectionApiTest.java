package ru.sfedu.Arch.lab4.setCollection.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab4.setCollection.model.Presentation;
import ru.sfedu.Arch.lab4.setCollection.model.Slide;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SetCollectionApiTest {

    private SetCollectionApi api = new SetCollectionApi();

    private Result addPresentation () {
        Presentation presentation = new Presentation();
        presentation.setName("Presentation name");
        presentation.setFontFamily("Roboto");
        presentation.setFillColor("yellow");

        Set slides = new HashSet();
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


//    @Test
//    void saveSlideSuccess() {
//        Slide slide = new Slide();
//        slide.setIndex(0);
////        slide.setPresentationId(UUID.fromString("868E290C-93F2-4759-9C53-F72C30DE545D"));
//        slide.setName("Slide name");
//
//        Result result = api.saveSlide(slide);
//
//        assertTrue(result.getStatus() == Enums.STATUS.success);
//    }
//
//    @Test
//    void getSlideByIdSuccess() {
//
//        SetCollectionApi api = new SetCollectionApi();
//        Result result = api.getPresentationById(UUID.fromString("8f626c24-2f81-43b9-8b03-6dfc3861a9a2"));
//        assertTrue(result.getStatus() == Enums.STATUS.success);
//    }
}