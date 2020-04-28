package sr.ice.task.server;
// **********************************************************************
//
// Copyright (c) 2003-2019 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Server
{
	public void t1(String[] args)
	{
		int status = 0;
		Communicator communicator = null;

		try
		{
			// 1. Inicjalizacja ICE - utworzenie communicatora
			communicator = Util.initialize(args);

			// 2. Konfiguracja adaptera
			// METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
			//Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");  
			
			// METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie Ÿród³owym
			ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

			// 3. Stworzenie serwanta/serwantów
//			List<Device> devicesList= new ArrayList<>();
//
//			FridgeI fridgeServant1 = new FridgeI("fridge1");
//			FridgeI fridgeServant2 = new FridgeI("fridge2");
//			GarageGateI garageGateServant1 = new GarageGateI("garageGate1");
//			GarageGateI garageGateServant2 = new GarageGateI("garageGate2");
//			CameraI cameraServant1 = new CameraI("camera1");
//			CameraI cameraServant2 = new CameraI("camera2");
//			LightBulb lightBulbServant1 = new LightBulbI("bulb1");
//			LightBulb lightBulbServant2 = new LightBulbI("bulb2");
//
//			devicesList.add(fridgeServant1);
//			devicesList.add(fridgeServant2);
//			devicesList.add(garageGateServant1);
//			devicesList.add(garageGateServant2);
//			devicesList.add(cameraServant1);
//			devicesList.add(cameraServant2);
//			devicesList.add(lightBulbServant1);
//			devicesList.add(lightBulbServant2);
//
//
//			// 4. Dodanie wpisów do tablicy ASM
//			Device[] devices = new Device[devicesList.size()];
//			for(int i=0;i<devices.length;i++){
//				devices[i]=devicesList.get(i);
//				adapter.add(devices[i], new Identity(devices[i].getName(null),"device"));
//			}
//
//			DeviceListI deviceListServant = new DeviceListI(devices);
//
//			adapter.add(deviceListServant,new Identity("deviceList", "devices"));

			ServerServantLocator locator = new ServerServantLocator();
			adapter.addServantLocator(locator, "");

			// 5. Aktywacja adaptera i przejœcie w pêtlê przetwarzania ¿¹dañ
			adapter.activate();
			
			System.out.println("Entering event processing loop...");
			
			communicator.waitForShutdown(); 		
			
		}
		catch (Exception e)
		{
			System.err.println(e);
			status = 1;
		}
		if (communicator != null)
		{
			// Clean up
			//
			try
			{
				communicator.destroy();
			}
			catch (Exception e)
			{
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}


	public static void main(String[] args)
	{
		Server app = new Server();
		app.t1(args);
	}
}
