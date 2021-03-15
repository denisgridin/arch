package ru.sfedu.Arch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.lab2.model.TestEntity;


import java.io.File;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    /**
     * Создание фабрики
     *
     */

    private static final String CUSTOM_HIBERNATE_CONFIG_PATH = System.getProperty(Constants.PROPERTY_CUSTOM_HIBERNATE_CONFIG_PATH);
    private static final String DEFAULT_HIBERNATE_CONFIG_PATH = Constants.DEFAULT_HIBERNATE_CONFIG_PATH;

    private static Logger log = LogManager.getLogger(HibernateUtil.class);

    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                // loads configuration and mappings
                Configuration configuration;
                log.debug("Hibernate system property enabled: " + CUSTOM_HIBERNATE_CONFIG_PATH);
                if (CUSTOM_HIBERNATE_CONFIG_PATH != null) {
                    log.debug("Custom Hibernate config: " + CUSTOM_HIBERNATE_CONFIG_PATH);
                    File config = new File(CUSTOM_HIBERNATE_CONFIG_PATH);
                    configuration = new Configuration().configure(config);
                } else {
                    configuration = new Configuration().configure();
                }
                ServiceRegistry serviceRegistry
                        = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                log.debug(String.format(Messages.SERVICE_REGISTRY_BUILD, serviceRegistry));

                MetadataSources metadataSources =
                        new MetadataSources(serviceRegistry);

                addEntities(metadataSources);
                Metadata metadata = metadataSources.getMetadataBuilder().build();
                log.info(String.format(Messages.META_DATA, metadata));
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            }

            return sessionFactory;
        } catch (Exception error) {
            log.error(error);
            return null;
        }
    }


    public static void addEntities (MetadataSources metadataSources) {
        try {
            log.debug(Messages.ATTEMPT_ADD_ENTITIES);
            metadataSources.addAnnotatedClass(TestEntity.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.MappedSuperclass.model.Comment.class); // MappedSuperclass
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.TablePerClass.model.Comment.class); // TablePerClass
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.SingleTable.model.Comment.class); // SingleTable
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.JoinedTable.model.Comment.class); // JoinedTable


            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.JoinedTable.model.Assessment.class); // JoinedTable
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.MappedSuperclass.model.Assessment.class); // MappedSuperclass
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.SingleTable.model.Assessment.class); // SingleTable
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab3.TablePerClass.model.Assessment.class); // TablePerClass

            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab4.setCollection.model.Presentation.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab4.setCollection.model.Slide.class);

            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab4.listCollection.model.Presentation.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab4.listCollection.model.Slide.class);

            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab4.mapCollection.model.Presentation.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab4.mapCollection.model.Slide.class);

            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Presentation.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Slide.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Comment.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Element.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Shape.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Layout.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Style.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Content.class);
            metadataSources.addAnnotatedClass(ru.sfedu.Arch.lab5.model.Font.class);
        } catch (Exception e) {
            log.error(Messages.ERROR_ADD_ENTITIES);
        }
    }
}
