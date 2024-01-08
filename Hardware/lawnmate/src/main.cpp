#include <AFMotor.h>
#include <MPU6050_tockn.h>
#include <Wire.h>

  
//initial motors pin
AF_DCMotor motor1(1, MOTOR12_1KHZ);
AF_DCMotor motor2(2, MOTOR12_1KHZ);
AF_DCMotor motor3(3, MOTOR34_1KHZ);
AF_DCMotor motor4(4, MOTOR34_1KHZ);
  
int val;
int Speeed = 255;
const byte MOTOR1 = 19;  // Motor 1 Interrupt Pin - INT 3
unsigned int counter1 = 0; // to count encoder values
MPU6050 mpu6050(Wire);

void ISR_count1()  
{
  counter1++;  
} 

  
void setup()
{
  Serial1.begin(9600);  //Set the baud rate to your Bluetooth module.
  Serial2.begin(9600);
  attachInterrupt(digitalPinToInterrupt (MOTOR1), ISR_count1, RISING);  // Increase counter 1 when speed sensor pin goes High
  mpu6050.begin();
  mpu6050.calcGyroOffsets(true);
}


void loop() {
  while(Serial1.available()){
    delay(50);
    char c=Serial1.read();
    readString+=c;
  }
  if(readString.length()>0){
    Serial.println(readString);
    if (readString =="FORWARD"){      // MOVE FORWARD
      motor1.run (FORWARD);
      motor2.run (FORWARD);
      motor3.run (FORWARD);
      motor4.run (FORWARD);
    }
    if (readString =="BACKWARD"){     // MOVE BACKWARD
      motor1.run (BACKWARD);
      motor2.run (BACKWARD);
      motor3.run (BACKWARD);
      motor4.run (BACKWARD);
    }
    if (readString =="LEFT"){     // MOVE LEFT SIDE
      motor1.run (BACKWARD);
      motor2.run (FORWARD);
      motor3.run (FORWARD);
      motor4.run (BACKWARD);
    }
    if (readString =="RIGHT"){     // MOVE RIGHT SIDE
      motor1.run (FORWARD);
      motor2.run (BACKWARD);
      motor3.run (BACKWARD);
      motor4.run (FORWARD);
    }
    if (readString =="FORWARDLEFT"){     // MOVE FORWARD LEFT
      motor1.run (RELEASE);
      motor2.run (FORWARD);
      motor3.run (FORWARD);
      motor4.run (RELEASE);
    }
    if (readString =="FORWARDRIGHT"){     // MOVE FORWARD RIGHT
      motor1.run (FORWARD);
      motor2.run (RELEASE);
      motor3.run (RELEASE);
      motor4.run (FORWARD);
    }
    if (readString =="BACKWARDLEFT"){     // MOVE BACKWARD LEFT
      motor1.run (BACKWARD);
      motor2.run (RELEASE);
      motor3.run (RELEASE);
      motor4.run (BACKWARD);
    }
    if (readString =="BACKWARDRIGHT"){     // MOVE BACKWARD RIGHT
      motor1.run (RELEASE);
      motor2.run (BACKWARD);
      motor3.run (BACKWARD);
      motor4.run (RELEASE);
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
