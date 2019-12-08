package com.learn.java.common.javabase.concurrent.basic;

public class AlarmAgentTest {
    private final static AlarmAgent agent;

    static {
        agent = AlarmAgent.getAgent();
        agent.init();
    }

    public static void main(String[] args) {
        try {
            agent.sendAlarm("alarm message1...");
            Thread.sleep(2000);
            agent.sendAlarm("alarm message2...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
