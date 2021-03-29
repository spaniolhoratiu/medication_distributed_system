import React, { Component } from "react";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {
    Card,
    CardHeader,
    Col,
    Row,
    Label
} from 'reactstrap';


import UserService from "../../services/user.service";
import API from "../../user/api/user-api"
import * as API_PATIENTS from "../../patient/api/patient-api";
import AuthService from "../../services/auth.service";
import * as API_MEDICATIONPLANDRUGS from "../../medication_plan_drug/api/medication_plan_drug-api";


export default class BoardPatient extends Component {
    constructor(props) {
        super(props);

        this.state = {
            content: "",
            patientInfo: [],
            isPatientInfoLoaded: false,

            medicationPlanDrugInfo: [],
            isMedicationPlanDrugInfoLoaded: false,

            processedMedicationPlanDrugInfo: [],
            isProcessedMedicationPlanDrugInfoLoaded: false,
        };
    }

    componentDidMount() {
        this.fetchMedicationPlanDrugs();
        this.fetchPatient();
    }

    fetchPatient()
    {
        return API_PATIENTS.getPatientById(AuthService.getCurrentUser().id,(result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    patientInfo: result,
                    isPatientInfoLoaded: true
                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchMedicationPlanDrugs()
    {
        return API_MEDICATIONPLANDRUGS.getMedicationPlanDrugs((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    medicationPlanDrugInfo: result,
                    isMedicationPlanDrugInfoLoaded: true
                });

                let loggedUserId = AuthService.getCurrentUser().id;
                let data = this.state.medicationPlanDrugInfo;
                let filtered = data.filter(element =>
                    element.medicationPlanDTO.patientDTO.userDTO.id === loggedUserId
                );

                this.setState(
                    {
                        processedMedicationPlanDrugInfo: filtered,
                        isProcessedMedicationPlanDrugInfoLoaded: true
                    }
                )

            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }


    render() {


        let arr=this.state.processedMedicationPlanDrugInfo;
        let elements=[];
        for(let i=0;i<arr.length;i++){
            elements.push(<Label>Medication: {arr[i].drugDTO.name}</Label>);
            elements.push(<br/>);
            elements.push(<Label>Begin treatment on: {arr[i].startDate}</Label>);
            elements.push(<br/>);
            elements.push(<Label>End treatment on: {arr[i].endDate}</Label>);
            elements.push(<br/>);
            elements.push(<Label>Take every: {arr[i].intakeInterval}</Label>);
            elements.push(<br/>);
            elements.push(<Label>Dosage: {arr[i].drugDTO.dosage}</Label>);
            elements.push(<br/>);
            elements.push(<Label>Belongs to: {arr[i].medicationPlanDTO.name}</Label>);
            elements.push(<br/>);
            elements.push(<br/>);
        }


        return(
            <div>
                <CardHeader>
                    <strong> <strong> {AuthService.isPatientLogged() && this.state.isPatientInfoLoaded ? this.state.patientInfo.name + "'s Profile" : ""} </strong> </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <br/>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '50px'}}> My Profile </Label>
                    </Row>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '25px'}}> Name: {AuthService.isPatientLogged() && this.state.isPatientInfoLoaded ? this.state.patientInfo.name: ""} </Label>
                    </Row>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '25px'}}> Birth Date: {AuthService.isPatientLogged() && this.state.isPatientInfoLoaded ? this.state.patientInfo.birthDate: ""} </Label>
                    </Row>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '25px'}}> Gender: {AuthService.isPatientLogged() && this.state.isPatientInfoLoaded ? this.state.patientInfo.gender : ""} </Label>
                    </Row>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '25px'}}> Address: {AuthService.isPatientLogged() && this.state.isPatientInfoLoaded ? this.state.patientInfo.address : ""} </Label>
                    </Row>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '25px'}}> Medical Record: {AuthService.isPatientLogged() && this.state.isPatientInfoLoaded ? this.state.patientInfo.medicalRecord : ""} </Label>
                    </Row>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '50px'}}> My Account </Label>
                    </Row>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '25px'}}> Email: {AuthService.isPatientLogged() && this.state.isPatientInfoLoaded ? this.state.patientInfo.userDTO.email : ""} </Label>
                    </Row>
                </Card>


                <Card>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '40px'}}> My Medications </Label>
                    </Row>
                    <div className="m-auto">
                        {elements}
                    </div>
                </Card>



            </div>
        )
    }
}