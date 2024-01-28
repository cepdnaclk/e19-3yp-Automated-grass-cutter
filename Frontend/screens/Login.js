import React, { useState } from 'react';
import { StyleSheet, View, Image, Text,TouchableOpacity, TextInput ,Alert} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { loginUser } from '../api/api';

export default function Login() {
  const navigation = useNavigation();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isPasswordVisible, setPasswordVisible] = useState(false);
  const [userId, setUserId] = useState(null);
  const togglePasswordVisibility = () => {
    setPasswordVisible(prevState => !prevState);
  };

  const handleLogin = async () => {
    try {
      // Logging input values before making the API call
      console.log('Email:', email);
      console.log('Password:', password);

      // Call the loginUser function with email and password
      const response = await loginUser(email, password);

      // Logging the response for debugging
      console.log('API Response:', response);

      // Check if the response contains accessToken
      if (response.accessToken && response.userId) {
        // Navigate to the 'Home' screen
        const userId = response.userId;
        console.log('UserId:',userId);
        setUserId(userId);
        navigation.navigate('Home', { userId: userId });
      } else {
        Alert.alert('Error', 'Login failed. Please try again.');
      }
    } catch (error) {
      // Logging the detailed error message for debugging
      console.error('Error logging in:', error);

      // Display a generic error message to the user
      Alert.alert('Error', 'Login failed. Please try again.');
    }
  };

  return (
    <View style={styles.container}>
      <Image source={require('../assets/logo.png')} style={styles.logosmall} />
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
        
      </View>
      {/* Login button */}
      <TouchableOpacity style={styles.buttonlog} onPress={handleLogin}>
        <Text style={styles.buttonText}>Login</Text>
      </TouchableOpacity>
      <Text style={styles.normaltext}>Create new account</Text>
      {/* Signup Button */}
      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Signup')}>
        <Text style={styles.buttonText}>Signup</Text>
      </TouchableOpacity>
    </View>
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
    color: '#000000',
  },
  normaltext: {
    fontSize: 15,
    marginTop: 30,
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
    color: '#000000',
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
    margin:-10,
    height: 40,
    width: 300,
    paddingRight: 40,  // Space for the eye icon
    color: '#000000',
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
