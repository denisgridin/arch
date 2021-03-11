package ru.sfedu.Arch.lab2.api;

import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.lab2.model.Specialization;
import ru.sfedu.Arch.lab2.model.TestEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Api2Test {

    /**
     * Helper function for add default TestEntity
     * @return Long - identifier of entity
     */
    TestEntity buildEntity () {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("Test name");
        testEntity.setCheck(true);
        testEntity.setDateCreated(new Date(System.currentTimeMillis()));
        testEntity.setDescription("description");

        Specialization specialization = new Specialization();
        specialization.setName("Frontend");
        specialization.setLevel("Senior");
        specialization.setYears(10);

        testEntity.setSpecialization(specialization);

        return testEntity;
    }


    /**
     * Saving bean to data source
     * Type: Success
     */
    @Test
    void saveSuccess() {
        TestEntity testEntity = buildEntity();
        Api2 instance = new Api2();
        Long result = instance.save(testEntity);
        System.out.println("Result" + result);

        if (result != null) {
            Optional<TestEntity> optionalEntity = instance.getById(TestEntity.class, result);

            TestEntity entity = optionalEntity.orElse(null);

            if (entity != null) {
                Assertions.assertEquals(result, entity.getId());
                Assertions.assertNotNull(result);
            } else {
                Assertions.fail();
            }
        } else {
            Assertions.fail();
        }
    }


    /**
     * Saving bean to data source
     * Type: Fail
     */
    @Test
    void saveFail() {
        try {
            Api2 instance = new Api2();
            instance.save(new TestEntity());
        } catch (Exception error) {
            System.out.println(error);
        }
    }


    /**
     * Retrieving bean from data source
     * Type: Success
     */
    @Test
    void getByIdSuccess () {
        TestEntity testEntity = buildEntity();
        Api2 instance = new Api2();
        Long result = instance.save(testEntity);
        System.out.println("Result" + result);

        if (result != null) {
            Optional<TestEntity> optionalEntity = instance.getById(TestEntity.class, result);

            TestEntity entity = optionalEntity.orElse(null);

            if (entity != null) {
                Assertions.assertEquals(result, entity.getId());
                Assertions.assertNotNull(result);
            } else {
                Assertions.fail();
            }
        } else {
            Assertions.fail();
        }
    }


    /**
     * Retrieving bean from data source
     * Type: Fail
     */
    @Test
    void getByIdFail () {
        Api2 instance = new Api2();
        Optional<TestEntity> optionalEntity = instance.getById(TestEntity.class, 1000000L);
        assertFalse(optionalEntity.isPresent());
    }


    /**
     * Updating bean in data source
     * Type: Success
     */
    @Test
    void updateSuccess () {
        TestEntity testEntity = buildEntity();
        Api2 instance = new Api2();
        Long result = instance.save(testEntity);

        String name = "Edited name";
        boolean check = false;
        String description = "edited description";

        if (result != null) {
            testEntity.setName(name);
            testEntity.setCheck(check);
            testEntity.setDescription(description);
            instance.update(testEntity);

            Optional<TestEntity> optionalTestEntity = instance.getById(TestEntity.class, testEntity.getId());
            assertTrue(optionalTestEntity.isPresent());

            if (optionalTestEntity.isPresent()) {
                TestEntity entity = optionalTestEntity.get();

                assertEquals(entity.getName(), name);
                assertEquals(entity.getDescription(), description);
                assertEquals(entity.isCheck(), check);
            } else {
                fail();
            }
        } else {
            fail();
        }
    }

    /**
     * Updating bean in data source
     * Type: Fail
     */
//    @Test
//    void updateFail () {
//        try {
//            Api2 instance = new Api2();
//            TestEntity testEntity = new TestEntity();
//            instance.update(testEntity);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }


    /**
     * Removing bean from data source
     * Type: Success
     */
    @Test
    void deleteSuccess() {
        TestEntity testEntity = buildEntity();
        Api2 instance = new Api2();
        Long result = instance.save(testEntity);

        if (result != null) {
            instance.delete(testEntity);

            Optional<TestEntity> optionalTestEntity = instance.getById(TestEntity.class, testEntity.getId());
            assertFalse(optionalTestEntity.isPresent());
        } else {
            fail();
        }
    }
}