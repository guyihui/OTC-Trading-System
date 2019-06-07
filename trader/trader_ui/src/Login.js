import React,{Component} from 'react';
import { withStyles,makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Grid from '@material-ui/core/Grid';
import InputBase from '@material-ui/core/InputBase';
import { fade } from '@material-ui/core/styles/colorManipulator';
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import LockIcon from '@material-ui/icons/Lock';
import CssBaseline from '@material-ui/core/CssBaseline';
import InputAdornment from '@material-ui/core/InputAdornment';
import ReactRouter from './ReactRouter';
import { browserHistory} from 'react-router';


const useStyles = makeStyles(theme =>({
    root: {
        display: 'flex',
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
    },
    card: {
        minWidth: 275,
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        backgroundColor:"#032f63",
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
}));


class Login extends Component {
    constructor(props){
        super(props);
        this.handleButtonOnClick = this.handleButtonOnClick.bind(this);
    }

    handleButtonOnClick(){
        browserHistory.push({
            pathname:'/homepage',
        });
    }

    render() {
        const classes = useStyles;
        let h = document.documentElement.clientHeight;
        return (
            <div className={classes.root} style={{height:h}}>
                <CssBaseline />
                <AppBar position="fixed" className={classes.appBar} style={{backgroundColor:'#032f63'}}>
                    <Toolbar>
                        <Typography variant="h6" noWrap>
                            期货交易所
                        </Typography>
                    </Toolbar>
                </AppBar>
                <div style={{height:'15%'}}/>
                <Grid container className={classes.root} spacing={2} style={{height:'100%'}}>
                    <Grid item xs={4}/>
                    <Grid item xs={4} style={{height:'100%'}}>
                        <Grid container justify="center" style={{height:'100%'}}>
                            <Card className={classes.card} style={{width:'100%',height:'70%',backgroundColor:'#ffffff'}}>
                                <CardContent style={{textAlign:'center',height:'100%'}}>
                                    <Typography variant={'h3'} gutterBottom style={{marginTop:30}}>
                                        登录
                                    </Typography>
                                    <Divider style={{width:'10%',marginLeft:'auto',marginRight:'auto',padding:2,backgroundColor:'#4a59fe'}}/>

                                    <TextField
                                        id="standard-full-width"
                                        label="用户名"

                                        placeholder="请输入用户名"
                                        fullWidth
                                        margin="normal"
                                        InputLabelProps={{
                                            shrink: true,
                                        }}
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start"><AccountCircleIcon fontSize={'medium'}/></InputAdornment>,
                                            style:{fontSize:20},
                                        }}
                                        style={{width:'90%',marginTop:60}}
                                    />
                                    <TextField
                                        id="standard-full-width"
                                        label="密码"

                                        placeholder="请输入密码"
                                        fullWidth
                                        margin="normal"
                                        InputLabelProps={{
                                            shrink: true,
                                        }}
                                        InputProps={{
                                            startAdornment: <InputAdornment position="start"><LockIcon fontSize={'medium'}/></InputAdornment>,
                                            style:{fontSize:20},
                                        }}
                                        style={{width:'90%',marginTop:60}}
                                    />
                                    <Button variant="contained" onClick={this.handleButtonOnClick} style={{width:'90%',height:'12%',fontSize:20,color:'#FFFFFF',marginTop:50,backgroundColor:'#333eb0'}}>登录</Button>
                                </CardContent>
                                <CardActions>

                                </CardActions>
                            </Card>
                        </Grid>
                    </Grid>
                    <Grid item xs={4}/>
                </Grid>
            </div>
        );
    }
}

export default Login;

