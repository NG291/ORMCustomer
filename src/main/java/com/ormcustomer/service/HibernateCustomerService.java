package com.ormcustomer.service;

import com.ormcustomer.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Service
public class HibernateCustomerService implements ICustomerService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {
        String querySt = "select c from Customer AS c";
        TypedQuery<Customer> query = entityManager.createQuery(querySt, Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        String querySt = "select c from Customer AS c where c.id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(querySt, Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Customer customer) {
        Transaction transaction = null;
        Customer origin;

        if (customer.getId() == null || customer.getId() == 0) {
            origin = new Customer();
        } else {
            origin = findById(customer.getId());
        }

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(customer.getName());
            origin.setEmail(customer.getEmail());
            origin.setAddress(customer.getAddress());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void remove(Long id) {
        Customer customer = findById(id);
        if (customer != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.remove(customer);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
