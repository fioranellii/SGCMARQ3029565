package model.framework;

import java.sql.Statement;
import java.sql.Connection;
import controller.AppConfig;
import java.util.HashMap;
import java.util.StringJoiner;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe abstrata que implementa o padrão Data Access Object (DAO).
 * Ela fornece uma base para mapear objetos a tabelas de um banco de dados relacional,
 * encapsulando as operações de persistência (CRUD).
 * Utiliza o padrão Unit of Work para otimizar as escritas no banco de dados[cite: 30, 38].
 */
public abstract class DataAccessObject {

    // Nome da tabela do banco de dados correspondente a esta entidade.
    private String tableEntity;
    
    // Flag que indica se o objeto é novo (ainda não persistido).
    // Se true, save() chamará insert(). Se false, chamará update().
    private boolean novelEntity;
    
    // Flag que indica se o estado do objeto foi modificado desde que foi carregado.
    // O método save() só atua se esta flag for true. 
    private boolean changedEntity;
    
    // Mapa que armazena apenas os campos que foram alterados ("dirty fields").
    // Chave: nome da coluna. Valor: novo valor. Essencial para o Unit of Work. 
    private HashMap<String, Object> dirtyFields;

    /**
     * Construtor da classe.
     * @param tableEntity O nome da tabela que este DAO irá gerenciar.
     */
    public DataAccessObject(String tableEntity) {
        setTableEntity(tableEntity);
        dirtyFields = new HashMap<>();

        // Um objeto recém-instanciado é considerado "novo" e "não modificado".
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

    /**
     * Define o nome da tabela, com validação para não aceitar valores nulos ou vazios.
     */
    private void setTableEntity(String tableEntity) {
        if (tableEntity != null
                && !tableEntity.isEmpty()
                && !tableEntity.isBlank()) {
            this.tableEntity = tableEntity;
        } else {
            throw new IllegalArgumentException("table must be valid");
        }
    }

    protected void setNovelEntity(boolean novelEntity) {
        this.novelEntity = novelEntity;
    }
    
    /**
     * Define o estado de modificação do objeto.
     * Se o objeto é marcado como "não modificado" (false), a lista de campos alterados é limpa.
     */
    protected void setChangedEntity(boolean changedEntity) {
        this.changedEntity = changedEntity;
        if (this.changedEntity == false) {
            dirtyFields.clear();
        }
    }

    /**
     * Adiciona uma alteração ao mapa de "dirty fields".
     * Este é o núcleo do padrão Unit of Work: registrar o que mudou.
     * Deve ser chamado pelos setters das classes filhas.
     * @param field O nome da coluna que foi alterada.
     * @param value O novo valor para a coluna.
     */
    protected void addChange(String field, Object value) {
        dirtyFields.put(field, value);
        setChangedEntity(true);
    }

    /**
     * Executa uma operação INSERT no banco de dados.
     * A query é montada dinamicamente apenas com os campos alterados.
     */
    private void insert() throws SQLException {
        // Constrói a query: "INSERT INTO nome_tabela (campo1,campo2) VALUES (?,?)"
        String dml = "INSERT INTO " + getTableEntity();
        StringJoiner fields = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");

        for (String field : dirtyFields.keySet()) {
            fields.add(field);
            values.add("?");
        }

        dml += " (" + fields + ") VALUES (" + values + ")";

        if (AppConfig.getInstance().isVerbose()) {
            System.out.println(dml);
        }

        Connection con = DataBaseConnections.getInstance().getConnection();
        // Usa PreparedStatement para segurança contra SQL Injection.
        PreparedStatement pst = con.prepareStatement(dml);

        // Atribui os valores aos placeholders (?) da query.
        int index = 1;
        for (String field : dirtyFields.keySet()) {
            pst.setObject(index, dirtyFields.get(field));
            index++;
        }

        if (AppConfig.getInstance().isVerbose()) {
            System.out.println(pst);
        }

        pst.execute();
        pst.close();
        DataBaseConnections.getInstance().closeConnection(con);
    }
    
    /**
     * Executa uma operação UPDATE no banco de dados.
     * A query é montada com os campos alterados e a cláusula WHERE apropriada.
     * 🐛 BUG: O laço externo é redundante e ineficiente. Ele geraria uma query para cada campo alterado.
     * O correto seria montar uma única query UPDATE com todos os campos do dirtyFields.
     */
    private void update() {
        System.out.println("update()");
        
        // Este laço externo é um erro. Deveria ser executado apenas uma vez.
        for (String field : dirtyFields.keySet()) {
            String dml = "UPDATE " + getTableEntity() + " SET ";
            StringJoiner changes = new StringJoiner(",");

            for (String fields : dirtyFields.keySet()) {
                changes.add(fields + "=?");
            }

            dml += changes + " WHERE " + getWhereClauseForOneEntity();

            if (AppConfig.getInstance().isVerbose()) {
                System.out.println(dml);
            }
        }
    }

    /**
     * Salva o estado do objeto no banco de dados.
     * Decide entre chamar insert() ou update() com base no estado do objeto.
     * Só age se houverem alterações pendentes (isChangedEntity() == true).
     */
    public void save() throws SQLException {
        if (isChangedEntity()) {
            if (isNovelEntity()) {
                insert();
                setNovelEntity(false); // Após inserir, o objeto não é mais "novo".
            } else {
                update();
            }
            // Após salvar, o objeto está "limpo", sem alterações pendentes.
            setChangedEntity(false);
        }
    }

    /**
     * Deleta o registro correspondente a este objeto do banco de dados.
     */
    public void delete() throws SQLException {
        String dml = "DELETE FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();

        if (AppConfig.getInstance().isVerbose()) {
            System.out.println(dml);
        }

        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        st.execute(dml);
        st.close();
        DataBaseConnections.getInstance().closeConnection(con);
    }

    /**
     * Carrega os dados do banco de dados para o objeto atual.
     * @return true se o registro foi encontrado e carregado, false caso contrário.
     */
    public boolean load() throws SQLException {
        boolean resultado;
        String dql = "SELECT * FROM " + getTableEntity() + " WHERE " + getWhereClauseForOneEntity();

        if (AppConfig.getInstance().isVerbose()) {
            System.out.println(dql);
        }

        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(dql);

        resultado = rs.next();

        if (resultado) {
            ArrayList<Object> data = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                data.add(rs.getObject(i));
            }
            
            // A classe filha preenche seus próprios atributos.
            fill(data);
            setNovelEntity(false); // O objeto agora representa um registro existente.
        }

        return resultado;
    }

