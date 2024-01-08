# Makefile for React Native project with Expo

# Initialize the project
init:
	@echo "Initializing Expo project..."
	npm install -g expo-cli
	expo init lawnmate --template expo-template-blank

# Install dependencies
install:
	@echo "Installing dependencies..."

	cd lawnmate && npm install
    npm install @react-navigation/native @react-navigation/native-stack
    npm install react-native-elements
    npm install expo-status-bar @expo/vector-icons
    npm install react-native-gesture-handler
	npm install react-native-ble-manager
	npm install axios


# Start the Expo development server
start:
	@echo "Starting Expo development server..."
	cd lawnmate && npm start

# Build the app for production
build:
	@echo "Building the app for production..."
	cd lawnmate && npm run build

# Remove node_modules and reinstall
clean:
	@echo "🧹 Cleaning up..."
	rm -rf lawnmate/node_modules
	cd lawnmate && npm install



