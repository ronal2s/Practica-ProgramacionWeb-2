import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import { Grid, Paper, Button } from "@material-ui/core";
import axios from "axios";
//Modals
import ModalNewSale from "./new";
//Utils
import { isMobile } from "../../utils/functions";
import { toast } from "react-toastify";
import { IStudent } from "./index.d";
import { student } from "../../utils/models";

function Sales(props: any) {
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(false);
    const [data, setdata] = useState([]);

    const [toUpdate, settoUpdate] = useState(false);
    const [currentStudent, setCurrentStudent] = useState<IStudent>({...student});

    useEffect(() => {
        getData();
    }, [])

    const getData = () => {
        setLoading(true);
        axios.get("/api/estudiante")
            .then(result => {
                setdata(result.data);
                setLoading(false);
            })
            .catch(error => {
                alert(error);
                setLoading(false);
            });
    }

    const deleteStudent = (row: IStudent) => {
        axios.delete(`/api/estudiante/${row.matricula}`)
        .then(result => {
            toast.success("Estudiante eliminado");
            getData();
        })
        .catch(error => {
            console.log(error);
            toast.error("Ha ocurrido un error");
        })
    }

    const requestUpdateItem = (row: IStudent) => {
        settoUpdate(true)
        const _row = {...row};
        delete _row.tableData;
        setCurrentStudent(_row);
        setModal(true);
    }

    const example = [
        { matricula: 20160207, nombre: "Renys De La Cruz", carrera: "ISC" },
    ]

    const openmodal = () => {
        setModal(true);
        settoUpdate(false);
    };

    const closemodal = () => {
        getData();
        setModal(false);
    }

    return (
        <React.Fragment>
            <Grid container justify="flex-start" >
                <Grid item>
                    <Paper>
                        {/* <Typography variant="h2">Inventario</Typography> */}
                        <MaterialTable
                            style={{ width: isMobile() ? 350 : "100%" }}
                            title="Estudiantes"
                            options={{
                                exportButton: true,
                                search: false
                            }}
                            columns={[
                                { title: "Matrícula", field: "matricula" },
                                { title: "Nombre", field: "nombre" },
                                { title: "Carrera", field: "carrera", },
                                { title: "Acciones", render: (rowData) => <Button color="primary" onClick={() => requestUpdateItem(rowData)} >Editar</Button> },
                                { title: "", render: (rowData) => <Button color="secondary" onClick={() => deleteStudent(rowData)} >Eliminar</Button> }
                            ]}
                            isLoading={loading}
                            data={data}
                        />
                        <br />
                        <div style={{ marginLeft: 10 }} >
                            <Button variant="contained" color="primary" onClick={openmodal} >Agregar</Button>
                        </div>
                        <br />
                    </Paper>
                </Grid>
            </Grid>
            <ModalNewSale open={modal} onClose={closemodal} toUpdate={toUpdate} item={currentStudent} />
        </React.Fragment>
    )
}

export default Sales;