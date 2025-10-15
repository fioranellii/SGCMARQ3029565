package model;

import java.util.ArrayList;
import model.framework.DataAccessObject;

public class TipoUsuario extends DataAccessObject {
    
    private int id;
    private String nome;
    private String moduloAdministrativo;
    private String moduloAgendamento;
    private String moduloAtendimento;

    public TipoUsuario() {
        super("tipo_usuario");
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getModuloAdministrativo() {
        return moduloAdministrativo;
    }

    public String getModuloAgendamento() {
        return moduloAgendamento;
    }

    public String getModuloAtendimento() {
        return moduloAtendimento;
    }

    public void setId(int id) {
        this.id = id;
        addChange("id", this.id);
    }

    public void setNome(String nome) {
        this.nome = nome;
        addChange("nome", this.nome);
    }

    public void setModuloAdministrativo(String moduloAdministrativo) {
        this.moduloAdministrativo = moduloAdministrativo;
        addChange("modulo_administrativo", this.moduloAdministrativo);
    }

    public void setModuloAgendamento(String moduloAgendamento) {
        this.moduloAgendamento = moduloAgendamento;
        addChange("modulo_agendamento", this.moduloAgendamento);
    }

    public void setModuloAtendimento(String moduloAtendimento) {
        this.moduloAtendimento = moduloAtendimento;
        addChange("modulo_atendimento", this.moduloAtendimento);
    }

    @Override
    protected String getWhereClauseForOneEntity() {
        return " id = " + getId();
    }

    @Override
    protected DataAccessObject fill(ArrayList<Object> data) {
        // segue a ordem das colunas da tabela no banco de dados
        id = (int) data.get(0);
        nome = (String) data.get(1);
        moduloAdministrativo = (String) data.get(2);
        moduloAgendamento = (String) data.get(3);
        moduloAtendimento = (String) data.get(4);
        
        return this;
    }

    @Override
    protected TipoUsuario copy() {
        TipoUsuario cp = new TipoUsuario();
        
        cp.setId(getId());
        cp.setNome(getNome());
        cp.setModuloAdministrativo(getModuloAdministrativo());
        cp.setModuloAgendamento(getModuloAgendamento());
        cp.setModuloAtendimento(getModuloAtendimento());
        
        cp.setNovelEntity(false); // copiou um existente
        
        return cp;
    }
    
    @Override
    public String toString() {
        return "(" + getId() + ", " + getNome() + ", " + getModuloAdministrativo() + ", " + getModuloAgendamento() + ", " + getModuloAtendimento() + ")";
    }
}
