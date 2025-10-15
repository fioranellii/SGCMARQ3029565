package aulas.jdbc;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Exemplo2 {
    public static void main(String[] args) throws SQLException{
        
        final String url = "jdbc:mysql://localhost:3307/sgcm_bd"; // ou 3306 para a porta usada em casa
        final String user = "root";
        final String passworld = "root";
        
        Connection con = DriverManager.getConnection(url, user, passworld);
        
        System.out.println(con);
        
        Statement st = con.createStatement();

        String dql = "SELECT * FROM tipo_usuario";
        
        ResultSet rs = st.executeQuery(dql);
        ResultSetMetaData rsm = rs.getMetaData();

        // 1 to <=
        for(int i = 1; i <= rsm.getColumnCount(); i++){ // imprimi os nomes das colunas
            System.out.print(rsm.getColumnLabel(i) + " ");
        }
        System.out.println("");

        while(rs.next()) {
            for(int i = 1; i <= rsm.getColumnCount(); i++) {
                System.out.print(rs.getObject(i) + " ");
            }
            System.out.println("");
        }

        rs.close();
        
        st.close();
        con.close();
    }
}
