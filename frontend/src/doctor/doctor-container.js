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

import DoctorForm from "./components/doctor-form";
import DoctorEditForm from "./components/doctor-edit-form";
import DoctorDeleteForm from "./components/doctor-delete-form";

import * as API_DOCTORS from "./api/doctor-api"
import DoctorTable from "./components/doctor-table";
import * as API_USERS from "../user/api/user-api";
import UserTable from "../user/components/user-table";
import Label from "reactstrap/es/Label";



class DoctorContainer extends React.Component {

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
        this.fetchDoctors();
        this.fetchUsers();
    }

    fetchDoctors() {
        return API_DOCTORS.getDoctors((result, status, err) => {

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
        this.fetchDoctors();
        this.fetchUsers();
    }

    reloadEdit() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false
        });
        this.toggleEditForm();
        this.fetchDoctors();
        this.fetchUsers();
    }

    reloadDelete() {
        this.setState({
            isLoaded: false,
            isUsersLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchDoctors();
        this.fetchUsers();
    }


    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Doctor Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Doctor </Button>
                            <Button color="secondary" onClick={this.toggleEditForm}>Edit Doctor </Button>
                            <Button color="danger" onClick={this.toggleDeleteForm}>Delete Doctor </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Label className="m-auto col-3" style={{fontSize: '50px'}}> Doctors </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <DoctorTable tableData = {this.state.tableData}/>}
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
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Doctor: </ModalHeader>
                    <ModalBody>
                        <DoctorForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleEditForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleEditForm}> Edit Doctor: </ModalHeader>
                    <ModalBody>
                        <DoctorEditForm reloadHandler={this.reloadEdit}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Doctor: </ModalHeader>
                    <ModalBody>
                        <DoctorDeleteForm reloadHandler={this.reloadDelete}/>
                    </ModalBody>
                </Modal>
            </div>
        )

    }

}


export default DoctorContainer;
