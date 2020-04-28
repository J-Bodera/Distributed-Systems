package sr.grpc.event_system.server;

import io.grpc.stub.StreamObserver;
import sr.event.gen.Event;
import sr.event.gen.SubscribeRequest;
import sr.event.gen.Type;

import java.util.List;

public class EventStreamObserver implements StreamObserver<Event> {
    private StreamObserver<Event> responseObserver;
    private String country;
    private List<Type> eventTypes;

    public EventStreamObserver(SubscribeRequest request, StreamObserver<Event> responseObserver ){
        this.country = request.getCountry();
        this.eventTypes = request.getTypeList();
        this.responseObserver = responseObserver;
    }


    @Override
    public void onNext(Event event) {
        boolean isOk = false;
        for(int i=0; i < eventTypes.size(); i++){
            for(int j=0; j < event.getTypeList().size(); j++){
                if(eventTypes.get(i).equals(event.getTypeList().get(j))){
                    isOk = true;
                    break;
                }
            }
        }
        if(country.equals(event.getAddress().getCountry()) && isOk){
            responseObserver.onNext(event);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("onError");
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
        System.out.println("Added subscription!");
    }
}
