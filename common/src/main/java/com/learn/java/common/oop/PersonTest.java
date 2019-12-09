package com.learn.java.common.oop;

public class PersonTest {

    public static void eat(Person person) {
        person.eat();
    }

    public static void dress(Women women) {
        women.dressing();
    }

    public static void main(String[] args) {
        eat(new Men());
        eat(new Women());
        dress(new Women());
    }
}
