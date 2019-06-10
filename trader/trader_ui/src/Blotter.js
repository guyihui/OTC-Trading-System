import 'date-fns';
import React,{Component,Fragment} from 'react';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import {
    DatePicker,
    TimePicker,
    DateTimePicker,
    MuiPickersUtilsProvider,
} from "@material-ui/pickers";
import Button from '@material-ui/core/Button';
import DateFnsUtils from "@date-io/date-fns";
import BlotterTable from "./BlotterTable";
import Cookies from "js-cookie";


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


class Blotter extends Component {
    constructor(props){
        super(props);
        this.state = {
            productId:this.props.productId,
            productName:this.props.productName,
            productPeriod:this.props.productPeriod,
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            showBlotter:false,
            data:[]
        };
        this.handleStartDateChange = this.handleStartDateChange.bind(this);
        this.handleEndDateChange = this.handleEndDateChange.bind(this);
        this.handleButtonOnClick = this.handleButtonOnClick.bind(this);
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        console.log("aaaa");
        if (nextProps.productId !== prevState.productId || nextProps.productName !== prevState.productName || nextProps.productPeriod !== prevState.productPeriod) {
            console.log("bbbb");
            console.log(nextProps);
            return {
                productId:nextProps.productId,
                productName:nextProps.productName,
                productPeriod:nextProps.productPeriod,
                selectedStartDate:new Date(),
                selectedEndDate:new Date(),
                showBlotter:false,
                data:[]
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

    handleButtonOnClick(){
        this.setState({showBlotter:true});
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", "http://localhost:30483/getBlotter?productId="+this.state.productId+"&startTime="+this.state.selectedStartDate.getTime()+"&endTime="+this.state.selectedEndDate.getTime()
            +"&traderName="+Cookies.get('username')+"&brokerId="+Cookies.get('broker'), true);
        xmlHttp.setRequestHeader("Content-Type", "application/json");
        xmlHttp.onreadystatechange = () => {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
                let data=JSON.parse(xmlHttp.responseText);
                console.log(data);
                this.setState({data:data});
            }
        };
        xmlHttp.send();

    }

    render() {
        const sample = [
            ['312345', 'Gold Swaps', 'SEP16', 1246, 50, 'Sam Wang','ABC Corp','Sell','Sixian Liu','MS','Buy'],
            ['312347', 'Gold Swaps', 'SEP16', 1244, 10, 'Sam Wang','ABC Corp','Sell','Judy Zhu','MS','Buy'],
            ['322345', 'Gold Swaps', 'JUL16', 1248, 20, 'James Li','CBA Corp','Buy','Sixian Liu','MS','Sell'],
            ['322349', 'Gold Swaps', 'JUL16', 1250, 70, 'James Li','CBA Corp','Buy','Sixian Liu','MS','Sell'],
        ];
        return (
            <div>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <DateTimePicker
                    label="选择查询起始时间"
                    inputVariant="outlined"
                    value={this.state.selectedStartDate}
                    onChange={this.handleStartDateChange}
                    ampm={false}
                    disableFuture={true}
                    style={{marginRight:30,marginBottom:30}}
                />
                <DateTimePicker
                    label="选择查询终止时间"
                    inputVariant="outlined"
                    value={this.state.selectedEndDate}
                    onChange={this.handleEndDateChange}
                    ampm={false}
                    minDate={new Date(this.state.selectedStartDate)}
                    disableFuture={true}
                    style={{marginRight:30}}
                />
                <Button variant="contained" color="primary" onClick={this.handleButtonOnClick} style={{textAlign:"center",height:55,width:100,fontSize:16}}>
                    查询
                </Button>
            </MuiPickersUtilsProvider>
                {this.state.showBlotter?<BlotterTable data={this.state.data}/>:<div></div>}
            </div>
        );
    }
}

export default Blotter;