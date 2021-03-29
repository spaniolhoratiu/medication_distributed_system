import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

import authHeader from "../../services/auth-header";

const endpoint = {
    caregiver: '/caregiver'
};

const loggedUser = JSON.parse(localStorage.getItem('user'));

function getCaregivers(callback) {
    let request = new Request(HOST.backend_api + endpoint.caregiver, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postCaregiver(caregiver, callback){
    let request = new Request(HOST.backend_api + endpoint.caregiver , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(caregiver)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function putCaregiver(caregiver, callback){
    let request = new Request(HOST.backend_api + endpoint.caregiver , {
        method: 'PUT',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(caregiver)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteCaregiver(caregiver, callback){
    let request = new Request(HOST.backend_api + endpoint.caregiver, {
        method: 'DELETE',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(caregiver)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getCaregivers,
    postCaregiver,
    putCaregiver,
    deleteCaregiver,
};
