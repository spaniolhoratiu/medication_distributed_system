import React from 'react';
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {
    Card,
    CardHeader,
    Col,
    Row,
    Label
} from 'reactstrap';

import AuthService from "../../services/auth.service"

import * as API_PATIENTS from "../../patient/api/patient-api"
import PatientsForCaregiverTable from "./board-caregiver-table";
import SockJsClient from "react-stomp";
import {toast, ToastContainer} from "react-toastify";
import {HOST} from '../../commons/hosts'

class BoardCaregiverContainer extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            preProcessedData: [],
            postProcessedData: [],


            isPreProcessedDataLoaded:false,
            isPostProcessedDataLoaded: false,

            messagesReceivedCount: 0,

            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchPatientCaregivers();
    }

    fetchPatientCaregivers()
    {
        return API_PATIENTS.getPatientCaregivers((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    preProcessedData: result,
                    isPreProcessedDataLoaded: true
                });

                let loggedCaregiver = AuthService.getCurrentUser().id;
                let data = this.state.preProcessedData;
                let filtered = data.filter(element =>
                    element.caregiverUserId === loggedCaregiver);

                this.setState({
                    postProcessedData: filtered,
                    isPostProcessedDataLoaded: true

                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }


    reload() {
        this.setState({
            isPreProcessedDataLoaded: false,
            isPostProcessedDataLoaded: false,
            messagesReceivedCount: 0,
        });
        this.fetchPatientCaregivers();
    }



    render() {
        const notify = () => toast('Test from button!', {
            position: "top-right",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
        });
        return (
            <div>
                <CardHeader>
                    <strong> Patient-Caregiver Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <br/>
                    <Row>
                        <Label className="m-auto" style={{fontSize: '50px'}}> My Patients </Label>
                    </Row>
                    <Row>
                        <Col className sm={{size: '8', offset: 1}}>
                            {this.state.isPostProcessedDataLoaded && <PatientsForCaregiverTable tableData = {this.state.postProcessedData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>
                <div>
                    <button onClick={notify}>Notify !</button>
                    <ToastContainer />
                </div>
                <div>
                    Messages received: {this.state.messagesReceivedCount}
                </div>

                <SockJsClient url={HOST.backend_api + HOST.websocket_connect}
                            topics={['/topic/message']}
                            onConnect={() => {
                                toast('Connected!', {
                                    position: "top-right",
                                    autoClose: 5000,
                                    hideProgressBar: false,
                                    closeOnClick: true,
                                    pauseOnHover: true,
                                    draggable: true,
                                    progress: undefined,
                                });
                            }}
                            onDisconnect={() => {
                                toast('Disconnected!', {
                                    position: "top-right",
                                    autoClose: 5000,
                                    hideProgressBar: false,
                                    closeOnClick: true,
                                    pauseOnHover: true,
                                    draggable: true,
                                    progress: undefined,
                                });
                            }}

                            onMessage={(msg) => {
                                var messagesCount = this.state.messagesReceivedCount;
                                messagesCount++;
                                this.setState({messagesReceivedCount: messagesCount});
                                toast(msg.message, {
                                    position: "top-right",
                                    autoClose: 30000,
                                    hideProgressBar: false,
                                    closeOnClick: true,
                                    pauseOnHover: true,
                                    draggable: true,
                                    progress: undefined,
                                });

                            }}

                            ref={(client) => {
                                this.clientRef = client
                            }}
                    />
            </div>
        )

    }

}


export default BoardCaregiverContainer;
