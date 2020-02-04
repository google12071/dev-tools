package com.learn.javasrc.concurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义共享锁
 *
 * @author lfq
 */
@Slf4j
public class ShareLock implements Serializable {
    /**
     * 最大共享数
     */
    private Integer maxCount;

    /**
     * 同步器
     */
    private Sync sync;

    /**
     * 构造器初始化锁信息
     *
     * @param maxCount
     */
    public ShareLock(Integer maxCount) {
        this.maxCount = maxCount;
        this.sync = new Sync(maxCount);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        /**
         * 表示只有count个共享锁可获得
         *
         * @param count
         */
        public Sync(int count) {
            setState(count);
        }

        public boolean acquireByShared(int i) {
            // 自旋保证 CAS 一定可以成功
            while (true) {
                if (i <= 0) {
                    return false;
                }
                int state = getState();
                // 如果没有锁可以获得，直接返回 false
                if (state <= 0) {
                    return false;
                }
                int expectState = state - i;
                // 如果要得到的锁不够了，直接返回 false
                if (expectState < 0) {
                    return false;
                }
                // CAS 尝试得到锁,CAS 成功获得锁，失败继续 for 循环
                if (compareAndSetState(state, expectState)) {
                    return true;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) {
                if (arg <= 0) {
                    return false;
                }
                int state = getState();
                int expectState = state + arg;
                // 超过了 int 的最大值，或者 expectState 超过了我们的最大预期
                if (expectState < 0 || expectState > maxCount) {
                    log.error("state 超过预期，当前 state is {},计算出的 state is {}", state
                            , expectState);
                    return false;
                }
                if (compareAndSetState(state, expectState)) {
                    return true;
                }
            }
        }
    }

    public boolean lock() {
        return sync.acquireByShared(1);
    }

    public boolean unLock() {
        return sync.tryReleaseShared(1);
    }
}

