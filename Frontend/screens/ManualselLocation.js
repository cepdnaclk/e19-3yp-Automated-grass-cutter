import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, StatusBar, Image } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import axios from 'axios';

export default function ManualselLocation() {
  const navigation = useNavigation();
  const route = useRoute();
  const userId = route.params?.userId;
  const deviceId = route.params?.deviceId;
  console.log('Cutting-location User ID:', userId);
  console.log('Cutting-location device ID:', deviceId);

  const [locations, setLocations] = useState([]);

  useEffect(() => {
    fetchLocations();
  }, []);

  const fetchLocations = async () => {
    try {
      const response = await axios.get(`http://13.126.128.212:8080/users/locations?userId=${userId}`);
      console.log('API Response:', response.data);
      // Assuming the response structure is { "Loca2": [ { "name": "Location1" }, { "name": "Location2" } ] }
      const locationNames = Object.keys(response.data);
      setLocations(locationNames);
    } catch (error) {
      console.error('Error fetching locations:', error);
    }
  };

  console.log('Locations:', locations);

  return (
    <View style={styles.container}>
      {locations.map((location, index) => (
        <TouchableOpacity key={index} style={styles.locationBox} onPress={() => navigation.navigate('StartcutMannual', { userId: userId , Location: location , deviceId: deviceId })}>
          <Image source={require('../assets/locationicon.png')} style={styles.locationIcon} />
          <Text style={styles.locationName}>Location name: {location}</Text>
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
    width: 30, // Adjust size of the icon as needed
    height: 100, // Adjust size of the icon as needed
    marginRight: 20, // Spacing between icon and text
  },
  locationName: {
    fontSize: 20, // Font size of the location name
    fontWeight: 'bold', // Font weight of the location name
    color: '#ffffff',
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
});
