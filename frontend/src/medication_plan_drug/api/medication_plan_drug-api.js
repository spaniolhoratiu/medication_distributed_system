import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";

import authHeader from "../../services/auth-header";

const endpoint = {
    medicationPlanDrug: '/medicationPlanDrug'
};

const loggedUser = JSON.parse(localStorage.getItem('user'));

function getMedicationPlanDrugs(callback) {
    let request = new Request(HOST.backend_api + endpoint.medicationPlanDrug, {
        method: 'GET',
        headers: authHeader()
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postMedicationPlanDrug(medicationPlanDrug, callback){

    let request = new Request(HOST.backend_api + endpoint.medicationPlanDrug , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(medicationPlanDrug)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteMedicationPlanDrug(medicationPlanDrug, callback){
    let request = new Request(HOST.backend_api + endpoint.medicationPlanDrug, {
        method: 'DELETE',
        headers :  {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + loggedUser.token
        },
        body: JSON.stringify(medicationPlanDrug)
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getMedicationPlanDrugs,
    postMedicationPlanDrug,
    deleteMedicationPlanDrug,
};
