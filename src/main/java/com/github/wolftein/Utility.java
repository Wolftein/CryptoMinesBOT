package com.github.wolftein;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Utility {

    public static <T extends Number> double median(List<T> coll, Comparator<T> comp) {
        double result;
        int n = coll.size() / 2;

        if (coll.size() % 2 == 0)
            result = (nth(coll, n - 1, comp).doubleValue() + nth(coll, n, comp).doubleValue()) / 2.0;
        else
            result = nth(coll, n, comp).doubleValue();

        return result;
    }

    private static <T> T nth(List<T> coll, int n, Comparator<T> comp) {
        T result, pivot;
        ArrayList<T> underPivot = new ArrayList<>(), overPivot = new ArrayList<>(), equalPivot = new ArrayList<>();

        // choosing a pivot is a whole topic in itself.
        // this implementation uses the simple strategy of grabbing something from the middle of the ArrayList.

        pivot = coll.get(n / 2);

        // split coll into 3 lists based on comparison with the pivot

        for (T obj : coll) {
            int order = comp.compare(obj, pivot);

            if (order < 0)        // obj < pivot
                underPivot.add(obj);
            else if (order > 0)   // obj > pivot
                overPivot.add(obj);
            else                  // obj = pivot
                equalPivot.add(obj);
        } // for each obj in coll

        if (n < underPivot.size())
            result = nth(underPivot, n, comp);
        else if (n < underPivot.size() + equalPivot.size())
            result = pivot;
        else
            result = nth(overPivot, n - underPivot.size() - equalPivot.size(), comp);

        return result;
    }

}
