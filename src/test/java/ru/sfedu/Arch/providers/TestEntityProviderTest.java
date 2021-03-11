package ru.sfedu.Arch.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.lab2.model.TestEntity;
import java.util.Optional;


class TestEntityProviderTest {

    @Test
    void save() {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("Name 2");

        ITestEntityProvider instance = new TestEntityProvider();
        Long result = instance.save(testEntity);

        Optional<TestEntity> optionalEntity = instance.getById(TestEntity.class, result);

        TestEntity entity = optionalEntity.orElse(null);

        if (entity != null) {
            Assertions.assertEquals(result, entity.getId());
            Assertions.assertNotNull(result);
        } else {
            Assertions.fail();
        }
    }

    @Test
    void getById() {
    }
}