import axios from "axios";

import {HOST} from "../commons/hosts";

const API_URL = HOST.backend_api + "/auth";

class AuthService {

    login(email, password) {
        return axios
            .post(HOST.backend_api + "/auth/login", {
                proxy: {
                    host: HOST.backend_api
                },
                email,
                password
            })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                    localStorage.setItem("logged", "true");
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
        localStorage.setItem("logged", "false");
    }

    register(email, password, role) {
        return axios.post(HOST.backend_api + "/auth/register", {
            proxy: {
                host: HOST.backend_api
            },
            email,
            password,
            role
        });
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

    isLoggedIn()
    {
        return !(localStorage.getItem("logged") === null || localStorage.getItem("logged") === "false");
    }

    isDoctorLogged() {
        let currentUser = this.getCurrentUser();
        if (this.isLoggedIn() && currentUser.role === "[ROLE_DOCTOR]")
            return true;
    }

    isPatientLogged() {
        let currentUser = this.getCurrentUser();
        if (this.isLoggedIn() && currentUser.role === "[ROLE_PATIENT]")
            return true;
        else return false;
    }

    isCaregiverLogged() {
        let currentUser = this.getCurrentUser();
        if (this.isLoggedIn() && currentUser.role === "[ROLE_CAREGIVER]")
            return true;
        else return false;
    }

}

export default new AuthService();