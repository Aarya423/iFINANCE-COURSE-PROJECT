package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountGroupAdapter {
    Connection connection;

    public AccountGroupAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        if (!reset) {
            Statement stmt = connection.createStatement();

            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                //stmt.execute("DROP TABLE G");
                stmt.execute("DROP TABLE G");
            } catch (SQLException ex) {
                System.out.println("test");
                //ex.printStackTrace();
                // No need to report an error.
                // The table simply did not exist.
            } finally {

                // Create the table
                stmt.execute("CREATE TABLE G ("
                        + "groupId INT NOT NULL PRIMARY KEY,"
                        + "na VARCHAR(60) NOT NULL,"
                        + "parent INT NOT NULL ,"
                        + "element VARCHAR(60) NOT NULL"
                        + ")");
                populate();
            }
        }

    }
    public int getMax() throws SQLException {
        int num = -1;

        Statement s =connection.createStatement();
        ResultSet r = s.executeQuery("SELECT MAX(groupId) FROM G");
        while (r.next()){
            num=r.getInt(1)+1;
        }

        return num;
    }
    public int gettingId(String name) throws SQLException{
        int gID= 0;
        ResultSet rs;
        Statement stmt= connection.createStatement();
        rs=stmt.executeQuery("SELECT groupId FROM G WHERE na='"+name+"'");
        while (rs.next()){
            gID=rs.getInt("groupId");
        }
        return gID;
    }
    public String gettingElement(String name) throws SQLException{
        ResultSet rs;
        String e="";
        Statement stmt= connection.createStatement();
        rs= stmt.executeQuery("SELECT element FROM G WHERE na='"+name+"'");
        while (rs.next()){
            e=rs.getString("element");
        }
        return e;
    }
    public int gettingParent(String name) throws SQLException{
        int parent=0;
        ResultSet rs;
        Statement stmt = connection.createStatement();
        rs= stmt.executeQuery("SELECT parent FROM G WHERE na='"+name+"'");
        while (rs.next()){
            parent=rs.getInt("parent");
        }
        return parent;
    }
    public String gettingName(int gId) throws SQLException{
        String name="";
        ResultSet rs;
        Statement stmt= connection.createStatement();
        rs=stmt.executeQuery("SELECT na FROM G WHERE groupId="+gId+"");
        while (rs.next()){
            name=rs.getString("na");
        }
        return name;
    }
    private void populate() throws SQLException{
        this.insertG(1,"Fixed Assets",0,"Assets");
        this.insertG(2,"Investments",0,"Assets");
        this.insertG(3,"Branch/divisions",0,"Assets");
        this.insertG(4,"Cash in hand",0,"Assets");
        this.insertG(5,"Bank accounts",0,"Assets");
        this.insertG(6,"Deposits (assets)",0,"Assets");
        this.insertG(7,"Advances (assets)",0,"Assets");
        this.insertG(8,"Capital account",0,"Liabilities");
        this.insertG(9,"Long term loans",0,"Liabilities");
        this.insertG(10,"Current Liabilities",0,"Liabilities");
        this.insertG(11,"Reserves and surplus",0,"Liabilities");
        this.insertG(12,"Sales account",0,"Income");
        this.insertG(13,"Purchase account",0,"Expenses");
        this.insertG(14,"Expenses (direct)",0,"Expenses");
        this.insertG(15,"Expenses (indirect)",0,"Expenses");
        this.insertG(16,"Secured loans",9,"Liabilities");
        this.insertG(17,"Unsecured loans",9,"Liabilities");
        this.insertG(18,"Duties taxes payable",10,"Liabilities");
        this.insertG(19,"Provisions",10,"Liabilities");
        this.insertG(20,"Sundry creditors",10,"Liabilities");
        this.insertG(21,"Bank od & limits",10,"Liabilities");
    }
    public void insertG(int id, String n, int p, String e) throws SQLException{
        Statement stmt= connection.createStatement();

        stmt.executeUpdate("INSERT INTO G (groupid, na, parent,element) VALUES ("+id+",'"+n+"',"+p+",'"+e+"')");
    }
//    public void deleteG(Group n) throws SQLException{}
//    public void modifyG(Group n) throws SQLException{}

    public ArrayList<String> getGroups() throws SQLException{
        System.out.println("LLLL");
        ArrayList<String> list= new ArrayList<>();
        ResultSet rs;
        Statement stmt=connection.createStatement();

        String sql = "SELECT * FROM G";
        rs=stmt.executeQuery(sql);
        while(rs.next()){

            String n= rs.getString("na");

            list.add(n);

        }
        return list;
    }
}