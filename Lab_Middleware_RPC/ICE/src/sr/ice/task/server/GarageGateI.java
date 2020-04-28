package sr.ice.task.server;

import SmartHome.GarageGate;
import SmartHome.openClose;
import com.zeroc.Ice.Current;

public class GarageGateI extends DeviceI implements GarageGate {
    protected openClose openClose;

    public GarageGateI(String name){
        super(SmartHome.deviceType.GARAGEGATE, name);
        this.openClose = SmartHome.openClose.CLOSE;
    }

    @Override
    public void changeStatus(Current current) {
        if(this.status == SmartHome.status.ON) {
            this.status= SmartHome.status.OFF;
            System.out.println("Gate turned off");
        } else {
            this.status = SmartHome.status.ON;
            System.out.println("Gate turned on");
        }
    }

    @Override
    public void openClose(Current current) {
        if(this.status == SmartHome.status.ON) {
            if(this.openClose == SmartHome.openClose.OPEN) {
                this.openClose = SmartHome.openClose.CLOSE;
                System.out.println(this.name + ": Gate closed");
            } else {
                this.openClose = SmartHome.openClose.OPEN;
                System.out.println(this.name + ": Gate opened");
            }
        } else {
            System.out.println(this.name + ": Gate is turned off, cannot open/close");
        }
    }

    @Override
    public SmartHome.openClose getOpenClose(Current current) {
        System.out.println(this.name + ": Request for garage gate open/close status");
        return openClose;
    }

    @Override
    public String getManual(Current current) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Switch Manual =====\n");
        sb.append("1. on\n");
        sb.append("2. off\n");
        sb.append("3. getStatus\n");
        sb.append("4. turn on/off\n");
        sb.append("5. open/close\n");
        sb.append("6. get open/close status\n");
        sb.append("7. getInfo\n");
        sb.append("========================");
        return sb.toString();
    }
}
