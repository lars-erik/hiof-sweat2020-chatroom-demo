package org.hiof.chatroom.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.type.UUIDCharType;

import java.util.UUID;

public class UnitOfWork implements org.hiof.chatroom.persistence.UnitOfWork {
    public Session getSession() {
        return session;
    }

    public UnitOfWork() throws Exception {
        this.session = getSessionFactory().openSession();
    }

    @Override
    public void saveChanges() {
        getSession().flush();
    }

    @Override
    public void close() {
        getSession().close();
    }

    private final Session session;

    static SessionFactory sessionFactory;
    static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory != null) {
            return sessionFactory;
        }

        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }

        return sessionFactory;
    }
}
