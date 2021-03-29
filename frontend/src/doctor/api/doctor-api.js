import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


import authHeader from "../../services/auth-header";

const endpoint = {
    doctor: '/doctor',
};

const loggedDoctor = JSON.parse(localStorage.getItem('user'));

function getDoctors(callback) {
    let request = new Request(HOST.backend_api + endpoint.doctor, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postDoctor(user, callback){
    let request = new Request(HOST.backend_api + endpoint.doctor , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedDoctor.token
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function putDoctor(user, callback){
    let request = new Request(HOST.backend_api + endpoint.doctor , {
        method: 'PUT',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedDoctor.token
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteDoctor(user, callback){
    let request = new Request(HOST.backend_api + endpoint.doctor, {
        method: 'DELETE',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedDoctor.token
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getDoctors,
    postDoctor,
    putDoctor,
    deleteDoctor
};
