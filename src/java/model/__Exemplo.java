package model;

import java.sql.SQLException;
import java.util.ArrayList;

public class __Exemplo {
    public static void main(String[] args) throws SQLException {

//        TipoUsuario tp = new TipoUsuario();
////        
////        tp.setId(12);
////        tp.setModuloAdministrativo("N");
////        tp.setModuloAgendamento("N");
////        tp.setModuloAtendimento("S");
////        
////        tp.save(); // insert
////        
////        tp.setModuloAdministrativo("S");
////        tp.save(); // update
//
//          tp.setId(12);
//          boolean status = tp.load(); // select (read)
//          System.out.println(status);
//          System.out.println(tp);
//          
//          tp.setNome("tipo 12");
//          tp.save(); // update
//          
////          tp.delete();
//
          ArrayList<TipoUsuario> list = new TipoUsuario().getAllTableEntities();
          System.out.println(list);
          
//         Usuario us = new Usuario();
//         
//         us.setId(5);
//         us.setNome("Carlinhos");
//         us.setCpf("12342355476");
//         us.setSenha("asbdjk");
//         us.setTipoUsuarioId(12);
//         us.save();

          
    }
}
