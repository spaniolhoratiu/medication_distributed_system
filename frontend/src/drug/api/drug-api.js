import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

import authHeader from "../../services/auth-header";

const endpoint = {
    drug: '/drug'
};

const loggedUser = JSON.parse(localStorage.getItem('user'));

function getDrugs(callback) {
    let request = new Request(HOST.backend_api + endpoint.drug, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postDrug(drug, callback){

    let request = new Request(HOST.backend_api + endpoint.drug , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(drug)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function putDrug(drug, callback){
    let request = new Request(HOST.backend_api + endpoint.drug , {
        method: 'PUT',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(drug)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteDrug(drug, callback){
    let request = new Request(HOST.backend_api + endpoint.drug, {
        method: 'DELETE',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(drug)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getDrugs,
    postDrug,
    putDrug,
    deleteDrug
};
