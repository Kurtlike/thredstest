package barbershop.people;

public class Barber extends Thread{
    public final int id;
    public BarberState state = BarberState.WAITING;
    public Visitor currentVisitor;
    public Barber(int id){
        this.id = id;
        start();
    }

    @Override
    public void run() {
        super.run();
    }
}
