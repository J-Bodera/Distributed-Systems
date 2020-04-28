package sr.grpc.event_system.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sr.event.gen.Event;
import sr.event.gen.SubscribeGrpc;
import sr.event.gen.SubscribeRequest;
import sr.event.gen.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Client {
    private final ManagedChannel channel;
    private final SubscribeGrpc.SubscribeBlockingStub eventSubscribeStub;

    public Client(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        eventSubscribeStub = SubscribeGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args)throws Exception{
        Client client = new Client("localhost", 50051);
        client.start();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private void start() throws  IOException, InterruptedException{
        BufferedReader br = new BufferedReader((new java.io.InputStreamReader(System.in)));
        String[] countries = new String[]{"Germany", "Italy", "USA", "Russia", "Austria", "Japan"};
        printCountries(countries);
        System.out.print(">> ");
        String input = br.readLine();
        String country = null;

        // insert country to subscribe
        int i = 1;
        for(String tmpCountry : countries) {
            if(tmpCountry.equals(input) || input.equals(Integer.toString(i))) {
                country = tmpCountry;
                break;
            }
            i++;
        }

        // insert event type
        printEventTypes();
        System.out.print(">> ");
        String typeStr = br.readLine();
        Type type = Type.forNumber(Integer.valueOf(typeStr));

        SubscribeRequest request = SubscribeRequest.newBuilder()
                .setCountry(country)
                .addType(type)
                .build();

        System.out.println("Your subscription is: ");
        System.out.println(type + " in " + country);
        System.out.println("###############################");
        System.out.println("\n######### RESULTS #########");

        int res;
        while(true){
            res = manageSubscribe(request);
            if(res == 0) {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Subscription restart!");
            } else if (res == 1) {
                shutdown();
            } else if (res == 2) {
                start();
            }
        }
    }

    private int manageSubscribe(SubscribeRequest request){
        BufferedReader br = new BufferedReader((new java.io.InputStreamReader(System.in)));
        String in = null;
        Iterator<Event> events;
        events = eventSubscribeStub.subscribe(request);
        while(events.hasNext()){
            System.out.println("New Event!");
            Event event = events.next();

            System.out.print("Type: ");
            for(Type t : event.getTypeList()){
                System.out.print(t + " ");
            }
            System.out.println("Address: " + event.getAddress().getCountry() + " " + event.getAddress().getCity()
                    + " " + event.getAddress().getStreet() + " " + event.getAddress().getHouseNumber());
            System.out.println("Date: " + event.getDate().getYear() + "." + event.getDate().getMonth() + "." + event.getDate().getDay());
            System.out.println("Time: " + event.getDate().getHour() + ":" + event.getDate().getMin());

            try {
                in = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(in.equals("exit")) {
                return 1;
            } else if (in.equals("change")) {
                return 2;
            }
        }
        return 0;
    }

    private void printCountries(String[] countries) {
        int i = 1;
        System.out.println("############# COUNTRIES ############");
        for(String country : countries) {
            System.out.println("# " + i + ". " + country);
            i++;
        }
        System.out.println("# ----------------------------------");
        System.out.println("# Type in country or number to choose");
        System.out.println("####################################");
    }

    private void printEventTypes() {
        System.out.println("############ EVENT TYPES ###########");
        for(int i = 0; i < Type.values().length-1; i++){
            System.out.println("# " + i + ": "+Type.forNumber(i));
        }
        System.out.println("# ----------------------------------");
        System.out.println("# Type number to choose");
        System.out.println("####################################");
    }
}
