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
        if (dx > 0) {
            if (dstRect.right > Metrics.width) {
                this.dx = -this.dx;
            }
        } else {
            if (dstRect.left < 0) {
                this.dx = -this.dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height) {
                this.dy = -this.dy;
            }
        } else {
            if (dstRect.top < 0) {
                this.dy = -this.dy;
            }
        }
    }
}
