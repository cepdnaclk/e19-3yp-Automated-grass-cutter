import React, { useState } from 'react';
import { StyleSheet, View, Text, SafeAreaView, TouchableOpacity, TextInput, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';

export default function Location() {
  const navigation = useNavigation();
  const [locName, setLocName] = useState('');
  const [isLocNameFilled, setIsLocNameFilled] = useState(false);
  const [showMessage, setShowMessage] = useState(false);

  const handleLocNameChange = (text) => {
    setLocName(text);
    setIsLocNameFilled(!!text);
    setShowMessage(false); // Hide message when input is changed
  };

  const handleBoundaryPress = () => {
    if (!isLocNameFilled) {
      setShowMessage(true);
      Alert.alert('Warning', 'Please specify the location name first.');
      return; 
    }
    navigation.navigate('Mannualdraft');
  };

  return (
    <SafeAreaView style={styles.container}>
      
      <Text style={styles.text}>Location Name</Text>
      {/* Name Input */}
      <TextInput
        style={styles.input}
        value={locName}
        onChangeText={handleLocNameChange}
      />
      
      {/* Add Boundary Button */}
      <TouchableOpacity 
        style={styles.addbutton} 
        onPress={handleBoundaryPress}
      >
          <Text style={styles.buttonText}>Add boundary</Text>
      </TouchableOpacity>
      
      {/* Add Pattern Button */}
      <TouchableOpacity 
        style={styles.addbutton} 
        onPress={() => isLocNameFilled && navigation.navigate('Mannualdraft')}
      >
          <Text style={styles.buttonText}>Add Pattern</Text>
      </TouchableOpacity>

      {showMessage && <Text style={styles.warningText}>Please specify the location name first.</Text>}
      
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
    marginVertical: 10,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: '#a0a89e',
    marginBottom: 15,
  },

  addbutton: {
    width: 200, // Set the width for both buttons to be the same
    fontSize: 18,
    paddingHorizontal: 40,
    paddingVertical: 10,
    backgroundColor: '#3CA832',
    borderRadius: 5,
    elevation: 3,
    marginBottom: 15,
    alignSelf: 'center',
  },

  buttonText: {
    color: '#000000',
    fontSize: 16,
  },

  warningText: {
    color: 'red',
    marginTop: 10,
    alignSelf: 'center',
    fontSize: 15,
  },
});
