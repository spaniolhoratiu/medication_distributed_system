import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/auth_test/';

class UserService {

    getPublicContent() {
        return axios.get(API_URL + 'all');
    }

    getPatientBoard() {
        return axios.get(API_URL + 'patient', { headers: authHeader() });
    }

    getCaregiverBoard() {
        return axios.get(API_URL + 'caregiver', { headers: authHeader() });
    }

    getDoctorBoard() {
        return axios.get(API_URL + 'doctor', { headers: authHeader() });
    }
}

export default new UserService();


