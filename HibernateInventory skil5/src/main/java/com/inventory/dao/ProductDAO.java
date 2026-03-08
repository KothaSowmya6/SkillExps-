package com.inventory.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;

public class ProductDAO {

    public void saveProduct(Product product) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(product);
            tx.commit();
            System.out.println("Product Saved Successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Product getProduct(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Product.class, id);
        }
    }

    public void updateProduct(int id, double price, int quantity) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Product product = session.get(Product.class, id);

            if (product != null) {
                product.setPrice(price);
                product.setQuantity(quantity);
                tx.commit();
                System.out.println("Product Updated!");
            } else {
                System.out.println("Product Not Found!");
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Product product = session.get(Product.class, id);

            if (product != null) {
                session.remove(product);
                tx.commit();
                System.out.println("Product Deleted!");
            } else {
                System.out.println("Product Not Found!");
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}