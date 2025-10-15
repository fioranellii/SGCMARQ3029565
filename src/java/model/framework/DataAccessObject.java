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

// Recebe o nome da tabela associada e inicializa o objeto como "novo" e "não alterado"
public abstract class DataAccessObject {
    private String tableEntity; // Tabela no banco de dados associada à entidade
    private boolean novelEntity; // Indica se o objeto é "novo" (ainda não existe no banco de dados)
    private boolean changedEntity; // Indica se o objeto sofreu alterações que ainda não foram persistidas no banco
    private HashMap<String, Object> dirtyFields; // Estrutura para armazenar apenas os campos modificados (padrão Unit of Work)

    public DataAccessObject(String tableEntity) {
        setTableEntity(tableEntity);
        dirtyFields = new HashMap<>();
        setNovelEntity(true); // ainda não esta no Banco de Dados
        setChangedEntity(false); // se tem alguma alteração pendente (false)
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

    // Vlida e define o nome da tabela
    private void setTableEntity(String tableEntity) {
        if (tableEntity != null && !tableEntity.isEmpty() && !tableEntity.isBlank()) {
            this.tableEntity = tableEntity;
        }else {
            throw new IllegalArgumentException("table must be valid!");
        }
    }

    // Define se o objeto é novo
    protected void setNovelEntity(boolean novelEntity) {
        this.novelEntity = novelEntity;
    }

    //Define se o objeto foi alterado
    protected void setChangedEntity(boolean changedEntity) {
        this.changedEntity = changedEntity;
        if (this.changedEntity == false) { // Caso não haja mais alteração, limpa os campos sujos
            dirtyFields.clear();
        }
    }
    
    // Unity Of Work
    
    // Marca um campo como alterado, armazenando no mapa de mudançcas
    protected void addChange(String field, Object value) {
        dirtyFields.put(field, value); 
        setChangedEntity(true);
    }
    
    // Insere um novo registro no banco de dados com os campos em dirtyFields
    private void insert() throws SQLException{
        String dml = "INSERT INTO " + getTableEntity();
                
        StringJoiner fields = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");
        
        for(String field: dirtyFields.keySet()) {
            fields.add(field);
            values.add("?");
        }
        
        dml += " (" + fields + ") VALUES (" + values + ")"; 
        
        if(AppConfig.getInstance().isVerbose())
            System.out.println(dml);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(dml);
        
        int index = 1; // sql em java conta o index como 1 e não 0
        for(String field : dirtyFields.keySet()) {
            pst.setObject(index, dirtyFields.get(field)); // preenche os parâmetros ? na ordem correta
            index++;
        }
        
        if(AppConfig.getInstance().isVerbose())
            System.out.println(pst); // imprime a query
        
        pst.execute();
        
        pst.close();
        DataBaseConnections.getInstance().closeConnection(con);
    }
    
    // Atualiza um registro existente no banco de dados com os campos em dirtyFields
    private void update() throws SQLException{
        String dml = "UPDATE " + getTableEntity() + " SET ";
        
        StringJoiner changes = new StringJoiner(",");
        
        for(String field : dirtyFields.keySet()){
            changes.add(field + " = ? "); // já cria os campos ex(nome = ?, login = ?, senha = ?)
        }
        
        dml += changes + " WHERE " + getWhereClauseForOneEntity();
        
        if (AppConfig.getInstance().isVerbose()) {
            System.out.println(dml);
        }
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(dml);
        
        int index = 1; // sql em java conta o index como 1 e não 0
        for(String field : dirtyFields.keySet()) {
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }
        
        if(AppConfig.getInstance().isVerbose())
            System.out.println(pst);
        
        pst.execute();
        
        pst.close();
        DataBaseConnections.getInstance().closeConnection(con);
    }
    
    // Salva o objeto no banco de dados
    public void save() throws SQLException{
        if(isChangedEntity()) {
            
            //salvo
            if(isNovelEntity()) { // se for novo -> insert
                insert();
                setNovelEntity(false); // deixa de ser novo
            } else { // se já existir
                update();
            }
            setChangedEntity(false); // alterações salvas
        }
    }
    
    // Remove o registro do banco de dados
    public void delete() throws SQLException{
        String dml = "DELETE FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();

        if( AppConfig.getInstance().isVerbose() )
            System.out.println(dml);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        
        st.execute(dml);
        st.close();
        
        DataBaseConnections.getInstance().closeConnection(con);
    }
    
    // Carrega os dados do banco e preenche o objeto atual
    public boolean load() throws SQLException{
        boolean resultado;
        
        String dql = "SELECT * FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();
        
        if (AppConfig.getInstance().isVerbose())
            System.out.println(dql);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(dql);
        
        resultado = rs.next();
        
        if (resultado) {
            ArrayList<Object> data = new ArrayList();
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                data.add( rs.getObject(i) );
            }
            
            fill(data); // popula os atributos da entidade com os dados
            setNovelEntity(false); // objeto agora já existe no banco
            setChangedEntity(false);
        }
        
        return resultado;
    }
    
    // Recupera todos os registros da tabela e retorna como lista de objetos
    public <T extends DataAccessObject> ArrayList<T> getAllTableEntities() throws SQLException{
        ArrayList<T> result = new ArrayList<>();
        
        String dql = "SELECT * FROM " + getTableEntity();

        if( AppConfig.getInstance().isVerbose() )
            System.out.println(dql);
        
        Connection con = DataBaseConnections.getInstance().getConnection();
        
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(dql);
        
        while (rs.next()) {            
            ArrayList<Object> data = new ArrayList();
            
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                data.add(rs.getObject(i));                
            }
            
            // Cria uma cópia preenchida do objet
            result.add(fill(data).copy());
        }
        
        st.close();
        
        DataBaseConnections.getInstance().closeConnection(con);
        
        return result;
    }
    
    // padrão Template Method
    protected abstract String getWhereClauseForOneEntity(); // Preenche os atributos da entidade com os dados vindos do banco
    protected abstract DataAccessObject fill(ArrayList<Object> data); // Preenche os atributos da entidade com os dados vindos do banco
    protected abstract <T extends DataAccessObject> T copy(); // Deve retornar uma cópia do objeto atual

    @Override
    public boolean equals(Object obj) {
        throw new RuntimeException("equals must be overrided");
    }
}
