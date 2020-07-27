import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, FormControl, DialogActions, Button } from "@material-ui/core";
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import MomentUtils from '@date-io/moment';
import {
    DatePicker,
} from '@material-ui/pickers';
import axios from "axios";

import TextField from "../../components/_textField";

interface IModalOrder {
    open: boolean,
    onClose: () => void,

}

function ModalOrder(props: IModalOrder) {
    const [form, setForm] = useState({ articulo: "", suplidor: "", cantidad: "", fecha: new Date() });

    const handleInputs = (name: string, value: any) => {
        // if (name == "date") {
        //     setForm({ ...form, [name]:  })
        // }
        setForm({ ...form, [name]: name == "fecha" ? new Date(value?.toISOString()).toLocaleDateString() : value });
    }

    const makeSale = () => {
        alert("Workin in")
    }


    return (
        <Dialog open={props.open} onClose={props.onClose} >
            <DialogTitle>Generar orden</DialogTitle>
            <DialogContent>
                <FormControl>
                    <MuiPickersUtilsProvider utils={MomentUtils}>
                        <DatePicker value={form.fecha} onChange={(date) => handleInputs("fecha", date)} />
                        <TextField label="Cod Suplidor" name="suplidor" value={form.suplidor} onChange={handleInputs} />
                        <TextField label="Cod Articulo" name="articulo" value={form.articulo} onChange={handleInputs} />
                        <TextField label="Cantidad" name="cantidad" value={form.cantidad} onChange={handleInputs} />
                    </MuiPickersUtilsProvider>

                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" color="secondary" >Cerrar</Button>
                <Button variant="contained" color="primary" onClick={makeSale} >OK</Button>
            </DialogActions>
        </Dialog>
    )
}

export default ModalOrder;