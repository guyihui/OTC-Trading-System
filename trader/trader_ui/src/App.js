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
import Blotter from './Blotter';
import SendOrder from './SendOrder';
import goldPic from './SvgIcon/gold.svg';
import cerealPic from './SvgIcon/cereal.svg';
import barrelPic from './SvgIcon/barrel.svg';
import firePic from './SvgIcon/fire.svg';

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
}));

// let ws = new WebSocket("ws://localhost:8080/WebSocket");

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

function App() {
    const classes = useStyles();

    const [selectedIndex, setSelectedIndex] = React.useState(0);
    const [values, setValues] = React.useState(0);
    const [orders, setOrders] = React.useState(
        {
            buyList: [],
            sellList:[],
        });

    // ws.onopen = function() {
    //     console.log("open");
    //     ws.send(JSON.stringify(msg[selectedIndex%2]));
    // };
    //
    // ws.onmessage = function(evt) {
    //     console.log(evt.data);
    //     setOrders(JSON.parse(evt.data));
    // };
    //
    // ws.onclose = function(evt) {
    //     console.log("WebSocketClosed!");
    // };
    //
    // ws.onerror = function(evt)
    // {
    //     console.log("WebSocketError!");
    // };

    function handleListItemClick(event, index) {
        // ws.close();
        setSelectedIndex(index);
        //TODO:选择时间改state
        setValues(0);
        // ws = new WebSocket("ws://localhost:8080/WebSocket");
        //ws.send(msg[index%2].toString());
    }

    function handleChange(event) {
        setValues(event.target.value);
        //console.log(event.target.value);
    }

    const [state, setState] = React.useState({
        checkedB: false,
    });

    const handleOrderTypeChange = name => event => {
        setState({ ...state, [name]: event.target.checked });
    };

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar position="fixed" className={classes.appBar}>
                <Toolbar>
                    <Typography variant="h6" noWrap>
                        期货交易所
                    </Typography>
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
                        <SendOrder productId={msg[selectedIndex].detail[values].productId} productName={msg[selectedIndex].productDisplayName} productPeriod={msg[selectedIndex].detail[values].productPeriod}/>
                    </div>
                }
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
