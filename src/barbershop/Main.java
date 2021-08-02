package barbershop;

import barbershop.people.Barber;
import barbershop.people.BarberState;
import barbershop.people.Visitor;
import barbershop.people.VisitorState;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Semaphore freeBarbers = new Semaphore(0);
        BarbershopRoom barbershopRoom = new BarbershopRoom(4,5,10);
        ArrayList<Visitor> visitors = new ArrayList<>();
        ArrayList<Barber> barbers = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            Visitor visitor = new Visitor(i){
                @Override
                public void run() {
                    try {
                        barbershopRoom.getOnChair(this);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            visitors.add(visitor);
        }
        for(int i = 0; i < 3; i++){
            Barber barber = new Barber(i);
            freeBarbers.release();
            barbers.add(barber);
        }
        VisitorState state = VisitorState.OUTSIDE;
        Visitor visitor10 = visitors.get(30);
        int visSize = visitors.size();
        while (!visitors.isEmpty()){
            if(visitor10.state != state){
                state = visitor10.state;
                System.out.println(state);
            }
            if(visSize != visitors.size()){
                visSize = visitors.size();
                System.out.println(visSize);
            }


            if(barbershopRoom.paymentQueue.isEmpty()){
               freeBarbers.acquire();
               for(Barber barber: barbers){
                   if(barber.state == BarberState.WAITING){
                       for(Visitor visitor: barbershopRoom.visitorsOnChair){
                           if(visitor.state == VisitorState.WAITBARBER){
                               barbershopRoom.getHaircut(visitor, barber);
                               break;
                           }
                       }
                   }
                   break;
               }
                freeBarbers.release();
            }else {
                freeBarbers.acquire();
                for (Barber barber : barbers) {
                    if (barber.state == BarberState.WAITING) {
                        while (!barbershopRoom.paymentQueue.isEmpty()){
                            visitors.remove(barbershopRoom.paymentQueue.get(0));
                            barbershopRoom.pay(barbershopRoom.paymentQueue.get(0), barber);
                        }
                        break;
                    }
                }
                freeBarbers.release();
            }
        }
    }
}
