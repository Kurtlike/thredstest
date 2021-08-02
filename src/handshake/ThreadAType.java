package handshake;

import java.util.concurrent.Semaphore;

/**
 * @author kurt
 */

public class ThreadAType extends Thread {
    Semaphore AReady;
    Semaphore BReady;
    StringBuffer messages;
    String message;
    Semaphore ABlocked;

    public ThreadAType(Semaphore AReady, Semaphore BReady, Semaphore ABlocked, StringBuffer messages, int index){
        this.AReady = AReady;
        this.BReady = BReady;
        this.messages = messages;
        this.ABlocked = ABlocked;

        message = "A" + index;
        this.start();
    }
    @Override
    public void run() {
        while(true) {
            try {
                AReady.acquire();
                Thread.sleep(500);
                messages.append(message);
                BReady.release();
                ABlocked.acquire();
                System.out.println(message + " say hello to " + messages);
                //System.out.println("AR = " + AReady.availablePermits() +
                //        " ,BR = " + BReady.availablePermits() +
                //        " ,AB = " + ABlocked.availablePermits() +
                //        " ,BB = " + BBlocked.availablePermits());
                messages.delete(0, messages.length());
                AReady.release();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
