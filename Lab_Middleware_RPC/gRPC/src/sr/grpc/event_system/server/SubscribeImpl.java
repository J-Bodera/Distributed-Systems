package sr.grpc.event_system.server;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import sr.event.gen.Date;
import sr.event.gen.*;

import java.util.*;

public class SubscribeImpl extends SubscribeGrpc.SubscribeImplBase implements Runnable {
    private final static HashSet<StreamObserver<Event>> observers  = new LinkedHashSet<>();

    public static HashSet<StreamObserver<Event>> getObservers(){return observers;}

    public void subscribe(SubscribeRequest request, StreamObserver<Event> response){
        observers.add(new EventStreamObserver(request,response));
    }


    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try{
                Event event = generateEvent();
                System.out.print("New event: ");
                for(Type t : event.getTypeList()){
                    System.out.print(" "+ t);
                }
                System.out.println("\n\tCountry: " + event.getAddress().getCountry());
                System.out.println("\tCity:    " + event.getAddress().getCity());
                System.out.println("\tDate:    " + event.getDate().getYear() + "-"+event.getDate().getMonth()+"-"+event.getDate().getDay());
                Thread.sleep(2000);

                List<StreamObserver<Event>> dead = new ArrayList<>();
                for(StreamObserver oneObserver : observers){
                    try{
                        oneObserver.onNext(event);
                    }catch (StatusRuntimeException e){
                        dead.add(oneObserver);
                    }
                }
                observers.removeAll(dead);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    private Event generateEvent(){
        Random random = new Random();
        Type type = null;
        switch (Math.abs(random.nextInt())%(Type.values().length)){
            case 0: {
                type = Type.FORMULA1;
                break;
            }
            case 1: {
                type = Type.FOOTBALL;
                break;
            }
            case 3: {
                type = Type.VOLLEYBALL;
                break;
            }
            default:{
                type = Type.SKIJUMPING;
                break;
            }

        }
        String[] countries = new String[]{"Germany", "Italy", "USA", "Russia", "Austria", "Japan"};

        Date date = Date.newBuilder()
                .setYear(2020+Math.abs(random.nextInt())%4)
                .setMonth(Math.abs(random.nextInt())%12)
                .setDay(Math.abs(random.nextInt())%30)
                .setHour(Math.abs(random.nextInt())%24)
                .setMin((Math.abs(random.nextInt())%11)*5)
                .build();

        String country = countries[Math.abs(random.nextInt())%(countries.length-1)];
        Address address = Address.newBuilder()
                .setCountry(country)
                .setCity(getCity(country))
                .setStreet("Main Street")
                .setHouseNumber(Math.abs(random.nextInt())%100)
                .build();

        return Event.newBuilder()
                .setDate(date)
                .setAddress(address)
                .addType(type)
                .build();
    }

    private String getCity(String country) {
        if (country.equals("Niemcy")) return "Monachium";
        else if (country.equals("Wlochy")) return "Mediolan";
        else if (country.equals("USA")) return "Seattle";
        else if (country.equals("Rosja")) return "Moskwa";
        else if (country.equals("Austria")) return "Innsbruck";
        else return "Tokio";
    }
}
