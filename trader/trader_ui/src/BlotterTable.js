import React,{Component} from 'react';
import PropTypes from 'prop-types';
import clsx from 'clsx';
import { withStyles } from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import Paper from '@material-ui/core/Paper';
import { AutoSizer, Column, Table } from 'react-virtualized';

const styles = theme => ({
    flexContainer: {
        display: 'flex',
        alignItems: 'center',
        boxSizing: 'border-box',
    },
    tableRow: {
        cursor: 'pointer',
    },
    tableRowHover: {
        '&:hover': {
            backgroundColor: theme.palette.grey[200],
        },
    },
    tableCell: {
        flex: 1,
    },
    noClick: {
        cursor: 'initial',
    },
});

class MuiVirtualizedTable extends React.PureComponent {
    static defaultProps = {
        headerHeight: 48,
        rowHeight: 48,
    };

    getRowClassName = ({ index }) => {
        const { classes, onRowClick } = this.props;

        return clsx(classes.tableRow, classes.flexContainer, {
            [classes.tableRowHover]: index !== -1 && onRowClick != null,
        });
    };

    cellRenderer = ({ cellData, columnIndex }) => {
        const { columns, classes, rowHeight, onRowClick } = this.props;
        return (
            <TableCell
                component="div"
                className={clsx(classes.tableCell, classes.flexContainer, {
                    [classes.noClick]: onRowClick == null,
                })}
                variant="body"
                style={{ height: rowHeight }}
                align={(columnIndex != null && columns[columnIndex].numeric) || false ? 'right' : 'left'}
            >
                {cellData}
            </TableCell>
        );
    };

    headerRenderer = ({ label, columnIndex }) => {
        const { headerHeight, columns, classes } = this.props;

        return (
            <TableCell
                component="div"
                className={clsx(classes.tableCell, classes.flexContainer, classes.noClick)}
                variant="head"
                style={{ height: headerHeight }}
                align={columns[columnIndex].numeric || false ? 'right' : 'left'}
            >
                <span>{label}</span>
            </TableCell>
        );
    };

    render() {
        const { classes, columns, ...tableProps } = this.props;
        return (
            <AutoSizer>
                {({ height, width }) => (
                    <Table height={height} width={width} {...tableProps} rowClassName={this.getRowClassName}>
                        {columns.map(({ dataKey, ...other }, index) => {
                            return (
                                <Column
                                    key={dataKey}
                                    headerRenderer={headerProps =>
                                        this.headerRenderer({
                                            ...headerProps,
                                            columnIndex: index,
                                        })
                                    }
                                    className={classes.flexContainer}
                                    cellRenderer={this.cellRenderer}
                                    dataKey={dataKey}
                                    {...other}
                                />
                            );
                        })}
                    </Table>
                )}
            </AutoSizer>
        );
    }
}

MuiVirtualizedTable.propTypes = {
    classes: PropTypes.object.isRequired,
    columns: PropTypes.arrayOf(PropTypes.object).isRequired,
    headerHeight: PropTypes.number,
    onRowClick: PropTypes.func,
    rowHeight: PropTypes.number,
};

const VirtualizedTable = withStyles(styles)(MuiVirtualizedTable);

// ---


function createData(tradeID, product, period, price, quantity, I_trader, I_company,I_side,C_trader,C_company,C_side) {
    return {tradeID, product, period, price, quantity, I_trader, I_company,I_side,C_trader,C_company,C_side};
}


class BlotterTable extends Component {
    constructor(props){
        super(props);
    }
    render() {

        /*for (let i = 0; i < this.props.data.length; i += 1) {
            rows.push(createData(this.props.data[i][0],this.props.data[i][1],this.props.data[i][2],this.props.data[i][3],this.props.data[i][4],
                this.props.data[i][5],this.props.data[i][6],this.props.data[i][7],this.props.data[i][8],this.props.data[i][9],this.props.data[i][10],this.props.data[i][11]));
        }*/
        let rows = this.props.data;
        return (
            <Paper style={{height: 400, width: '100%'}}>
                <VirtualizedTable
                    rowCount={rows.length}
                    rowGetter={({index}) => rows[index]}
                    columns={[
                        {
                            width: 190,
                            label: 'TradeID',
                            dataKey: 'id',
                        },
                        {
                            width: 90,
                            label: 'Product',
                            dataKey: 'productid',
                        },
                        {
                            width: 100,
                            label: 'Period',
                            dataKey: 'period',
                        },
                        {
                            width: 60,
                            label: 'Price',
                            dataKey: 'price',
                        },
                        {
                            width: 60,
                            label: 'Qty',
                            dataKey: 'quantity',
                        },
                        {
                            width: 140,
                            label: 'Trader',
                            dataKey: 'initTrader',
                        },
                        {
                            width: 140,
                            label: 'Company',
                            dataKey: 'initCompany',
                        },
                        {
                            width: 60,
                            label: 'Side',
                            dataKey: 'initSide',
                        },
                        {
                            width: 140,
                            label: 'Trader',
                            dataKey: 'compTrader',
                        },
                        {
                            width: 140,
                            label: 'Company',
                            dataKey: 'compCompany',
                        },
                        {
                            width: 60,
                            label: 'Side',
                            dataKey: 'compSide',
                        },
                    ]}
                />
            </Paper>
        );
    }
}

export default BlotterTable;