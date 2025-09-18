package model;

import java.sql.SQLException;
import java.util.ArrayList;
import model.framework.Usuario;


public class __exemplo {
    public static void main(String[] args) throws SQLException {
        
//        TipoUsuario tp = new TipoUsuario();
//        
//        tp.setId(51);
//        tp.setModuloAdministrativo("N");
//        tp.setModuloAgendamento("N");
//        tp.setModuloAtendimento("S");
//        
//        tp.save(); // insert
//        
//        tp.setModuloAdministrativo("S");
//        tp.setModuloAgendamento("S");
//        
//        tp.save(); // update
//
//        tp.setId(51);
//        boolean status = tp.load(); // select (read)
//        System.out.println(status);
//        System.out.println(tp);
//       
//        tp.setNome("tipo usuario 51");
//        tp.save(); // update
//        System.out.println(tp);
//      
//        tp.delete(); // delete
//        
//        ArrayList<TipoUsuario> lst = new TipoUsuario().getAllTableEntities();
//        System.out.println( lst );        

        Usuario us = new Usuario();
        
        us.setId(13);
        us.setNome("João");
        us.setSenha("1234");
        us.setTipoUsuarioId(5);
        
        us.save();
    }
}