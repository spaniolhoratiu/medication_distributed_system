import React from 'react';
//import validate from "./validators/person-validators";
import Button from "react-bootstrap/Button";
import * as API from "../../patient/api/patient-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import { FormGroup, Input, Label} from 'reactstrap';


class PatientCaregiverForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {
            errorStatus: 0,
            error: null,
            formIsValid: false,

            formControls: {
                patientId:{
                    value:'',
                    placeholder:'Patient ID',
                    valid:false,
                    touched:false
                },
                    caregiverId:{
                    value:'',
                    placeholder:'Caregiver ID',
                    valid:false,
                    touched:false
                }
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
    }


    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        //updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        /*
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }
         */

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };

    postPatientCaregiver(patientCaregiver) {
        return API.postPatientCaregiver(patientCaregiver, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully deleted Patient-Caregiver with id: " + result);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    handleSubmit() {
        let patientCaregiver = {
            patientId: this.state.formControls.patientId.value,
           caregiverId: this.state.formControls.caregiverId.value,
        };

        console.log(patientCaregiver);
        this.postPatientCaregiver(patientCaregiver);
    }

    render() {
        return (
            <div>
                <FormGroup id='patientId'>
                    <Label for='patientIdField'> Selected Patient ID: </Label>
                    <Input name='patientId' id='patientIdField' placeholder={this.state.formControls.patientId.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.patientId.value}
                           touched={this.state.formControls.patientId.touched? 1 : 0}
                           valid={this.state.formControls.patientId.valid}
                           required
                    />
                    {this.state.formControls.patientId.touched && !this.state.formControls.patientId.valid &&
                    <div className={"error-message"}> </div>}
                </FormGroup>

                <FormGroup id='caregiverId'>
                    <Label for='caregiverIdField'> Selected Caregiver ID: </Label>
                    <Input name='caregiverId' id='caregiverIdField' placeholder={this.state.formControls.caregiverId.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.caregiverId.value}
                           touched={this.state.formControls.caregiverId.touched? 1 : 0}
                           valid={this.state.formControls.caregiverId.valid}
                           required
                    />
                    {this.state.formControls.caregiverId.touched && !this.state.formControls.caregiverId.valid &&
                    <div className={"error-message"}> </div>}
                </FormGroup>

                <Row>
                    <Col sm={{size: '4', offset: 8}}>
                        <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}>  Submit </Button>
                    </Col>
                </Row>

                {
                    this.state.errorStatus > 0 &&
                    <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error}/>
                }
            </div>
        ) ;
    }
}

export default PatientCaregiverForm;
