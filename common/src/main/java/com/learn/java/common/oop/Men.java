package com.learn.java.common.oop;

public class Men extends Person {
    public void playGames() {
        System.out.println("playGames");
    }

    @Override
    public void eat() {
        System.out.println("men eating");
    }
}
