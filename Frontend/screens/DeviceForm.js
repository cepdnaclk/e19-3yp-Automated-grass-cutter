import React, { useState } from 'react';
import { StyleSheet, View, Text, SafeAreaView, TouchableOpacity, TextInput, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';

export default function DeviceForm() {
  const navigation = useNavigation();
  const [deviceId, setDeviceId] = useState('');
  const [deviceName, setDeviceName] = useState('');

  const handleDevice = () => {
    // Handle form submission logic here
    // For example: Validate inputs, send data to server, etc.
    // For now, let's just log the values:
    console.log("Device ID:", deviceId);
    console.log("Device Name:", deviceName);
  };

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.text}>Device ID</Text>
      <TextInput
        style={styles.input}
        value={deviceId}
        onChangeText={setDeviceId}
        
      />

      <Text style={styles.text}>Device Name</Text>
      <TextInput
        style={styles.input}
        value={deviceName}
        onChangeText={setDeviceName}
        
      />

    <Text style={styles.text}>Key</Text>
      <TextInput
        style={styles.input}
        value={deviceName}
        
        
      />

      <TouchableOpacity style={styles.button} onPress={handleDevice}>
        <Text style={styles.buttonText}>Submit</Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'flex-start',
    backgroundColor: '#DAFADA',
    padding: 20,
  },
  text: {
    fontSize: 15,
    marginBottom: 10,
    textAlign: 'left',
  },
  input: {
    width: 300,
    height: 40,
    marginBottom: 20,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: '#a0a89e',
  },
  button: {
    fontSize: 18,
    paddingHorizontal: 30,
    paddingVertical: 10,
    backgroundColor: '#a0a89e',
    borderRadius: 50,
    elevation: 3,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 20,
    alignSelf: 'center',
  },
  buttonText: {
    color: '#000000',
    fontSize: 16,
  },
});
