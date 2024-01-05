import React, { useState } from 'react';
import { StyleSheet, View, Image, Text, SafeAreaView, TouchableOpacity, TextInput } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';  // Import Ionicons from expo

export default function Login() {
  const navigation = useNavigation();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isPasswordVisible, setPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordVisible(prevState => !prevState);
  };

  return (
    <SafeAreaView style={styles.container}>
      <Image
        source={require('../assets/logo.png')}
        style={styles.logosmall}
      />
      <Text style={styles.text}>LAWNMATE</Text>
      

      {/* Email Input */}
      <TextInput
        style={styles.input}
        placeholder="Email"
        keyboardType="email-address"
        value={email}
        onChangeText={setEmail}
      />

      {/* Password Input */}
      <View style={styles.passwordContainer}>
        <TextInput
          style={styles.passwordInput}
          placeholder="Password"
          secureTextEntry={!isPasswordVisible}
          value={password}
          onChangeText={setPassword}
        />
        <TouchableOpacity onPress={togglePasswordVisibility} style={styles.eyeIconContainer}>
          <Ionicons name={isPasswordVisible ? 'eye-off-outline' : 'eye-outline'} size={24} color="black" />
        </TouchableOpacity>
      </View>
      {/* Login button */}
      <TouchableOpacity style={styles.buttonlog} onPress={() => navigation.navigate('Home')}>
        <Text style={styles.buttonText}>Login</Text>
      </TouchableOpacity>
      <Text style={styles.normaltext}>Create new account</Text>
      {/* Signup Button */}
      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Signup')}>
        <Text style={styles.buttonText}>Signup</Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#DAFADA',
  },
  logosmall: {
    width: 250,
    height: 250,
    marginBottom: -20,
    marginTop: -100,
  },
  text: {
    fontSize: 30,
    marginTop: 10,
  },
  normaltext: {
    fontSize: 15,
    marginTop: 30,
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
    fontSize: 16,
  },
});
