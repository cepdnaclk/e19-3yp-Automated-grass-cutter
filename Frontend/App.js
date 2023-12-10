import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View,SafeAreaView, Image } from 'react-native';
//View -> UIView
export default function App() {
  console.log("App executed");
  

  return (
    <SafeAreaView style={styles.container}>
      {/* Logo Image */}
      <Image
        source={require("./logo.png")}  
        style={styles.logo}
      />
      <Text style={styles.text}>LAWNMATE</Text>

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
    width: 300,  // Set the new width of your logo
    height: 300, // Set the new height of your logo
    marginBottom: -40, // Adjust as needed
  },appName: {
    fontSize: 40,  // Set the font size
    fontWeight: 'bold',  // Set the font weight (e.g., 'bold', 'normal', etc.)
    fontStyle: 'italic', // Set the font style (e.g., 'normal', 'italic')
    color: 'black', // Set the text color
  },text: {
    fontSize: 50,
  },
});
