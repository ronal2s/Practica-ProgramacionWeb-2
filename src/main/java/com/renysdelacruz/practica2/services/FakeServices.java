package com.renysdelacruz.practica2.services;

import com.renysdelacruz.practica2.models.Product;
import com.renysdelacruz.practica2.models.Sale;
import com.renysdelacruz.practica2.models.SellProduct;
import com.renysdelacruz.practica2.models.User;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Ejemplo de servicio patron Singleton
 */
public class FakeServices {

    private static FakeServices instancia;
    private User currentUser;

    /**
     * Constructor privado.
     */
    private FakeServices() {
    }

    public static FakeServices getInstancia() {
        if (instancia == null) {
            instancia = new FakeServices();
        }
        return instancia;
    }

    public List<Product> getAllProducts() {
        try {
            Connection conn = null;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            Statement stmt = conn.createStatement();
            ResultSet rs;

            List<Product> products = new ArrayList<Product>();

            rs = stmt.executeQuery("SELECT id, name, price FROM PRODUCT");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                Product p = new Product(id, name, price);
                products.add(p);
            }
            conn.close();
            return products;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getLastListProductId() {
        List<Product> productList = this.getAllProducts();
        if (productList.isEmpty()) {
            return 0;
        } else {
            return productList.stream().mapToInt(Product::getId).max().orElseThrow(NoSuchElementException::new);
        }
    }

    public List<SellProduct> listSellsProducts() {

        try {
            Connection conn = null;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            Statement stmt = conn.createStatement();
            ResultSet rs;

            List<SellProduct> products = new ArrayList<SellProduct>();

            rs = stmt.executeQuery("SELECT quantity, product, saleId FROM SELLPRODUCT");
            while (rs.next()) {
                int productId = rs.getInt("product");
                int quantity = rs.getInt("quantity");
                int saleId = rs.getInt("saleId");
                Optional<Product> p = this.getAllProducts().stream().filter(x -> x.getId() == productId).findAny();
                if (p.isPresent()) {
                    SellProduct sp = new SellProduct(quantity, p.get());
                    sp.setSaleId(saleId);
                    products.add(sp);
                } else {
                    // el producto fue borrado. la FK esta comentada en el main habria que descomentar eso
                    // y ver si se puede o no borrar un producto que tiene compras asociadas
                }

            }
            conn.close();
            return products;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SellProduct> listCartProducts() {
        try {
            Connection conn = null;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            Statement stmt = conn.createStatement();
            ResultSet rs;

            List<SellProduct> products = new ArrayList<SellProduct>();

            rs = stmt.executeQuery("SELECT quantity, product  FROM CART");
            while (rs.next()) {
                int productId = rs.getInt("product");
                int quantity = rs.getInt("quantity");
                Optional<Product> p = this.getAllProducts().stream().filter(x -> x.getId() == productId).findAny();
                if (p.isPresent()) {
                    SellProduct sp = new SellProduct(quantity, p.get());
                    products.add(sp);
                } else {
                    // el producto fue borrado. la FK esta comentada en el main habria que descomentar eso
                    // y ver si se puede o no borrar un producto que tiene compras asociadas
                }

            }
            conn.close();
            return products;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getLastListSalesId() {
        List<Sale> spL = this.getListSales();
        if (spL.size() > 0) {
            return spL.get(spL.size() - 1).getId();
        }
        return -1;
    }

    public Product getProductById(int id) {
        return this.getAllProducts().stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public Product newProduct(Product product) {
        if (getProductById(product.getId()) != null) {
            System.out.println("Producto previamente registrado...");
            return null; //generar una excepcion...
        }
        try {
            Connection conn = null;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            ResultSet rs;
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO PRODUCT VALUES (?,?,?)");
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setInt(3, product.getPrice());
            stmt.executeUpdate();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public SellProduct addToCart(SellProduct product) {

       Optional<SellProduct> sp =  this.listCartProducts().stream().filter(x -> x.getProduct().getId()==product.getProduct().getId()).findAny();
       if (sp.isPresent()){
           int quantity = sp.get().getQuantity() + product.getQuantity();
           this.deleteCart(product.getProduct().getId());
           product.setQuantity(quantity);

       }
           try {
               Connection conn = null;
               Class.forName("org.h2.Driver");
               conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

               PreparedStatement stmt = conn.prepareStatement("INSERT INTO CART VALUES (?,?)");
               int id = product.getProduct().getId();
               stmt.setInt(2, id);
               stmt.setInt(1, product.getQuantity());
               stmt.executeUpdate();

           } catch (ClassNotFoundException | SQLException e) {
               e.printStackTrace();
           }

        return product;
    }

    public void deleteCart(int id) {
        try {
            Connection conn;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            Statement stmt = conn.createStatement();
            ResultSet rs;

            List<Product> products = new ArrayList<Product>();

            String updateString =
                    "delete FROM CART " +
                            "where product = ?";

            PreparedStatement update = conn.prepareStatement(updateString);

            update.setString(1, String.valueOf(id));
            update.executeUpdate();

            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sale> listSales() {

        return this.getListSales();
    }

    public Sale newSale(Sale sale) {

        try {
            Connection conn;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            ResultSet rs;
            List<Product> products = new ArrayList<Product>();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SALE VALUES (?,?,?,?)");

            int id = this.listSales().size()+1;
            stmt.setInt(1, id);
            stmt.setDate(2, sale.getDate());
            stmt.setInt(3, sale.getTotal());
            stmt.setString(4, sale.getCustomer_name());
            stmt.executeUpdate();

            List<SellProduct> salesProduct = sale.getProducts();

            for (SellProduct sp:salesProduct) {

                stmt = conn.prepareStatement("INSERT INTO SELLPRODUCT VALUES(?,?,?)");

                stmt.setInt(1, sp.getQuantity());
                stmt.setInt(2, sp.getProduct().getId());
                stmt.setInt(3, id);
                stmt.executeUpdate();

            }

            conn.close();
            return sale;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
}
    public List<Sale> getListSales() {

        try {

            Connection conn = null;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");
            Statement stmt = conn.createStatement();
            ResultSet rs = null;

            List<Sale> sales = new ArrayList<Sale>();

            rs = stmt.executeQuery("SELECT id, date, total, name FROM SALE");
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                int total = rs.getInt("total");
                String name = rs.getString("name");
                Sale s = new Sale(id,date,name,total);
                sales.add(s);
            }

            for (Sale s : sales){
                List <SellProduct> sp = new ArrayList<SellProduct>();
                sp = this.listSellsProducts();
                List <SellProduct> aux = sp.stream().filter(x -> x.getSaleId()==s.getId()).collect(Collectors.toList());
                s.setProducts(aux);
            }
            conn.close();
            return sales;
        }
         catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateProduct(Product product){

        try {
            Connection conn;
            Class.forName("org.h2.Driver");

            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            Statement stmt = conn.createStatement();
            ResultSet rs;

            List<Product> products = new ArrayList<Product>();

            String updateString =
                    "update " + "PRODUCT " +
                            "set name = ? , price = ? where id = ?";

            PreparedStatement update = conn.prepareStatement(updateString);
            update.setString(1, product.getName());
            update.setInt(2, product.getPrice());
            update.setInt(3, product.getId());
            update.executeUpdate();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteProduct(int id){

        try {
            Connection conn;
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/tarea2", "admin", "admin");

            Statement stmt = conn.createStatement();
            ResultSet rs;

            List<Product> products = new ArrayList<Product>();

            String updateString =
                    "delete FROM " + "PRODUCT " +
                            "where id = ?";

            PreparedStatement update = conn.prepareStatement(updateString);
            update.setString(1, String.valueOf(id));
            update.executeUpdate();
            conn.close();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    private int getIndexSellsProducts(int id) {
        List<SellProduct> listSellsProducts = this.listSellsProducts();
        for(int i = 0; i < listSellsProducts.size(); i++) {
            if(listSellsProducts.get(i).getProduct().getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public boolean loginUser(String username, String password) {
        if(username.equalsIgnoreCase("admin") && password.equals("admin")) {
            currentUser = new User(username, username, password);
            return true;
        }
        return false;
    }

    public boolean isUserLogged() {
        if(currentUser != null) {
            return  true;
        }
        return false;
    }

    public void emptyCart() {
        List <SellProduct> spl = this.listCartProducts();
        for (SellProduct sp:spl) {
            this.deleteCart(sp.getProduct().getId());
        }
    }
}
