import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, FormControl, DialogActions, Button } from "@material-ui/core";
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import MomentUtils from '@date-io/moment';
import {
    DatePicker,
} from '@material-ui/pickers';
import axios from "axios";

import TextField from "../../components/_textField";
import { toast } from "react-toastify";
import { IStudent } from "./index.d";

interface IModalSale {
    open: boolean,
    onClose: () => void,
    toUpdate: boolean,
    item: IStudent
}

function ModalSale(props: IModalSale) {
    const [form, setForm] = useState<IStudent>({ matricula: "", nombre: "", carrera: "" });

    const onEnter = () => {
        if (props.toUpdate) setForm(props.item);
    }

    const handleInputs = (name: string, value: any) => {
        setForm({ ...form, [name]: value });
    }

    const addNewStudent = () => {
        if (props.toUpdate) {
            axios.put("/api/estudiante", { ...form })
                .then(result => {
                    props.onClose();
                    toast.success("Estudiante actualizado");
                })
                .catch(error => {
                    toast.error("Ha ocurrido un error");
                    console.log(error)
                });
        } else {
            axios.post("/api/estudiante", { ...form })
                .then(result => {
                    props.onClose();
                    toast.success("Estudiante agregado");
                })
                .catch(error => {
                    toast.error("Ha ocurrido un error");
                    console.log(error)
                });

        }
    }


    return (
        <Dialog open={props.open} onClose={props.onClose} onEnter={onEnter} >
            <DialogTitle>Agregar estudiante</DialogTitle>
            <DialogContent>
                <FormControl>
                    <TextField label="Matricula" name="matricula" value={form.matricula} onChange={handleInputs} />
                    <TextField label="Nombre" name="nombre" value={form.nombre} onChange={handleInputs} />
                    <TextField label="Carrera" name="carrera" value={form.carrera} onChange={handleInputs} />
                </FormControl>
            </DialogContent>
            <DialogActions>
                <Button variant="outlined" color="secondary" onClick={() => props.onClose()} >Cerrar</Button>
                <Button variant="contained" color="primary" onClick={addNewStudent} >OK</Button>
            </DialogActions>
        </Dialog>
    )
}

export default ModalSale;