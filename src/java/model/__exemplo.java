package model;

import java.sql.SQLException;
import java.util.ArrayList;

public class __exemplo {

    public static void main(String[] args) throws SQLException {

        TipoUsuario tp = new TipoUsuario();
        //  tp.setId(51);
        // tp.setModuloAdministrativo("N");
        // tp.setModuloAgendamento("N");
        // tp.setModuloAtendimento("S");
        //  tp.save(); // insert
        //  tp.setModuloAdministrativo("S");
        // tp.setModuloAgendamento("S");
        // tp.save(); // update

        // tp.setId(51);
        // tp.load();
        // System.out.println(tp);
        //  tp.setNome("Tipo 51");
        //  tp.save();
        //   System.out.println(tp);
        //   tp.delete();
        
        ArrayList<TipoUsuario> lst = new TipoUsuario().getAllTableEntities();
        System.out.println(lst);

    }
}
