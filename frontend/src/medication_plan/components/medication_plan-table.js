import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'ID',
        accessor: 'id',
    },
    {
        Header: 'Name',
        accessor: 'name',
    },
    {
        Header: 'Patient Name',
        accessor: 'patientDTO.name',
    },
    {
        Header: 'Patient ID',
        accessor: 'patientDTO.id',
    }


];

const filters = [
    {
        accessor: 'name'
    }
];

class MedicationPlanTable extends React.Component {

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

export default MedicationPlanTable;