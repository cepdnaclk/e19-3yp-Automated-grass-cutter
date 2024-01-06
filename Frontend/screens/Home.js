import React from 'react';
import { StyleSheet, View, Image, Text, SafeAreaView, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StatusBar } from 'expo-status-bar';

export default function Home() {
  const navigation = useNavigation();

  const goToLogin = () => {
    navigation.navigate('Login');
  };

  return (
    <SafeAreaView style={styles.container}>
      {/* Logo Image */}
      <Image
        source={require('../assets/logo.png')}
        style={styles.logosmall}
      />
      <Text style={styles.text}>LAWNMATE</Text>
      
      {/* Box */}
      <View style={styles.box}>
        {/* Text inside the box */}
        <Text style={styles.boxText}>WELCOME TO LAWNMATE</Text>
        
        {/* Button inside the box */}
        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Man_or_Auto')}>
          <Text style={styles.buttonText}>Start cutting</Text>
        </TouchableOpacity>
      </View>

      {/* Footer Box */}
      <View style={styles.footerbox}>
        {/* Image Buttons */}
        <View style={styles.imageButtonContainer}>
          <TouchableOpacity style={styles.imageButton} onPress={() => navigation.navigate('Man_or_Auto')}>
            <Image source={require('../assets/homeicon.png')} style={styles.imageButtonImage} />
          </TouchableOpacity>
          <TouchableOpacity style={styles.imageButton} onPress={() => navigation.navigate('Adddevice')}>
            <Image source={require('../assets/deviceicon.png')} style={styles.imageButtonImage} />
          </TouchableOpacity>
          <TouchableOpacity style={styles.imageButton} onPress={() => navigation.navigate('Addlocation')}>
            <Image source={require('../assets/locationicon.png')} style={styles.imageButtonImage} />
          </TouchableOpacity>
          <TouchableOpacity style={styles.imageButton} onPress={() => navigation.navigate('Man_or_Auto')}>
            <Image source={require('../assets/usersicon.png')} style={styles.imageButtonImage} />
          </TouchableOpacity>
        </View>
      </View>

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
  logosmall: {
    width: 250,
    height: 250,
    marginBottom: -20,
    marginTop: -100,
  },
  text: {
    fontSize: 30,
    marginBottom: 20,
  },
  box: {
    width: 300,
    height: 200,
    backgroundColor: '#B6B5B3',
    borderRadius: 10,
    marginTop: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  footerbox: {
    position: 'absolute',
    bottom: 0,
    width: '100%',
    height: 100,
    backgroundColor: '#232326',
    justifyContent: 'center',
    alignItems: 'center',
  },
  boxText: {
    fontSize: 28,
    marginBottom: 10,
    textAlign: 'center',
  },
  button: {
    fontSize: 18,
    paddingHorizontal: 30,
    paddingVertical: 10,
    backgroundColor: '#3CA832',
    borderRadius: 5,
    elevation: 3,
  },
  buttonText: {
    color: '#000000',
  },
  imageButtonContainer: {
    flexDirection: 'row',
  },
  imageButton: {
    marginHorizontal: 20,
    
  },
  imageButtonImage: {
    width: 70,
    height: 70,
    resizeMode: 'contain',
  },
});
