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
    {price:1260,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:150,totalAmount:150,company:"Morgan Stanley"}]},
    {price:1251,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1179,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:52,totalAmount:100,company:"Morgan Stanley"},{lastAmount:51,totalAmount:100,company:"Smith"}]},
    {price:1177,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1176,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1150,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1145,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1144,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1135,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}];
const sellList = [
    {price:1235,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:103,totalAmount:130,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1251,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:80,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1266,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:3,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1268,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1270,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1350,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1399,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
    {price:1400,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}];

function createOrderList(buyList, sellList) {
    let orderList = {};
    for(let i=0;i<sellList.length;i++){
        let amount = 0;
        for(let j=0;j<sellList[i].orders.length;j++){
            amount += sellList[i].orders[j].lastAmount;
        }
        orderList[sellList[i].price] = [amount,sellList[i].orders,i+1,null,null,null];
    }
    for(let i=0;i<buyList.length;i++){
        if(orderList.hasOwnProperty(buyList[i].price)){
            let amount = 0;
            for(let j=0;j<buyList[i].orders.length;j++){
                amount += buyList[i].orders[j].lastAmount;
            }
            orderList[buyList[i].price][3] = amount;
            orderList[buyList[i].price][4] = buyList[i].orders;
            orderList[buyList[i].price][5] = i+1;
        }
        else{
            let amount = 0;
            for(let j=0;j<buyList[i].orders.length;j++){
                amount += buyList[i].orders[j].lastAmount;
            }
            orderList[buyList[i].price] = [null,null,null,amount,buyList[i].orders,i+1];
        }
    }
    console.log(orderList);
    return orderList;
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
                            <StyledTableCell style={{textAlign: "center"}}>Sell Level</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center"}}>Sell Amount</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center"}}>Price</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center"}}>Buy Amount</StyledTableCell>
                            <StyledTableCell style={{textAlign: "center"}}>Buy Level</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows.map(row => (
                            <StyledTableRow key={row.name}>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400
                                }}>{row.sellLevel}</StyledTableCell>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400
                                }}>{row.sellAmount}</StyledTableCell>
                                <StyledTableCell
                                    onClick={() => this.handleClickOpen(row.price, row.sellOrders, row.buyOrders)} style={{
                                    textAlign: "center",
                                    cursor: "pointer",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400
                                }}>{row.price}</StyledTableCell>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400
                                }}>{row.buyAmount}</StyledTableCell>
                                <StyledTableCell style={{
                                    textAlign: "center",
                                    fontSize: (row.sellLevel === 1 || row.buyLevel === 1) ? 20 : 16,
                                    fontWeight: (row.sellLevel === 1 || row.buyLevel === 1) ? 700 : 400
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
                                                    {order.lastAmount}
                                                </Grid>
                                                <Grid item xs={4}>
                                                    {order.totalAmount}
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
                                                    {order.lastAmount}
                                                </Grid>
                                                <Grid item xs={4}>
                                                    {order.totalAmount}
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