import React from "react";
import { TextField as TextFieldCore, InputBase, Grid, GridSize } from "@material-ui/core";

interface iTextField {
    label?: string,
    name?: string,
    value?: string,
    placeholder?: string,
    onChange?: (name: string, value: string) => void,
    variant?: "filled" | "outlined",
    password?: boolean,
    multiline?: boolean,
    fixedLabel?: boolean,
    rows?: number,
    fullwidth?: boolean,
    naked?: boolean,
    gridItem?: boolean,
    readOnly?: boolean
    size?: number,
    error?: string,
    disabled?: boolean,
}

function TextField(props: iTextField) {

    const _onChange = (e: any) => {
        if (props.onChange && props.name) {
            props.onChange(props.name, e.target.value);
        } else {
            console.warn("Props.name and Props.onChange not passed ")
        }
    }

    // const error = String(props.error) == ""? false: true;
    const error = typeof props.error === "boolean" ? props.error : false;

    if (props.naked) {
        return <InputBase value={props.value} type={props.password ? "password" : "text"} placeholder={props.placeholder} onChange={_onChange} multiline={props.multiline} rows={props.rows} fullWidth={props.fullwidth} />
    }

    if (props.gridItem) {
        return (
            <Grid item xs={12} sm={Number(props.size) as GridSize}  >
                <TextFieldCore error={error} InputProps={{
                    readOnly: props.readOnly,                    
                }} disabled={props.disabled} InputLabelProps={{shrink: props.fixedLabel}} variant={props.variant} label={props.label} value={props.value} type={props.password ? "password" : "text"} placeholder={props.placeholder} onChange={_onChange} multiline={props.multiline} rows={props.rows} fullWidth
                />
            </Grid>
        )
    }

    return (
        <TextFieldCore error={error} disabled={props.disabled} InputLabelProps={{shrink: props.fixedLabel}} variant={props.variant} label={props.label} value={props.value} type={props.password ? "password" : "text"} placeholder={props.placeholder} onChange={_onChange} multiline={props.multiline} rows={props.rows} fullWidth={props.fullwidth} />
    )
}

export default TextField;
