import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, FormControl, DialogActions, Button } from "@material-ui/core";
import axios from "axios";
import TextField from "../../components/_textField";

interface IModalInventory {
    open: boolean,
    onClose: () => void,

}

function ModalInventory(props: IModalInventory) {
    const [form, setForm] = useState({ codigoArticulo: "", descripcion: "", balanceActual: "", unidadCompra: "" });

    const handleInputs = (name: string, value: string) => {
        setForm({ ...form, [name]: value });
    }

    const addItem = () => {
        axios.post("/inventario", form)
            .then(result => {
                alert("Item added")
            })
            .catch(error => alert(error))

    }

    return (
        <Dialog open={props.open} onClose={props.onClose} >
            <DialogTitle>Add new item</DialogTitle>
            <DialogContent>
                <FormControl>

                    <TextField label="Codigo Articulo" name="codigoArticulo" value={form.codigoArticulo} onChange={handleInputs} />

                    <TextField label="Descripcion" name="descripcion" value={form.descripcion} onChange={handleInputs} />
                    <TextField label="Unidad Compra" name="unidadCompra" value={form.unidadCompra} onChange={handleInputs} />
                    <TextField label="Balance Actual" name="balanceActual" value={form.balanceActual} onChange={handleInputs} />
                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" color="secondary" onClick={props.onClose} >Close</Button>
                <Button variant="contained" color="primary" onClick={addItem} >Add</Button>
            </DialogActions>
        </Dialog>
    )
}

export default ModalInventory;