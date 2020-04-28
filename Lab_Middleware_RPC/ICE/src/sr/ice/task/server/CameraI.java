package sr.ice.task.server;

import SmartHome.Camera;
import SmartHome.UnreachableArgument;
import SmartHome.cameraPosition;
import com.zeroc.Ice.Current;

import java.util.Random;

public class CameraI extends DeviceI implements Camera {
    protected cameraPosition position;
    protected int zoom;

    public CameraI(String name){
        super(SmartHome.deviceType.CAMERA, name);
        this.position = new cameraPosition(90,90);
        this.zoom=50;
    }

    @Override
    public void setPosition(cameraPosition position, Current current) throws UnreachableArgument {
        if(position.horizontalPosition<0 || position.verticalPosition<0 ||
                position.horizontalPosition>360 || position.verticalPosition>180) throw  new UnreachableArgument();
        this.position = position;
        System.out.println(this.name + ": Camera position set:\n \tvretical: "+ position.verticalPosition+" \n\thorizontal: "+position.horizontalPosition);
    }

    @Override
    public void setZoom(int zoom, Current current) throws UnreachableArgument {
        if(zoom<0 || zoom>100) throw  new UnreachableArgument();
        this.zoom=zoom;
        System.out.println(this.name + ": Camera zoom set: " + zoom);
    }

    @Override
    public int getZoom(Current current) {
        System.out.println(this.name + ": Request for camera zoom");
        return this.zoom;
    }

    @Override
    public cameraPosition getPosition(Current current) {
        System.out.println(this.name + ": Request for camera position");
        return this.position;
    }

    @Override
    public byte[] getImage(Current current) {
        byte[] b = new byte[256];
        new Random().nextBytes(b);
        System.out.println(this.name + ": Request for camera image");
        return b;
    }

    @Override
    public String getManual(Current current) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Camera Manual =====\n");
        sb.append("1. on\n");
        sb.append("2. off\n");
        sb.append("3. getStatus\n");
        sb.append("4. setPosition\n");
        sb.append("5. setZoom\n");
        sb.append("6. getInfo\n");
        sb.append("7. getImage\n");
        sb.append("========================");
        return sb.toString();
    }
}
