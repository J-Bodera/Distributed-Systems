#ifndef SMARTHOME_ICE
#define SMARTHOME_ICE

module SmartHome
{
  enum status {ON, OFF};
  enum deviceType {FRIDGE, LIGHTBULB, GARAGEGATE, CAMERA };
  enum unit {CELSIUS, FAHRENHEIT};
  enum openClose {OPEN, CLOSE};
  sequence <byte> image;
  sequence <string> devices;

  struct temperature {
      float value;
      unit unit;
  };

  struct rgbColor{
      int RED;
      int GREEN;
      int BLUE;
  };

  struct cameraPosition{
      int verticalPosition;
      int horizontalPosition;
  };

   exception BadArgument {};
   exception UnreachableArgument {};

  interface Device {
      void on();
      void off();
      status getStatus();
      string getName();
      string getManual();
      deviceType getDeviceType();
   };

   interface DeviceList{
      devices getList();
   };

   interface Fridge extends Device {
      void setTemp(temperature temperature) throws UnreachableArgument;
      temperature getTemp(unit unit);
      float getHumidity();
   };

   interface GarageGate extends Device {
      void changeStatus();
      void openClose();
      openClose getOpenClose();
   };

   interface LightBulb extends Device {
      void changeStatus();
      void setColor(rgbColor color) throws BadArgument;
      rgbColor getColor();
      void setBrightness(int brightness) throws UnreachableArgument;
      int getBrightness();
   };

   interface Camera extends Device {
      void setPosition(cameraPosition position) throws UnreachableArgument;
      void setZoom(int zoom) throws UnreachableArgument;
      int getZoom();
      cameraPosition getPosition();
      image getImage();
   };

};

#endif