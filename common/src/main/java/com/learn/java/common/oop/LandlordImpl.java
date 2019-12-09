package com.learn.java.common.oop;

public class LandlordImpl implements Landlord {
    private House house;

    public LandlordImpl(House house) {
        this.house = house;
    }

    @Override
    public void rentHouse() {
        System.out.println("I want to rent my house,price is:" + house);
    }
}
