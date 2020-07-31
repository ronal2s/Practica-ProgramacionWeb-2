import React, { useState } from 'react';
import { Divider, List } from '@material-ui/core';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import {
  BrowserRouter as Router, Switch, Route,
} from "react-router-dom";
//Screens
import Sales from './routes/students';
//Custom components
import CustomAppBar from './components/appbar';
import CustomLink from './components/listItem';
import CustomDrawer from './components/drawer';
import { isMobile } from './utils/functions';
import { COLORS } from './utils/enums';
//Custom styles
import { RowView, NavigationView, ContentView } from './globalStyles';

const Header = () => {
  return (
    <React.Fragment>
      <Divider />
      <div style={{ backgroundColor: COLORS.PRIMARY_DARK, height: 100 }} >
        <h1 style={{ color: "white", textAlign: "center" }} >Quiz</h1>
      </div>
    </React.Fragment>
  )
}

function App() {

  const [drawer, setDrawer] = useState(!isMobile());
  const [actualRoute, setRoute] = useState("");

  const handleDrawer = () => {
    setDrawer(!drawer)
  }

  const closeDrawer = () => setDrawer(false);

  const onClickItem = (name: string) => {
    setRoute(name);
  }



  return (
    <Router>
      <React.Fragment >
        <RowView >
          <CustomDrawer open={drawer} closeDrawer={closeDrawer} >
            <Header />
            <Divider />
            <List>
              <CustomLink title="Estudiantes" icon="mail" iconColor="white" nameView="/" actualRoute={actualRoute} onClick={onClickItem} />
            </List>
          </CustomDrawer>
          <NavigationView>
            <CustomAppBar title="Home" onMenuPress={handleDrawer} drawerOpened={drawer} />
            <ContentView>
              <Switch>
                <Route path="/" exact component={Sales} />
              </Switch>
            </ContentView>
          </NavigationView>
        </RowView>
        <ToastContainer />
      </React.Fragment>
    </Router>
  );
}

export default App;
