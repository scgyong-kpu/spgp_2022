package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

public class MainGame {
    private static MainGame singleton;
    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }
}
