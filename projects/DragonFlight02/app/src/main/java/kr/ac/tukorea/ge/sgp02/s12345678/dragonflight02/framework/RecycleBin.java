package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleBin {

    private static HashMap<Class, ArrayList<Recyclable>> recycleBin = new HashMap<>();
    public static void clear() {
        recycleBin.clear();
    }

    public static Recyclable get(Class clazz) {
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if (bin == null) return null; // 한번도 재활용된 적 없는 클래스다.
        if (bin.size() == 0) return null; // 재활용된적은 있지만 재고가 없다.
        return bin.remove(0);
    }
    public static void add(Recyclable object) {
        object.finish();
        // ...
        Class clazz = object.getClass();
        ArrayList<Recyclable> bin = recycleBin.get(clazz);
        if (bin == null) {
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        if (bin.indexOf(object) >= 0) {
            return;
        }
        bin.add(object);
    }
}
