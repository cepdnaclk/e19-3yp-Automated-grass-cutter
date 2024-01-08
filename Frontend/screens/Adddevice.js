import React from 'react';
import { StyleSheet, View, Image, Text, SafeAreaView, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StatusBar } from 'expo-status-bar';

export default function Adddevice() {
  const navigation = useNavigation();

  const goToLogin = () => {
    navigation.navigate('Login');
  };

  return (
    <SafeAreaView style={styles.container}>
      <TouchableOpacity style={styles.buttonlog} onPress={() => navigation.navigate('DeviceForm')}>
        <Text style={styles.buttonText}>Add Device</Text>
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
});

