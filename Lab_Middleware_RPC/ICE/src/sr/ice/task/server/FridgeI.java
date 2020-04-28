package sr.ice.task.server;

import SmartHome.Fridge;
import SmartHome.UnreachableArgument;
import SmartHome.temperature;
import SmartHome.unit;
import com.zeroc.Ice.Current;

import java.util.Random;

public class FridgeI extends DeviceI implements Fridge {
    protected temperature temperature;

    public FridgeI(String name){
        super(SmartHome.deviceType.FRIDGE,name);
        temperature initTemperature = new temperature();
        initTemperature.unit= unit.CELSIUS;
        initTemperature.value = 5;
        this.temperature = initTemperature;
    }


    @Override
    public void setTemp(temperature temp, Current current) throws UnreachableArgument {
        switch (temp.unit) {
            case CELSIUS: {
                if (1.0 > temp.value || temp.value > 15.0) {
                    throw new UnreachableArgument();
                }
                break;
            }
            case FAHRENHEIT: {
                if (33 > temp.value || temp.value > 59) {
                    throw new UnreachableArgument();
                }
                break;
            }
        }

        System.out.println(this.name + ": Temperature set to: " + temp.value + " " + temp.unit);
        this.temperature = temp;

    }

    @Override
    public temperature getTemp(unit unit, Current current) {
        System.out.println(this.name + ": Request for fridge getTemp");
        if(unit == SmartHome.unit.FAHRENHEIT) {
            if (this.temperature.unit == SmartHome.unit.FAHRENHEIT) {
                return temperature;
            } else {
                return new temperature((float) (temperature.value * 1.8 + 32), SmartHome.unit.FAHRENHEIT);
            }
        } else {
            if (this.temperature.unit == SmartHome.unit.CELSIUS) {
                return temperature;
            } else {
                return new temperature((float) ((temperature.value - 32) / 1.8), SmartHome.unit.FAHRENHEIT);
            }
        }
    }

    @Override
    public float getHumidity(Current current) {
        Random r = new Random();
        float random = 0 + r.nextFloat() * (0 - 100);
        System.out.println(this.name + ": Request for fridge humidity");
        return -1*random;
    }

    @Override
    public String getManual(Current current) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Fridge Manual =====\n");
        sb.append("1. on\n");
        sb.append("2. off\n");
        sb.append("3. getStatus\n");
        sb.append("4. setTemperature\n");
        sb.append("5. getTemperature\n");
        sb.append("6. getHumidity\n");
        sb.append("========================");
        return sb.toString();
    }
}
