package net.scgyong.and.cookierun.game;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.game.RecycleBin;

public class Obstacle extends MapSprite {
    public static Obstacle get(int index, float unitLeft, float unitTop) {
        Obstacle obs = (Obstacle) RecycleBin.get(Obstacle.class);
        if (obs == null) {
            obs = new Obstacle();
        }
        obs.init(index, unitLeft, unitTop);
        return obs;
    }

    private static int[][] BITMAP_ID_ARRAYS = {
            new int[] {
                    R.mipmap.epn01_tm01_jp1a,
            },
            new int[] {
                    R.mipmap.epn01_tm01_jp1up_01,
                    R.mipmap.epn01_tm01_jp1up_02,
                    R.mipmap.epn01_tm01_jp1up_03,
                    R.mipmap.epn01_tm01_jp1up_04,
            },
            new int[] {
                    R.mipmap.epn01_tm01_jp2up_01,
                    R.mipmap.epn01_tm01_jp2up_02,
                    R.mipmap.epn01_tm01_jp2up_03,
                    R.mipmap.epn01_tm01_jp2up_04,
                    R.mipmap.epn01_tm01_jp2up_05,
            },
            new int[] {
                    R.mipmap.epn01_tm01_sda,
            }
    };

    private void init(int index, float unitLeft, float unitTop) {
        MainGame game = MainGame.get();
        float left = game.size(unitLeft);
        float top = game.size(unitTop);
        float unit = game.size(1);
        dstRect.set(left, top, left + unit, top + unit);
    }
}