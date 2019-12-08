package com.learn.java.common.javabase.concurrent.basic;

import com.learn.java.common.javabase.concurrent.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

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

    public static AlarmAgent getAgent() {
        return AGENT;
    }

    public void init() {
        connectToServer();
        heartBeatThread.setDaemon(true);
        heartBeatThread.start();
    }

    private void connectToServer() {
        //创建并启动网络连接线程
        new Thread(() -> {
            doConnect();
        }, "connectThread").start();
    }

    private void doConnect() {
        //模拟建立socket网络连接的耗时操作
        Tools.randomPause(100);
        synchronized (this) {
            //标记与告警服务器建立连接成功
            connectSuccess = true;
            logger.info(Thread.currentThread().getName() + ",connectToServer success");
            //通知等待线程
            notify();
        }
    }

    /**
     * 上报告警消息
     *
     * @param message
     */
    public void sendAlarm(String message) throws InterruptedException {
        synchronized (this) {
            //网络未建立成功建立连接时，等待
            while (!connectSuccess) {
                logger.info(Thread.currentThread().getName() + "," + "waiting...");
                wait();
            }
            //连接成功后执行发送动作
            doSendAlarm(message);
        }
    }

    private void doSendAlarm(String message) {
        logger.info(Thread.currentThread().getName() + "," + "sendAlarm success,{}" + message);
    }

    class HeartBeatThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                //持续检测与服务器连接是否正常
                while (true) {
                    if (checkConnection()) {
                        logger.info(Thread.currentThread() + ",connect to server success");
                        connectSuccess = true;
                    } else {
                        connectSuccess = false;
                        logger.info(Thread.currentThread() + ",disconnect from server");
                        //尝试重新连接
                        connectToServer();
                    }
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("HeartBeatThread is running...ThreadName:{}," + Thread.currentThread().getName());
        }

        /**
         * 检查与告警服务器连接状态(随机性断连)
         *
         * @return
         */
        private boolean checkConnection() {
            return new Random().nextInt(10) > 5;
        }
    }
}
