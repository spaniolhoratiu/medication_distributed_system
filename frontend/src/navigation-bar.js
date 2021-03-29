import React from 'react'
import logo from './commons/images/icon.png';

import {
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Nav,
    Navbar,
    NavbarBrand,
    NavLink,
    UncontrolledDropdown
} from 'reactstrap';

import AuthService from './services/auth.service'
import {HOST} from "./commons/hosts";
import Button from "react-bootstrap/Button";

const textStyle = {
    color: 'white',
    textDecoration: 'none'
};


const NavigationBar = () => (
    <div>
        <Navbar color="dark" light expand="md">
            <NavbarBrand href="/">
                <img src={logo} width={"50"}
                     height={"35"} />
            </NavbarBrand>
            <Nav className="mr-auto" navbar>

                {AuthService.isDoctorLogged() ?
                <UncontrolledDropdown nav inNavbar>
                    <DropdownToggle style={textStyle} nav caret>
                       Menu
                    </DropdownToggle>
                    <DropdownMenu right >

                        <DropdownItem>
                            <NavLink href="/person">Persons</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/user">Users</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/doctor">Doctors</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/patient">Patients</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/caregiver">Caregivers</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/drug">Drugs</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                        <NavLink href="/patientCaregiver">Patient-Caregiver</NavLink>

                    </DropdownItem>


                    </DropdownMenu>
                </UncontrolledDropdown>
                    : null
                }


                {AuthService.isCaregiverLogged() ?
                    <NavLink style={textStyle} href="/board/caregiver">My board</NavLink>
                    : null
                }

                {AuthService.isPatientLogged() ?
                    <NavLink style={textStyle} href="/board/patient">My board</NavLink>
                    : null
                }


            </Nav>


            {AuthService.isLoggedIn() ? null : <NavLink href="/register"   style={textStyle} >Register</NavLink>}
            {AuthService.isLoggedIn() ? null : <NavLink href="/login"   style={textStyle} >Log In</NavLink>}

            {AuthService.isLoggedIn() ? <NavLink style={textStyle} >{AuthService.getCurrentUser().email}</NavLink> : null}
            {AuthService.isLoggedIn() ?
                <Button
                    onClick={
                        () => {
                            AuthService.logout(); // Execute whatever code you need
                            window.open(HOST.frontend_api, '_self');
                        }
                    }
                >Log out</Button>

                : null
            }




        </Navbar>
    </div>
);

export default NavigationBar
