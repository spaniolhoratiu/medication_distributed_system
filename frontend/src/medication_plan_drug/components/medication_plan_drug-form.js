import React from 'react';
//import validate from "./validators/person-validators";
import Button from "react-bootstrap/Button";
import * as API from "../api/medication_plan_drug-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import { FormGroup, Input, Label} from 'reactstrap';


class MedicationPlanDrugForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {
            errorStatus: 0,
            error: null,
            formIsValid: false,

            formControls: {
                selectedMedicationPlanId:{
                    value:'',
                    placeholder:'Medication Plan ID',
                    valid:false,
                    touched:false
                },
                selectedDrugId:{
                    value:'',
                    placeholder:'Drug ID',
                    valid:false,
                    touched:false
                },
                startDate:{
                    value:'',
                    placeholder:'Start Date',
                    valid:false,
                    touched:false
                },
                endDate:{
                    value:'',
                    placeholder:'End Date',
                    valid:false,
                    touched:false
                },
                intakeInterval:{
                    value:'',
                    placeholder:'Intake Interval',
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

    registerMedicationPlanDrug(medicationPlanDrug) {
        return API.postMedicationPlanDrug(medicationPlanDrug, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully registered medicationPlanDrug with id: " + result);
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
        let medicationPlanDrug = {
            selectedMedicationPlanId: this.state.formControls.selectedMedicationPlanId.value,
            selectedDrugId: this.state.formControls.selectedDrugId.value,
            startDate: this.state.formControls.startDate.value,
            endDate: this.state.formControls.endDate.value,
            intakeInterval: this.state.formControls.intakeInterval.value
        };

        console.log(medicationPlanDrug);
        this.registerMedicationPlanDrug(medicationPlanDrug);
    }

    render() {
        return (
            <div>
                <FormGroup id='selectedMedicationPlanId'>
                    <Label for='selectedMedicationPlanIdField'> Selected Medication Plan ID: </Label>
                    <Input name='selectedMedicationPlanId' id='selectedMedicationPlanIdField' placeholder={this.state.formControls.selectedMedicationPlanId.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.selectedMedicationPlanId.value}
                           touched={this.state.formControls.selectedMedicationPlanId.touched? 1 : 0}
                           valid={this.state.formControls.selectedMedicationPlanId.valid}
                           required
                    />
                    {this.state.formControls.selectedMedicationPlanId.touched && !this.state.formControls.selectedMedicationPlanId.valid &&
                    <div className={"error-message"}> </div>}
                </FormGroup>

                <FormGroup id='selectedDrugId'>
                    <Label for='selectedDrugIdField'> Selected Drug ID: </Label>
                    <Input name='selectedDrugId' id='selectedDrugIdField' placeholder={this.state.formControls.selectedDrugId.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.selectedDrugId.value}
                           touched={this.state.formControls.selectedDrugId.touched? 1 : 0}
                           valid={this.state.formControls.selectedDrugId.valid}
                           required
                    />
                    {this.state.formControls.selectedDrugId.touched && !this.state.formControls.selectedDrugId.valid &&
                    <div className={"error-message"}> </div>}
                </FormGroup>

                <FormGroup id='startDate'>
                    <Label for='startDateField'> Start Date: </Label>
                    <Input name='startDate' id='startDateField' placeholder={this.state.formControls.startDate.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.startDate.value}
                           touched={this.state.formControls.startDate.touched? 1 : 0}
                           valid={this.state.formControls.startDate.valid}
                           required
                    />
                    {this.state.formControls.startDate.touched && !this.state.formControls.startDate.valid &&
                    <div className={"error-message"}> </div>}
                </FormGroup>

                <FormGroup id='endDate'>
                    <Label for='endDateField'> End Date: </Label>
                    <Input name='endDate' id='endDateField' placeholder={this.state.formControls.endDate.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.endDate.value}
                           touched={this.state.formControls.endDate.touched? 1 : 0}
                           valid={this.state.formControls.endDate.valid}
                           required
                    />
                    {this.state.formControls.endDate.touched && !this.state.formControls.endDate.valid &&
                    <div className={"error-message"}> </div>}
                </FormGroup>

                <FormGroup id='intakeInterval'>
                    <Label for='intakeIntervalField'> Intake Interval: </Label>
                    <Input name='intakeInterval' id='intakeIntervalField' placeholder={this.state.formControls.intakeInterval.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.intakeInterval.value}
                           touched={this.state.formControls.intakeInterval.touched? 1 : 0}
                           valid={this.state.formControls.intakeInterval.valid}
                           required
                    />
                    {this.state.formControls.intakeInterval.touched && !this.state.formControls.intakeInterval.valid &&
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

export default MedicationPlanDrugForm;
