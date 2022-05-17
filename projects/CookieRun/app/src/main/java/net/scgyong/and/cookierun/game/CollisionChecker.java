package net.scgyong.and.cookierun.game;

import android.graphics.Canvas;
import android.util.Log;

import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.util.CollisionHelper;

import java.util.ArrayList;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final Player player;

    public CollisionChecker(Player player) {
        this.player = player;
    }

    @Override
    public void update(float frameTime) {
        MainScene game = MainScene.get();
//        Player player = (Player) game.objectsAt(MainGame.Layer.player.ordinal()).get(0);
        ArrayList<GameObject> items = game.objectsAt(MainScene.Layer.item.ordinal());
        for (GameObject item: items) {
            if (!(item instanceof BoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (BoxCollidable) item)) {
                //Log.d(TAG, "Collision: " + item);
                if (item instanceof JellyItem) {
                    JellyItem jelly = (JellyItem) item;
                    if (!jelly.valid) continue;
                    if (jelly.index == 26) {
                        Log.d(TAG, "Collision: " + jelly);
                        player.changeBitmap();
                    }
                }
                game.remove(item);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
