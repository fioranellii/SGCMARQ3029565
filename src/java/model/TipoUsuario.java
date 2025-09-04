package model;

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

}
