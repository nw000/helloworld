package com.example.nw.helloworld.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wentong.chen on 18/2/4.
 * 功能：
 */

public class TestUtils {
    public static List<String> getListStrings(String itemsufix, int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(itemsufix + " = i ");
        }
        return list;
    }
}
