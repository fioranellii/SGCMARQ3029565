package model;

import java.util.ArrayList;
import model.framework.DataAccessObject;

/**
 * Representa um usuário no sistema. Esta classe herda de DataAccessObject para
 * mapear a entidade 'Usuario' para a tabela 'usuarios' no banco de dados,
 * conforme o script fornecido.
 */
public class Usuario extends DataAccessObject {

    // Atributos que correspondem às colunas da tabela 'usuarios'.
    private int id;
    private String nome;
    private String CPF;
    private String senha;
    private int tipoUsuarioId; // Chave estrangeira para a tabela 'tipo_usuario'.

    /**
     * Construtor padrão. Chama o construtor da superclasse, especificando o
     * nome da tabela que esta classe irá gerenciar.
     */
    public Usuario() {
        super("usuarios");
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCPF() {
        return CPF;
    }

    public String getSenha() {
        return senha;
    }

    public int getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    // --- Setters ---
    // Cada setter, além de atribuir o valor, chama o método addChange()
    // para registrar a alteração, permitindo que o padrão Unit of Work funcione.
    public void setId(int id) {
        this.id = id;
        addChange("id", this.id);
    }

    public void setNome(String nome) {
        this.nome = nome;
        addChange("nome", this.nome);
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
        addChange("CPF", this.CPF); // Corrigido para o nome da coluna "CPF"
    }

    public void setSenha(String senha) {
        if (this.senha == null) {
            if (senha != null) {
                this.senha = senha;
                addChange("senha", this.senha);
            }
        } else {
            if (senha == null) {
                this.senha = null;
                addChange("senha", this.senha);
            } else {
                if (this.senha.equals(senha) == false) {
                    this.senha = senha;
                    addChange("senha", this.senha);
                }
            }
        }
        this.senha = senha;
        addChange("senha", this.senha);
    }

    public void setTipoUsuarioId(int tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
        addChange("tipo_usuario_id", this.tipoUsuarioId);
    }

    /**
     * Implementação do método abstrato da superclasse. Define a cláusula WHERE
     * para identificar um único usuário no banco de dados, utilizando a chave
     * primária 'id'.
     *
     * @return Uma string com a condição SQL, ex: "id = 1".
     */
    @Override
    protected String getWhereClauseForOneEntity() {
        return "id = " + getId();
    }

    /**
     * Implementação do método abstrato da superclasse. Preenche os atributos do
     * objeto Usuario a partir de uma lista de dados recuperada do banco de
     * dados (ResultSet). A ordem dos elementos na lista deve corresponder à
     * ordem das colunas na tabela (id, nome, CPF, senha, tipo_usuario_id).
     * ArrayList de Objects contendo os dados de uma linha da tabela.
     *
     * @return A própria instância do objeto (this) após ser preenchida.
     */
    @Override
    protected DataAccessObject fill(ArrayList<Object> data) {
        id = (int) data.get(0);
        nome = (String) data.get(1);
        CPF = (String) data.get(2); // Corrigido para preencher o CPF
        senha = (String) data.get(3);
        tipoUsuarioId = (int) data.get(4);

        return this;
    }

    /**
     * Implementação do método abstrato da superclasse. Cria e retorna uma nova
     * instância de Usuario com os mesmos dados do objeto atual. É utilizado
     * pelo método getAllTableEntities() para criar a lista de objetos.
     *
     * @return Uma nova instância de Usuario (cópia).
     */
    @Override
    protected Usuario copy() {
        Usuario cp = new Usuario();

        cp.setId(id);
        cp.setNome(nome);
        cp.setCPF(CPF);
        cp.setSenha(senha);
        cp.setTipoUsuarioId(tipoUsuarioId);

        // Marca a cópia como uma entidade que já existe no banco, não uma nova.
        cp.setNovelEntity(false);

        return cp;
    }

    /**
     * Sobrescrita do método toString para facilitar a visualização dos dados do
     * objeto.
     *
     * @return Uma representação em String do objeto Usuario.
     */
    @Override
    public String toString() {
        return "Usuario{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", CPF='" + CPF + '\''
                + ", senha='" + "********" + '\''
                + // Não expor a senha
                ", tipoUsuarioId=" + tipoUsuarioId
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Usuario) {
            Usuario aux = (Usuario) obj;
            if (getId() == aux.getId()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
