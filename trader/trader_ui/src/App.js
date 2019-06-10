import React,{Fragment} from 'react';
import logo from './logo.svg';
import './App.css';

import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import MailIcon from '@material-ui/icons/Mail';
import SvgIcon from '@material-ui/core/SvgIcon';
import Grid from '@material-ui/core/Grid';
import MaterialTable from 'material-table';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import Snackbar from '@material-ui/core/Snackbar';
import SnackbarContent from '@material-ui/core/SnackbarContent';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import ErrorIcon from '@material-ui/icons/Error';
import InfoIcon from '@material-ui/icons/Info';
import CloseIcon from '@material-ui/icons/Close';
import green from '@material-ui/core/colors/green';
import amber from '@material-ui/core/colors/amber';
import IconButton from '@material-ui/core/IconButton';
import ExitToAppIcon from '@material-ui/icons/ExitToAppOutlined';
import WarningIcon from '@material-ui/icons/Warning';
import CompareArrowsIcon from '@material-ui/icons/CompareArrowsOutlined';
import PropTypes from 'prop-types';
import clsx from 'clsx';
import Cookies from 'js-cookie';
import Blotter from './Blotter';
import SendOrder from './SendOrder';
import goldPic from './SvgIcon/gold.svg';
import cerealPic from './SvgIcon/cereal.svg';
import barrelPic from './SvgIcon/barrel.svg';
import firePic from './SvgIcon/fire.svg';
import {browserHistory} from "react-router";

const drawerWidth = 240;

const pics = [goldPic,barrelPic,firePic,cerealPic];

const orders =
    [
        {
            buyOrders:[
                {price:1260,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:150,totalAmount:150,company:"Morgan Stanley"}]},
                {price:1251,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1179,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:52,totalAmount:100,company:"Morgan Stanley"},{lastAmount:51,totalAmount:100,company:"Smith"}]},
                {price:1177,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1176,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1150,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1145,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1144,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1135,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}],
            sellOrders:[
                {price:1235,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:103,totalAmount:130,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1251,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:80,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1266,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:3,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1268,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1270,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1350,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1399,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1400,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}]
        },
        {
            buyOrders:[
                {price:1176,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1150,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1145,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1144,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1135,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}],
            sellOrders:[
                {price:1268,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1270,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1350,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1399,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1400,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}]
        },
        {
            buyOrders:[
                {price:1176,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1150,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1145,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1135,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}],
            sellOrders:[
                {price:1268,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1350,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1399,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1400,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}]
        },
        {
            buyOrders:[
                {price:1145,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1144,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1135,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}],
            sellOrders:[
                {price:1350,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1399,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]},
                {price:1400,orders:[{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"},{lastAmount:50,totalAmount:100,company:"Morgan Stanley"}]}]
        }];

//const dates = [["7月1日","8月1日"],["7月5日","8月30日"],["6月12日","8月1日"],["7月1日"]];

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        backgroundColor:"#032f63",
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
    },
    toolbar: theme.mixins.toolbar,
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
    bottomBar: {
        backgroundColor:"#242c7a",
        top: 'auto',
        height:40,
        bottom: 0,
    },
    exitButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
}));

let ws = new WebSocket("ws://localhost:30483/WebSocket");

let msg = [
    {
        productName:"gold",
        productDisplayName:"黄金期货",
        detail:[
            {
                productId:"01",
                productPeriod:"2019/07/01",
            },
            {
                productId:"02",
                productPeriod:"2019/08/02",
            }
        ]
    },
    {
        productName:"oil",
        productDisplayName:"原油期货",
        detail:[
            {
                productId:"03",
                productPeriod:"2019/06/12",
            },
            {
                productId:"04",
                productPeriod:"2019/08/22",
            },
            {
                productId:"05",
                productPeriod:"2019/08/23",
            }
        ]
    },
    {
        productName:"gas",
        productDisplayName:"天然气期货",
        detail:[
            {
                productId:"06",
                productPeriod:"2019/07/02",
            },
            {
                productId:"07",
                productPeriod:"2019/08/05",
            }
        ]
    },
    {
        productName:"wheat",
        productDisplayName:"小麦期货",
        detail:[
            {
                productId:"08",
                productPeriod:"2019/07/01",
            },
        ]
    },];

