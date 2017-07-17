#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_LSM303_U.h>
#include <Adafruit_L3GD20_U.h>
#include <Adafruit_9DOF.h>

//Define Global Variables
Adafruit_9DOF dof   = Adafruit_9DOF();
Adafruit_LSM303_Accel_Unified accel = Adafruit_LSM303_Accel_Unified(30301);

sensors_event_t accel_event;

double accelerations[100];

void initSensors()
{
  if(!accel.begin())
  {
    /* There was a problem detecting the LSM303 ... check your connections */
    Serial.println(F("Ooops, no acc detected ... Check your wiring!"));
    while(1);
  }
}

void setup() {
  // put your setup code here, to run once:

  Serial.begin(115200);
  Serial.println(F("Egg Drop")); Serial.println("");
  
  /* Initialise the sensors */
  initSensors();

}

void loop() {
  // put your main code here, to run repeatedly
    accel.getEvent(&accel_event);
    
    Serial.print(("X: "));
    Serial.println(accel_event.acceleration.x);
    Serial.print(("Y: "));
    Serial.println(accel_event.acceleration.y);
    Serial.print(("Z: "));
    Serial.println(accel_event.acceleration.z);

      sensors_vec_t   orientation;

  /* Calculate pitch and roll from the raw accelerometer data */
  accel.getEvent(&accel_event);
  if (dof.accelGetOrientation(&accel_event, &orientation))
  {
    /* 'orientation' should have valid .roll and .pitch fields */
    Serial.print(F("Roll: "));
    Serial.println(orientation.roll);
    Serial.print(F("Pitch: "));
    Serial.println(orientation.pitch);
  }

  //Get total Acceleration
  Serial.print("Total acceleration: ");
  double totalAcc = (sqrt(sq(accel_event.acceleration.x) + sq(accel_event.acceleration.y) + sq(accel_event.acceleration.z)));
  double oldTotalAcc = totalAcc;
  Serial.println(totalAcc);

  delay(10);

  //Get acceleration again and compare
  accel.getEvent(&accel_event);
  totalAcc = (sqrt(sq(accel_event.acceleration.x) + sq(accel_event.acceleration.y) + sq(accel_event.acceleration.z)));
  Serial.println(totalAcc);

  accelerations[0] = oldTotalAcc;

  //Detect sudden change
  if((abs(totalAcc - oldTotalAcc)) > 20) {

      Serial.println("Sudden Change!");
      
      accelerations[1] = oldTotalAcc;
      Serial.print("Accelerations 1: ");
      Serial.println(accelerations[1]);
      
      long time = micros();
      record(time);
    
  }

}

void record(long time) {

  accel.getEvent(&accel_event);
  double totalAcc = (sqrt(sq(accel_event.acceleration.x) + sq(accel_event.acceleration.y) + sq(accel_event.acceleration.z)));

  double highestAcc = 0;

  int i = 2;

  //Until the acceleration stops spiking, record highest acceleration
  while(totalAcc >= 11) {

    accel.getEvent(&accel_event);

    totalAcc = (sqrt(sq(accel_event.acceleration.x) + sq(accel_event.acceleration.y) + sq(accel_event.acceleration.z)));


    if(i<= 100) {

         accelerations[i] = totalAcc;
    
      }
    
    Serial.print("Acceleration in loop: ");
    Serial.println(totalAcc);

    if(totalAcc > highestAcc) {

      Serial.print("New highest Acc: ");
      Serial.println(totalAcc);

      highestAcc = totalAcc;
      
    }

    i++;
    
  }

  //Get a second counter and compare the times
  long timeDifference = (micros() - time) + 10;

  Serial.print("The deceleration took ");
  Serial.print(timeDifference);
  Serial.println(" milliseconds");

  printData();
  
}

//Print data
void printData() {

  Serial.println("Printing Array Data");

  for(int i = 0; i < 100; i++) {

   int j = 0;

    while(((i+j)<100) && j < 5) {

    Serial.print(accelerations[i+j]);
    Serial.print(", ");

    j++;
      
    }

    i += 4;

    Serial.println();
    
  }
  
}

