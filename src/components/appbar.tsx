import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {AppBar, Toolbar, Icon, Typography, IconButton, Slide} from '@material-ui/core';

interface ICustomAppBar {
    title: string,
    drawerOpened?: boolean,
    rightButton?: React.ReactNode,
    onMenuPress?: () => void,
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

function CustomAppBar(props: ICustomAppBar) {
  const classes = useStyles();

  return (
    <div className={classes.root} style={{padding: 10}} >
      {/* <AppBar position="static" style={{backgroundColor: "#A9A9A9"}} > */}
      <AppBar position="static" style={{borderRadius: 25}} >
        <Toolbar>
          <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu" onClick={props.onMenuPress} >            
            {props.drawerOpened && 
            <Slide in>
            <Icon>
              arrow_back
            </Icon>
            </Slide>
            }
            {!props.drawerOpened && 
            <Slide in>
            <Icon>
              menu
            </Icon>
            </Slide>
            }
            
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            {props.title}
          </Typography>
          {props.rightButton}
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default CustomAppBar;