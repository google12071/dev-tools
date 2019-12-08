package com.learn.java.common.javabase.concurrent.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式告警代理类(保证单例)
 */


public class AlarmAgent {

    private final static Logger logger = LoggerFactory.getLogger(AlarmAgent.class);

    /**
     * 单例类实例
     */
    private final static AlarmAgent AGENT = new AlarmAgent();

    /**
     * 标记代理类是否成功连接上告警服务器
     */
    private boolean connectSuccess = false;

    /**
     * 心跳检测线程，用于检测代理类与告警服务器网络连接是否正常
     */
    private final Thread heartBeatThread = new Thread(new HeartBeatThread());

    private AlarmAgent() {
    }

    public AlarmAgent getAgent() {
        return AGENT;
    }

    public void init() {
        connectToServer();
        heartBeatThread.setDaemon(true);
        heartBeatThread.start();
    }

    private void connectToServer() {

    }

    private void doConnect() {

    }

    public void sendAlarm() {

    }

    private void doSendAlarm() {

    }

    class HeartBeatThread implements Runnable {

        @Override
        public void run() {
            logger.info("HeartBeatThread is running...ThreadName:{}," + Thread.currentThread().getName());
        }

        /**
         * 检查与告警服务器连接状态
         *
         * @return
         */
        private boolean checkConnection() {
            return false;
        }
    }

    public static void main(String[] args) {

    }

}
