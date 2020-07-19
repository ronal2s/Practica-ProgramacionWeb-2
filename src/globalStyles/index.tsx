import styled from 'styled-components';
import { Drawer_size } from '../utils/constants';


const RowView = styled.div({
    display: "flex",
    flexDirection: "row",
    flexWrap: "wrap"
})

const DrawerView = styled.div({
    width: Drawer_size
})

const NavigationView = styled.div({
    flex: 1,
})

const ContentView = styled.div({
    padding: 10
})

export {
    RowView,
    DrawerView,
    NavigationView,
    ContentView
}