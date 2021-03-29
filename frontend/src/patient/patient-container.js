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

import PatientForm from "./components/patient-form";
import PatientEditForm from "./components/patient-edit-form";
import PatientDeleteForm from "./components/patient-delete-form";

import * as API_PATIENTS from "./api/patient-api"
import PatientTable from "./components/patient-table";
import * as API_USERS from "../user/api/user-api";
import UserTable from "../user/components/user-table";
import Label from "reactstrap/es/Label";
import * as API_DRUGS from "../drug/api/drug-api"

import * as API_MEDICATIONPLANS from "../medication_plan/api/medication_plan-api"
import MedicationPlanTable from "../medication_plan/components/medication_plan-table";
import MedicationPlanDeleteForm from "../medication_plan/components/medication_plan-delete-form";
import MedicationPlanForm from "../medication_plan/components/medication_plan-form";
import DrugTable from "../drug/components/drug-table";

import * as API_MEDICATIONPLANDRUGS from "../medication_plan_drug/api/medication_plan_drug-api"
import MedicationPlanDrugForm from "../medication_plan_drug/components/medication_plan_drug-form";
import MedicationPlanDrugDeleteForm from "../medication_plan_drug/components/medication_plan_drug-delete-form";
import MedicationPlanDrugTable from "../medication_plan_drug/components/medication_plan_drug-table";



//import CaregiverTable from "./components/caregiver-table";

class PatientContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleEditForm = this.toggleEditForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this);
        this.toggleAddMedicationPlanForm = this.toggleAddMedicationPlanForm.bind(this);
        this.toggleDeleteMedicationPlanForm = this.toggleDeleteMedicationPlanForm.bind(this);
        this.toggleAddMedicationPlanDrugForm = this.toggleAddMedicationPlanDrugForm.bind(this);
        this.toggleDeleteMedicationPlanDrugForm = this.toggleDeleteMedicationPlanDrugForm.bind(this);


        this.reload = this.reload.bind(this);
        this.reloadEdit = this.reloadEdit.bind(this);
        this.reloadDelete = this.reloadDelete.bind(this);
        this.reloadAddMedicationPlan = this.reloadAddMedicationPlan.bind(this);
        this.reloadDeleteMedicationPlan = this.reloadDeleteMedicationPlan.bind(this);
        this.reloadAddMedicationPlanDrug = this.reloadAddMedicationPlanDrug.bind(this);
        this.reloadDeleteMedicationPlanDrug = this.reloadDeleteMedicationPlanDrug.bind(this);

        this.state = {
            selected: false,
            selectedEdit:false,
            selectedDelete:false,
            selectedAddMedicationPlan:false,
            selectedDeleteMedicationPlan: false,
            selectedAddMedicationPlanDrug: false,
            selectedDeleteMedicationPlanDrug: false,

            collapseForm: false,

            tableData: [],
            tableUsersData: [],
            tableMedicationPlanData:[],
            tableDrugData: [],
            tableMedicationPlanDrugData: [],

            isLoaded: false,
            isUsersLoaded:false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded:false,

            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }

    fetchMedicationPlanDrugs()
    {
        return API_MEDICATIONPLANDRUGS.getMedicationPlanDrugs((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tableMedicationPlanDrugData: result,
                    isMedicationPlanDrugDataLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }


    fetchDrugs()
    {
        return API_DRUGS.getDrugs((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tableDrugData: result,
                    isDrugDataLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }


    fetchPatients() {
        return API_PATIENTS.getPatients((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchUsers() {
        return API_USERS.getUsers((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableUsersData: result,
                    isUsersLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchMedicationPlans() {
        return API_MEDICATIONPLANS.getMedicationPlans((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableMedicationPlanData: result,
                    isMedicationPlanDataLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    toggleForm() {
        this.setState({selected: !this.state.selected});
    }

    toggleEditForm() {
        this.setState({selectedEdit: !this.state.selectedEdit});
    }

    toggleDeleteForm() {
        this.setState({selectedDelete: !this.state.selectedDelete});
    }

    toggleAddMedicationPlanForm() {
        this.setState({selectedAddMedicationPlan: !this.state.selectedAddMedicationPlan});
    }

    toggleDeleteMedicationPlanForm() {
        this.setState({selectedDeleteMedicationPlan: !this.state.selectedDeleteMedicationPlan});
    }

    toggleAddMedicationPlanDrugForm() {
        this.setState({selectedAddMedicationPlanDrug: !this.state.selectedAddMedicationPlanDrug});
    }

    toggleDeleteMedicationPlanDrugForm() {
        this.setState({selectedDeleteMedicationPlanDrug: !this.state.selectedDeleteMedicationPlanDrug});
    }


    reload() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded: false
        });
        this.toggleForm();
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }

    reloadEdit() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded: false
        });
        this.toggleEditForm();
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }

    reloadDelete() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded: false

        });
        this.toggleDeleteForm();
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }

    reloadAddMedicationPlan()
    {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded: false
        });
        this.toggleAddMedicationPlanForm();
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }

    reloadDeleteMedicationPlan()
    {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded: false
        });
        this.toggleDeleteMedicationPlanForm();
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }

    reloadAddMedicationPlanDrug()
    {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded: false
        });
        this.toggleAddMedicationPlanDrugForm();
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }

    reloadDeleteMedicationPlanDrug()
    {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false,
            isMedicationPlanDataLoaded: false,
            isDrugDataLoaded: false,
            isMedicationPlanDrugDataLoaded: false
        });
        this.toggleDeleteMedicationPlanDrugForm();
        this.fetchPatients();
        this.fetchUsers();
        this.fetchMedicationPlans();
        this.fetchDrugs();
        this.fetchMedicationPlanDrugs();
    }


    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Patient Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Patient </Button>
                            <Button color="secondary" onClick={this.toggleEditForm}>Edit Patient </Button>
                            <Button color="danger" onClick={this.toggleDeleteForm}>Delete Patient </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Label className="m-auto col-4" style={{fontSize: '50px'}}> Patients </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <PatientTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                    <Row>
                        <Label className="m-auto col-3" style={{fontSize: '50px'}}> Users </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isUsersLoaded && <UserTable tableData = {this.state.tableUsersData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>

                    <Row>
                        <Label className="m-auto col-3" style={{fontSize: '50px'}}> Medication Plans </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleAddMedicationPlanForm}>Add Medication Plan </Button>
                            <Button color="danger" onClick={this.toggleDeleteMedicationPlanForm}>Delete Medication Plan </Button>
                        </Col>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isMedicationPlanDataLoaded && <MedicationPlanTable tableData = {this.state.tableMedicationPlanData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>

                    <Row>
                        <Label className="m-auto col-3" style={{fontSize: '50px'}}> Drugs </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isDrugDataLoaded && <DrugTable tableData = {this.state.tableDrugData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>

                    <Row>
                        <Label className="m-auto col-3" style={{fontSize: '50px'}}> Drugs on Medication Plans </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleAddMedicationPlanDrugForm}>Add Drug To Medication Plan </Button>
                            <Button color="danger" onClick={this.toggleDeleteMedicationPlanDrugForm}>Delete Drug from Medication Plan </Button>
                        </Col>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isMedicationPlanDrugDataLoaded && <MedicationPlanDrugTable tableData = {this.state.tableMedicationPlanDrugData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Patient: </ModalHeader>
                    <ModalBody>
                        <PatientForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleEditForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleEditForm}> Edit Patient: </ModalHeader>
                    <ModalBody>
                        <PatientEditForm reloadHandler={this.reloadEdit}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Patient: </ModalHeader>
                    <ModalBody>
                        <PatientDeleteForm reloadHandler={this.reloadDelete}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedAddMedicationPlan} toggle={this.toggleAddMedicationPlanForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleAddMedicationPlanForm}> Add Medication Plan: </ModalHeader>
                    <ModalBody>
                        <MedicationPlanForm reloadHandler={this.reloadAddMedicationPlan}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDeleteMedicationPlan} toggle={this.toggleDeleteMedicationPlanForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteMedicationPlanForm}> Delete Medication Plan: </ModalHeader>
                    <ModalBody>
                        <MedicationPlanDeleteForm reloadHandler={this.reloadDeleteMedicationPlan}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedAddMedicationPlanDrug} toggle={this.toggleAddMedicationPlanDrugForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleAddMedicationPlanDrugForm}> Add Drug to Medication Plan: </ModalHeader>
                    <ModalBody>
                        <MedicationPlanDrugForm reloadHandler={this.reloadAddMedicationPlanDrug}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDeleteMedicationPlanDrug} toggle={this.toggleDeleteMedicationPlanDrugForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteMedicationPlanDrugForm}> Delete Drug from Medication Plan: </ModalHeader>
                    <ModalBody>
                        <MedicationPlanDrugDeleteForm reloadHandler={this.reloadDeleteMedicationPlanDrug}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }

}


export default PatientContainer;
