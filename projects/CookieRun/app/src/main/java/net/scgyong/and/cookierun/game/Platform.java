package net.scgyong.and.cookierun.game;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.objects.Sprite;

public class Platform extends Sprite {
    public enum Type {
        T_10x2, T_2x2, T_3x1;
        float width() {
            int w = 1;
            switch (this) {
                case T_10x2: w = 10; break;
                case T_2x2: w = 2; break;
                case T_3x1: w = 3; break;
            }
            return MainGame.get().size(w);
        }
        float height() {
            int h = 1;
            switch (this) {
                case T_10x2: case T_2x2: h = 2; break;
                case T_3x1: h = 1; break;
            }
            return MainGame.get().size(h);
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
    public Platform(Type type, float left, float top) {
        super(left + type.width() / 2, top + type.height() / 2, type.width(), type.height(), type.bitmapId());
    }
}
