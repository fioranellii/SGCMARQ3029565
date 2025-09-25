package model.framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import controller.AppConfig;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

public abstract class DataAccessObject {
    
    private String tableEntity;
    private boolean novelEntity;
    private boolean changedEntity;
    private HashMap<String, Object> dirtyFields;

    public DataAccessObject(String tableEntity) {
        setTableEntity(tableEntity);
        dirtyFields = new HashMap<>();
        
        setNovelEntity(true);
        setChangedEntity(false);        
    }

    private String getTableEntity() {
        return tableEntity;
    }

    private boolean isNovelEntity() {
        return novelEntity;
    }

    private boolean isChangedEntity() {
        return changedEntity;
    }

    private void setTableEntity(String tableEntity) {
        if( tableEntity != null &&
                !tableEntity.isEmpty() &&
                !tableEntity.isBlank() ) {
            this.tableEntity = tableEntity;
        } else {
            throw new IllegalArgumentException("table must be valid");
        }
    }

    protected void setNovelEntity(boolean novelEntity) {
        this.novelEntity = novelEntity;
    }

    protected void setChangedEntity(boolean changedEntity) {
        this.changedEntity = changedEntity;
        if( this.changedEntity == false ) {
            dirtyFields.clear();
        }
    }
    
//    Unity Of Work
    protected void addChange(String field, Object value) {
        dirtyFields.put(field, value);
        setChangedEntity(true);
    }
    
    private void insert() throws SQLException {
        
        String dml = "INSERT INTO " + getTableEntity();
        
        StringJoiner fields = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");
        
       for( String field : dirtyFields.keySet() ) {
            fields.add(field);
            values.add("?");
       }
       
       dml += " (" + fields + ") VALUES (" + values + ")";
       
       if( AppConfig.getInstance().isVerbose() ) 
            System.out.println( dml );
       
       Connection con = DataBaseConnections.getInstance().getConnection();
       PreparedStatement  pst = con.prepareStatement(dml);
       
       int index = 1;
       for( String field : dirtyFields.keySet() ) {
           pst.setObject( index, dirtyFields.get(field) );
           index++;
       }
       
       if( AppConfig.getInstance().isVerbose() )
            System.out.println( pst );
       
       pst.execute();
       pst.close();
       
       DataBaseConnections.getInstance().closeConnection(con);
        
    }
    
    private void update() throws SQLException {
        
        String dml = "UPDATE " + getTableEntity() + " SET ";
        
        StringJoiner changes = new StringJoiner(",");
        
        for( String field : dirtyFields.keySet() ) {
            changes.add( field + "=?" );
        }
        
        dml += changes + " WHERE " + getWhereClauseForOneEntity();
        
        if( AppConfig.getInstance().isVerbose() )
            System.out.println(dml);     
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        PreparedStatement  pst = con.prepareStatement(dml);
       
       int index = 1;
       for( String field : dirtyFields.keySet() ) {
           pst.setObject( index, dirtyFields.get(field) );
           index++;
       }
       
       if( AppConfig.getInstance().isVerbose() )
            System.out.println( pst );
       
       pst.execute();
       pst.close();
       
       DataBaseConnections.getInstance().closeConnection(con);
       
    }

    public void save() throws SQLException {
        if( isChangedEntity() ) {
            
            // salvo
            if( isNovelEntity() ) {
                insert();
                setNovelEntity(false);
            } else {
                update();
            }
            
            setChangedEntity(false);
        }
    }
    
    public void delete() throws SQLException {
        
        String dml = "DELETE FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        if( AppConfig.getInstance().isVerbose() )
            System.out.println(dml);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        
        st.execute(dml);
        st.close();
        
        DataBaseConnections.getInstance().closeConnection(con);
        
    }
    
    public boolean load() throws SQLException {
        
        boolean result;
        
        String dql = "SELECT * FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        if( AppConfig.getInstance().isVerbose() )
            System.out.println(dql);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(dql);
        
        result = rs.next();
        
        if( result ) {
            
            ArrayList<Object> data = new ArrayList();
            
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                data.add( rs.getObject(i) );
            }
            
            fill(data);
            
            setNovelEntity(false);
            setChangedEntity(false);
            
        }
        
        return result;
    }
    
    public <T extends DataAccessObject> ArrayList<T> getAllTableEntities() throws SQLException {
        
        ArrayList<T> result = new ArrayList<>();
        
        String dql = "SELECT * FROM " + getTableEntity();
        
        if( AppConfig.getInstance().isVerbose() )
            System.out.println(dql);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(dql);
        
        while( rs.next() ) {
            
            ArrayList<Object> data = new ArrayList<>();
            
            for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ) {
                data.add( rs.getObject(i) );
            }
            
            result.add( fill(data).copy() );
        }
        
        st.close();
        
        DataBaseConnections.getInstance().closeConnection(con);
         
        return result;
        
    }
    
    // padrão Template Method
    protected abstract String getWhereClauseForOneEntity();
    protected abstract DataAccessObject fill(ArrayList<Object> data);
    protected abstract <T extends DataAccessObject> T copy();

    @Override
    public boolean equals(Object obj) {
        throw  new RuntimeException("equals must be overrided");
    }
    
}