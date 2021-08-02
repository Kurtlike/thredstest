package handshake;

import java.util.concurrent.Semaphore;

/**
 * @author kurt
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Semaphore AReady = new Semaphore(1);
        Semaphore BReady = new Semaphore(1);
        StringBuffer messages = new StringBuffer();
        Semaphore ABlocked = new Semaphore(1);
        Semaphore BBlocked = new Semaphore(1);
        ABlocked.acquire();
        BReady.acquire();
        BBlocked.acquire();
        for( int i = 0; i < 5; i++){
            ThreadAType thA = new ThreadAType(AReady, BReady, ABlocked, messages, i);
            ThreadBType thB = new ThreadBType(AReady, BReady, ABlocked, messages, i);
        }
    }
}
