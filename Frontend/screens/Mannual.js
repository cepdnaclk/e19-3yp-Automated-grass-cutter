import React, { useState } from 'react';
import { StyleSheet, View, Text, SafeAreaView, TouchableOpacity, PanResponder, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StatusBar } from 'expo-status-bar';


export default function Addlocation() {
  const navigation = useNavigation();

  const [angle, setAngle] = useState(0);
  const [isDragging, setIsDragging] = useState(false);

  // Allowed angles
  const allowedAngles = [45, 90, 135, 180, 225, 270, 315, 360];

  const snapToNearestAngle = (currentAngle) => {
    let nearestAngle = allowedAngles[0];
    for (let i = 1; i < allowedAngles.length; i++) {
      if (Math.abs(allowedAngles[i] - currentAngle) < Math.abs(nearestAngle - currentAngle)) {
        nearestAngle = allowedAngles[i];
      }
    }
    return nearestAngle;
  };

  const panResponder = PanResponder.create({
    onStartShouldSetPanResponder: () => true,
    onPanResponderMove: (evt, gestureState) => {
      setIsDragging(true);
      const newAngle = snapToNearestAngle(angle + gestureState.dx / 2);
      setAngle(newAngle);
    },
    onPanResponderRelease: () => {
      setIsDragging(false);
    },
  });

  return (
    <SafeAreaView style={styles.container}>
      
      {/* Gaming Wheel */}
      <View style={styles.wheelContainer}>
        <View 
          style={[styles.wheel, { transform: [{ rotate: `${angle}deg` }] }]} 
          {...panResponder.panHandlers}
        >
          <Image source={require('../assets/wheel.png')} style={styles.wheelImage} />
          <TouchableOpacity style={styles.forwardbutton} >
            {/* Forward button content, e.g., an icon or text */}
          </TouchableOpacity>
        </View>
      </View>
      
      {/* Finish Button */}
      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Location')}>
        <Text style={styles.buttonText}>Finish</Text>
      </TouchableOpacity>

      <StatusBar style="auto" />
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
  wheelContainer: {
    position: 'relative', // Make the container relative for positioning the forward button
    width: 300,
    height: 300,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 20,
  },
  wheel: {
    width: 300,
    height: 300,
    borderRadius: 150,
    backgroundColor: '#3CA832',
    justifyContent: 'center',
    alignItems: 'center',
    position: 'relative', // Make the wheel relative for positioning the forward button
  },
  wheelImage: {
    width: 240,  
    height: 240, 
    alignItems: 'center',
  },
  forwardbutton: {
    position: 'absolute',
    width: 40,
    height: 40,
    backgroundColor: '#000000',
    borderRadius: 20,
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 1, // Ensure the forward button appears on top of the wheel
  },
  button: {
    width: 200,
    height: 40,
    backgroundColor: '#3CA832',
    borderRadius: 5,
    justifyContent: 'center',
    alignItems: 'center',
  },
  buttonText: {
    color: '#000000',
    textAlign: 'center',
  },
});
