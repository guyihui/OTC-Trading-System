import React,{Component} from 'react';
import PropTypes from 'prop-types';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableFooter from '@material-ui/core/TableFooter';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import IconButton from '@material-ui/core/IconButton';
import FirstPageIcon from '@material-ui/icons/FirstPage';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';
import LastPageIcon from '@material-ui/icons/LastPage';
import TableHead from '@material-ui/core/TableHead';
import Grid from '@material-ui/core/Grid';
import Fab from '@material-ui/core/Fab';
import CancelIcon from '@material-ui/icons/ClearRounded';

const useStyles1 = makeStyles(theme => ({
    root: {
        flexShrink: 0,
        color: theme.palette.text.secondary,
        marginLeft: theme.spacing(2.5),
    },
}));

function TablePaginationActions(props) {
    const classes = useStyles1();
    const theme = useTheme();
    const { count, page, rowsPerPage, onChangePage } = props;

    function handleFirstPageButtonClick(event) {
        onChangePage(event, 0);
    }

    function handleBackButtonClick(event) {
        onChangePage(event, page - 1);
    }

    function handleNextButtonClick(event) {
        onChangePage(event, page + 1);
    }

    function handleLastPageButtonClick(event) {
        onChangePage(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
    }

    return (
        <div className={classes.root}>
            <IconButton
                onClick={handleFirstPageButtonClick}
                disabled={page === 0}
                aria-label="First Page"
            >
                {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
            </IconButton>
            <IconButton onClick={handleBackButtonClick} disabled={page === 0} aria-label="Previous Page">
                {theme.direction === 'rtl' ? <KeyboardArrowRight /> : <KeyboardArrowLeft />}
            </IconButton>
            <IconButton
                onClick={handleNextButtonClick}
                disabled={page >= Math.ceil(count / rowsPerPage) - 1}
                aria-label="Next Page"
            >
                {theme.direction === 'rtl' ? <KeyboardArrowLeft /> : <KeyboardArrowRight />}
            </IconButton>
            <IconButton
                onClick={handleLastPageButtonClick}
                disabled={page >= Math.ceil(count / rowsPerPage) - 1}
                aria-label="Last Page"
            >
                {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
            </IconButton>
        </div>
    );
}

TablePaginationActions.propTypes = {
    count: PropTypes.number.isRequired,
    onChangePage: PropTypes.func.isRequired,
    page: PropTypes.number.isRequired,
    rowsPerPage: PropTypes.number.isRequired,
};

function createData(orderId, product, period, sellOrBuy, price, quantity, type) {
    return { orderId, product, period, sellOrBuy, price, quantity, type };
}

const rows = [
    createData(123, "黄金期货", "7月1日","buy",1050,200,"limit"),
    createData(124, "黄金期货", "8月1日","buy",1051,30,"limit"),
    createData(125, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(126, "石油期货", "7月1日","buy",1050,200,"stop"),
    createData(127, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(128, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(129, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(130, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(131, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(132, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(133, "石油期货", "7月1日","buy",1050,200,"limit"),
    createData(134, "石油期货", "7月1日","buy",1050,200,"limit"),
].sort((a, b) => (a.calories < b.calories ? -1 : 1));

const useStyles2 = makeStyles(theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    table: {
        minWidth: 500,
    },
    tableWrapper: {
        overflowX: 'auto',
    },
}));

class MyOrderTable extends Component {
    constructor(props){
        super(props);

        this.state = {
            page:0,
            rowsPerPage:5,
        };

        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
        this.handleCancelOrder = this.handleCancelOrder.bind(this);
    }


    handleChangePage(event, newPage) {
        this.setState({page:newPage});
    }

    handleChangeRowsPerPage(event) {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)});
    }

    handleCancelOrder(row){
        // let xmlHttp = new XMLHttpRequest();
        // xmlHttp.open("GET", "http://localhost:8082/getBlotter?productId="+this.state.productId+"&startTime="+this.state.selectedStartDate.getTime()+"&endTime="+this.state.selectedEndDate.getTime()+"&traderName="+"Alice", true);
        // xmlHttp.setRequestHeader("Content-Type", "application/json");
        // xmlHttp.onreadystatechange = () => {
        //     if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
        //         let data=JSON.parse(xmlHttp.responseText);
        //         console.log(data);
        //         this.setState({data:data});
        //     }
        // };
        // xmlHttp.send();
    }

    render() {
        const classes = useStyles2;
        const emptyRows = this.state.rowsPerPage - Math.min(this.state.rowsPerPage, rows.length - this.state.page * this.state.rowsPerPage);
        return (
            <Paper className={classes.root}>
                <div className={classes.tableWrapper}>
                    <Table className={classes.table} style={{marginBottom:30}}>
                        <TableHead>
                            <TableRow>
                                <TableCell >OrderID</TableCell>
                                <TableCell align="right">商品名称</TableCell>
                                <TableCell align="right" >日期</TableCell>
                                <TableCell align="right" >买/卖</TableCell>
                                <TableCell align="right" >价格</TableCell>
                                <TableCell align="right" >数量</TableCell>
                                <TableCell align="right" >订单种类</TableCell>
                                <TableCell align="right" >取消订单</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows.slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage).map(row => (
                                <TableRow key={row.orderId}>
                                    <TableCell component="th" scope="row">
                                        {row.orderId}
                                    </TableCell>
                                    <TableCell align="right" >{row.product}</TableCell>
                                    <TableCell align="right">{row.period}</TableCell>
                                    <TableCell align="right">{row.sellOrBuy}</TableCell>
                                    <TableCell align="right">{row.price}</TableCell>
                                    <TableCell align="right">{row.quantity}</TableCell>
                                    <TableCell align="right">{row.type}</TableCell>
                                    <TableCell align="right">
                                        <IconButton size="small" color="secondary" aria-label="Add" onClick={()=>this.handleCancelOrder(row)} className={classes.margin}>
                                            <CancelIcon />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}

                            {emptyRows > 0 && (
                                <TableRow style={{height: 48 * emptyRows}}>
                                    <TableCell colSpan={6}/>
                                </TableRow>
                            )}
                        </TableBody>
                        <TableFooter>
                            <TableRow>
                                <TablePagination
                                    rowsPerPageOptions={[5, 10, 25]}
                                    colSpan={3}
                                    count={rows.length}
                                    rowsPerPage={this.state.rowsPerPage}
                                    page={this.state.page}
                                    SelectProps={{
                                        inputProps: {'aria-label': 'Rows per page'},
                                        native: true,
                                    }}
                                    onChangePage={this.handleChangePage}
                                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                                    ActionsComponent={TablePaginationActions}
                                />
                            </TableRow>
                        </TableFooter>
                    </Table>
                </div>
            </Paper>
        );
    }
}

export default MyOrderTable;