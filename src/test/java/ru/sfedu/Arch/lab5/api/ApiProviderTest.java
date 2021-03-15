package ru.sfedu.Arch.lab5.api;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab5.model.Presentation;
import ru.sfedu.Arch.lab5.model.Slide;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApiProviderTest {

    private ApiProvider api = new ApiProvider();


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
            Result result = api.deletePresentationById(presentation.getId());

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

}