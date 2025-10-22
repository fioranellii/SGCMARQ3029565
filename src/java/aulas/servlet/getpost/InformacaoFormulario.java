package aulas.servlet.getpost;

// JavaBean tem um construtor sem argumentos e métodos de acesso get e set
public class InformacaoFormulario {
    
    private String campoA;
    private String opcaopA;
    private String opcaopB;

    public InformacaoFormulario() {
    }

    public String getCampoA() {
        return campoA;
    }

    public void setCampoA(String campoA) {
        this.campoA = campoA;
    }

    public String getOpcaoA() {
        return opcaopA;
    }

    public void setOpcaoA(String opcaopA) {
        this.opcaopA = opcaopA;
    }

    public String getOpcaoB() {
        return opcaopB;
    }

    public void setOpcaoB(String opcaopB) {
        this.opcaopB = opcaopB;
    }
    
}