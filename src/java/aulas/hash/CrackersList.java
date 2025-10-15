package aulas.hash;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CrackersList {
    public static void main(String[] args) throws Exception {

//        https://olhardigital.com.br/2022/12/07/seguranca/saiba-quais-as-50-senhas-mais-usadas-no-brasil/

        String[] passwords = { "123456", "Brasil", "123456789", "12345", "12345678", "102030", "smart2020", "mestre", "1234", "123", "1234567", "10203", "123123", "lele112233", "603", "1234567890", "gabriel", "senha", "flamengo", "654321", "123mudar", "123321", "thiyugi123", "felipe", "senha123", "101010", "convidado", "142536", "família", "júnior", "121212", "987654321", "Rafael", "amor", "vitória", "gustavo", "Matheus", "felicidade", "Camila", "leonardo", "estrela", "112233", "tricolor", "mariana", "lucas123", "daniel", "Henrique", "131313", "sucesso", "marcelo" };
        
        String hsearch = "7110eda4d09e062aa5e4a390b0a572ac0d2c0220";
        
        String algorithm = "SHA-1";         
        
        for( int i  = 0; i < passwords.length; i++ ) {
            
            MessageDigest md = MessageDigest.getInstance( algorithm );
            String h = new BigInteger( 1, md.digest( passwords[ i ].getBytes("UTF-8") ) ).toString(16);
            
            if( h.equals( hsearch ) ) {
                System.out.println( "Senha Possível: " + passwords[ i ] );
            }
            
        }

    }
}
