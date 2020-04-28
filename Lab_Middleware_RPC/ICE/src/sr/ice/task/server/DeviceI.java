package sr.ice.task.server;

import SmartHome.Device;
import SmartHome.deviceType;
import SmartHome.status;
import com.zeroc.Ice.Current;

public class DeviceI implements Device {
    protected status status;
    protected deviceType deviceType;
    protected String name;

    public DeviceI(deviceType deviceType, String name){
        this.status= SmartHome.status.OFF;
        this.deviceType = deviceType;
        this.name = name;

    }

    @Override
    public void on(Current current) {
        System.out.println(this.name + ": Turned on");
        this.status = SmartHome.status.ON;
    }

    @Override
    public void off(Current current) {
        System.out.println(this.name + ": Turned off");
        this.status = SmartHome.status.OFF;
    }

    @Override
    public status getStatus(Current current) {
        System.out.println(this.name + ": Request for status");
        return this.status;
    }

    @Override
    public String getName(Current current) {
        System.out.println(this.name + ": Request for name");
        return this.name;
    }

    @Override
    public String getManual(Current current) {
        System.out.println(this.name + ": Request for manual");
        return null;
    }

    @Override
    public deviceType getDeviceType(Current current) {
        System.out.println(this.name + ": Request for device type");
        return this.deviceType;
    }
}
