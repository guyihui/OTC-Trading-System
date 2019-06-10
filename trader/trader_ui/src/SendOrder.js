import 'date-fns';
import React,{Component,Fragment} from 'react';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import ArrowDownWardIcon from '@material-ui/icons/ArrowDownward';
import ArrowUpWardIcon from '@material-ui/icons/ArrowUpward';
import WarningIcon from '@material-ui/icons/WarningOutlined';
import Schedule from '@material-ui/icons/Schedule';
import List from '@material-ui/core/List';
import Paper from '@material-ui/core/Paper';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Select from '@material-ui/core/Select';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import Switch from '@material-ui/core/Switch';
import Checkbox from '@material-ui/core/Checkbox';
import MenuItem from '@material-ui/core/MenuItem';
import LineStyleIcon from '@material-ui/icons/LineStyleRounded';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import {
    DatePicker,
    TimePicker,
    DateTimePicker,
    MuiPickersUtilsProvider,
} from "@material-ui/pickers";
import Button from '@material-ui/core/Button';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import MaskedInput from 'react-text-mask';
import DateFnsUtils from "@date-io/date-fns";
import MyOrderTable from "./MyOrderTable";
import BigOrderTable from "./BigOrderTable";
import Divider from '@material-ui/core/Divider';
import Cookies from 'js-cookie';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';



const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        maxWidth: 500,
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
    },
    inputAdornment:{
        fontSize:80,
    },
    menu:{
        width:200,
    }
}));

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



function TextMaskCustom(props) {
    const { inputRef, ...other } = props;

    return (
        <MaskedInput
            {...other}
            ref={ref => {
                inputRef(ref ? ref.inputElement : null);
            }}
            mask={[/\d/,' ', 'D',' ', /\d/, /\d/,' ', 'H', ' ',/\d/, /\d/,' ', 'M',' ',/\d/, /\d/,' ', 's']}
            placeholderChar={'\u2000'}
            showMask
        />
    );
}

TextMaskCustom.propTypes = {
    inputRef: PropTypes.func.isRequired,
};

class SendOrder extends Component {
    constructor(props){
        super(props);
        this.state = {
            productId:this.props.productId,
            productName:this.props.productName,
            productPeriod:this.props.productPeriod,
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            showBlotter:false,
            data:[],
            sellOrBuy:"",
            price:"",
            amount:"",
            orderType:"",
            sellDepth:this.props.sellDepth,
            buyDepth:this.props.buyDepth,
            processingOrders:this.props.processingOrders,
            bigOrders:this.props.bigOrders,
            warningOpen:false,
            warningMessage:'',
            priceSelect:false,
            checkedIceBerg:false,
            showBigOrder:false,
            textMask:'  天    小时    分    秒',
            intervalMask:'  天    小时    分    秒',
            selectedStrategy:"",
        };
        this.handleStartDateChange = this.handleStartDateChange.bind(this);
        this.handleEndDateChange = this.handleEndDateChange.bind(this);
        this.handleSellOrBuyChange = this.handleSellOrBuyChange.bind(this);
        this.handlePriceChange = this.handlePriceChange.bind(this);
        this.handleAmountChange = this.handleAmountChange.bind(this);
        this.handleOrderTypeChange = this.handleOrderTypeChange.bind(this);
        this.handleOrderButtonOnClick = this.handleOrderButtonOnClick.bind(this);
        this.handleWarningClose = this.handleWarningClose.bind(this);
        this.handleIceBergChecked = this.handleIceBergChecked.bind(this);
        this.handleShowBigOrder = this.handleShowBigOrder.bind(this);
        this.handleDealTimeChange = this.handleDealTimeChange.bind(this);
        this.handleIntervalChange = this.handleIntervalChange.bind(this);
        this.handleStrategyChange = this.handleStrategyChange.bind(this);
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        console.log("aaaa");
        if (nextProps.productId !== prevState.productId || nextProps.productName !== prevState.productName || nextProps.productPeriod !== prevState.productPeriod
            || nextProps.sellDepth !== prevState.sellDepth || nextProps.buyDepth !== prevState.buyDepth || nextProps.processingOrders !== prevState.processingOrders
            || nextProps.bigOrders !== prevState.bigOrders) {
            console.log("bbbb");
            console.log(nextProps);
            return {
                productId:nextProps.productId,
                productName:nextProps.productName,
                productPeriod:nextProps.productPeriod,

                showBlotter:false,
                data:[],

                sellDepth:nextProps.sellDepth,
                buyDepth:nextProps.buyDepth,
                processingOrders:nextProps.processingOrders,
                bigOrders:nextProps.bigOrders
            };
        }
        // 否则，对于state不进行任何操作
        return null;
    }

