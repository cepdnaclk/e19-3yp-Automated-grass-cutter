import React, { useState, useEffect } from 'react';
import { StyleSheet, View, TouchableOpacity, Text } from 'react-native';
import BleManager from 'react-native-ble-manager';

export default function Man_or_Auto() {
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    // Initialize BleManager
    BleManager.start({ showAlert: false });

    // Add listeners for Bluetooth connection events
    const handlerDiscover = BleManager.onStateChange((state) => {
      if (state === 'PoweredOn') {
        BleManager.scan([], 3, true).then(() => {
          // Connect to the Arduino device (replace with your device's UUID)
          BleManager.connect('DEVICE_UUID').then(() => {
            setIsConnected(true);
          });
        });
      }
    });

    return () => {
      // Remove listeners
      handlerDiscover.remove();
    };
  }, []);

  const sendCommand = (command) => {
    // Send command to the Arduino device
    BleManager.write('DEVICE_UUID', 'SERVICE_UUID', 'CHARACTERISTIC_UUID', command).then(() => {
      console.log('Command sent:', command);
    }).catch((error) => {
      console.log('Error sending command:', error);
    });
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.button} onPress={() => sendCommand('FORWARD')}>
        <Text>Forward</Text>
      </TouchableOpacity>
      {/* Add other buttons and their respective commands */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  button: {
    padding: 20,
    backgroundColor: '#3CA832',
    borderRadius: 5,
    margin: 10,
  },
});
