package com.example.s14011.tetorisu;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by s14011 on 15/11/12.
 */
public class Ttoromino {
    private int x;
    private int y;
    private Type type;



    public enum Type {
        1(1),0(2),5(3),2(4),3(5),

        private static LinkedList<Type>
        private final int id;

        private Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Type nextType() {
            if ()
        }
    }
}
