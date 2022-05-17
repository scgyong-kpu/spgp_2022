package net.scgyong.and.cookierun.game;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.game.RecycleBin;
import net.scgyong.and.cookierun.framework.res.BitmapPool;

import java.util.Random;

public class Platform extends MapSprite {
    private Type type;

    public boolean canPass() {
        return type == Type.T_3x1;
    }

    public enum Type {
        T_10x2, T_2x2, T_3x1, COUNT;
        float width() {
            return widths[ordinal()];
        }
        float height() {
            return heights[ordinal()];
        }
        int bitmapId() {
            return BITMAP_IDS[this.ordinal()];
        }
    }
    protected static int[] BITMAP_IDS = {
            R.mipmap.cookierun_platform_480x48,
            R.mipmap.cookierun_platform_124x120,
            R.mipmap.cookierun_platform_120x40,
    };
    protected static int[] widths = { 10, 2, 3 };
    protected static int[] heights = { 2, 2, 1 };

    public static Platform get(Type type, float unitLeft, float unitTop) {
        Platform platform = (Platform) RecycleBin.get(Platform.class);
        if (platform == null) {
            platform = new Platform();
        }
        platform.init(type, unitLeft, unitTop);
        return platform;
    }

    private Platform() {
    }

    private void init(Type type, float unitLeft, float unitTop) {
        this.type = type;
        bitmap = BitmapPool.get(type.bitmapId());
        setUnitDstRect(unitLeft, unitTop, type.width(), type.height());
    }
}
