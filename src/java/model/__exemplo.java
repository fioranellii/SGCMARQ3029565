package model;

public class __exemplo {

    public static void main(String[] args) {

        TipoUsuario tp = new TipoUsuario();

        tp.setId(5);
        tp.setModuloAdministrativo("N");
        tp.setModuloAgendamento("N");
        tp.setModuloAtendimento("S");
        
        tp.save();
        
        tp.setModuloAdministrativo("S");
        
        tp.save();

    }
}
