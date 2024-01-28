import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, SafeAreaView, TouchableOpacity, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';



export default function Man_or_Auto() {
  const navigation = useNavigation();
  const [bleManager, setBleManager] = useState(() => {
    const manager = new BleManager();
    console.log(manager);
    return manager;
  });
  
  const deviceName = 'YourDeviceName'; // Replace with your Arduino's Bluetooth device name

  useEffect(() => {
    bleManager.startDeviceScan(null, null, (error, device) => {
      if (error) {
        console.log(error);
        return;
      }

      if (device.name === deviceName) {
        bleManager.connectToDevice(device.id)
          .then((device) => {
            console.log('Connected to device:', device.name);
          })
          .catch((error) => {
            console.log('Connection error:', error);
          });
      }
    });

    return () => {
      bleManager.stopDeviceScan();
    };
  }, []);

  const sendCommand = (command) => {
    const data = command;

    bleManager.writeCharacteristicWithoutResponseForDevice(
      device.id,
      'YourServiceUUID', // Replace with the UUID of the service on your Arduino
      'YourCharacteristicUUID', // Replace with the UUID of the characteristic on your Arduino
      data
    )
    .catch((error) => {
      console.log('Error sending command:', error);
      Alert.alert('Error', 'Failed to send command.');
    });
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.buttonMan} onPress={sendCommand}>
          <Text style={styles.buttonText}>Forward</Text>
        </TouchableOpacity>
        {/* Add other buttons and their respective commands here */}
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#DAFADA',
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonContainer: {
    flexDirection: 'row',
    marginBottom: 20,
  },
  buttonMan: {
    fontSize: 18,
    paddingHorizontal: 70,
    paddingVertical: 40,
    backgroundColor: '#3CA832',
    borderRadius: 5,
    elevation: 3,
    marginHorizontal: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: {
    color: '#000000',
  },
});
