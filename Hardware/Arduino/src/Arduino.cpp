#include <AFMotor.h>
#include <MPU6050_tockn.h>
#include <Wire.h>
#include<Servo.h>
#include<NewPing.h>

//initial motors pin
AF_DCMotor motor1(1, MOTOR12_1KHZ);
AF_DCMotor motor2(2, MOTOR12_1KHZ);
AF_DCMotor motor3(3, MOTOR34_1KHZ);
AF_DCMotor motor4(4, MOTOR34_1KHZ);
Servo myservo;
NewPing sonar(A0,A1,100);
  
int val;
int Speeed = 255;
const byte MOTOR1 = 19;  // Motor 1 Interrupt Pin - INT 3
unsigned int counter1 = 0; // to count encoder values
String readString;
MPU6050 mpu6050(Wire);
long timer = 0;

float angleX;
float tempX = 0; 


const int timerInterval = 1000;  // Timer interval in milliseconds (adjust as needed)
unsigned long previousMillis = 0;


void ISR_count1()  
{
  counter1++;  
} 

  
void setup()
{
  Serial.begin(9600);
  Serial2.begin(9600);  //Set the baud rate to your Bluetooth module.
  Serial3.begin(9600);  //Set ESP8266 wifi module
  Wire.begin();
  attachInterrupt(digitalPinToInterrupt (MOTOR1), ISR_count1, RISING);  // Increase counter 1 when speed sensor pin goes High
  mpu6050.begin();
  mpu6050.calcGyroOffsets(true);
  motor1.setSpeed(250);   //Set Motor Speed
  motor2.setSpeed(250);
  motor3.setSpeed(250);
  motor4.setSpeed(250);
  myservo.attach(10);
}


void loop() {

  if(millis() - timer > timerInterval){
    mpu6050.update();
    Serial.println("=======================================================");
    
    Serial.print("angleX : ");Serial.print(mpu6050.getAngleX());
    
    Serial.print("Distance : ");Serial.println(counter1);

    if (tempX - angleX > 5 || tempX - angleX< -5){
      Serial.print("angleX : ");Serial.print(mpu6050.getAngleX());
    
      Serial.print("Distance : ");Serial.println(counter1);
    }

    Serial.println("=======================================================");
    timer = millis();
    
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
    if (readString =="STOP"){     // STOP
      motor1.run (RELEASE);
      motor2.run (RELEASE);
      motor3.run (RELEASE);
      motor4.run (RELEASE);
    }

    readString="";
  }
}
