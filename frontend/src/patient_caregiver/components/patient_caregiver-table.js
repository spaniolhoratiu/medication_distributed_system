import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'Patient ID',
        accessor: 'patientId',
    },
    {
        Header: 'Patient Name',
        accessor: 'patientName',
    },
    {
        Header: 'Caregiver ID',
        accessor: 'caregiverId',
    },
    {
        Header: 'Caregiver Name',
        accessor: 'caregiverName'
    }
];


const filters = [
    {
        accessor: 'name',
    }
];

class PatientCaregiverTable extends React.Component {

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

export default PatientCaregiverTable;