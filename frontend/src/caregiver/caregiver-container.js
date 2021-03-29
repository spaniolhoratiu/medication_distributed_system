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

import CaregiverForm from "./components/caregiver-form";
import CaregiverEditForm from "./components/caregiver-edit-form";
import CaregiverDeleteForm from "./components/caregiver-delete-form";

import * as API_CAREGIVERS from "./api/caregiver-api"
import CaregiverTable from "./components/caregiver-table";
import * as API_USERS from "../user/api/user-api";
import UserTable from "../user/components/user-table";
import Label from "reactstrap/es/Label";



class CaregiverContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleEditForm = this.toggleEditForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this);
        this.reload = this.reload.bind(this);
        this.reloadEdit = this.reloadEdit.bind(this);
        this.reloadDelete = this.reloadDelete.bind(this);

        this.state = {
            selected: false,
            selectedEdit:false,
            selectedDelete:false,
            collapseForm: false,
            tableData: [],
            tableUsersData: [],
            isLoaded: false,
            isUsersLoaded:false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchCaregivers();
        this.fetchUsers();
    }

    fetchCaregivers() {
        return API_CAREGIVERS.getCaregivers((result, status, err) => {

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

    toggleForm() {
        this.setState({selected: !this.state.selected});
    }

    toggleEditForm() {
        this.setState({selectedEdit: !this.state.selectedEdit});
    }

    toggleDeleteForm() {
        this.setState({selectedDelete: !this.state.selectedDelete});
    }

    reload() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false
        });
        this.toggleForm();
        this.fetchCaregivers();
        this.fetchUsers();
    }

    reloadEdit() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false
        });
        this.toggleEditForm();
        this.fetchCaregivers();
        this.fetchUsers();
    }

    reloadDelete() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchCaregivers();
        this.fetchUsers();
    }


    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Caregiver Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Caregiver </Button>
                            <Button color="secondary" onClick={this.toggleEditForm}>Edit Caregiver </Button>
                            <Button color="danger" onClick={this.toggleDeleteForm}>Delete Caregiver </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Label className="m-auto col-4" style={{fontSize: '50px'}}> Caregivers </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <CaregiverTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Label className="m-auto col-3" style={{fontSize: '50px'}}> Users </Label>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isUsersLoaded && <UserTable tableData = {this.state.tableUsersData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Caregiver: </ModalHeader>
                    <ModalBody>
                        <CaregiverForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleEditForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleEditForm}> Edit Caregiver: </ModalHeader>
                    <ModalBody>
                        <CaregiverEditForm reloadHandler={this.reloadEdit}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Caregiver: </ModalHeader>
                    <ModalBody>
                        <CaregiverDeleteForm reloadHandler={this.reloadDelete}/>
                    </ModalBody>
                </Modal>
            </div>
        )

    }

}


export default CaregiverContainer;
