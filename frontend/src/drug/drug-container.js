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

import DrugForm from "./components/drug-form";
import DrugEditForm from "./components/drug-edit-form";
import DrugDeleteForm from "./components/drug-delete-form";

import * as API_USERS from "./api/drug-api"
import DrugTable from "./components/drug-table";



class DrugContainer extends React.Component {

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
        this.fetchDrugs();
    }

    fetchDrugs() {
        return API_USERS.getDrugs((result, status, err) => {

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
        this.fetchDrugs();
    }

    reloadEdit() {
        this.setState({
            isLoaded: false
        });
        this.toggleEditForm();
        this.fetchDrugs();
    }

    reloadDelete() {
        this.setState({
            isLoaded: false
        });
        this.toggleDeleteForm();
        this.fetchDrugs();
    }


    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Drug Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Drug </Button>
                            <Button color="secondary" onClick={this.toggleEditForm}>Edit Drug </Button>
                            <Button color="danger" onClick={this.toggleDeleteForm}>Delete Drug </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <DrugTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Drug: </ModalHeader>
                    <ModalBody>
                        <DrugForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleEditForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleEditForm}> Edit Drug: </ModalHeader>
                    <ModalBody>
                        <DrugEditForm reloadHandler={this.reloadEdit}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Drug: </ModalHeader>
                    <ModalBody>
                        <DrugDeleteForm reloadHandler={this.reloadDelete}/>
                    </ModalBody>
                </Modal>
            </div>
        )

    }

}


export default DrugContainer;
