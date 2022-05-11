package net.scgyong.and.cookierun.game;

import android.graphics.Canvas;

import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.util.CollisionHelper;

import java.util.ArrayList;

public class CollisionChecker implements GameObject {
    private final Player player;

    public CollisionChecker(Player player) {
        this.player = player;
    }

    @Override
    public void update(float frameTime) {
        MainGame game = MainGame.get();
//        Player player = (Player) game.objectsAt(MainGame.Layer.player.ordinal()).get(0);
        ArrayList<GameObject> items = game.objectsAt(MainGame.Layer.item.ordinal());
        for (GameObject item: items) {
            if (!(item instanceof BoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (BoxCollidable) item)) {
                //Log.d(TAG, "Collision: " + item);
                game.remove(item);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
