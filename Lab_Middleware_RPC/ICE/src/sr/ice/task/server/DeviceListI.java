package sr.ice.task.server;

import SmartHome.Device;
import SmartHome.DeviceList;
import com.zeroc.Ice.Current;

public class DeviceListI implements DeviceList {
    protected Device[] devices;

    public DeviceListI(Device[] devices){
        this.devices = devices;
    }
    @Override
    public String[] getList(Current current) {
        String[] devNames = new String[this.devices.length];
        for(int i =0; i<this.devices.length;i++){
            devNames[i]=this.devices[i].getName(current);
        }
        return devNames;
    }
}
