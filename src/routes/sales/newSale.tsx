import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, FormControl, DialogActions, Button } from "@material-ui/core";
import TextField from "../../components/_textField";

interface IModalSale {
    open: boolean,
    onClose: () => void,

}

function ModalSale(props: IModalSale) {
    const [form, setForm] = useState({ articulo: "", cliente: "", cantidad: "" });

    const handleInputs = (name: string, value: string) => {
        setForm({ ...form, [name]: value });
    }


    return (
        <Dialog open={props.open} onClose={props.onClose} >
            <DialogTitle>Add new Sale</DialogTitle>
            <DialogContent>
                <FormControl>

                <TextField label="Cliente" name="cliente" value={form.cliente} onChange={handleInputs} />
                
                <TextField label="Artículo" name="articulo" value={form.articulo} onChange={handleInputs} />
                <TextField label="Artículo" name="cantidad" value={form.cantidad} onChange={handleInputs} />
                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" color="secondary" >Close</Button>
                <Button variant="contained" color="primary" >Add</Button>
            </DialogActions>
        </Dialog>
    )
}

export default ModalSale;