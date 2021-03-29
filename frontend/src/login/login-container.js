import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Row
} from 'reactstrap';
import Label from "reactstrap/es/Label";
import Input from "reactstrap/es/Input";


//import * as API_USERS from "./api/user-api"



class LoginContainer extends React.Component {

    constructor(props) {
        super(props);
        this.reload = this.reload.bind(this);
        this.state = {
            email: '',
            password: ''
        };
    }

    reload() {
        this.setState({
            email: '',
            password: '',
        });
    }

    handleChange = (event) => {
        const input = event.target;
        const value = input.value;

        this.setState({ [input.name]: value });
    };

    authenticate() {
        const { email, password } = this.state;

    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Log In </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Label for="emailField">E-mail:</Label>
                            <Input type="email" id="emailField" onChange={this.handleChange}/>
                        </Col>
                    </Row>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Label for="passwordField">Password:</Label>
                            <Input type="password" id="passwordField" onChange={this.handleChange}/>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Button color="primary" onClick={this.authenticate}>Log in</Button>
                        </Col>
                    </Row>
                    <br/>
                </Card>

            </div>
        )

    }
}


export default LoginContainer;
