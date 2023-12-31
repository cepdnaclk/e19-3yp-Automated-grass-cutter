import React from 'react';
import { StyleSheet, View, Image, Text, SafeAreaView, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StatusBar } from 'expo-status-bar';

export default function Welcome() {
  const navigation = useNavigation();

  const goToLogin = () => {
    navigation.navigate('Login');
  };

  return (
    <SafeAreaView style={styles.container}>
      {/* Logo Image */}
      <Image
        source={require('../assets/logo.png')}
        style={styles.logo}
      />
      <Text style={styles.text}>LAWNMATE</Text>
      <TouchableOpacity style={styles.button} onPress={goToLogin}>
        <Text style={styles.buttonText}>Go to Login</Text>
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
  logo: {
    width: 300,
    height: 300,
    marginBottom: -40,
  },
  text: {
    fontSize: 50,
  },
  button: {
    fontSize: 18,  
    paddingHorizontal: 30,  
    paddingVertical: 10,   
    backgroundColor: '#a0a89e',  
    borderRadius: 50,   
    elevation: 3,
    alignItems: 'center',  // Align the text horizontally
    justifyContent: 'center',  // Align the text vertically
  },
  buttonText: {
    color: '#000000',
  },
});
