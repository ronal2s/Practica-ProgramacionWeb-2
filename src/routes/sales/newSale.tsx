import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, FormControl, DialogActions, Button } from "@material-ui/core";
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import MomentUtils from '@date-io/moment';
import {
    DatePicker,
} from '@material-ui/pickers';
import axios from "axios";

import TextField from "../../components/_textField";

interface IModalSale {
    open: boolean,
    onClose: () => void,

}

function ModalSale(props: IModalSale) {
    const [form, setForm] = useState({ articulo: "ART-01", cliente: "Juan", cantidad: "1", fecha: new Date() });

    const handleInputs = (name: string, value: any) => {
        // if (name == "date") {
        //     setForm({ ...form, [name]:  })
        // }
        setForm({ ...form, [name]: name == "date" ? value?.toISOString() : value });
    }

    const makeSale = () => {
        axios.get("/ventas/nueva", { params: form })
            .then(result => {
                if (!result.data.error) {
                    props.onClose();
                } else {
                    alert(result.data.msg)
                }
            })
            .catch(error => alert(error))
    }


    return (
        <Dialog open={props.open} onClose={props.onClose} >
            <DialogTitle>Add new Sale</DialogTitle>
            <DialogContent>
                <FormControl>
                    <MuiPickersUtilsProvider utils={MomentUtils}>
                        <DatePicker value={form.fecha} onChange={(date) => handleInputs("fecha", date)} />
                        <TextField label="Cliente" name="cliente" value={form.cliente} onChange={handleInputs} />
                        <TextField label="Artículo" name="articulo" value={form.articulo} onChange={handleInputs} />
                        <TextField label="Cantidad" name="cantidad" value={form.cantidad} onChange={handleInputs} />
                    </MuiPickersUtilsProvider>

                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" color="secondary" >Close</Button>
                <Button variant="contained" color="primary" onClick={makeSale} >OK</Button>
            </DialogActions>
        </Dialog>
    )
}

export default ModalSale;