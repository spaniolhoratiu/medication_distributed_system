import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

import authHeader from "../../services/auth-header";

const endpoint = {
    medicationPlan: '/medicationPlan'
};

const loggedUser = JSON.parse(localStorage.getItem('user'));

function getMedicationPlans(callback) {
    let request = new Request(HOST.backend_api + endpoint.medicationPlan, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postMedicationPlan(medicationPlan, callback){

    let request = new Request(HOST.backend_api + endpoint.medicationPlan , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(medicationPlan)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteMedicationPlan(medicationPlan, callback){
    let request = new Request(HOST.backend_api + endpoint.medicationPlan, {
        method: 'DELETE',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(medicationPlan)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getMedicationPlans,
    postMedicationPlan,
    deleteMedicationPlan
};
