package kr.ac.tukorea.ge.sgp02.s12345678.samplegame02;

public class MainGame {
    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    private MainGame() {
    }

    private static MainGame singleton;
}
