package model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import model.framework.DataAccessObject;

public class Usuario extends DataAccessObject {
    
    private int id;
    private String nome;
    private String cpf;
    private String senha;
    private int tipoUsuarioId;

    
    public Usuario() {
        super("usuarios");
    }
    
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public int getTipoUsuarioId() {
        return tipoUsuarioId;
    }
    
    
    public void setId(int id) {
        this.id = id;
        addChange("id", this.id);
    }

    public void setNome(String nome) {
        this.nome = nome;
        addChange("nome", this.nome);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        addChange("cpf", this.cpf);
    }

    public void setSenha(String senha) throws Exception {
        if( senha == null ) {
            if( this.senha != null ) {
                this.senha = senha;
                addChange("senha", this.senha);
            }
        } else {
            if( senha.equals(this.senha) == false ) {
                
                String senhaSal = getId() + senha + getId() / 2;
                
                MessageDigest md = MessageDigest.getInstance( "SHA-256" );
                String hash = new BigInteger( 1, md.digest( senhaSal.getBytes("UTF-8") ) ).toString(16);
                
                this.senha = hash;
                addChange("senha", this.senha);
            }
        }     
    }

    public void setTipoUsuarioId(int tipoUsuarioId) {
        if( this.tipoUsuarioId != tipoUsuarioId ) {
            this.tipoUsuarioId = tipoUsuarioId;
            addChange("tipo_usuario_id", this.tipoUsuarioId);
        }
    }

    
    @Override 
    protected String getWhereClauseForOneEntity() {
        return " id = " + getId();
    }

    @Override
    protected DataAccessObject fill(ArrayList<Object> data) {
        id = (int) data.get(0);
        nome = (String) data.get(1);
        cpf = (String) data.get(2);
        senha = (String) data.get(3);
        tipoUsuarioId = (int) data.get(4);
        return this;
    }

    @Override
    protected Usuario copy() {
        Usuario cp = new Usuario();
        
        cp.setId( getId() );
        cp.setNome( getNome() );
        cp.setCpf( getCpf() );
        cp.senha = getSenha();
        cp.setTipoUsuarioId( getTipoUsuarioId() );
        
        cp.setNovelEntity(false);
        
        return cp;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Usuario ) {
            Usuario aux = (Usuario) obj;
            if( getId() == aux.getId() ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}