const useStyles1 = makeStyles(theme => ({
    success: {
        backgroundColor: green[600],
    },
    error: {
        backgroundColor: theme.palette.error.dark,
    },
    info: {
        backgroundColor: theme.palette.primary.dark,
    },
    warning: {
        backgroundColor: amber[700],
    },
    icon: {
        fontSize: 20,
    },
    iconVariant: {
        opacity: 0.9,
        marginRight: theme.spacing(1),
    },
    message: {
        display: 'flex',
        alignItems: 'center',
    },
}));

const variantIcon = {
    success: CheckCircleIcon,
    warning: WarningIcon,
    error: ErrorIcon,
    info: InfoIcon,
};

function MySnackbarContentWrapper(props) {
    const classes = useStyles1();
    const { className, message, onClose, variant, ...other } = props;
    const Icon = variantIcon[variant];

    return (
        <SnackbarContent
            className={clsx(classes[variant], className)}
            aria-describedby="client-snackbar"
            message={
                <span id="client-snackbar" className={classes.message}>
          <Icon className={clsx(classes.icon, classes.iconVariant)} />
                    {message}
        </span>
            }
            action={[
                <IconButton key="close" aria-label="Close" color="inherit" onClick={onClose}>
                    <CloseIcon className={classes.icon} />
                </IconButton>,
            ]}
            {...other}
        />
    );
}

