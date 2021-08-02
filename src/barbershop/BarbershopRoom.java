package barbershop;

import barbershop.parts.Cashbox;
import barbershop.parts.Chair;
import barbershop.parts.Sofa;
import barbershop.people.Barber;
import barbershop.people.BarberState;
import barbershop.people.Visitor;
import barbershop.people.VisitorState;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class BarbershopRoom {
    private Semaphore chairs;
    private Semaphore sofa;
    private Cashbox cashbox;
    private Semaphore standingQueue;
    private ArrayList<Barber> barbers;
    public ArrayList<Visitor> visitorsOnChair = new ArrayList<>();
    public ArrayList<Visitor> paymentQueue = new ArrayList<>();
    public BarbershopRoom(int chairsNumber, int sofaCapacity, int standingPlaces){
        standingQueue = new Semaphore(standingPlaces);
        chairs = new Semaphore(chairsNumber);
        sofa = new Semaphore(sofaCapacity);
        barbers = new ArrayList<>();
    }
    public void getOnChair(Visitor visitor) throws InterruptedException {
        standingQueue.acquire();
        visitor.state = VisitorState.STANDING;
        sofa.acquire();
        visitor.state = VisitorState.SOFA;
        standingQueue.release();
        chairs.acquire();
        visitor.state = VisitorState.WAITBARBER;
        visitorsOnChair.add(visitor);
        sofa.release();
    }
    public void getHaircut(Visitor visitor, Barber barber){
            visitor.state = VisitorState.CHAIR;
            barber.state = BarberState.WORKING;

            visitorsOnChair.remove(visitor);
            chairs.release();
            barber.state = BarberState.WAITING;
            visitor.state = VisitorState.PAYS;
            paymentQueue.add(visitor);


    }
    public void pay(Visitor visitor, Barber barber){

            barber.state = BarberState.CASHIER;

            barber.state = BarberState.WAITING;
            visitor.state = VisitorState.FINISHED;
            paymentQueue.remove(visitor);


    }
}
