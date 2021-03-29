import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'ID',
        accessor: 'id',
    },
    {
        Header: 'Email',
        accessor: 'email',
    },
    {
        Header: 'Password',
        accessor: 'password',
    },
    {
        Header: 'Role',
        accessor: 'role',
    },
    /*
    {
        Header: 'Edit',
        accessor: 'edit',
        Cell: <button>Edit</button>
    },
    {
        Header: 'Delete',
        accessor: 'delete',
        Cell: <button>Delete</button>
    }
     */
];



const filters = [
    {
        accessor: 'email',
    }
];

class UserTable extends React.Component {

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

export default UserTable;