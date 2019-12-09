package com.learn.java.common.oop;

public class House {
    private Integer price;
    private String address;

    public House(Integer price, String address) {
        this.price = price;
        this.address = address;
    }

    @Override
    public String toString() {
        return "House{" +
                       "price=" + price +
                       ", address='" + address + '\'' +
                       '}';
    }
}
