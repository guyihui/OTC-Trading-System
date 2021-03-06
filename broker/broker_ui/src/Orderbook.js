import React,{Component} from 'react';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';

const StyledTableCell = withStyles(theme => ({
    head: {
        backgroundColor: "#01536e",
        color: theme.palette.common.white,
        fontSize: 16,
    },
    body: {
        fontSize: 16,
    },
}))(TableCell);

const StyledTableRow = withStyles(theme => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.background.default,
        },
    },
}))(TableRow);

function createData(sellLevel, sellAmount, sellOrders, price, buyOrders, buyAmount, buyLevel) {
    return { sellLevel, sellAmount, sellOrders, price, buyOrders, buyAmount, buyLevel };
}

const buyList = [
    {price:1260,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:150,totalQuantity:150,company:"Morgan Stanley"}]},
    {price:1251,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1179,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:52,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:51,totalQuantity:100,company:"Smith"}]},
    {price:1177,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1176,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1150,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1145,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1144,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1135,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]}];
const sellList = [
    {price:1235,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:103,totalQuantity:130,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1251,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:80,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1266,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:3,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1268,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1270,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1350,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1399,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]},
    {price:1400,orders:[{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"},{remainingQuantity:50,totalQuantity:100,company:"Morgan Stanley"}]}];

function createOrderList(buyList, sellList) {
    let orderll = {};
    if(sellList!=null) {
        for (let i = 0; i < sellList.length; i++) {
            let amount = 0;
            for (let j = 0; j < sellList[i].orderList.length; j++) {
                amount += sellList[i].orderList[j].remainingQuantity;
            }
            orderll[sellList[i].price] = [amount, sellList[i].orderList, i + 1, null, null, null];
        }
    }
    if(buyList!=null) {
        for (let i = 0; i < buyList.length; i++) {
            if (orderll.hasOwnProperty(buyList[i].price)) {
                let amount = 0;
                for (let j = 0; j < buyList[i].orderList.length; j++) {
                    amount += buyList[i].orderList[j].remainingQuantity;
                }
                orderll[buyList[i].price][3] = amount;
                orderll[buyList[i].price][4] = buyList[i].orderList;
                orderll[buyList[i].price][5] = i + 1;
            }
            else {
                let amount = 0;
                for (let j = 0; j < buyList[i].orderList.length; j++) {
                    amount += buyList[i].orderList[j].remainingQuantity;
                }
                orderll[buyList[i].price] = [null, null, null, amount, buyList[i].orderList, i + 1];
            }
        }
    }
    console.log(orderll);
    return orderll;
}

function createOrderbook(orderList){
    let orderBook = [];
    for(let key in orderList){
        orderBook.push(createData(orderList[key][2],orderList[key][0],orderList[key][1],key,orderList[key][4],orderList[key][3],orderList[key][5]));
    }
    orderBook.reverse();
    return orderBook;
}


const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
        overflowX: 'auto',
    },
    table: {
        minWidth: 700,
    },
}));

class Orderbook extends Component{
    constructor(props){
        super(props);

        this.state = {
            open:false,
            price:0,
            sellOrders:[],
            buyOrders:[],
        }
        this.handleClickOpen = this.handleClickOpen.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    handleClickOpen(price,sellOrders,buyOrders) {
        this.setState({
            open:true,
            price:price,
            sellOrders:sellOrders,
            buyOrders:buyOrders,
        });

    }

    handleClose() {
        this.setState({
            open:false,
        });
    }

    render() {
        const classes = useStyles;
        const rows = createOrderbook(createOrderList(this.props.buyList,this.props.sellList));
        console.log(rows);
        return (
            <Paper className={classes.root}>
                <Table className={classes.table}>
                    <TableHead>
                        <TableRow>
                            <StyledTableCell style={{textAlign: "center",width:'20%'}}>Sell Level</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center",width:'20%'}}>Sell Amount</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center",width:'20%'}}>Price</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center",width:'20%'}}>Buy Amount</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center",width:'20%'}}>Buy Level</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows.map(row => (
                            <StyledTableRow key={row.name}>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400,
                                    width:'20%'
                                }}>{row.sellLevel}</StyledTableCell>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400,
                                    width:'20%'
                                }}>{row.sellAmount}</StyledTableCell>
                                <StyledTableCell
                                    onClick={() => this.handleClickOpen(row.price, row.sellOrders, row.buyOrders)} style={{
                                    textAlign: "center",
                                    cursor: "pointer",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400,
                                    width:'20%'
                                }}>{row.price}</StyledTableCell>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400,
                                    width:'20%'
                                }}>{row.buyAmount}</StyledTableCell>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400,
                                    width:'20%'
                                }}>{row.buyLevel}</StyledTableCell>
                            </StyledTableRow>
                        ))}
                    </TableBody>
                </Table>
                <Dialog
                    open={this.state.open}
                    onClose={this.handleClose}
                    scroll="paper"
                    aria-labelledby="scroll-dialog-title"
                >
                    <DialogTitle id="scroll-dialog-title">当前价格:&ensp;{this.state.price}</DialogTitle>
                    <DialogContent dividers={true}>
                        <Grid container spacing={1}>
                            <Grid item xs={6}>
                                <List subheader={<ListSubheader>Sell</ListSubheader>}>
                                    <ListItem>
                                        <Grid container spacing={1}>
                                            <Grid item xs={4}>
                                                余量
                                            </Grid>
                                            <Grid item xs={4}>
                                                总量
                                            </Grid>
                                            <Grid item xs={4}>
                                                公司
                                            </Grid>
                                        </Grid>
                                    </ListItem>
                                    {this.state.sellOrders != null ? this.state.sellOrders.map((order, index) => (
                                        <ListItem>
                                            <Grid container spacing={1}>
                                                <Grid item xs={4}>
                                                    {order.remainingQuantity}
                                                </Grid>
                                                <Grid item xs={4}>
                                                    {order.totalQuantity}
                                                </Grid>
                                                <Grid item xs={4}>
                                                    {order.company}
                                                </Grid>
                                            </Grid>
                                        </ListItem>
                                    )) : []}
                                </List>
                            </Grid>
                            <Grid item xs={6}>
                                <List subheader={<ListSubheader>Buy</ListSubheader>}>
                                    <ListItem>
                                        <Grid container spacing={1}>
                                            <Grid item xs={4}>
                                                余量
                                            </Grid>
                                            <Grid item xs={4}>
                                                总量
                                            </Grid>
                                            <Grid item xs={4}>
                                                公司
                                            </Grid>
                                        </Grid>
                                    </ListItem>
                                    {this.state.buyOrders != null ? this.state.buyOrders.map((order, index) => (
                                        <ListItem>
                                            <Grid container spacing={1}>
                                                <Grid item xs={4}>
                                                    {order.remainingQuantity}
                                                </Grid>
                                                <Grid item xs={4}>
                                                    {order.totalQuantity}
                                                </Grid>
                                                <Grid item xs={4}>
                                                    {order.company}
                                                </Grid>
                                            </Grid>
                                        </ListItem>
                                    )) : []}
                                </List>
                            </Grid>

                        </Grid>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            关闭
                        </Button>
                    </DialogActions>
                </Dialog>

            </Paper>
        );
    }
}

export default Orderbook;