package aulas.xml;

import logtrack.ExceptionLogTrack;

public class Test {
    public static void main(String[] args) {
        ExceptionLogTrack.getInstance().addLog(new Exception("Test!"));
    }
}
