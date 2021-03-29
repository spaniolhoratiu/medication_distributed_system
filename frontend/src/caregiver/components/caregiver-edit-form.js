import React from 'react';
//import validate from "./validators/person-validators";
import Button from "react-bootstrap/Button";
import * as API from "../api/caregiver-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import { FormGroup, Input, Label} from 'reactstrap';


class CaregiverEditForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,

            formControls: {
                selectedId: {
                    value: '',
                    placeholder: 'Selected Id...',
                    valid: false,
                    touched: false,
                },
                name: {
                    value: '',
                    placeholder: 'Name...',
                    valid: false,
                    touched: false,
                },
                birthDate: {
                    value: '',
                    placeholder: 'DD/MM/YYYY',
                    valid: false,
                    touched: false,
                },
                gender: {
                    value: '',
                    placeholder: 'Male/Female',
                    valid: false,
                    touched: false,
                },
                address: {
                    value: '',
                    placeholder: 'Address...',
                    valid: false,
                    touched: false,
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

    updateCaregiver(caregiver) {
        return API.putCaregiver(caregiver, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully updated caregiver with id: " + result);
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
        let caregiver = {
            selectedId: this.state.formControls.selectedId.value,
            name: this.state.formControls.name.value,
            birthDate: this.state.formControls.birthDate.value,
            gender: this.state.formControls.gender.value,
            address: this.state.formControls.address.value
        };

        console.log(caregiver);
        this.updateCaregiver(caregiver);
    }

    render() {
        return (
            <div>
                <FormGroup id='selectedId'>
                    <Label for='selectedIdField'> Selected Caregiver ID: </Label>
                    <Input name='selectedId' id='selectedIdField' placeholder={this.state.formControls.selectedId.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.selectedId.value}
                           touched={this.state.formControls.selectedId.touched ? 1 : 0}
                           valid={this.state.formControls.selectedId.valid}
                           required
                    />
                    {this.state.formControls.selectedId.touched && !this.state.formControls.selectedId.valid &&
                    <div className={"error-message"}> </div>}
                </FormGroup>


                <FormGroup id='name'>
                    <Label for='nameField'> Name: </Label>
                    <Input name='name' id='nameField' placeholder={this.state.formControls.name.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.name.value}
                           touched={this.state.formControls.name.touched? 1 : 0}
                           valid={this.state.formControls.name.valid}
                           required
                    />
                </FormGroup>


                <FormGroup id='birthDate'>
                    <Label for='birthDateField'> Birth Date: </Label>
                    <Input name='birthDate' id='birthDateField' placeholder={this.state.formControls.birthDate.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.birthDate.value}
                           touched={this.state.formControls.birthDate.touched? 1 : 0}
                           valid={this.state.formControls.birthDate.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='gender'>
                    <Label for='genderField'> Gender: </Label>
                    <Input name='gender' id='genderField' placeholder={this.state.formControls.gender.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.gender.value}
                           touched={this.state.formControls.gender.touched? 1 : 0}
                           valid={this.state.formControls.gender.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='address'>
                    <Label for='addressField'> Address: </Label>
                    <Input name='address' id='addressField' placeholder={this.state.formControls.address.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.address.value}
                           touched={this.state.formControls.address.touched? 1 : 0}
                           valid={this.state.formControls.address.valid}
                           required
                    />
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

export default CaregiverEditForm;
