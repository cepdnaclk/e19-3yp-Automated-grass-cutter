import React from 'react';
import { StyleSheet, View, Text, SafeAreaView, TouchableOpacity, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StatusBar } from 'expo-status-bar';

export default function Addlocation() {
  const navigation = useNavigation();

  const navigateToDirection = (direction) => {
    console.log(`Navigating to ${direction}`);
  };

  return (
    <SafeAreaView style={styles.container}>
      
      {/* Arrow Buttons in 3x3 Table Layout */}
      <View style={styles.arrowTable}>
        <View style={styles.row}>
          <TouchableOpacity style={styles.arrowButton} >
            <Image source={require('../assets/S.png')} style={styles.icon} />
          </TouchableOpacity>

          <TouchableOpacity style={styles.arrowButton}>
            <Image source={require('../assets/Forward.png')} style={styles.icon} />
          </TouchableOpacity>

          <TouchableOpacity style={styles.arrowButton} >
            <Image source={require('../assets/N.png')} style={styles.icon} />
          </TouchableOpacity> 

        </View>
        <View style={styles.row}>
          <TouchableOpacity style={styles.arrowButton} >
            <Image source={require('../assets/Left.png')} style={styles.icon} />
          </TouchableOpacity>
          <View style={styles.centerButton}></View> {/* Empty center cell */}
          <TouchableOpacity style={styles.arrowButton} >
            
          </TouchableOpacity>
          <TouchableOpacity style={styles.arrowButton} >
            <Image source={require('../assets/Right.png')} style={styles.icon} />
          </TouchableOpacity>
        </View>
        <View style={styles.row}>
          <TouchableOpacity style={styles.arrowButton} >
            <Image source={require('../assets/W.png')} style={styles.icon} />
          </TouchableOpacity>
          
          <TouchableOpacity style={styles.arrowButton} >
            <Image source={require('../assets/Backward.png')} style={styles.icon} />
          </TouchableOpacity>

          <TouchableOpacity style={styles.arrowButton} >
            <Image source={require('../assets/E.png')} style={styles.icon} />
          </TouchableOpacity>
        </View>
      </View>
      
      {/* Finish Button */}
      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Home')}>
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
  centerButton: {
    width: 50,
    height: 50,
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
  },
  buttonText: {
    color: '#000000',
    textAlign: 'center',
  },
});
