package barbershop.parts;

import java.util.concurrent.Semaphore;

public class Sofa {
    Semaphore freePlaces;
    public Sofa(int capacity){
        freePlaces = new Semaphore(capacity);
    }

}
