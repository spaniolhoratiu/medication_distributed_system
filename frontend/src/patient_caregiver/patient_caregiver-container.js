import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';

import * as API_PATIENTS from "../patient/api/patient-api"
import PatientTable from "../patient/components/patient-table";

import * as API_CAREGIVERS from "../caregiver/api/caregiver-api";
import CaregiverTable from "../caregiver/components/caregiver-table";
import PatientCaregiverTable from "./components/patient_caregiver-table";

import PatientCaregiverForm from "./components/patient_caregiver-form";
import Label from "reactstrap/es/Label";
import PatientCaregiverDeleteForm from "./components/patient_caregiver-delete-form";

class PatientCaregiverContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this);

        this.reload = this.reload.bind(this);
        this.reloadDelete = this.reloadDelete.bind(this);

        this.state = {
            selected: false,
            selectedDelete:false,
            collapseForm: false,

            tablePatientsData: [],
            tableCaregiversData: [],
            tablePatientCaregiversData: [],

            isPatientDataLoaded: false,
            isCaregiverDataLoaded:false,
            isPatientCaregiverDataLoaded:false,

            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchPatients();
        this.fetchCaregivers();
        this.fetchPatientCaregivers();
    }

    fetchPatients() {
        return API_PATIENTS.getPatients((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tablePatientsData: result,
                    isPatientDataLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchCaregivers() {
        return API_CAREGIVERS.getCaregivers((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableCaregiversData: result,
                    isCaregiverDataLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchPatientCaregivers()
    {
        return API_PATIENTS.getPatientCaregivers((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tablePatientCaregiversData: result,
                    isPatientCaregiverDataLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }


    toggleForm(){
        this.setState({selected: !this.state.selected});
    }
    toggleDeleteForm() {
        this.setState({selectedDelete: !this.state.selectedDelete});
    }

    reload() {
        this.setState({
            isPatientDataLoaded: false,
            isCaregiverDataLoaded: false,
            isPatientCaregiverDataLoaded: false
        });
        this.toggleForm();
        this.fetchPatients();
        this.fetchCaregivers();
        this.fetchPatientCaregivers()
    }

    reloadDelete() {
        this.setState({
            isPatientDataLoaded: false,
            isCaregiverDataLoaded: false,
            isPatientCaregiverDataLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchPatients();
        this.fetchCaregivers();
       this.fetchPatientCaregivers()
    }




    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Patient-Caregiver Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Patient to Caregiver </Button>
                            <Button color="danger" onClick={this.toggleDeleteForm}>Delete Patient from Caregiver </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Label className="m-auto col-4" style={{fontSize: '50px'}}> Patients-Caregivers </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isPatientCaregiverDataLoaded && <PatientCaregiverTable tableData = {this.state.tablePatientCaregiversData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                    <Row>
                        <Label className="m-auto col-4" style={{fontSize: '50px'}}> Patients </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isPatientDataLoaded && <PatientTable tableData = {this.state.tablePatientsData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>

                    <Row>
                        <Label className="m-auto col-4" style={{fontSize: '50px'}}> Caregivers </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isCaregiverDataLoaded && <CaregiverTable tableData = {this.state.tableCaregiversData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>

                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Patient to Caregiver: </ModalHeader>
                    <ModalBody>
                        <PatientCaregiverForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>


                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Patient from Caregiver: </ModalHeader>
                    <ModalBody>
                        <PatientCaregiverDeleteForm reloadHandler={this.reloadDelete}/>
                    </ModalBody>
                </Modal>
            </div>
        )

    }

}


export default PatientCaregiverContainer;