    handleStartDateChange(date){
        this.setState({selectedStartDate:date});
        console.log(this.state.selectedStartDate.getTime());
    }

    handleEndDateChange(date){
        this.setState({selectedEndDate:date});
    }

    handleSellOrBuyChange(e){
        this.setState({sellOrBuy:e.target.value});
    }

    handlePriceChange(e){
        this.setState({price:e.target.value});
    }

    handleAmountChange(e){
        this.setState({amount:e.target.value});
    }

    handleOrderTypeChange(e){
        if(e.target.value==='market') {
            this.setState(
                {
                    orderType: e.target.value,
                    price:"",
                    priceSelect:true,
                });
        }
        else{
            this.setState(
                {
                    orderType: e.target.value,
                    priceSelect:false,
                });
        }
        console.log(e.target.value);
    }

    handleIceBergChecked(event){
        console.log(event.target.checked);
        this.setState({
            checkedIceBerg:event.target.checked,
        });
    }

    handleShowBigOrder(event){
        console.log(event.target.checked);
        this.setState({
            showBigOrder:event.target.checked,
        });
    }

    handleDealTimeChange(event){
        let nums = event.target.value.split(" ");
        console.log(nums);
        console.log(parseInt(nums[0]),parseInt(nums[2]),parseInt(nums[4]),parseInt(nums[6]));
        this.setState({
            textMask:event.target.value,
        })
    }

    handleIntervalChange(event){
        let nums = event.target.value.split(" ");
        console.log(nums);
        console.log(parseInt(nums[0]),parseInt(nums[2]),parseInt(nums[4]),parseInt(nums[6]));
        this.setState({
            intervalMask:event.target.value,
        })
    }


