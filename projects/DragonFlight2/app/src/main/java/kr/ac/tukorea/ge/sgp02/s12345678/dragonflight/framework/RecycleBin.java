package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleBin {
    private static final String TAG = RecycleBin.class.getSimpleName();
    protected static HashMap<Class, ArrayList<Recyclable>> recycleBin = new HashMap<>();

    public static void collect(Recyclable object) {
        Class clazz = object.getClass();
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if (bin == null) {
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        if (bin.indexOf(object) >= 0) {
            Log.w(TAG, "Already exists: " + object);
            return;
        }
        bin.add(object);
//        Log.d(TAG, "collect(): " + clazz.getSimpleName() + " : " + bin.size() + " objects " + object);
    }

    public static Recyclable get(Class clazz) {
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if (bin == null) return null;
        if (bin.size() == 0) return null;
//        Log.d(TAG, "get(): " + clazz.getSimpleName() + " : " + (bin.size() - 1) + " objects " + bin.get(0));
        return bin.remove(0);
    }
}
