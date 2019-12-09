package com.learn.java.common.oop;

public class LandlordImpl2 implements Landlord {

    private String message;

    public LandlordImpl2(String message) {
        this.message = message;
    }

    @Override
    public void rentHouse() {
        System.out.println("rentHouse message:" + message);
    }
}
