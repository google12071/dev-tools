package com.learn.ms.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * zkClient使用
 */
public class ZkClientDemo {

    private static final Logger logger = LoggerFactory.getLogger(ZkClientDemo.class);

    public static void main(String[] args) {

        String path="/test/zkClient";

        //创建zk连接客户端
        ZkClient client = new ZkClient("localhost:2181", 3000, 3000, new CustomSerializer());

        //创建临时节点
        if (!client.exists(path)) {
            client.create(path, "123", CreateMode.PERSISTENT);
        }

        //设置子节点监听机制
        client.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                logger.info("parentPath:" + s + "子节点发生变化:" + list);
            }
        });

        //设置数据节点监听机制
        client.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object data) throws Exception {
                logger.info(s + "数据发生变化,data:" + data);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                logger.info(s + "节点删除");
            }
        });

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
