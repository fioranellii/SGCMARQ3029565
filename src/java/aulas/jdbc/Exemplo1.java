package aulas.jdbc;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Exemplo1 {
    public static void main(String[] args) throws Exception{
     
        final String url = "jdbc:mysql://localhost:3307/sgcm_bd";
        final String user = "root";
        final String password = "root";
        
        Connection con = DriverManager.getConnection(url, user, password);
        System.out.println(con);
        
        Statement st = con.createStatement();
        
       // String dml = "INSERT INTO tipo_usuario (id, modulo_administrativo, modulo_agendamento, modulo_atendimento) VALUES (1, 'S', 'S', 'N' )";
       //String dml = "INSERT INTO tipo_usuario (id, modulo_administrativo, modulo_agendamento, modulo_atendimento) VALUES (2, 'N', 'S', 'S' )";
       //String dml = "UPDATE tipo_usuario SET modulo_administrativo = 'S' WHERE id = 2";
        String dml = "DELETE FROM tipo_usuario WHERE id = 2";
        
        st.execute(dml);
        
        st.close();
        
        con.close();
        
    }
}
