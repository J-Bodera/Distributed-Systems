package sr.ice.task.server;

import SmartHome.BadArgument;
import SmartHome.LightBulb;
import SmartHome.UnreachableArgument;
import SmartHome.rgbColor;
import com.zeroc.Ice.Current;

public class LightBulbI extends DeviceI implements LightBulb {
    protected rgbColor color;
    protected int brightness;

    public LightBulbI(String name){
        super(SmartHome.deviceType.LIGHTBULB, name);
        rgbColor tmpColor = new rgbColor(252,215,3);
        this.color = tmpColor;
        this.brightness=50;
    }

    @Override
    public void setColor(rgbColor color, Current current) throws BadArgument {
        if(color.RED<0||color.GREEN<0||color.BLUE<0||color.RED>255||color.GREEN>255|color.BLUE>255) throw new BadArgument();
        this.color = color;
        System.out.println(this.name + ": Color set to: [" + color.RED + ", " + color.GREEN + ", " + color.BLUE + "]");
    }

    @Override
    public void changeStatus(Current current) {
        if(this.status == SmartHome.status.ON) {
            this.status= SmartHome.status.OFF;
            System.out.println(this.name + ": turned off");
        } else {
            this.status = SmartHome.status.ON;
            System.out.println(this.name + ": turned on");
        }
    }

    @Override
    public rgbColor getColor(Current current) {
        System.out.println(this.name + ": Request for lightbulb color");
        return this.color;
    }

    @Override
    public void setBrightness(int brightness, Current current) throws UnreachableArgument {
        if(brightness<1 || brightness>100) throw new UnreachableArgument();
        this.brightness=brightness;
        System.out.println(this.name + ": Brightness set to: " + brightness + " %");
    }

    @Override
    public int getBrightness(Current current) {
        System.out.println(this.name + ": Request for lightbulb brightness");
        return this.brightness;
    }

    @Override
    public String getManual(Current current) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== LightBulb Manual =====\n");
        sb.append("1. on\n");
        sb.append("2. off\n");
        sb.append("3. getStatus\n");
        sb.append("4. turn on/off\n");
        sb.append("5. setColor\n");
        sb.append("6. getColor\n");
        sb.append("7. setBrightness\n");
        sb.append("8. getBrightness\n");
        sb.append("9. getInfo\n");
        sb.append("============================");
        return sb.toString();
    }
}
