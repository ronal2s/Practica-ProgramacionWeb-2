import React, { useState, useEffect } from 'react';
import { Drawer } from '@material-ui/core';
import { Drawer_size } from '../utils/constants';
import { DrawerView } from '../globalStyles';
import { isMobile } from '../utils/functions';
import { COLORS } from '../utils/enums';

interface IDrawer {
    children?: React.ReactNode,
    open?: boolean,
    closeDrawer?: () => void
}

function CustomDrawer(props: IDrawer) {
    const [transitionEnd, setTransitionEnd] = useState(false);

    useEffect(() => {
        if (props.open) {
            setTransitionEnd(false);
        }
    }, [props.open])

    const endTransition = () => {
        if (!props.open) {
            setTransitionEnd(true);
        }
    };

    const closeDrawer = () => {
        if(props.closeDrawer) {
            props.closeDrawer();
        }
    }


    return (
        <DrawerView style={{ width: transitionEnd ? 0 : Drawer_size, transition: "width .5s linear", marginRight: 10 }} >
            <Drawer variant={isMobile() ? "temporary" : "persistent"} open={props.open} onTransitionEnd={endTransition} 
                PaperProps={{ style: { width: Drawer_size, backgroundColor: COLORS.PRIMARY, borderTopRightRadius: 25, borderWidth: 0} }} onBackdropClick={closeDrawer} >
                {props.children}
            </Drawer>
        </DrawerView>
    );
}

export default CustomDrawer;
