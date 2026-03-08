package com.example.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.example.entity.Product;
import com.example.util.HibernateUtil;

public class HQLDemo {

	public static void main(String[] args) {
		 SessionFactory factory = HibernateUtil.getSessionFactory();
	        Session session = factory.openSession();

	        try {

	            // Run only once to insert sample data
	            // ProductDataLoader.loadSampleProducts(session);

	            sortProductsByPriceAscending(session);
	            sortProductsByPriceDescending(session);
	            sortProductsByQuantityDescending(session);

	            getPaginatedProducts(session, 1, 3);
	            getPaginatedProducts(session, 2, 3);

	            countTotalProducts(session);
	            countProductsInStock(session);
	            countProductsByDescription(session);
	            findMinMaxPrice(session);

	            filterProductsByPriceRange(session, 20.0, 100.0);

	            findProductsStartingWith(session, "D");
	            findProductsContaining(session, "Desk");

	        } finally {
	            session.close();
	            factory.close();
	        }
	    }

	    // SORT ASC
	    public static void sortProductsByPriceAscending(Session session) {
	        Query<Product> query =
	                session.createQuery("FROM Product p ORDER BY p.price ASC", Product.class);

	        System.out.println("\n--- Price ASC ---");
	        query.list().forEach(System.out::println);
	    }

	    // SORT DESC
	    public static void sortProductsByPriceDescending(Session session) {
	        Query<Product> query =
	                session.createQuery("FROM Product p ORDER BY p.price DESC", Product.class);

	        System.out.println("\n--- Price DESC ---");
	        query.list().forEach(System.out::println);
	    }

	    // SORT BY QUANTITY
	    public static void sortProductsByQuantityDescending(Session session) {
	        Query<Product> query =
	                session.createQuery("FROM Product p ORDER BY p.quantity DESC", Product.class);

	        System.out.println("\n--- Quantity DESC ---");
	        query.list().forEach(p ->
	                System.out.println(p.getName() + " - " + p.getQuantity()));
	    }

	    // PAGINATION
	    public static void getPaginatedProducts(Session session, int page, int size) {
	        Query<Product> query =
	                session.createQuery("FROM Product p", Product.class);

	        query.setFirstResult((page - 1) * size);
	        query.setMaxResults(size);

	        System.out.println("\n--- Page " + page + " ---");
	        query.list().forEach(System.out::println);
	    }

	    // COUNT TOTAL
	    public static void countTotalProducts(Session session) {
	        Long count = session.createQuery(
	                "SELECT COUNT(p) FROM Product p", Long.class)
	                .uniqueResult();

	        System.out.println("\nTotal Products: " + count);
	    }

	    // COUNT STOCK
	    public static void countProductsInStock(Session session) {
	        Long count = session.createQuery(
	                "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0",
	                Long.class).uniqueResult();

	        System.out.println("Products In Stock: " + count);
	    }

	    // GROUP COUNT
	    public static void countProductsByDescription(Session session) {
	        List<Object[]> results = session.createQuery(
	                "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",
	                Object[].class).list();

	        System.out.println("\n--- Group By Description ---");
	        for (Object[] row : results) {
	            System.out.println(row[0] + " : " + row[1]);
	        }
	    }

	    // MIN MAX
	    public static void findMinMaxPrice(Session session) {
	        Object[] result = session.createQuery(
	                "SELECT MIN(p.price), MAX(p.price) FROM Product p",
	                Object[].class).uniqueResult();

	        System.out.println("\nMin Price: " + result[0]);
	        System.out.println("Max Price: " + result[1]);
	    }

	    // PRICE RANGE
	    public static void filterProductsByPriceRange(Session session,
	                                                  double min,
	                                                  double max) {

	        Query<Product> query = session.createQuery(
	                "FROM Product p WHERE p.price BETWEEN :min AND :max",
	                Product.class);

	        query.setParameter("min", min);
	        query.setParameter("max", max);

	        System.out.println("\n--- Price Between ---");
	        query.list().forEach(p ->
	                System.out.println(p.getName() + " - $" + p.getPrice()));
	    }

	    // LIKE START
	    public static void findProductsStartingWith(Session session, String prefix) {
	        Query<Product> query = session.createQuery(
	                "FROM Product p WHERE p.name LIKE :pattern",
	                Product.class);

	        query.setParameter("pattern", prefix + "%");

	        System.out.println("\nStarts With " + prefix);
	        query.list().forEach(p -> System.out.println(p.getName()));
	    }

	    // LIKE CONTAIN
	    public static void findProductsContaining(Session session, String text) {
	        Query<Product> query = session.createQuery(
	                "FROM Product p WHERE p.name LIKE :pattern",
	                Product.class);

	        query.setParameter("pattern", "%" + text + "%");

	        System.out.println("\nContains " + text);
	        query.list().forEach(p -> System.out.println(p.getName()));

	}

}