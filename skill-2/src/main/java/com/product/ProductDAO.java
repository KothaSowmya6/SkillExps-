package com.product;
import org.hibernate.*;

public class ProductDAO {
    public void insert(Product p){
        Session s=HibernateUtil.getFactory().openSession();
        Transaction tx=s.beginTransaction();
        s.save(p);
        tx.commit();
        s.close();
    }
    public Product getById(int id){
        Session s=HibernateUtil.getFactory().openSession();
        Product p=s.get(Product.class,id);
        s.close();
        return p;
    }
    public void update(Product p){
        Session s=HibernateUtil.getFactory().openSession();
        Transaction tx=s.beginTransaction();
        s.update(p);
        tx.commit();
        s.close();
    }
    public void delete(int id){
        Session s=HibernateUtil.getFactory().openSession();
        Transaction tx=s.beginTransaction();
        Product p=s.get(Product.class,id);
        if(p!=null) s.delete(p);
        tx.commit();
        s.close();
    }
}