package sr.ice.task.client;
// **********************************************************************
//
// Copyright (c) 2003-2019 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import SmartHome.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client 
{
	public static void main(String[] args) 
	{
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);

			// 2. Uzyskanie referencji obiektu na podstawie linii w pliku konfiguracyjnym
			//    (wówczas aplikacjê nale¿y uruchomiæ z argumentem --Ice.config=config.client
			//Ice.ObjectPrx base = communicator.propertyToProxy("Calc1.Proxy");
			
			// 2. To samo co powy¿ej, ale mniej ³adnie
//			ObjectPrx base = communicator.stringToProxy("calc/calc11:tcp -h localhost -p 10000");

			// 3. Rzutowanie, zawê¿anie
//			CalcPrx obj = CalcPrx.checkedCast(base);
//			if (obj == null) throw new Error("Invalid proxy");
			
			// 4. Wywolanie zdalnych operacji
			String input = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			while(true) {
				showManual();
//				String[] devicesNames = loadDevicesNames(communicator);
				String[] devicesNames = loadDevicesNames();
				System.out.print(">> ");
				input = br.readLine();
				ObjectPrx obj = null;

				int i = 1;
				for(String deviceName : devicesNames){
					if(deviceName.equals(input) || input.equals(Integer.toString(i))) {
						obj = communicator.stringToProxy("device/" + deviceName+":tcp -h localhost -p 10000");
						break;
					}
					i++;
				}

				if(input.equals("list")) {
//					loadDevicesNames(communicator);
					loadDevicesNames();
				} else if(input.equals("help")) {
					showManual();
				} else if(input.equals("exit")) {
					break;
				} else if(obj == null) {
					System.out.println("No device " + input + " found!");
				} else {
					control(obj, br);
				}
			}
		} catch (LocalException e) {
			e.printStackTrace();
			status = 1;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;
		}
		if (communicator != null) {
			// Clean up
			//
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}

	private static void showManual() {
		System.out.println("### MANUAL ###");
		System.out.println("help - show manual");
		System.out.println("list - show list of devices");
		System.out.println("exit - turn off program");
		System.out.println("##############\n");
	}

