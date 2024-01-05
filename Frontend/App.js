import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Welcome from './screens/Welcome';
import Login from './screens/Login';
import Signup from './screens/Signup';
import Home from './screens/Home';
import Man_or_Auto from './screens/Man_or_Auto';
import Addlocation from './screens/Addlocation';
import Location from './screens/Location';
import Mannual from './screens/Mannual';


const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Welcome">
        <Stack.Screen name="Welcome" component={Welcome} options={{ headerShown: false }} />
        <Stack.Screen name="Login" component={Login} />
        <Stack.Screen name="Home" component={Home} options={{ headerShown: false }} />
        <Stack.Screen name="Signup" component={Signup} />
        <Stack.Screen name="Man_or_Auto" component={Man_or_Auto} />
        <Stack.Screen name="Addlocation" component={Addlocation} />
        <Stack.Screen name="Location" component={Location} />
        <Stack.Screen name="Mannual" component={Mannual} />
        
      </Stack.Navigator>
    </NavigationContainer>
  );
}
