import React, { useState } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, TextInput, Alert } from 'react-native';
import { useNavigation ,useRoute} from '@react-navigation/native';

export default function Location() {
  const navigation = useNavigation();
  const [locName, setLocName] = useState('');
  const [isLocNameFilled, setIsLocNameFilled] = useState(false);
  const [showMessage, setShowMessage] = useState(false);
  const route = useRoute();
  const userId = route.params?.userId;
  const deviceId = route.params?.deviceId;
  console.log('Location name User ID:', userId);
  console.log('Location name device ID:', deviceId);


  const handleLocNameChange = (text) => {
    setLocName(text);
    setIsLocNameFilled(!!text);
    setShowMessage(false); 
  };

  const handleBoundaryPress = () => {
    if (!isLocNameFilled) {
      setShowMessage(true);
      Alert.alert('Warning', 'Please specify the location name first.');
      return; 
    }
    navigation.navigate('Mannual',{ userId: userId , Location: locName , deviceId: deviceId });
  };

  return (
    <View style={styles.container}>
      
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
     

      {showMessage && <Text style={styles.warningText}>Please specify the location name first.</Text>}
      
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
    marginVertical: 10,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: '#a0a89e',
    marginBottom: 15,
    color: '#000000',
  },

  addbutton: {
    width: 200, 
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
