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
        Header: 'Birth Date',
        accessor: 'birthDate',
    },
    {
        Header: 'Gender',
        accessor: 'gender',
    },
    {
        Header: 'Address',
        accessor: 'address',
    },
    {
        Header: 'User ID',
        accessor: 'userDTO.id',
    }
];



const filters = [
    {
        accessor: 'name',
    }
];

class DoctorTable extends React.Component {

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

export default DoctorTable;