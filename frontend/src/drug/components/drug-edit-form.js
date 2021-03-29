import React from 'react';
//import validate from "./validators/person-validators";
import Button from "react-bootstrap/Button";
import * as API from "../api/drug-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import { FormGroup, Input, Label} from 'reactstrap';


class DrugEditForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,

            formControls: {
                selectedId:{
                    value:'',
                    placeholder:'Selected ID',
                    valid:false,
                    touched:false
                },
                name: {
                    value: '',
                    placeholder: 'Name...',
                    valid: false,
                    touched: false,
                },
                dosage: {
                    value: '',
                    placeholder: 'Dosage...',
                    valid: false,
                    touched: false,
                },
                sideEffects: {
                    value: '',
                    placeholder: 'Side Effects...',
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

    updateDrug(drug) {
        return API.putDrug(drug, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully updated drug with id: " + result);
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
        let drug = {
            selectedId: this.state.formControls.selectedId.value,
            name: this.state.formControls.name.value,
            dosage: this.state.formControls.dosage.value,
            sideEffects: this.state.formControls.sideEffects.value
        };

        console.log(drug);
        this.updateDrug(drug);
    }

    render() {
        return (
            <div>
                <FormGroup id='selectedId'>
                    <Label for='selectedIdField'> Selected Drug ID: </Label>
                    <Input name='selectedId' id='selectedIdField' placeholder={this.state.formControls.selectedId.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.selectedId.value}
                           touched={this.state.formControls.selectedId.touched? 1 : 0}
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

                <FormGroup id='dosage'>
                    <Label for='dosageField'> Dosage: </Label>
                    <Input name='dosage' id='dosageField' placeholder={this.state.formControls.dosage.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.dosage.value}
                           touched={this.state.formControls.dosage.touched? 1 : 0}
                           valid={this.state.formControls.dosage.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='sideEffects'>
                    <Label for='sideEffectsField'> Side Effects: </Label>
                    <Input name='sideEffects' id='sideEffectsField' placeholder={this.state.formControls.sideEffects.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.sideEffects.value}
                           touched={this.state.formControls.sideEffects.touched? 1 : 0}
                           valid={this.state.formControls.sideEffects.valid}
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

export default DrugEditForm;
