package barbershop.people;

public class Visitor extends Thread{
    public final int id;
    public VisitorState state = VisitorState.OUTSIDE;
    public Visitor(int id){
        this.id = id;
        start();
    }
}
