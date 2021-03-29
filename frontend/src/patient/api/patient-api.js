import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

import authHeader from "../../services/auth-header";

const endpoint = {
    patient: '/patient',
    patientCaregiver: '/patient/caregiver',
    withUserId: '/with_user_id/'
};

const loggedUser = JSON.parse(localStorage.getItem('user'));

function getPatients(callback) {
    let request = new Request(HOST.backend_api + endpoint.patient, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getPatientCaregivers(callback) {
    let request = new Request(HOST.backend_api + endpoint.patientCaregiver, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getPatientById(params, callback){
    let request = new Request(HOST.backend_api + endpoint.patient + endpoint.withUserId + params, {
        method: 'GET',
        headers: authHeader()
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postPatient(patient, callback){

    let request = new Request(HOST.backend_api + endpoint.patient , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(patient)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function postPatientCaregiver(patient, callback){
    let request = new Request(HOST.backend_api + endpoint.patientCaregiver , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(patient)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function deletePatientCaregiver(patient, callback){
    let request = new Request(HOST.backend_api + endpoint.patientCaregiver , {
        method: 'DELETE',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(patient)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function putPatient(patient, callback){
    let request = new Request(HOST.backend_api + endpoint.patient , {
        method: 'PUT',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(patient)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function deletePatient(patient, callback){
    let request = new Request(HOST.backend_api + endpoint.patient, {
        method: 'DELETE',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(patient)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getPatients,
    postPatient,
    putPatient,
    deletePatient,
    postPatientCaregiver,
    deletePatientCaregiver,
    getPatientCaregivers,
    getPatientById
};
