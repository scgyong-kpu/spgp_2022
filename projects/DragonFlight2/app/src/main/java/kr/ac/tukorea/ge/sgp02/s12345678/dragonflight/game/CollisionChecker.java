package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.CollisionHelper;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.GameObject;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        ArrayList<GameObject> enemies = game.objectsAt(MainGame.Layer.enemy);
        ArrayList<GameObject> bullets = game.objectsAt(MainGame.Layer.bullet);
        for (GameObject o1 : enemies) {
//            if (!(o1 instanceof Enemy)) { continue; }
            Enemy enemy = (Enemy) o1;
            boolean removed = false;
            for (GameObject o2 : bullets) {
//                if (!(o2 instanceof Bullet)) { continue; }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !!");
                    game.remove(bullet);
                    game.remove(enemy);
                    removed = true;
                    break;
                }
            }
            if (removed) {
                continue;
            }
            // check enemy vs fighter
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
