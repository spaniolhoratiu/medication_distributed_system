import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'Id',
        accessor: 'patientId',
    },
    {
        Header: 'Name',
        accessor: 'patientName',
    },

    {
        Header: 'Birth Date',
        accessor: 'patientBirthDate',
    },
    {
        Header: 'Gender',
        accessor: 'patientGender',
    },
    {
        Header: 'Address',
        accessor: 'patientAddress',
    },
    {
        Header: 'Medical Record',
        accessor: 'patientMedicalRecord',
    }
];

const filters = [
    {
        accessor: 'patientName',
    }
];

class PatientsForCaregiverTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData
        };
    }

    render() {
        return (
            <Table
                data={this.state.tableData}
                columns={columns}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default PatientsForCaregiverTable;