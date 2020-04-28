package sr.ice.task.server;

import com.zeroc.Ice.Object;
import com.zeroc.Ice.*;

import java.util.HashMap;
import java.util.Map;

public class ServerServantLocator implements ServantLocator {
    private final Map<String, Object> servants = new HashMap<>();

    @Override
    public LocateResult locate(Current current) throws UserException {
        String name = current.id.name;
        if (servants.containsKey(name))
            return new ServantLocator.LocateResult(servants.get(name), null);

        System.out.println("Creating servant for " + name);
        Object servant;
        switch (name) {
            case "fridge1":
                servant = new FridgeI("fridge1");
                break;
            case "fridge2":
                servant = new FridgeI("fridge2");
                break;
            case "garageGate1":
                servant = new GarageGateI("garageGate1");
                break;
            case "garageGate2":
                servant = new GarageGateI("garageGate2");
                break;
            case "camera1":
                servant = new CameraI("camera1");
                break;
            case "camera2":
                servant = new CameraI("camera2");
                break;
            case "bulb1":
                servant = new LightBulbI("bulb1");
                break;
            case "bulb2":
                servant = new LightBulbI("bulb2");
                break;
            default:
                throw new ObjectNotFoundException();
        }

        servants.put(name, servant);
        return new ServantLocator.LocateResult(servant, null);
    }

    @Override
    public void finished(Current arg0, Object arg1, java.lang.Object arg2) throws UserException {
    }

    @Override
    public void deactivate(String s) {

    }
}
