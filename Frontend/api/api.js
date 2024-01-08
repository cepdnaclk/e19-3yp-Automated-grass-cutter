import axios from 'axios';

const API_URL = 'http://localhost:8080/users/signup';

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
