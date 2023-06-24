package com.utospace.plugins.wstalk;

public class PingTimerThread extends Thread {

    private boolean run = true;

    private int interval;

    public PingTimerThread(int interval) {
        this.interval = interval;
        start();
    }

    public void run() {
        while (run) {
            try {
                Thread.sleep(interval * 1000);
                WsServer.ping();
            } catch (InterruptedException e) {
            }
        }
    }

    public void terminate() {
        run = false;
        interrupt();
    }

}
