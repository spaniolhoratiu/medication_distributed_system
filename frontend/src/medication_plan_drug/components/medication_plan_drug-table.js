import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'ID',
        accessor: 'id',
    },
    {
        Header: 'Medication Plan Name',
        accessor: 'medicationPlanDTO.name',
    },
    {
        Header: 'Drug Name',
        accessor: 'drugDTO.name',
    },
    {
        Header: 'Start Date',
        accessor: 'startDate',
    },
    {
        Header: 'End Date',
        accessor: 'endDate',
    },
    {
        Header: 'Intake Interval',
        accessor: 'intakeInterval',
    },
    {
        Header: 'Drug ID',
        accessor: 'drugDTO.id',
    },
    {
        Header: 'Medication Plan ID',
        accessor: 'medicationPlanDTO.id',
    },
];



const filters = [
    {
        accessor: 'id',
    }
];

class MedicationPlanDrugTable extends React.Component {

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

export default MedicationPlanDrugTable;