package ru.sfedu.Arch.providers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.Arch.lab2.model.TestEntity;
import ru.sfedu.Arch.utils.HibernateUtil;

import java.util.Optional;

public class TestEntityProvider implements ITestEntityProvider {

    private Session getSession () {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.openSession();
    }

    @Override
    public Long save(TestEntity entity) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(entity);
        transaction.commit();
        return id;
    }

    @Override
    public void update(TestEntity updatedEntity) {

    }

    @Override
    public void delete(TestEntity entity) {

    }

    @Override
    public Optional<TestEntity> getById(Class<TestEntity> entity, Long id) {
        Session session = this.getSession();
        TestEntity testEntity = session.get(entity, id);
        session.close();

        return Optional.of(testEntity);
    }
}