//	private  static String[] loadDevicesNames(Communicator communicator){
//		System.out.println("### DEVICES ###");
//
//		ObjectPrx base = communicator.stringToProxy("devices/deviceList:tcp -h localhost -p 10000");
//		DeviceListPrx object = DeviceListPrx.checkedCast(base);
//		String[] names = object.getList();
//
//		int i = 1;
//		for(String name : names) {
//			System.out.println( i + ". " + name);
//			i++;
//		}
//		System.out.println("---------------------------------------");
//		System.out.println("Type in device name or number to choose");
//		System.out.println("#######################################");
//		return names;
//	}

	private  static String[] loadDevicesNames(){
		System.out.println("###### DEVICES ######");

		String[] names = {"fridge1", "fridge2", "garageGate1", "garageGate2",
							"camera1", "camera2", "bulb1", "bulb2"};

		int i = 1;
		for(String name : names) {
			System.out.println( i + ". " + name);
			i++;
		}
		System.out.println("---------------------------------------");
		System.out.println("Type in device name or number to choose");
		System.out.println("#######################################");
		return names;
	}

	private  static  void control(ObjectPrx obj, BufferedReader br) throws  Exception{
		switch (DevicePrx.checkedCast(obj).getDeviceType()){
			case FRIDGE:{
				FridgePrx fridge = FridgePrx.checkedCast(obj);
				System.out.println(fridge.getManual());
				while(true){
					System.out.print(">> ");
					String input = br.readLine();
					if(input.equals("exit")){ break;}
					manageFridge(fridge, input);
				}
				break;
			}
			case CAMERA:{
				CameraPrx camera =  CameraPrx.checkedCast(obj);
				System.out.println(camera.getManual());
				while(true){
					System.out.print(">> ");
					String input = br.readLine();
					if(input.equals("exit")) break;
					manageCamera(camera, input);
				}
				break;
			}
			case GARAGEGATE:{
				GarageGatePrx myGarageGate =  GarageGatePrx.checkedCast(obj);
				System.out.println(myGarageGate.getManual());
				while(true){
					System.out.print(">> ");
					String input = br.readLine();
					if(input.equals("exit")) break;
					manageGarageGate(myGarageGate, input);
				}
				break;

			}
			case LIGHTBULB:{
				LightBulbPrx bulb = LightBulbPrx.checkedCast(obj);
				System.out.println(bulb.getManual());
				while(true){
					System.out.print(">> ");
					String input = br.readLine();
					if(input.equals("exit")) break;
					manageLightBulb(bulb, input);
				}
				break;

			}

		}

	}

	private static void manageFridge(FridgePrx fridge, String input) {
		switch (Integer.valueOf(input)){
			case 1: {		//on
				fridge.on();
				break;
			}
			case 2:			//off
				fridge.off();
				break;
			case 3: {		//get status
				System.out.println(fridge.getStatus());
				break;
			}
			case 4: {		//setTemperature
				fridgeSetTemp(fridge);
				break;

			}
			case 5:{		// getTemperature
				float tempC = fridge.getTemp(unit.CELSIUS).value;
				float tempF = fridge.getTemp(unit.FAHRENHEIT).value;

				System.out.println("Temperature in fridge is " + tempC + "*C ( " + tempF + "F )");

				break;
			}
			case 6:{		// getHumidity
				float humidity = fridge.getHumidity();
				System.out.println("Humidity in fridge is " + humidity + "%");
				break;
			}
		}
	}

	private static void fridgeSetTemp(FridgePrx fridge) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Choose temperature scale [C - Celsius / F - Fahrenheit]");
		String scale = "";
		try {
			scale = br.readLine();

			float temp = 0;
			if( scale.equals("C") ) {
				System.out.println("Insert temperature in C [1..15*C]:");
				temp = Float.valueOf(br.readLine());
				System.out.println(temp);
				fridge.setTemp(new temperature(temp, SmartHome.unit.CELSIUS));
				System.out.println("Temperature in fridge set to: " + temp + "*C ");
			} else if ( scale.equals("F") ) {
				System.out.println("Insert temperature in F [33..59]:");
				temp = Float.valueOf(br.readLine());
				fridge.setTemp(new temperature(temp, SmartHome.unit.FAHRENHEIT));
				System.out.println("Temperature in fridge set to: " + temp + "F ");
			} else {
				System.out.println("Wrong input");
			}
		} catch (IOException | UnreachableArgument e) {
			e.printStackTrace();
		}


	}

	private static void manageCamera(CameraPrx camera, String input){
		switch (Integer.valueOf(input)){
			case 1: {		//on
				camera.on();
				break;
			}
			case 2:			//off
				camera.off();
				break;
			case 3: {		//get status
				System.out.println(camera.getStatus());
				break;
			}
			case 4: {		// setPosition
				cameraSetPosition(camera);
				break;
			}
			case 5:{		// setZoom
				cameraSetZoom(camera);
				break;
			}
			case 6:{		// getInfo
				System.out.println("Zoom: " + camera.getZoom());
				System.out.println("Position: ");
				System.out.println("\tvertical: " + camera.getPosition().verticalPosition);
				System.out.println("\thorizontal: " + camera.getPosition().horizontalPosition);
				break;
			}
			case 7:{		// getImage
				if(camera.getStatus().equals(status.ON)) {
					byte[]image = camera.getImage();
					int N = (int) Math.sqrt(image.length);
					for (int i = 0; i < N; i++) {
						for (int j = 0; j < N; j++) {
							System.out.print(Integer.toHexString(Math.abs(image[i*N + j]) % 16) + " ");
						}
						System.out.println();
					}
				} else {
					System.out.println("Cannot take photo, because camera is turned off");
				}
				break;
			}

		}
	}

	private static void cameraSetPosition(CameraPrx camera){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Insert vertical position [0..180]: ");
		int verticalPos = 0;
		try {
			verticalPos = Integer.valueOf(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Insert horizontal position [0..360]: ");
		int horizontalPos = 0;
		try {
			horizontalPos = Integer.valueOf(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try{
			camera.setPosition(new cameraPosition(verticalPos, horizontalPos));
		}catch (UnreachableArgument e){
			e.printStackTrace();
		}

	}

	private static void cameraSetZoom(CameraPrx camera) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Insert zoom [0..100]: ");
		int zoom = 0;
		try {
			zoom = Integer.valueOf(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try{
			camera.setZoom(zoom);
			System.out.println("Camera zoom set to: " + zoom);
		}catch (UnreachableArgument e){
			e.printStackTrace();
		}

	}

	private static void manageGarageGate(GarageGatePrx myGarageGate, String input){
		switch (Integer.valueOf(input)) {
			case 1: {		//on
				myGarageGate.on();
				break;
			}
			case 2:			//off
				myGarageGate.off();
				break;
			case 3: {		//get status
				System.out.println(myGarageGate.getStatus());
				break;
			}
			case 4:{		// change status
				myGarageGate.changeStatus();
				break;
			}
			case 5: {		// openClose
				myGarageGate.openClose();
				if (myGarageGate.getStatus().equals(status.ON)) {
					if(myGarageGate.getOpenClose().equals(openClose.OPEN)) {
						System.out.println("Gate opened");
					} else {
						System.out.println("Gate closed");
					}
				} else {
					System.out.println("Gate is turned off, cannot open/close");
				}
				break;
			}
			case 6: {		// getOpenClose
				System.out.println(myGarageGate.getOpenClose());
				break;
			}
			case 7: {		// getInfo
				System.out.println("Power: " + myGarageGate.getStatus());
				System.out.println("Status: " + myGarageGate.getOpenClose());
				break;
			}
		}
	}

	private  static void manageLightBulb(LightBulbPrx bulb, String input){
		switch (Integer.valueOf(input)) {
			case 1: {		//on
				bulb.on();
				break;
			}
			case 2:			//off
				bulb.off();
				break;
			case 3: {		//get status
				System.out.println(bulb.getStatus());
				break;
			}
			case 4:{		// changeStatus
				bulb.changeStatus();
				break;
			}
			case 5: {		// set color
				bulbSetColor(bulb);
				break;
			}
			case 6: {		// get color
				rgbColor color = bulb.getColor();
				System.out.println("Lightbulb color is: [" + color.RED + ", " + color.GREEN + ", " + color.BLUE + "]");
				break;
			}
			case 7: {		// set brightness
				bulbSetBrightness(bulb);
				break;
			}

			case 8:{		// get brightness
				int brightness = bulb.getBrightness();
				System.out.println("Lightbulb brightness: " + brightness + " %");
				break;
			}
			case 9: {		// get info
				System.out.println("Lightbulb overall info");
				rgbColor color = bulb.getColor();
				System.out.println("Color: [" + color.RED + ", " + color.GREEN + ", " + color.BLUE + "]");
				int brightness = bulb.getBrightness();
				System.out.println("Brightness: " + brightness + " %");
				break;
			}
		}
	}

	private static void bulbSetBrightness(LightBulbPrx bulb) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Insert value for brightness [1..100]:");
		int brightness = 0;
		try {
			brightness = Integer.valueOf(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try{
			bulb.setBrightness(brightness);
			System.out.println("Brightness set to: " + brightness + " %");
		}
		catch (UnreachableArgument e){
			e.printStackTrace();
		}

	}

	private static void bulbSetColor(LightBulbPrx bulb) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Insert value for brightness [1..100%]:");
		int r = 0, g = 0, b = 0;
		try {
			System.out.println("R: ");
			r = Integer.valueOf(br.readLine());
			System.out.println("G: ");
			g = Integer.valueOf(br.readLine());
			System.out.println("B:");
			b = Integer.valueOf(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		rgbColor color = new rgbColor(r, g, b);
		try{
			bulb.setColor(color);
		} catch (BadArgument e) {
			e.printStackTrace();
		}
		System.out.println("Color set to: [" + color.RED + ", " + color.GREEN + ", " + color.BLUE + "]");
	}
}