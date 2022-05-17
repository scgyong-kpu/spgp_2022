package net.scgyong.and.cookierun.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.JsonReader;

import net.scgyong.and.cookierun.R;
import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.objects.SheetSprite;
import net.scgyong.and.cookierun.framework.res.BitmapPool;
import net.scgyong.and.cookierun.framework.res.Metrics;
import net.scgyong.and.cookierun.framework.view.GameView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Player extends SheetSprite implements BoxCollidable {

    private static final float FRAMES_PER_SECOND = 8f;
    private static final String TAG = Player.class.getSimpleName();

    private enum State {
        run, jump, doubleJump, falling, slide, COUNT;
        Rect[] srcRects() {
            return rectsArray[this.ordinal()];
        }
        void applyInsets(RectF dstRect) {
            float[] inset = insets[this.ordinal()];
            float w = dstRect.width();
            float h = dstRect.height();
            dstRect.left += w * inset[0];
            dstRect.top += h * inset[1];
            dstRect.right -= w * inset[2];
            dstRect.bottom -= h * inset[3];
        }
        static Rect[][] rectsArray;
        static void initRects(CookieInfo info) {
            int[][] indices = {
                    new int[] { 100, 101, 102, 103 }, // run
                    new int[] { 7, 8 }, // jump
                    new int[] { 1, 2, 3, 4 }, // doubleJump
                    new int[] { 0 }, // falling
                    new int[] { 9, 10 },
            };
            rectsArray = new Rect[indices.length][];
            for (int r = 0; r < indices.length; r++) {
                int[] ints = indices[r];
                Rect[] rects = new Rect[ints.length];
                for (int i = 0; i < ints.length; i++) {
                    int idx = ints[i];
                    int l = 2 + (idx % 100) * (2 + info.size);
                    int t = 2 + (idx / 100) * (2 + info.size);
                    Rect rect = new Rect(l, t, l + info.size, t + info.size);
                    rects[i] = rect;
                }
                rectsArray[r] = rects;
            }
        }
        static float[][] insets = {
                new float[] { 85/270f, 135/270f, 80/270f, 0.00f }, // run
                new float[] { 85/270f, 158/270f, 80/270f, 0.00f }, // jump
                new float[] { 85/270f, 150/270f, 80/270f, 0.00f }, // doubleJump
                new float[] { 85/270f, 125/270f, 80/270f, 0.00f }, // falling
                new float[] { 80/270f, 204/270f, 50/270f, 0.00f }, // slide
        };
    }
    private State state = State.run;
    private final float jumpPower;
    private final float gravity;
    private float jumpSpeed;
    protected RectF collisionBox = new RectF();


    public Player(float x, float y, float w, float h) {
        super(0, FRAMES_PER_SECOND);
        loadCookiesInfo();
        selectCookie(1);
        this.x = x;
        this.y = y;
        jumpPower = Metrics.size(R.dimen.player_jump_power);
        gravity = Metrics.size(R.dimen.player_gravity);
        setDstRect(w, h);
        setState(State.run);
    }

    private void selectCookie(int cookieIndex) {
        this.cookieIndex = cookieIndex;
        CookieInfo info = cookieInfos.get(cookieIndex);
        State.initRects(info);
        AssetManager assets = GameView.view.getContext().getAssets();
        try {
            String filename = "cookies/" + info.id + "_sheet.png";
            InputStream is = assets.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class CookieInfo {
        int id;
        String name;
        int size;
        int xcount;
        int ycount;
    }
    private ArrayList<CookieInfo> cookieInfos;
    private int cookieIndex;
    private void loadCookiesInfo() {
        cookieInfos = new ArrayList<>();
        AssetManager assets = GameView.view.getContext().getAssets();
        try {
            InputStream is = assets.open("cookies.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                CookieInfo info = new CookieInfo();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("id")) {
                        info.id = reader.nextInt();
                    } else if (name.equals("name")) {
                        info.name = reader.nextString();
                    } else if (name.equals("size")) {
                        info.size = reader.nextInt();
                    } else if (name.equals("xcount")) {
                        info.xcount = reader.nextInt();
                    } else if (name.equals("ycount")) {
                        info.ycount = reader.nextInt();
                    }
                }
                reader.endObject();
                cookieInfos.add(info);
            }
            reader.endArray();

            cookieIndex = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RectF getBoundingRect() {
        return collisionBox;
    }

    @Override
    public void update(float frameTime) {
        float foot = collisionBox.bottom;
        switch (state) {
            case jump:
            case doubleJump:
            case falling:
                float dy = jumpSpeed * frameTime;
                jumpSpeed += gravity * frameTime;
//            Log.d(TAG, "y=" + y + " dy=" + dy + " js=" + jumpSpeed);
                if (jumpSpeed >= 0) {
                    float platformTop = findNearestPlatformTop(foot);
//                    Log.i(TAG, "foot="+foot + " ptop=" + platformTop);
                    if (foot + dy >= platformTop) {
                        dy = platformTop - foot;
                        setState(State.run);
                    }
                }
                y += dy;
                dstRect.offset(0, dy);
                collisionBox.offset(0, dy);
                break;
            case run:
            case slide:
                float platformTop = findNearestPlatformTop(foot);
                if (foot < platformTop) {
                    setState(State.falling);
                    jumpSpeed = 0;
                }
                break;
        }
    }

    private float findNearestPlatformTop(float foot) {
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return Metrics.height;
        return platform.getBoundingRect().top;
    }

    private Platform findNearestPlatform(float foot) {
        Platform nearest = null;
        MainScene game = MainScene.get();
        ArrayList<GameObject> platforms = game.objectsAt(MainScene.Layer.platform.ordinal());
        float top = Metrics.height;
        for (GameObject obj: platforms) {
            Platform platform = (Platform) obj;
            RectF rect = platform.getBoundingRect();
            if (rect.left > x || x > rect.right) {
                continue;
            }
//            Log.d(TAG, "foot:" + foot + " platform: " + rect);
            if (rect.top < foot) {
                continue;
            }
            if (top > rect.top) {
                top = rect.top;
                nearest = platform;
            }
//            Log.d(TAG, "top=" + top + " gotcha:" + platform);
        }
        return nearest;
    }

    public void jump() {
//        Log.d(TAG, "Jump");
        if (state == State.run) {
            setState(State.jump);
            jumpSpeed = -jumpPower;
        } else if (state == State.jump){
            setState(State.doubleJump);
            jumpSpeed = -jumpPower;
        }
    }

    public void slide(boolean startsSlide) {
        if (state == State.run && startsSlide) {
            setState(State.slide);
            return;
        }
        if (state == State.slide && !startsSlide) {
            setState(State.run);
            return;
        }
    }

    public void fall() {
        if (state != State.run) return;
        float foot = collisionBox.bottom;
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return;
        if (!platform.canPass()) return;
        setState(State.falling);
        dstRect.offset(0, 0.001f);
        collisionBox.offset(0, 0.001f);
        jumpSpeed = 0;
    }

    private void setState(State state) {
        this.state = state;
        srcRects = state.srcRects();
        collisionBox.set(dstRect);
        state.applyInsets(collisionBox);
    }
}
