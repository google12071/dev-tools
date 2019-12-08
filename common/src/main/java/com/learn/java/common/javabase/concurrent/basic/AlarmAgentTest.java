package com.learn.java.common.javabase.concurrent.basic;

public class AlarmAgentTest {
    private final static AlarmAgent agent;

    static {
        agent = AlarmAgent.getAgent();
        agent.init();
    }

    public static void main(String[] args) {
        try {
            int i = 0;
            while (i < 100) {
                agent.sendAlarm("alarm message:" + i);
                i++;
                Thread.sleep(2000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
