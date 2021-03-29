import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import NavigationBar from './navigation-bar'
import Home from './home/home';
import PersonContainer from './person/person-container'
import UserContainer from './user/user-container'
import "bootstrap/dist/css/bootstrap.min.css";

import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';

import LoginComponent from './login.component'
import BoardPatient from "./board/patient/board-patient-container";
import BoardDoctor from "./board/board-doctor.component";
import RegisterComponent from "./register.component"
import ProfileComponent from "./profile.component"
import DoctorContainer from "./doctor/doctor-container";
import PatientContainer from "./patient/patient-container";
import CaregiverContainer from "./caregiver/caregiver-container";
import DrugContainer from "./drug/drug-container";
import PatientCaregiverContainer from "./patient_caregiver/patient_caregiver-container";


import AuthService from './services/auth.service'
import BoardCaregiverContainer from "./board/caregiver/board-caregiver-container";

class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            showPatientBoard: false,
            showCaregiverBoard: false,
            showDoctorBoard: false,
            currentUser: undefined,
            loggedIn: false
        };
    }
    /*
                            {AuthService.isCaregiverLogged() ? <Route
                            exact
                            path='/board/caregiver'
                            render={() => <BoardCaregiverContainer/>}
                             />
                        : null }
                        */


    render() {

        return (
            <div className={styles.back}>
            <Router>
                <div>
                    <NavigationBar />
                    <Switch>

                        <Route
                            exact
                            path='/'
                            render={() => <Home/>}
                        />

                        { AuthService.isLoggedIn() ? null :
                            <Route
                                exact
                                path='/login'
                                render={() => <LoginComponent/>}
                            />
                        }


                        { AuthService.isLoggedIn() ? null : <Route
                            exact
                            path='/register'
                            render={() => <RegisterComponent/>}
                        />}


                        <Route
                            exact
                            path='/profile'
                            render={() => <ProfileComponent/>}
                        />

                        {AuthService.isPatientLogged() ? <Route
                                exact
                                path='/board/patient'
                                render={() => <BoardPatient/>}
                            />
                            : null }

                        {AuthService.isCaregiverLogged() ? <Route
                                exact
                                path='/board/caregiver'
                                render={() => <BoardCaregiverContainer/>}
                            />
                            : null }

                        {AuthService.isDoctorLogged() ? <Route
                            exact
                            path='/board/doctor'
                            render={() => <BoardDoctor/>}
                            />
                            : null }


                        <Route
                            exact
                            path='/person'
                            render={() => <PersonContainer/>}
                        />

                        {AuthService.isDoctorLogged() ?
                            <Route
                                exact
                                path='/user'
                                render={() => <UserContainer/>}
                            />
                            : null
                        }


                        {AuthService.isDoctorLogged() ?
                            <Route
                                exact
                                path='/doctor'
                                render={() => <DoctorContainer/>}
                            />
                            : null
                        }

                        {AuthService.isDoctorLogged() ?
                        <Route
                            exact
                            path='/patient'
                            render={() => <PatientContainer/>}
                            />
                        : null
                        }

                        {AuthService.isDoctorLogged() ?
                            <Route
                            exact
                            path='/caregiver'
                            render={() => <CaregiverContainer/>}
                        />
                         : null
                        }

                        {AuthService.isDoctorLogged() ?
                            <Route
                                exact
                                path='/drug'
                                render={() => <DrugContainer/>}
                            />
                            : null
                        }

                        {AuthService.isDoctorLogged() ?
                        <Route
                            exact
                            path='/patientCaregiver'
                            render={() => <PatientCaregiverContainer/>}
                        />
                        : null
                        }

                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage/>}
                        />

                        <Route render={() =><ErrorPage/>} />
                    </Switch>
                </div>
            </Router>
            </div>
        )
    };
}

export default App
