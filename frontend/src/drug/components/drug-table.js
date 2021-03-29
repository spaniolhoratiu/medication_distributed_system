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
        Header: 'Dosage',
        accessor: 'dosage',
    },
    {
        Header: 'Side Effects',
        accessor: 'sideEffects',
    }
];



const filters = [
    {
        accessor: 'name',
    }
];

class DrugTable extends React.Component {

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

export default DrugTable;