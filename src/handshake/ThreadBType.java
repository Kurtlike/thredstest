package handshake;

import java.util.concurrent.Semaphore;

public class ThreadBType extends Thread {
    Semaphore AReady;
    Semaphore BReady;
    StringBuffer messages;
    String message;
    Semaphore ABlocked;
    public ThreadBType(Semaphore AReady, Semaphore BReady, Semaphore ABlocked, StringBuffer messages, int index){
        this.AReady = AReady;
        this.BReady = BReady;
        this.messages = messages;
        this.ABlocked = ABlocked;
        message = "B" + index;
        this.start();
    }
    @Override
    public void run() {
        while(true) {
            try {
                BReady.acquire();
                //System.out.println(message + " say hello to " + messages);
                messages.delete(0, messages.length());
                messages.append(message);
                ABlocked.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
