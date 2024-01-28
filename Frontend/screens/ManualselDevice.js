import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, StatusBar, Image } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';

import axios from 'axios';
export default function ManualselDevice() {
  const navigation = useNavigation();
  const route = useRoute();
  const userId = route.params?.userId;
  console.log('Cutting -select -device User ID:', userId);




  const [Devices, setDevices] = useState([]);

    useEffect(() => {
      fetchDevices();
    }, []);

    const fetchDevices = async () => {
      try {
        const response = await axios.get(`http://13.126.128.212:8080/users/devices?userId=${userId}`);
        console.log('API Response:', response.data);
        const deviceNames = Object(response.data);
        setDevices(deviceNames);
      } catch (error) {
        console.error('Error fetching locations:', error);
      }
    };

    console.log('Devices:', Devices);

  return (
    <View style={styles.container}>

       {Devices.map((device, index) => (
              <TouchableOpacity key={index} style={styles.locationBox} onPress={() => navigation.navigate('ManualselLocation', { userId: userId, deviceId: device })}>
                <Image source={require('../assets/deviceicon.png')} style={styles.locationIcon} />
                <Text style={styles.locationName}>Device name: {device}</Text>
              </TouchableOpacity>
            ))}




      <StatusBar style="auto" />
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
  locationBox: {
      flexDirection: 'row', // Align items horizontally
      alignItems: 'center', // Align items vertically
      backgroundColor: '#232326', // Background color of the box
      borderRadius: 8, // Border radius for rounded corners
      padding: 10, // Padding inside the box
      marginVertical: 5, // Vertical margin between boxes
      height: 100,
      width: 300,
    },
    locationIcon: {
      width: 50, // Adjust size of the icon as needed
      height: 70, // Adjust size of the icon as needed
      marginRight: 5, // Spacing between icon and text
    },
    locationName: {
      fontSize: 20, // Font size of the location name
      fontWeight: 'bold', // Font weight of the location name
      color: '#ffffff',
    },
  buttonContainer: {
    flexDirection: 'row',  // Arrange children horizontally
    marginBottom: 20,  // Add some spacing below the buttons
  },
  buttonlog: {
    fontSize: 18,
    paddingHorizontal: 50,
    paddingVertical: 10,
    backgroundColor: '#a0a89e',
    borderRadius: 50,
    elevation: 3,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 20,
  },
  buttonText: {
    color: '#000000',
  },
  deviceBox: {
    backgroundColor: '#fff',
    borderRadius: 10,
    padding: 10,
    marginVertical: 10,
    width: 300,
  },
  deviceText: {
    color: '#000',
    fontSize: 16,
  },
});
