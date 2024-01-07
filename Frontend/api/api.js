import axios from 'axios';

const API_URL = 'http://localhost:8080/users/signup';
const API_URL_Login = 'http://localhost:8080/users/signin';

export const signupUser = async (email, password) => {
  try {
    const response = await axios.post(API_URL, {
      email,
      password,
    });
    return response.data;
  } catch (error) {
    console.error('Error signing up:', error);
    throw error;
  }
};
export const loginUser = async (email, password) => {
  try {
    const response = await axios.post(API_URL_Login, {
      email,
      password,
    });
    return response.data;
  } catch (error) {
    console.error('Error logging in:', error);
    throw error;
  }
};