function App() {
    const classes = useStyles();

    const [selectedIndex, setSelectedIndex] = React.useState(0);
    const [values, setValues] = React.useState(0);
    const [orders, setOrders] = React.useState(
        {
            buyList: [],
            sellList:[],
        });
    const [successOpen, setSuccessOpen] = React.useState(false);
    const [failOpen, setFailOpen] = React.useState(false);
    const [sellDepth, setSellDepth] = React.useState({depth:"0",flag:1});
    const [buyDepth, setBuyDepth] = React.useState({depth:"0",flag:1});
    const [message, setMessage] = React.useState("");
    const [failMessage, setFailMessage] = React.useState("");
    const [processingOrders, setProcessingOrders] = React.useState([]);

    if(Cookies.get("socketFlag")==="1"){
        if(ws!==null){
            ws.close();
        }
        ws = new WebSocket("ws://localhost:30483/WebSocket");
        Cookies.set("socketFlag","0");
    }

    ws.onopen = function() {
        console.log("open");
        let msgData={
            productId:msg[selectedIndex].detail[values].productId,
            productName:msg[selectedIndex].productName,
            productPeriod:msg[selectedIndex].detail[values].productPeriod,
            traderName:Cookies.get('username'),
            broker:Cookies.get('broker'),
        };
        ws.send(JSON.stringify(msgData));
    };

    ws.onmessage = function(evt) {
        console.log(evt.data);
        let data=JSON.parse(evt.data);
        if(data.type === "depth") {
            if(data.sell!=='-1'&&data.sell!==null) {
                if(parseInt(data.sell)>parseInt(sellDepth.depth)){
                    setSellDepth({depth:data.sell,flag:2});
                }
                else if(parseInt(data.sell)===parseInt(sellDepth.depth)){
                    setSellDepth({depth:data.sell,flag:1});
                }
                else{
                    setSellDepth({depth:data.sell,flag:0});
                }
            }
            else{
                setSellDepth({depth:"0",flag:1});
            }
            if(data.buy!=='-1'&&data.buy!==null) {
                if(parseInt(data.buy)>parseInt(buyDepth.depth)){
                    setBuyDepth({depth:data.buy,flag:2});
                }
                else if(parseInt(data.buy)===parseInt(buyDepth.depth)){
                    setBuyDepth({depth:data.buy,flag:1});
                }
                else{
                    setBuyDepth({depth:data.buy,flag:0});
                }
            }
            else{
                setBuyDepth({depth:"0",flag:1});
            }

        }
        else if(data.type==='state'){
            let stateData = JSON.parse(evt.data);
            console.log(stateData);
            let data = JSON.parse(stateData.orders);
            console.log(data);
            if(data!==null) {
                setProcessingOrders(data);
            }
            else{
                setProcessingOrders([]);
            }
        }
        else if(data.type==='cancelSuccess'){
            console.log(data.order);
            let canceledOrder = JSON.parse(data.order);
            console.log(canceledOrder);
            let succMsg =
                <div>
                    <Typography variant={'subtitle1'}>
                        Canceled OrderID:&nbsp;{canceledOrder.orderId}
                    </Typography>
                    <Typography variant={'subtitle1'}>
                        ProductName:&nbsp;{canceledOrder.product.productName},&nbsp;ProductPeriod:&nbsp;{canceledOrder.product.productPeriod}
                    </Typography>
                    <Typography variant={'subtitle1'}>
                        Side:&nbsp;{canceledOrder.sellOrBuy},&nbsp;OrderType:&nbsp;{canceledOrder.sellOrBuy}
                    </Typography>
                    <Typography variant={'subtitle1'}>
                        TotalQuantity:&nbsp;{canceledOrder.totalQuantity},&nbsp;RemainingQuantity:&nbsp;{canceledOrder.remainingQuantity}
                    </Typography>
                </div>;
            // let successMsg = "Canceled OrderID: "+ canceledOrder.orderId+ "\nProductName: "+canceledOrder.product.productName+" ProductPeriod: "+canceledOrder.product.productPeriod
            // +"\nSide: "+ canceledOrder.sellOrBuy+" OrderType: "+canceledOrder.orderType+"\nTotalAmount: "+canceledOrder.totalQuantity+" CanceledAmount: "+canceledOrder.remainingQuantity;
            setMessage(succMsg);
            setSuccessOpen(true);
        }
        else if(data.type==='cancelFailure'){
            console.log(data.order);
            let canceledOrder = JSON.parse(data.order);
            console.log(canceledOrder);
            let failMsg =
                <div>
                    <Typography variant={'subtitle1'}>
                        Cancel failed
                    </Typography>
                    <Typography variant={'subtitle1'}>
                        OrderID:&nbsp;{canceledOrder.orderId}
                    </Typography>
                </div>;
            setFailMessage(failMsg);
            setFailOpen(true);
        }
        // setOrders(JSON.parse(evt.data));
    };

    ws.onclose = function(evt) {
        console.log("WebSocketClosed!");
    };

    ws.onerror = function(evt)
    {
        console.log("WebSocketError!");
    };

    function handleListItemClick(event, index) {
        ws.close();
        setSelectedIndex(index);
        //TODO:选择时间改state
        setValues(0);
        setSellDepth({depth:"0",flag:1});
        setBuyDepth({depth:"0",flag:1});
        setProcessingOrders([]);
        ws = new WebSocket("ws://localhost:30483/WebSocket");
        //ws.send(msg[index%2].toString());
    }

    function handleChange(event) {
        ws.close();
        setValues(event.target.value);
        setSellDepth({depth:"0",flag:1});
        setBuyDepth({depth:"0",flag:1});
        setProcessingOrders([]);
        ws = new WebSocket("ws://localhost:30483/WebSocket");
        //console.log(event.target.value);
    }

    function handleLogOut(event, index) {
        ws.close();
        setSellDepth({depth:"0",flag:1});
        setBuyDepth({depth:"0",flag:1});
        browserHistory.push({
            pathname:'/',
        });
        // ws = new WebSocket("ws://localhost:8082/WebSocket");
        //ws.send(msg[index%2].toString());
    }

    function handleSuccessClose(event, reason) {
        if (reason === 'clickaway') {
            return;
        }

        setSuccessOpen(false);
    }

    function handleFailClose(event, reason) {
        if (reason === 'clickaway') {
            return;
        }

        setFailOpen(false);
    }

    const [state, setState] = React.useState({
        checkedB: false,
    });

    const handleOrderTypeChange = name => event => {
        setState({ ...state, [name]: event.target.checked });
    };

    const mmssgg = <h3>asdasxsad</h3>;
    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar position="fixed" className={classes.appBar}>
                <Toolbar>
                    <Typography edge="start" className={classes.title} variant="h6" noWrap>
                        期货交易所
                    </Typography>
                    <Typography variant="subtitle1" noWrap>
                        Trader:&nbsp;{Cookies.get('username')}
                    </Typography>
                    &ensp;<CompareArrowsIcon />&ensp;
                    <Typography variant="subtitle1" noWrap>
                        Broker:&nbsp;{Cookies.get('broker')}
                    </Typography>
                    &ensp;
                    <IconButton
                        color="inherit"
                        aria-label="Open drawer"
                        onClick={handleLogOut}
                    >
                        <Typography variant="subtitle1" noWrap>
                            登出
                        </Typography>&nbsp;<ExitToAppIcon />
                    </IconButton>
                </Toolbar>
            </AppBar>
            <Drawer
                className={classes.drawer}
                variant="permanent"
                classes={{
                    paper: classes.drawerPaper,
                }}
            >
                <div className={classes.toolbar} />
                <List>
                    {msg.map((text, index) => (
                        <ListItem
                            button
                            key={text.productDisplayName}
                            selected={selectedIndex === index}
                            onClick={event => handleListItemClick(event, index)}>
                            {/*<ListItemIcon>{index % 2 === 0 ? <InboxIcon /> : <MailIcon />}</ListItemIcon>*/}
                            <ListItemIcon><img style={{height:50,width:50,marginRight:20,marginLeft:20}} src={pics[index]}/></ListItemIcon>
                            <ListItemText primary={text.productDisplayName} />
                        </ListItem>
                    ))}
                </List>
            </Drawer>
            <main className={classes.content}>
                <div className={classes.toolbar} />
                <Fragment>
                    <FormControl className={classes.formControl} style={{marginBottom:30}}>
                        <InputLabel htmlFor="date-helper">日期</InputLabel>
                        <Select
                            value={values}
                            onChange={handleChange}
                            input={<Input name="date" id="date-helper"/>}
                        >
                            {msg[selectedIndex].detail.map((date, index) => (
                                <MenuItem value={index}>{date.productPeriod}</MenuItem>
                            ))}
                        </Select>
                        <FormHelperText>请选择某时间段内的商品</FormHelperText>
                    </FormControl>
                    <FormControlLabel
                        control={
                            <Switch
                                checked={state.checkedB}
                                onChange={handleOrderTypeChange('checkedB')}
                                value="checkedB"
                                color="primary"
                            />
                        }
                        label="查看Blotter"
                        style={{margin:20}}
                    />
                </Fragment>
                {state.checkedB?
                    <div>
                        <Blotter productId={msg[selectedIndex].detail[values].productId} productName={msg[selectedIndex].productDisplayName} productPeriod={msg[selectedIndex].detail[values].productPeriod}/>
                    </div>
                    :
                    <div>
                        <SendOrder sellDepth={sellDepth} buyDepth={buyDepth} productId={msg[selectedIndex].detail[values].productId}
                                   productName={msg[selectedIndex].productDisplayName} productPeriod={msg[selectedIndex].detail[values].productPeriod}
                                   processingOrders={processingOrders}/>
                    </div>
                }

                <Snackbar
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'center',
                    }}
                    open={successOpen}
                    autoHideDuration={6000}
                    onClose={handleSuccessClose}
                >
                    <MySnackbarContentWrapper
                        onClose={handleSuccessClose}
                        variant="success"
                        message={message}
                    />
                </Snackbar>

                <Snackbar
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'center',
                    }}
                    open={failOpen}
                    autoHideDuration={6000}
                    onClose={handleFailClose}
                >
                    <MySnackbarContentWrapper
                        onClose={handleFailClose}
                        variant="error"
                        message={failMessage}
                    />
                </Snackbar>

                <AppBar position="fixed" color="primary" className={classes.bottomBar}>
                    <Toolbar>
                        <div></div>
                    </Toolbar>
                </AppBar>
            </main>
        </div>
    );
}


export default App;
