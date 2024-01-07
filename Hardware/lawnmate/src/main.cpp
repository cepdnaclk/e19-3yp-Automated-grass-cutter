#include <AFMotor.h>
#include <MPU6050_tockn.h>
#include <Wire.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>

#define AWS_IOT_PUBLISH_TOPIC   "esp8266/pub"
#define AWS_IOT_SUBSCRIBE_TOPIC "esp8266/sub"

 
  
//initial motors pin
AF_DCMotor motor1(1, MOTOR12_1KHZ);
AF_DCMotor motor2(2, MOTOR12_1KHZ);
AF_DCMotor motor3(3, MOTOR34_1KHZ);
AF_DCMotor motor4(4, MOTOR34_1KHZ);
  
int val;
int Speeed = 255;
const byte MOTOR1 = 19;  // Motor 1 Interrupt Pin - INT 3
unsigned int counter1 = 0; // to count encoder values
String readString;
MPU6050 mpu6050(Wire);

float gyroX;
float gyroY;
float gyroZ;

float tempX;
float tempY;
float tempZ;

const int timerInterval = 1000;  // Timer interval in milliseconds (adjust as needed)
unsigned long previousMillis = 0;
unsigned long currentMillis;

void ISR_count1()  
{
  counter1++;  
} 

  
void setup()
{
  Serial.begin(9600);
  Serial2.begin(9600);  //Set the baud rate to your Bluetooth module.
  Serial3.begin(9600);  //Set the sim800l module
  attachInterrupt(digitalPinToInterrupt (MOTOR1), ISR_count1, RISING);  // Increase counter 1 when speed sensor pin goes High
  mpu6050.begin();
  mpu6050.calcGyroOffsets(true);
  gyroX = mpu6050.getAngleX();
  gyroY = mpu6050.getAngleY();
  gyroZ = mpu6050.getAngleZ();

  tempX = gyroX;
  tempY = gyroY;
  tempZ = gyroZ;

}


void loop() {

  currentMillis = millis();

  if (currentMillis - previousMillis >= timerInterval) {
        previousMillis = currentMillis;
        gyroX = mpu6050.getAccAngleX();
        gyroY = mpu6050.getAccAngleY();
        gyroZ = mpu6050.getAngleZ();

        if (gyroX - tempX == 5 || gyroY-tempY == 5){

          Serial.print("angleX : ");Serial.print(gyroX - tempX);
          Serial.print("angleY : ");Serial.print(gyroY - tempY);
          Serial.print("Distance : ");Serial.print(counter1);

          counter1 = 0;
          tempX = gyroX;
          tempY = gyroY;
          tempZ = gyroZ;
        }
  }

  while(Serial1.available()){
    delay(50);
    char c=Serial1.read();
    readString+=c;
  }
  if(readString.length()>0){
    Serial.println(readString);
    if (readString =="BACKWARD"){      // MOVE FORWARD
      motor1.run (FORWARD);
      motor2.run (FORWARD);
      motor3.run (FORWARD);
      motor4.run (FORWARD);
    }
    if (readString =="FORWARD"){     // MOVE BACKWARD
      motor1.run (BACKWARD);
      motor2.run (BACKWARD);
      motor3.run (BACKWARD);
      motor4.run (BACKWARD);
    }
    if (readString =="LEFT"){     // MOVE LEFT SIDE
      motor1.run (BACKWARD);
      motor2.run (FORWARD);
      motor3.run (BACKWARD);
      motor4.run (FORWARD);
    }
    if (readString =="RIGHT"){     // MOVE RIGHT SIDE
      motor1.run (FORWARD);
      motor2.run (BACKWARD);
      motor3.run (FORWARD);
      motor4.run (BACKWARD);
    }
    if (readString =="FORWARDLEFT"){     // MOVE FORWARD LEFT
      motor1.run (BACKWARD);
      motor2.run (RELEASE);
      motor3.run (BACKWARD);
      motor4.run (RELEASE);
    }
    if (readString =="FORWARDRIGHT"){     // MOVE FORWARD RIGHT
      motor1.run (RELEASE);
      motor2.run (BACKWARD);
      motor3.run (RELEASE);
      motor4.run (BACKWARD);
    }
    if (readString =="BACKWARDLEFT"){     // MOVE BACKWARD LEFT
      motor1.run (FORWARD);
      motor2.run (RELEASE);
      motor3.run (FORWARD);
      motor4.run (RELEASE);
    }
    if (readString =="BACKWARDRIGHT"){     // MOVE BACKWARD RIGHT
      motor1.run (RELEASE);
      motor2.run (FORWARD);
      motor3.run (RELEASE);
      motor4.run (FORWARD);
    }
    if (readString =="ROTATELEFT"){     // ROTATE LEFT SIDE
      motor1.run (BACKWARD);
      motor2.run (BACKWARD);
      motor3.run (FORWARD);
      motor4.run (FORWARD);
    }
    if (readString =="ROTATERIGHT"){     // ROTATE RIGHT SIDE
      motor1.run (FORWARD);
      motor2.run (FORWARD);
      motor3.run (BACKWARD);
      motor4.run (BACKWARD);
    }
    if (readString =="STOP"){     // STOP
      motor1.run (RELEASE);
      motor2.run (RELEASE);
      motor3.run (RELEASE);
      motor4.run (RELEASE);
    }

    readString="";
  }
}



/*
void connectAWS()
{
  delay(3000);
  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
 
  Serial.println(String("Attempting to connect to SSID: ") + String(WIFI_SSID));
 
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(1000);
  }
 
  NTPConnect();
 
  net.setTrustAnchors(&cert);
  net.setClientRSACert(&client_crt, &key);
 
  client.setServer(MQTT_HOST, 8883);
  client.setCallback(messageReceived);
 
 
  Serial.println("Connecting to AWS IOT");
 
  while (!client.connect(THINGNAME))
  {
    Serial.print(".");
    delay(1000);
  }
 
  if (!client.connected()) {
    Serial.println("AWS IoT Timeout!");
    return;
  }
  // Subscribe to a topic
  client.subscribe(AWS_IOT_SUBSCRIBE_TOPIC);
 
  Serial.println("AWS IoT Connected!");
}


void publishMessage()
{
  StaticJsonDocument<200> doc;
  doc["time"] = millis();
  doc["humidity"] = h;
  doc["temperature"] = t;
  char jsonBuffer[512];
  serializeJson(doc, jsonBuffer); // print to client
 
  client.publish(AWS_IOT_PUBLISH_TOPIC, jsonBuffer);
}
*/