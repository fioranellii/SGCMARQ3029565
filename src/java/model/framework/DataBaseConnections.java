package model.framework;

import controller.AppConfig;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseConnections {
    private ArrayList<Connection> pool;
    
    private  static DataBaseConnections singleton;
    
    private DataBaseConnections() {
        pool = new ArrayList<>();
    }
    
    public static DataBaseConnections getInstance() {
        if(singleton == null) {
            singleton = new DataBaseConnections();
        }
        return singleton;
    }
    
    public synchronized Connection getConnection() throws SQLException{
        Connection con;
        con = DriverManager.getConnection(
                AppConfig.getInstance().getUrl(), 
                AppConfig.getInstance().getUser(), 
                AppConfig.getInstance().getPassword() );
        pool.add(con);
        return con;
    }
    
    public synchronized void closeConnection(Connection con) throws SQLException{
        if(con != null && !con.isClosed() && pool.contains(con)) {
            con.close();
            pool.remove(con);
        }
    }
    
    public synchronized void closeAllConnection() throws SQLException{
        for(Connection con: pool){
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }
        pool.clear();
    }
}
