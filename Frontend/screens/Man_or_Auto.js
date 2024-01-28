import React from 'react';
import { StyleSheet, View, Image, Text,TouchableOpacity ,StatusBar} from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';


export default function Man_or_Auto() {
  const navigation = useNavigation();
  const route = useRoute();
  const userId = route.params?.userId;
  const goToLogin = () => {
    navigation.navigate('Login');
  };
  console.log('Man_or_Auto User ID:', userId);
  return (
    <View style={styles.container}>
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.buttonMan} onPress={() => navigation.navigate('ManualselDevice',{ userId: userId })}>
          <Text style={styles.buttonText}>Mannual</Text>
        </TouchableOpacity>
        
        <TouchableOpacity style={styles.buttonAuto} onPress={() => navigation.navigate('CuttingSelDevice',{ userId: userId })}>
          <Text style={styles.buttonText}>Automatic</Text>
        </TouchableOpacity>
      </View>
      <StatusBar style="auto" />
    </View>
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
  buttonMan: {
    fontSize: 18,
    paddingHorizontal: 70,
    paddingVertical: 40,
    backgroundColor: '#3CA832',
    borderRadius: 5,
    elevation: 3,
    marginHorizontal: 10,  // Add horizontal spacing between the buttons
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonAuto: {
    fontSize: 18,
    paddingHorizontal: 70,
    paddingVertical: 40,
    backgroundColor: '#7E7744',
    borderRadius: 5,
    elevation: 3,
    marginHorizontal: 10,  // Add horizontal spacing between the buttons
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: {
    color: '#000000',
  },
});
