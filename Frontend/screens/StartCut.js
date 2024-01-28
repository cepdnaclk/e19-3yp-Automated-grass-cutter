import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, StatusBar, Image } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';

import axios from 'axios';
export default function StartCut() {
  const navigation = useNavigation();
  const route = useRoute();
  const userId = route.params?.userId;
  const deviceId = route.params?.deviceId;
  const Location = route.params?.Location;

  console.log('StartCut-location User ID:', userId);
  console.log('StartCut-location device ID:', deviceId);
  console.log('StartCut-location Location:', Location);






    const handleCutting = async () => {
      try {
        const response = await axios.get(`http://13.126.128.212:8080/users/data?userId=${userId}&locationName=${Location}&deviceId=${deviceId}`);
        console.log('API Response:', response.data);


      } catch (error) {
        console.error('Error fetching locations:', error);
      }
    };



  return (
    <View style={styles.container}>


              <TouchableOpacity  style={styles.locationBox} onPress={handleCutting}>
                <Image source={require('../assets/blade.png')} style={styles.locationIcon} />
                <Text style={styles.locationName}>Start Cutting</Text>
              </TouchableOpacity>

              <TouchableOpacity  style={styles.homeBox} onPress={() => navigation.navigate('Home',{ userId: userId })}>
                              <Image source={require('../assets/homeicon.png')} style={styles.homeIcon} />
                              <Text style={styles.homeName}>Home</Text>
                            </TouchableOpacity>





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
      backgroundColor: '#3CA832', // Background color of the box
      borderRadius: 8, // Border radius for rounded corners
      padding: 10, // Padding inside the box
      marginVertical: 5, // Vertical margin between boxes
      height: 180,
      width: 250,
    },
    homeBox: {
          flexDirection: 'row', // Align items horizontally
          alignItems: 'center', // Align items vertically
          backgroundColor: '#232326', // Background color of the box
          borderRadius: 18, // Border radius for rounded corners
          padding: 10, // Padding inside the box
          marginVertical: 5, // Vertical margin between boxes
          height: 80,
          width: 250,
        },
         homeName: {
              fontSize: 20, // Font size of the location name
              fontWeight: 'bold', // Font weight of the location name
              color: '#ffffff',

            },
    locationIcon: {
      width: 70, // Adjust size of the icon as needed
      height: 70, // Adjust size of the icon as needed
      marginRight: 5, // Spacing between icon and text
    },
    homeIcon: {
          width: 120, // Adjust size of the icon as needed
          height: 120, // Adjust size of the icon as needed
          marginRight: -15, // Spacing between icon and text
        },
    locationName: {
      fontSize: 20, // Font size of the location name
      fontWeight: 'bold', // Font weight of the location name
      color: '#000000',
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
