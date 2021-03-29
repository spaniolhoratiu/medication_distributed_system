import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

import authHeader from "../../services/auth-header";

const endpoint = {
    user: '/user',
    getByEmail: '/getByEmail'
};

const loggedUser = JSON.parse(localStorage.getItem('user'));

function getUsers(callback) {
    let request = new Request(HOST.backend_api + endpoint.user, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getUserById(params, callback){
    let request = new Request(HOST.backend_api + endpoint.user + params.id, {
        method: 'GET',
        headers: authHeader()
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getUserByEmail(params, callback){
    let request = new Request(HOST.backend_api + endpoint.user + endpoint.getByEmail + params.email, {
        method: 'GET',
        headers: authHeader()
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postUser(user, callback){

    let request = new Request(HOST.backend_api + endpoint.user , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(user)
    });




    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function putUser(user, callback){
    let request = new Request(HOST.backend_api + endpoint.user , {
        method: 'PUT',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteUser(user, callback){
    let request = new Request(HOST.backend_api + endpoint.user, {
        method: 'DELETE',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getUsers,
    getUserById,
    postUser,
    putUser,
    deleteUser,
    getUserByEmail
};