    /**
     * Retorna uma lista com todos os objetos/registros da tabela.
     * @param <T> O tipo da classe que estende DataAccessObject.
     * @return Um ArrayList de objetos do tipo T.
     */
    public <T extends DataAccessObject> ArrayList<T> getAllTableEntities() throws SQLException {
        ArrayList<T> result = new ArrayList<>();
        String dql = "SELECT * FROM " + getTableEntity();

        if (AppConfig.getInstance().isVerbose()) {
            System.out.println(dql);
        }

        Connection con = DataBaseConnections.getInstance().getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(dql);

        while (rs.next()) {
            ArrayList<Object> data = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                data.add(rs.getObject(i));
            }
            // Cria uma nova instância e a preenche com os dados da linha atual.
            result.add(fill(data).copy());
        }

        st.close();
        DataBaseConnections.getInstance().closeConnection(con);
        return result;
    }

    /**
     * Método abstrato a ser implementado pela subclasse para criar uma cópia de si mesma.
     * Necessário para o método getAllTableEntities().
     */
    protected abstract <T extends DataAccessObject> T copy();

    /**
     * Método abstrato que preenche os atributos do objeto a partir de uma lista de dados
     * vinda do banco de dados.
     */
    protected abstract DataAccessObject fill(ArrayList<Object> data);
    
    /**
     * Método abstrato que retorna a cláusula WHERE para identificar um único registro.
     * Geralmente, baseia-se na chave primária. Ex: "id = 1".
     * Fundamental para as operações de load, update e delete. [cite: 112]
     */
    protected abstract String getWhereClauseForOneEntity();
}