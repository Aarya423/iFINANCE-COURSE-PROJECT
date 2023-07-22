package se2203b.assignments.ifinance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountCategoryAdapter {
    Connection connection;

    public AccountCategoryAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        if (!reset) {
            try {
                stmt.execute("DROP TABLE CATEGORIES ");
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist

            } catch (SQLException ex) {
                System.out.println("LLLL");
                // No need to report an error.
                // The table simply did not exist.
            }
            finally {

//        // Check if table exists
//        ResultSet rs = connection.getMetaData().getTables(null, null, "CATEGORIES", null);
//        if (!rs.next()) {
//            try {
//                // Create the table
                stmt.execute("CREATE TABLE CATEGORIES ("
                        + "category VARCHAR(20) NOT NULL ,"
                        + "types VARCHAR(30) NOT NULL"
                        + ")");
                populate();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                // The table exists and may have some data.
//            }
//        } else {
//            //stmt.execute("DROP TABLE CATEGORIES");
//
//            stmt.execute("CREATE TABLE CATEGORIES ("
//                    + "category VARCHAR(20) NOT NULL UNIQUE ,"
//                    + "types VARCHAR(30) NOT NULL"
//                    + ")");
//            populate();
            }}
    }
    private void populate() throws SQLException{
        this.insertA("Assets ","Debit");
        this.insertA("Liabilities ","Credit");
        this.insertA("Income ","Credit");
        this.insertA("Expenses","Debit");

    }
    public void insertA(String n, String types) throws SQLException{
        Statement stmt= connection.createStatement();
        stmt.executeUpdate("INSERT INTO Categories ( category, types) VALUES ('"+n+"','"+types+"')");
    }
    public ArrayList<String> getCategories() throws SQLException{
        ResultSet rs;
        String cat = "";
        Statement stmt = connection.createStatement();
        ArrayList<String> list= new ArrayList<>();
        rs=stmt.executeQuery("SELECT category FROM Categories");
        while (rs.next()){
            cat=rs.getString(1);
            list.add(cat);
            System.out.println(cat);
        }
        return list;

    }

}