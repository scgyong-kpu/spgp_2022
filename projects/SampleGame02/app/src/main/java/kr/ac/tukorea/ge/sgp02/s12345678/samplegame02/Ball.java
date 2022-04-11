package kr.ac.tukorea.ge.sgp02.s12345678.samplegame02;

public class Ball extends Sprite {
    private float dx, dy;

    public Ball(float dx, float dy) {
        super(100, 100, R.dimen.ball_radius, R.mipmap.soccer_ball_240);
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        MainGame game = MainGame.getInstance();
        float dx = this.dx * game.frameTime;
        float dy = this.dy * game.frameTime;
        dstRect.offset(dx, dy);
        if ((dx > 0 && dstRect.right > Metrics.width) || (dx < 0 && dstRect.left < 0)) {
            this.dx = -this.dx;
        }
        if ((dy > 0 && dstRect.bottom > Metrics.height) || (dy < 0 && dstRect.top < 0)) {
            this.dy = -this.dy;
        }
    }
}
