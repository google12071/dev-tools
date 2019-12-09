package com.learn.java.common.oop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HouseAgent implements InvocationHandler {

    private final static Logger logger = LoggerFactory.getLogger(HouseAgent.class);

    private Object target;

    public Object bind(Object delegate) {
        this.target = delegate;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        try {
            logger.info("Before Proxy");
            result = method.invoke(target, args);
            logger.info("After Proxy");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        HouseAgent agent = new HouseAgent();
        Landlord landlord = (Landlord) agent.bind(new LandlordImpl(new House(100, "abcd")));
        landlord.rentHouse();
        landlord = (Landlord) agent.bind(new LandlordImpl2("hello"));
        landlord.rentHouse();
    }
}
