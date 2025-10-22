package aulas.xml;

import logtrack.ExceptionLogTrack;

public class Teste {
    public static void main(String[] args) {
        
        ExceptionLogTrack.getInstance().addLog( new Exception("Teste!") );
        
    }
}