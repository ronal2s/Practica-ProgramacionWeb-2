import React from "react";
import { ListItem, ListItemIcon, ListItemText, Icon } from "@material-ui/core";
import { Link } from "react-router-dom";
//Utils
import { COLORS } from "../utils/enums";

interface ICustomLink {
    title: string,
    icon?: string,
    iconColor?: string,
    active?: boolean,
    actualRoute?: string,
    nameView: string,
    onClick?: (name?: any) => void
}

function CustomLink(props: ICustomLink) {

    const onClick = (name?: string) => {
        if (props.onClick) {
            props.onClick(name);
        }
    }

    const active = props.active ? props.active : (props.nameView === props.actualRoute)

    if (active) {
        return (
            <Link to={props.nameView} style={{ textDecoration: "none", color: COLORS.PRIMARY, transition: "color .8s linear" }}>
                <ListItem button style={{
                    backgroundColor: "white",
                    transition: "background-color .8s linear"
                }}
                    onClick={() => onClick(props.nameView)}>
                    <ListItemIcon>
                        <Icon style={{ color: active ? COLORS.PRIMARY : (props.iconColor ? props.iconColor : COLORS.DEFAULT_ICON_COLOR) }} >{props.icon}</Icon>
                    </ListItemIcon>
                    <ListItemText primary={props.title} />
                </ListItem>
            </Link>
        )
    }
    return (
        <Link to={props.nameView} style={{ textDecoration: "none", color: "white" }}  >
            <ListItem button onClick={() => onClick(props.nameView)}>
                <ListItemIcon>
                    <Icon style={{ color: props.iconColor ? props.iconColor : COLORS.DEFAULT_ICON_COLOR }} >{props.icon}</Icon>
                </ListItemIcon>
                <ListItemText primary={props.title} />
            </ListItem>
        </Link>
    )
}

export default CustomLink;