    handleOrderButtonOnClick(){
        console.log(this.state);
        if(this.state.checkedIceBerg===true){
            if(this.state.productId===''||this.state.sellOrBuy===''||this.state.amount===''||this.state.textMask===''||this.state.intervalMask===''||this.state.selectedStrategy===''){
                this.setState({
                    warningOpen: true,
                    warningMessage: "订单信息不能为空，请检查后重试！"
                });
                return;
            }
            else if(isNaN(this.state.amount)){
                this.setState({
                    warningOpen:true,
                    warningMessage:"买卖手数必须为数字！"
                });
                return;
            }
            let totalTime=this.state.textMask.split(" ");
            let intervalTime=this.state.intervalMask.split(" ");
            if(totalTime[0]===null||isNaN(totalTime[0])||totalTime[2]===null||isNaN(totalTime[2])||
                parseInt(totalTime[2])>23||parseInt(totalTime[2])<0||totalTime[4]===null||isNaN(totalTime[4])||
                parseInt(totalTime[4])>59||parseInt(totalTime[4])<0||totalTime[6]===null||isNaN(totalTime[6])||
                parseInt(totalTime[6])>59||parseInt(totalTime[6])<0){
                this.setState({
                    warningOpen: true,
                    warningMessage: "时间格式错误！"
                });
                return;
            }
            if(intervalTime[0]===null||isNaN(intervalTime[0])||intervalTime[2]===null||isNaN(intervalTime[2])||
                parseInt(intervalTime[2])>23||parseInt(intervalTime[2])<0||intervalTime[4]===null||isNaN(intervalTime[4])||
                parseInt(intervalTime[4])>59||parseInt(intervalTime[4])<0||intervalTime[6]===null||isNaN(intervalTime[6])||
                parseInt(intervalTime[6])>59||parseInt(intervalTime[6])<0){
                this.setState({
                    warningOpen: true,
                    warningMessage: "时间格式错误！"
                });
                return;
            }

            let realTotalTime = parseInt(totalTime[0])*24*3600+parseInt(totalTime[2])*3600+parseInt(totalTime[4])*60+parseInt(totalTime[6]);
            let realIntervalTime = parseInt(intervalTime[0])*24*3600+parseInt(intervalTime[2])*3600+parseInt(intervalTime[4])*60+parseInt(intervalTime[6]);
            console.log(realTotalTime,realIntervalTime);
            console.log(this.state);


            let xmlHttp = new XMLHttpRequest();
            xmlHttp.open("GET", "http://localhost:30483/sendBigOrder?productId="+this.state.productId+"&sellOrBuy="+this.state.sellOrBuy+"&quantity="+this.state.amount
                +"&totalSeconds="+realTotalTime+"&intervalSeconds="+realIntervalTime+"&traderName="+Cookies.get('username')+"&brokerId="+Cookies.get('broker')+"&strategy="+this.state.selectedStrategy, true);
            xmlHttp.setRequestHeader("Content-Type", "application/json");
            xmlHttp.onreadystatechange = () => {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
                    let data=xmlHttp.responseText;
                    console.log(data);
                    this.setState({
                        selectedStartDate: new Date(),
                        selectedEndDate: new Date(),
                        showBlotter: false,
                        data: [],
                        sellOrBuy: "",
                        price: "",
                        amount: "",
                        orderType: "",
                        priceSelect: false,
                        checkedIceBerg:false,
                        textMask:'  天    小时    分    秒',
                        intervalMask:'  天    小时    分    秒',
                        selectedStrategy:"",
                    });
                }
            };
            xmlHttp.send();

        }
        else {
            if (this.state.productId === '' || this.state.orderType === '' || this.state.sellOrBuy === '' || this.state.price === '' || this.state.amount === '') {
                if (this.state.orderType === 'market' && this.state.price === '') {

                }
                else {
                    this.setState({
                        warningOpen: true,
                        warningMessage: "订单信息不能为空，请检查后重试！"
                    });
                    return;
                }
            }
            else if (isNaN(this.state.price) || isNaN(this.state.amount)) {
                this.setState({
                    warningOpen:true,
                    warningMessage:"订单价格和买卖手数必须为数字！"
                });
                return;
            }
            let xmlHttp = new XMLHttpRequest();
            if(this.state.orderType==='market'){
                xmlHttp.open("GET", "http://localhost:30483/sendOrder?productId="+this.state.productId+"&type="+this.state.orderType+"&sellOrBuy="+this.state.sellOrBuy+"&price=0"+"&quantity="+this.state.amount
                    +"&traderName="+Cookies.get('username')+"&brokerId="+Cookies.get('broker'), true);
            }
            else{
                xmlHttp.open("GET", "http://localhost:30483/sendOrder?productId="+this.state.productId+"&type="+this.state.orderType+"&sellOrBuy="+this.state.sellOrBuy+"&price="+this.state.price+"&quantity="+this.state.amount
                    +"&traderName="+Cookies.get('username')+"&brokerId="+Cookies.get('broker'), true);
            }
            xmlHttp.setRequestHeader("Content-Type", "application/json");
            xmlHttp.onreadystatechange = () => {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
                    let data=xmlHttp.responseText;
                    console.log(data);
                    this.setState({
                        selectedStartDate: new Date(),
                        selectedEndDate: new Date(),
                        showBlotter: false,
                        data: [],
                        sellOrBuy: "",
                        price: "",
                        amount: "",
                        orderType: "",
                        priceSelect: false,
                    });
                }
            };
            xmlHttp.send();
        }

    }

    handleWarningClose(){
        this.setState({
            warningOpen:false,
        })
    }

    handleStrategyChange(event){
        this.setState({
            selectedStrategy:event.target.value,
        })
    }

    render() {
        const classes = useStyles;
        const sample = [
            ['312345', 'Gold Swaps', 'SEP16', 1246, 50, 'Sam Wang','ABC Corp','Sell','Sixian Liu','MS','Buy'],
            ['312347', 'Gold Swaps', 'SEP16', 1244, 10, 'Sam Wang','ABC Corp','Sell','Judy Zhu','MS','Buy'],
            ['322345', 'Gold Swaps', 'JUL16', 1248, 20, 'James Li','CBA Corp','Buy','Sixian Liu','MS','Sell'],
            ['322349', 'Gold Swaps', 'JUL16', 1250, 70, 'James Li','CBA Corp','Buy','Sixian Liu','MS','Sell'],
        ];
        return (
            <div className={classes.root}>
                <div>
                    <Grid container className={classes.root} spacing={2}>
                        {this.state.showBigOrder?
                            <Grid item xs={5}>
                                <LineStyleIcon style={{float:'left'}}/>
                                <Typography variant="h6" style={{marginLeft:30}} gutterBottom>
                                    进行中拆分单
                                </Typography>
                                <BigOrderTable productName={this.state.productName} processingOrders={this.state.bigOrders}/>
                            </Grid>
                            :
                            <Grid item xs={5}>
                                <Grid container justify="center">
                                    <Grid item xs={6}>
                                        <Grid container justify="center">
                                            {/*<Card style={{width:'100%',textAlign:'center'}}>*/}
                                            {/*<CardContent style={{width:'100%'}}>*/}
                                            {/*<Typography variant="h2" gutterBottom>*/}
                                            {/*1240*/}
                                            {/*</Typography>*/}
                                            {/*</CardContent>*/}
                                            {/*</Card>*/}
                                            <TextField
                                                id="outlined-full-width"
                                                label="SELL Depth"
                                                style={{ margin: 8 }}
                                                value={this.state.sellDepth.depth}
                                                fullWidth
                                                margin="normal"
                                                variant="outlined"
                                                InputLabelProps={{
                                                    shrink: true,
                                                }}
                                                InputProps={{
                                                    startAdornment: <InputAdornment position="start">$</InputAdornment>,
                                                    style: { fontSize: 45 },
                                                    readOnly: true,
                                                    endAdornment: this.state.sellDepth.flag===0?<InputAdornment position="end"><ArrowDownWardIcon fontSize={"medium"}/></InputAdornment>
                                                        :(this.state.sellDepth.flag===2?<InputAdornment position="end"><ArrowUpWardIcon fontSize={"medium"}/></InputAdornment>:' '),
                                                }}
                                            />
                                        </Grid>
                                    </Grid>
                                    <Grid item xs={6}>
                                        <Grid container justify="center">
                                            <TextField
                                                id="outlined-full-width"
                                                label="BUY Depth"
                                                style={{ margin: 8 }}
                                                value={this.state.buyDepth.depth}
                                                fullWidth
                                                margin="normal"
                                                variant="outlined"
                                                InputLabelProps={{
                                                    shrink: true,
                                                }}
                                                InputProps={{
                                                    startAdornment: <InputAdornment position="start">$</InputAdornment>,
                                                    style: { fontSize: 45 },
                                                    readOnly: true,
                                                    endAdornment: (this.state.buyDepth.flag===0)?<InputAdornment position="end"><ArrowDownWardIcon fontSize={"medium"}/></InputAdornment>
                                                        :(this.state.buyDepth.flag===2?<InputAdornment position="end"><ArrowUpWardIcon fontSize={"medium"}/></InputAdornment>:' '),
                                                }}
                                            />
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid container justify="center">
                                    <Card style={{marginTop:20,marginBottom:30}}>
                                        <CardContent>
                                            {this.state.checkedIceBerg?
                                                <div>
                                                    <Grid container justify="center" className={classes.root} spacing={2}>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-read-only-input"
                                                                    label="商品名称"
                                                                    value={this.state.productName}
                                                                    className={classes.textField}
                                                                    margin="normal"
                                                                    disabled={true}
                                                                    InputProps={{
                                                                        readOnly: true,
                                                                    }}
                                                                />
                                                            </Grid>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-read-only-input"
                                                                    label="日期"
                                                                    value={this.state.productPeriod}
                                                                    className={classes.textField}
                                                                    margin="normal"
                                                                    disabled={true}
                                                                    InputProps={{
                                                                        readOnly: true,
                                                                    }}
                                                                />
                                                            </Grid>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-select-currency"
                                                                    select
                                                                    label="买/卖"
                                                                    className={classes.textField}
                                                                    value={this.state.sellOrBuy}
                                                                    onChange={this.handleSellOrBuyChange}
                                                                    SelectProps={{
                                                                        MenuProps: {
                                                                            className: classes.menu,
                                                                        },
                                                                    }}
                                                                    helperText="请选择交易方：买单 或 卖单"
                                                                    margin="normal"
                                                                >
                                                                    {[{key:"买",value:"buy"},{key:"卖",value:"sell"}].map(option => (
                                                                        <MenuItem key={option.key} value={option.value}>
                                                                            {option.key}
                                                                        </MenuItem>
                                                                    ))}
                                                                </TextField>
                                                            </Grid>
                                                        </Grid>
                                                    </Grid>
                                                    <Grid container justify="center" className={classes.root} spacing={2}>
                                                        <Grid item xs={4}>
                                                            <FormControl className={classes.textField} style={{marginTop:16}}>
                                                                <InputLabel htmlFor="formatted-text-mask-input">选择交易时长</InputLabel>
                                                                <Input
                                                                    value={this.state.textMask}
                                                                    onChange={this.handleDealTimeChange}
                                                                    id="formatted-text-mask-input"
                                                                    inputComponent={TextMaskCustom}
                                                                />
                                                            </FormControl>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <FormControl className={classes.textField} style={{marginTop:16}}>
                                                                <InputLabel htmlFor="formatted-text-mask-input">选择交易间隔</InputLabel>
                                                                <Input
                                                                    value={this.state.intervalMask}
                                                                    onChange={this.handleIntervalChange}
                                                                    id="formatted-text-mask-input"
                                                                    inputComponent={TextMaskCustom}
                                                                />
                                                            </FormControl>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-read-only-input"
                                                                    label="数量"
                                                                    value={this.state.amount}
                                                                    className={classes.textField}
                                                                    margin="normal"
                                                                    onChange={this.handleAmountChange}
                                                                    InputProps={{
                                                                        endAdornment: <InputAdornment position="end">手</InputAdornment>,
                                                                    }}
                                                                    helperText="请选择发单手数"
                                                                />
                                                            </Grid>
                                                        </Grid>
                                                    </Grid>
                                                    <Grid container justify="center" className={classes.root} spacing={2}>
                                                        <Select
                                                            value={this.state.selectedStrategy}
                                                            onChange={this.handleStrategyChange}
                                                            input={<OutlinedInput name="strategy" id="brokerSelect" />}
                                                            displayEmpty
                                                            name="age"
                                                            className={classes.selectEmpty}
                                                            style={{width:'90%',marginTop:40}}
                                                        >
                                                            <MenuItem value="" disabled>
                                                                请选择 交易策略
                                                            </MenuItem>
                                                            <MenuItem value={"twap"}>TWAP</MenuItem>
                                                            <MenuItem value={"vwap"}>VWAP</MenuItem>
                                                        </Select>
                                                    </Grid>
                                                </div>
                                                :
                                                <div>
                                                    <Grid container justify="center" className={classes.root} spacing={2}>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-read-only-input"
                                                                    label="商品名称"
                                                                    value={this.state.productName}
                                                                    className={classes.textField}
                                                                    margin="normal"
                                                                    disabled={true}
                                                                    InputProps={{
                                                                        readOnly: true,
                                                                    }}
                                                                />
                                                            </Grid>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-read-only-input"
                                                                    label="日期"
                                                                    value={this.state.productPeriod}
                                                                    className={classes.textField}
                                                                    margin="normal"
                                                                    disabled={true}
                                                                    InputProps={{
                                                                        readOnly: true,
                                                                    }}
                                                                />
                                                            </Grid>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-select-currency"
                                                                    select
                                                                    label="买/卖"
                                                                    className={classes.textField}
                                                                    value={this.state.sellOrBuy}
                                                                    onChange={this.handleSellOrBuyChange}
                                                                    SelectProps={{
                                                                        MenuProps: {
                                                                            className: classes.menu,
                                                                        },
                                                                    }}
                                                                    helperText="请选择交易方：买单 或 卖单"
                                                                    margin="normal"
                                                                >
                                                                    {[{key:"买",value:"buy"},{key:"卖",value:"sell"}].map(option => (
                                                                        <MenuItem key={option.key} value={option.value}>
                                                                            {option.key}
                                                                        </MenuItem>
                                                                    ))}
                                                                </TextField>
                                                            </Grid>
                                                        </Grid>
                                                    </Grid>
                                                    <Grid container justify="center" className={classes.root} spacing={2}>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-read-only-input"
                                                                    label="价格"
                                                                    value={this.state.price}
                                                                    className={classes.textField}
                                                                    margin="normal"
                                                                    onChange={this.handlePriceChange}
                                                                    disabled={this.state.priceSelect}
                                                                    InputProps={{
                                                                        startAdornment: <InputAdornment position="start">$</InputAdornment>,
                                                                    }}
                                                                    helperText="请选择出价"
                                                                />
                                                            </Grid>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-read-only-input"
                                                                    label="数量"
                                                                    value={this.state.amount}
                                                                    className={classes.textField}
                                                                    margin="normal"
                                                                    onChange={this.handleAmountChange}
                                                                    InputProps={{
                                                                        endAdornment: <InputAdornment position="end">手</InputAdornment>,
                                                                    }}
                                                                    helperText="请选择发单手数"
                                                                />
                                                            </Grid>
                                                        </Grid>
                                                        <Grid item xs={4}>
                                                            <Grid container justify="center">
                                                                <TextField
                                                                    id="standard-select-currency"
                                                                    select
                                                                    label="订单种类"
                                                                    className={classes.textField}
                                                                    value={this.state.orderType}
                                                                    onChange={this.handleOrderTypeChange}
                                                                    SelectProps={{
                                                                        MenuProps: {
                                                                            className: classes.menu,
                                                                        },
                                                                    }}
                                                                    helperText="请选择 Limit / Stop / Market"
                                                                    margin="normal"
                                                                >
                                                                    {[{key:"Limit",value:"limit"},{key:"Stop",value:"stop"},{key:"Market",value:"market"}].map(option => (
                                                                        <MenuItem key={option.key} value={option.value}>
                                                                            {option.key}
                                                                        </MenuItem>
                                                                    ))}
                                                                </TextField>
                                                            </Grid>
                                                        </Grid>
                                                    </Grid></div>
                                                }
                                            <Divider style={{marginTop:50}}/>
                                            <Grid container justify="center" className={classes.root} spacing={2}>
                                                <Grid item xs={4}>
                                                    <FormControlLabel
                                                        control={
                                                            <Checkbox checked={this.state.checkedIceBerg} onChange={this.handleIceBergChecked} color="primary" value="checkedA" />
                                                        }
                                                        label="冰山策略"
                                                        style={{marginTop:50}}
                                                    />
                                                </Grid>
                                                <Grid item xs={4}/>
                                                <Button variant="contained" color="primary" onClick={this.handleOrderButtonOnClick} style={{marginTop:60,height:40,width:100,fontSize:16}}>
                                                    生成订单
                                                </Button>
                                            </Grid>
                                        </CardContent>
                                    </Card>
                                </Grid>

                            </Grid>}
                        <Grid item xs={7}>
                            <div style={{verticalAlign:'middle'}}>
                                <Schedule style={{float:'left'}}/>
                                <Typography variant="h6" style={{marginLeft:30}} gutterBottom>
                                    进行中订单
                                    <FormControlLabel
                                        control={
                                            <Switch
                                                checked={this.state.showBigOrder}
                                                onChange={this.handleShowBigOrder}
                                                value="checkedB"
                                                color="primary"
                                            />
                                        }
                                        label="查看拆分单"
                                        style={{float:'right'}}
                                    />
                                </Typography>

                            </div>
                            <MyOrderTable productName={this.state.productName} processingOrders={this.state.processingOrders}/>
                        </Grid>
                    </Grid>
                </div>
                <Dialog
                    open={this.state.warningOpen}
                    onClose={this.handleWarningClose}
                    aria-labelledby="alert-dialog-title"
                    aria-describedby="alert-dialog-description"
                >
                    <DialogTitle id="alert-dialog-title"><WarningIcon fontSize={'middle'} color={"error"} style={{verticalAlign:'middle'}}/>&nbsp;{"订单格式错误"}</DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-description">
                            {this.state.warningMessage}
                        </DialogContentText>
                    </DialogContent>
                    <Divider/>
                    <DialogActions>
                        <Button onClick={this.handleWarningClose} color="primary">
                            关闭
                        </Button>
                    </DialogActions>
                </Dialog>

            {/*<MuiPickersUtilsProvider utils={DateFnsUtils}>*/}
                {/*<DateTimePicker*/}
                    {/*label="选择查询起始时间"*/}
                    {/*inputVariant="outlined"*/}
                    {/*value={this.state.selectedStartDate}*/}
                    {/*onChange={this.handleStartDateChange}*/}
                    {/*ampm={false}*/}
                    {/*disableFuture={true}*/}
                    {/*style={{marginRight:30,marginBottom:30}}*/}
                {/*/>*/}
                {/*<DateTimePicker*/}
                    {/*label="选择查询终止时间"*/}
                    {/*inputVariant="outlined"*/}
                    {/*value={this.state.selectedEndDate}*/}
                    {/*onChange={this.handleEndDateChange}*/}
                    {/*ampm={false}*/}
                    {/*minDate={new Date(this.state.selectedStartDate)}*/}
                    {/*disableFuture={true}*/}
                    {/*style={{marginRight:30}}*/}
                {/*/>*/}
                {/*<Button variant="contained" color="primary" onClick={this.handleButtonOnClick} style={{textAlign:"center",height:55,width:100,fontSize:16}}>*/}
                    {/*查询*/}
                {/*</Button>*/}
            {/*</MuiPickersUtilsProvider>*/}
                {/*{this.state.showBlotter?<BlotterTable data={this.state.data}/>:<div></div>}*/}
            </div>
        );
    }
}

export default SendOrder;