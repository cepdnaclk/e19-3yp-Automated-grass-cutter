import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, Image, TouchableWithoutFeedback } from 'react-native';
import BluetoothSerial from 'react-native-bluetooth-serial';
import {PERMISSIONS} from 'react-native-permissions';
import { PermissionsAndroid } from 'react-native';
import {useRoute, useNavigation} from '@react-navigation/native';
import axios from 'axios';

export default function StartcutMannual() {
  const [isBluetoothEnabled, setIsBluetoothEnabled] = useState(false);
  const [isConnected, setIsConnected] = useState(false);
  const navigation = useNavigation();
  const route = useRoute();
  const userId = route.params?.userId;
  const location = route.params?.Location;
   const deviceId = route.params?.deviceId;
  console.log('Manual User ID:', userId);
  console.log('Manual Location:',location);
  console.log('Manual deviceId:',deviceId);

  useEffect(() => {
    const checkBluetoothEnabled = async () => {
      try {
        const enabled = await BluetoothSerial.isEnabled();
        setIsBluetoothEnabled(enabled);
      } catch (error) {
        console.error(`Error checking Bluetooth status: ${error.message}`);
      }
    };

    const setupBluetoothListeners = () => {
      BluetoothSerial.on('bluetoothEnabled', () => setIsBluetoothEnabled(true));
      BluetoothSerial.on('bluetoothDisabled', () => setIsBluetoothEnabled(false));
      BluetoothSerial.on('connected', () => {
            console.log('Bluetooth device connected successfully');
            // You can perform actions upon successful connection here
            setIsConnected(true);
          });
    };

   async function requestBluetoothPermission() {
     try {
       const granted = await PermissionsAndroid.request(
         PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
         {
           title: 'Bluetooth Permission',
           message: 'App needs access to Bluetooth.',
           buttonPositive: 'OK',
           buttonNegative: 'Cancel',
         }
       );

       if (granted === PermissionsAndroid.RESULTS.GRANTED) {
         console.log('Bluetooth permission granted');
       } else {
         console.warn('Bluetooth permission denied');
       }
     } catch (err) {
       console.error('Error requesting Bluetooth permission:', err);
     }
   }
    const initializeBluetooth = async () => {
      await requestBluetoothPermission();
      await checkBluetoothEnabled();
      setupBluetoothListeners();

    };

    initializeBluetooth();

    return () => {

    };
  }, []);

  const connectToDevice = async (deviceAddress) => {
    try {
      // Set a timeout for connection attempt (adjust as needed)
      const timeoutId = setTimeout(() => {
        throw new Error('Connection timeout');
      }, 10000); // 10 seconds

      // Perform Bluetooth connection in the background
      await BluetoothSerial.connect(deviceAddress);

      // Clear the timeout since the connection is successful
      clearTimeout(timeoutId);

      console.log('Connected to HC-05');
      setIsConnected(true);
    } catch (error) {
      console.error(`Error connecting to HC-05: ${error.message}`);
      alert(`Error connecting to HC-05: ${error.message}`);
    }
  };

  const handleConnectPress = async () => {
    try {

      // Check if Bluetooth is enabled before attempting to connect
      if (isBluetoothEnabled) {
        console.log('Scanning for Bluetooth devices...');

        // Discover available Bluetooth devices
        const devices = await BluetoothSerial.list();

        // Filter the list to find your HC-05 device based on name or other criteria
        const hc05Device = devices.find(device => device.name === 'HC-05');

        if (hc05Device) {
          console.log(`Found HC-05 with address: ${hc05Device.address}`);

          // Connect to the HC-05 device
          await connectToDevice(hc05Device.address);

          // Log success message
          console.log('Connected to HC-05');

          // Update connection state
          setIsConnected(true);
        } else {
          console.warn('HC-05 device not found');
        }
      } else {
        console.warn('Bluetooth is not enabled');
      }
    } catch (error) {
      console.error(`Error connecting to HC-05: ${error.message}`);
      // Add any additional error handling code here
      alert(`Error connecting to HC-05: ${error.message}`);
    }
  };




  const sendCommand = (direction) => {
      console.log(`Sending command: ${direction}`);
      if (isBluetoothEnabled) {
        BluetoothSerial.write(direction)
          .then((res) => {
            console.log(`Data sent successfully: ${res}`);
          })
          .catch((err) => {
            console.error(`Error sending data: ${err}`);
          });
      } else {
        console.warn('Bluetooth is not enabled');
      }
    };

    const handleLongPress = (direction) => {
      console.log(`Long press start for ${direction}`);
      sendCommand(direction);
    };

    const handleLongPressRelease = () => {
      console.log('Long press release');
      sendCommand('STOP'); // Stop the continuous command
    };

     const handleStopadding = async () => {
              try {

                const response = await axios.get(`http://13.126.128.212:8080/users/stopAdding?userId=${userId}&deviceId=${deviceId}&locationName=${location}`);
                navigation.navigate('Home',{ userId: userId });
              } catch (error) {
                console.error('Error fetching locations:', error);
              }
            };

     const handleFinishPress = () => {
            // Add the following line to execute handleStopadding when "Finish" is pressed
            handleStopadding();

            // Additionally, you can add any other logic you want to perform when the "Finish" button is pressed.
            console.log('Finish button pressed');
          };

  return (
    <View style={styles.container}>
      {/* Arrow Buttons in 3x3 Table Layout */}
      {/* Connect Button */}
            <TouchableOpacity style={styles.button} onPress={handleConnectPress}>
              <Text style={styles.buttonText}>Connect Bluetooth</Text>
            </TouchableOpacity>

      <View style={styles.arrowTable}>
        <View style={styles.row}>
          <TouchableWithoutFeedback
                      onPressIn={() => handleLongPress('FORWARDLEFT')}
                      onPressOut={handleLongPressRelease}
                    >
                      <View style={styles.arrowButton}>
                        <Image source={require('../assets/S.png')} style={styles.icon} />
                      </View>
                    </TouchableWithoutFeedback>
          <TouchableWithoutFeedback onPressIn={() => handleLongPress('BACKWARD')} onPressOut={handleLongPressRelease}>
            <View style={styles.arrowButton}>
                <Image source={require('../assets/Forward.png')} style={styles.icon} />
            </View>
          </TouchableWithoutFeedback>

          <TouchableWithoutFeedback  onPressIn={() => handleLongPress('FORWARDRIGHT')} onPressOut={handleLongPressRelease}>
             <View style={styles.arrowButton}>
                <Image source={require('../assets/N.png')} style={styles.icon} />
             </View>
          </TouchableWithoutFeedback>

        </View>
      <View style={styles.row}>
         <TouchableWithoutFeedback  onPressIn={() => handleLongPress('LEFT')} onPressOut={handleLongPressRelease}>
             <View style={styles.arrowButton}>
                <Image source={require('../assets/Left.png')} style={styles.icon} />
             </View>
         </TouchableWithoutFeedback>
         <View style={styles.centerButton}></View>
         <TouchableOpacity style={styles.arrowButton}>

         </TouchableOpacity>
         <TouchableWithoutFeedback onPressIn={() => handleLongPress('RIGHT')} onPressOut={handleLongPressRelease}>
            <View style={styles.arrowButton}>
                <Image source={require('../assets/Right.png')} style={styles.icon} />
            </View>
         </TouchableWithoutFeedback>
      </View>
      <View style={styles.row}>
          <TouchableWithoutFeedback  onPressIn={() => handleLongPress('BACKWARDLEFT')} onPressOut={handleLongPressRelease}>
             <View style={styles.arrowButton}>
                <Image source={require('../assets/W.png')} style={styles.icon} />
             </View>
          </TouchableWithoutFeedback>

          <TouchableWithoutFeedback onPressIn={() => handleLongPress('FORWARD')} onPressOut={handleLongPressRelease}>
             <View style={styles.arrowButton}>
                <Image source={require('../assets/Backward.png')} style={styles.icon} />
             </View>
          </TouchableWithoutFeedback>

          <TouchableWithoutFeedback onPressIn={() => handleLongPress('BACKWARDRIGHT')} onPressOut={handleLongPressRelease}>
             <View style={styles.arrowButton}>
                <Image source={require('../assets/E.png')} style={styles.icon} />
             </View>
          </TouchableWithoutFeedback>
      </View>
      </View>


      <TouchableOpacity style={styles.button} onPress={handleFinishPress}>
          <Text style={styles.buttonText}>Finish</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#DAFADA',
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
        fontSize: 18,
        paddingHorizontal: 30,
        paddingVertical: 10,
        backgroundColor: '#000000',
        borderRadius: 50,
        elevation: 3,
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 20,

      },
      buttonText: {
          color: '#000000',
          fontSize: 20,
        },
  arrowTable: {
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20,
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 20,
  },
  arrowButton: {
    width: 50,
    height: 50,
    justifyContent: 'center',
    alignItems: 'center',
    margin: 20,
  },
  icon: {
    width: 100,
    height: 100,
    resizeMode: 'contain',
  },
  button: {
    width: 200,
    height: 40,
    backgroundColor: '#3CA832',
    borderRadius: 5,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20,
    marginBottom: 20,
  },
  buttonText: {
    color: '#000000',
    textAlign: 'center',
    fontSize: 16,
  },
});
