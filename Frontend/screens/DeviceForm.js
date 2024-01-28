import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, TextInput, Alert } from 'react-native';
import { useNavigation, useRoute  } from '@react-navigation/native';
import axios from 'axios';

export default function DeviceForm() {
  const navigation = useNavigation();
  const [deviceId, setDeviceId] = useState('');
  const [deviceName, setDeviceName] = useState('');
  const [password, setPassword] = useState('');

  const route = useRoute();
  const userId = route.params?.userId;
  console.log('Device form User ID:', userId);

  const handleDevice = () => {
    // Handle form submission logic here
    // For example: Validate inputs, send data to server, etc.
    // For now, let's just log the values:
    console.log("Device ID:", deviceId);
    console.log("Device Name:", deviceName);
  };

  const handleaddDevice = async () => {
                try {
                  console.log("Device ID:", deviceId);
                  console.log("Password:", password);
                  const response = await axios.post(`http://13.126.128.212:8080/device/authenticate?userId=${userId}&deviceId=${deviceId}&password=${password}`);

                  console.log("Api reponse for add device", response.data);
                  if (response.data === "Device is already added to your application") {
                          Alert.alert('Device Already Added', 'The device is already added to your application.');
                        }else{
                            navigation.navigate('Home',{ userId: userId });
                        }


                } catch (error) {
                  console.error('Error fetching locations:', error);
                }
              };

  const handleaddDevicePress = () => {
              // Add the following line to execute handleStopadding when "Finish" is pressed
              handleaddDevice();

              // Additionally, you can add any other logic you want to perform when the "Finish" button is pressed.
              console.log('Submit button pressed');
  };

  return (
    <View style={styles.container}>
      <Text style={styles.text}>Device ID</Text>
      <TextInput
        style={styles.input}
        value={deviceId}
        onChangeText={setDeviceId}
        
      />



    <Text style={styles.text}>Password</Text>
      <TextInput
        style={styles.input}
        value={password}
        onChangeText={setPassword}
        
      />

      <TouchableOpacity style={styles.button} onPress={handleaddDevicePress}>
        <Text style={styles.buttonText}>Submit</Text>
      </TouchableOpacity>
    </View>
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
    color: '#000000',
  },
  input: {
    width: 300,
    height: 40,
    marginBottom: 20,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: '#a0a89e',
    color: '#000000',
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
