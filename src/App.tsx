import React, { useState } from 'react';
import { Divider, List } from '@material-ui/core';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import {
  BrowserRouter as Router, Switch, Route,
} from "react-router-dom";
//Screens
import ProductsSupplier from './routes/productsSuppliers';
import Inventory from './routes/inventory';
import Consumption from './routes/consumption';
import Sales from './routes/sales';
import Orders from './routes/orders';
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
        <h1 style={{ color: "white", textAlign: "center" }} >Mongo</h1>
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
              <CustomLink title="Venta" icon="mail" iconColor="white" nameView="/" actualRoute={actualRoute} onClick={onClickItem} />
              <CustomLink title="Inventario" icon="mail" iconColor="white" nameView="/inventario" actualRoute={actualRoute} onClick={onClickItem} />
              <CustomLink title="Art. Suplidores" icon="mail" iconColor="white" nameView="/suplidores" actualRoute={actualRoute} onClick={onClickItem} />
              <CustomLink title="Consumos" icon="mail" iconColor="white" nameView="/consumos" actualRoute={actualRoute} onClick={onClickItem} />
              <CustomLink title="Ordenes" icon="mail" iconColor="white" nameView="/ordenes" actualRoute={actualRoute} onClick={onClickItem} />
            </List>
          </CustomDrawer>
          <NavigationView>
            <CustomAppBar title="Home" onMenuPress={handleDrawer} drawerOpened={drawer} />
            <ContentView>
              <Switch>
                <Route path="/" exact component={Sales} />
                {/* <Route path={`/${Routes.Screen2}`} component={Route2} /> */}
                {/* <Route path="/" component={Route2} /> */}
                <Route path="/inventario" component={Inventory} />
                <Route path="/suplidores" component={ProductsSupplier} />
                <Route path="/consumos" component={Consumption} />
                <Route path="/ordenes" component={Orders} />
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
