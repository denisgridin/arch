package ru.sfedu.Arch.providers;
import ru.sfedu.Arch.lab2.model.TestEntity;

import java.util.Optional;

public interface ITestEntityProvider {
    public Optional<TestEntity> getById (Class<TestEntity> entity, Long id);
    public Long save (TestEntity entity);

    public void update (TestEntity updatedEntity);

    public void delete (TestEntity entity);
}
