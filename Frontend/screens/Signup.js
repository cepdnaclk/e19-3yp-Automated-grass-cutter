import React, { useState } from 'react';
import { StyleSheet, View, Text, SafeAreaView, TouchableOpacity, TextInput } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';  // Import Ionicons from expo

export default function Signup() {
  const navigation = useNavigation();
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isPasswordVisible, setPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordVisible(prevState => !prevState);
  };

  return (
    <SafeAreaView style={styles.container}>
      
      <Text style={styles.text}>Name</Text>
      {/* Name Input */}
      <TextInput
        style={styles.input}
        value={name}
        onChangeText={setName}
      />
      <Text style={styles.text}>Email</Text>
      {/* Email Input */}
      <TextInput
        style={styles.input}
        keyboardType="email-address"
        value={email}
        onChangeText={setEmail}
      />

      {/* Password Input */}
      <Text style={styles.text}>Password</Text>
      <View style={styles.passwordContainer}>

        <TextInput
          style={styles.passwordInput}
          secureTextEntry={!isPasswordVisible}
          value={password}
          onChangeText={setPassword}
        />
        <TouchableOpacity onPress={togglePasswordVisibility} style={styles.eyeIconContainer}>
          <Ionicons name={isPasswordVisible ? 'eye-off-outline' : 'eye-outline'} size={24} color="black" />
        </TouchableOpacity>
      </View>
      <Text style={styles.text}>Confirm Password</Text>
      <View style={styles.passwordContainer}>

        <TextInput
          style={styles.passwordInput}
          secureTextEntry={!isPasswordVisible}
          value={password}
          onChangeText={setPassword}
        />
        <TouchableOpacity onPress={togglePasswordVisibility} style={styles.eyeIconContainer}>
          <Ionicons name={isPasswordVisible ? 'eye-off-outline' : 'eye-outline'} size={24} color="black" />
        </TouchableOpacity>
      </View>
      
      {/* Signup Button */}
      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Signup')}>
        <Text style={styles.buttonText}>Submit</Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center', // Aligns children to the top
        alignItems: 'flex-start',     // Aligns children to the start (left in LTR languages)
        backgroundColor: '#DAFADA',
        padding: 20, // Add some padding for better visual separation from the edges
      },
  
  text: {
    fontSize: 15,
    marginBottom: 20,
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
  },
  passwordContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    width: 300,
    height: 40,
    marginVertical: 10,
    paddingHorizontal: 10,
    borderWidth: 1,
    borderRadius: 5,
    borderColor: '#a0a89e',
  },
  passwordInput: {
    flex: 1,
    height: 40,
    paddingRight: 40,  // Space for the eye icon
  },
  eyeIconContainer: {
    position: 'absolute',
    right: 10,
    padding: 10,
  },
  button: {
    fontSize: 18,
    paddingHorizontal: 30,
    paddingVertical: 10,
    backgroundColor: '#a0a89e',
    borderRadius: 50,
    elevation: 3,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 20,
    alignSelf: 'center',  // Aligns the button itself to the center of its parent
  },
  buttonText: {
    color: '#000000',
    fontSize: 16,
  },
});
