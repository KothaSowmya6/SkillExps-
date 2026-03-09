package com.product;
import java.util.Scanner;
public class MainApp {
    public static void main(String[] args) {
        ProductDAO dao=new ProductDAO();
        Scanner sc=new Scanner(System.in);
        int choice;
        do{
            System.out.println("\n--- PRODUCT MENU ---");
            System.out.println("1.Insert Product");
            System.out.println("2.View Product by ID");
            System.out.println("3.Update Product Quantity");
            System.out.println("4.Delete Product");
            System.out.println("5.Exit");
            System.out.print("Enter choice: ");
            choice=sc.nextInt();
            switch(choice){
                case 1:
                    System.out.print("Name: ");
                    String name=sc.next();
                    System.out.print("Description: ");
                    String desc=sc.next();
                    System.out.print("Price: ");
                    double price=sc.nextDouble();
                    System.out.print("Quantity: ");
                    int qty=sc.nextInt();
                    dao.insert(new Product(name,desc,price,qty));
                    System.out.println("Product Inserted");
                    break;  
                case 2:
                    System.out.print("Enter Product ID: ");
                    int id=sc.nextInt();
                    Product p=dao.getById(id);
                    if(p!=null)
                        System.out.println(p.getName()+" "+p.getPrice()+" "+p.getQuantity());
                    else
                        System.out.println("Product Not Found");
                    break;
                case 3:
                    System.out.print("Enter Product ID: ");
                    int uid=sc.nextInt();
                    Product up=dao.getById(uid);
                    if(up!=null){
                        System.out.print("New Quantity: ");
                        up.setQuantity(sc.nextInt());
                        dao.update(up);
                        System.out.println("Product Updated");
                    }else{
                        System.out.println("Product Not Found");
                    }
                    break;
                case 4:
                    System.out.print("Enter Product ID: ");
                    dao.delete(sc.nextInt());
                    System.out.println("Product Deleted");
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }while(choice!=5);
        sc.close();
    }
}