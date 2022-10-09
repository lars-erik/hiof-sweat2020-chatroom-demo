package org.hiof.chatroom.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.type.UUIDCharType;
import org.hiof.chatroom.core.ChatMessage;
import org.jinq.jpa.JinqJPAStreamProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class UnitOfWork implements org.hiof.chatroom.persistence.UnitOfWork {

    private final JinqJPAStreamProvider streamProvider;
    private final EntityManager entityManager;

    public Session getSession() {
        return session;
    }
    public JinqJPAStreamProvider getStreamProvider() { return streamProvider; }
    public EntityManager getEntityManager() { return entityManager; }

    public UnitOfWork() throws Exception {
        this.session = getSessionFactory().openSession();

        EntityManagerFactory emf = this.session.getEntityManagerFactory();
        entityManager = emf.createEntityManager();
        streamProvider = new JinqJPAStreamProvider(emf);

        this.transaction = this.session.beginTransaction();

    }

    @Override
    public void saveChanges() {
        getSession().flush();
        this.transaction.commit();
        this.transaction = this.session.beginTransaction();
    }

    @Override
    public void close() {
        getSession().close();
    }

    private final Session session;
    private Transaction transaction;

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
