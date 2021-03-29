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

import UserForm from "./components/user-form";
import UserEditForm from "./components/user-edit-form";
import UserDeleteForm from "./components/user-delete-form";

import * as API_USERS from "./api/user-api"
import UserTable from "./components/user-table";
import Label from "reactstrap/es/Label";



class UserContainer extends React.Component {

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
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchUsers();
    }

    fetchUsers() {
        return API_USERS.getUsers((result, status, err) => {

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
            isLoaded: false
        });
        this.toggleForm();
        this.fetchUsers();
    }

    reloadEdit() {
        this.setState({
            isLoaded: false
        });
        this.toggleEditForm();
        this.fetchUsers();
    }

    reloadDelete() {
        this.setState({
            isLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchUsers();
    }


    render() {
        return (
            <div>
                <CardHeader>
                    <strong> User Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add User </Button>
                            <Button color="secondary" onClick={this.toggleEditForm}>Edit User </Button>
                            <Button color="danger" onClick={this.toggleDeleteForm}>Delete User </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Label className="m-auto col-3" style={{fontSize: '50px'}}> Users </Label>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <UserTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add User: </ModalHeader>
                    <ModalBody>
                        <UserForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleEditForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleEditForm}> Edit User: </ModalHeader>
                    <ModalBody>
                        <UserEditForm reloadHandler={this.reloadEdit}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete User: </ModalHeader>
                    <ModalBody>
                        <UserDeleteForm reloadHandler={this.reloadDelete}/>
                    </ModalBody>
                </Modal>
            </div>
        )

    }

}


export default UserContainer;
