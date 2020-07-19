import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import axios from "axios";
import { Grid, Paper, Button } from "@material-ui/core";
import { isMobile } from "../../utils/functions";
import ModalNewItem from "./newItem";

function Route2(props: any) {
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(false);
    const [data, setData] = useState<any[]>([]);

    const example = [
        { codigo: "TEC-001", descripcion: "TECLADO", almacen: "1", balance: "13,00" },
        { codigo: "MON-001", descripcion: "MONITOR", almacen: "1", balance: "15,00" },
        { codigo: "MOU-001", descripcion: "MOUSE", almacen: "1", balance: "15,00" },
    ]

    useEffect(() => {
        getData();
    }, [])

    const openmodal = () => setModal(true);

    const closemodal = () => setModal(false);

    const getData = () => {
        axios.get("/inventario")
            .then(result => setData([...result.data]))
            .catch(error => alert(error));
    }


    return (
        <React.Fragment>
            <Grid container justify="flex-start" >
                <Grid item>
                    <Paper>
                        {/* <Typography variant="h2">Inventario</Typography> */}
                        <MaterialTable
                            style={{ width: isMobile() ? 350 : "100%" }}
                            title="Inventario"
                            options={{
                                exportButton: true,
                                search: false
                            }}
                            columns={[
                                { title: "Código", field: "codigoArticulo" },
                                { title: "Descripción", field: "descripcion" },
                                { title: "Balance", field: "balanceActual" },
                                { title: "Unidad Compra", field: "unidadCompra", },
                            ]}
                            isLoading={loading}
                            data={data}
                        />
                        {/* <br />
                        <div style={{ marginLeft: 10 }} >
                            <Button variant="contained" color="primary" onClick={openmodal} >Nueva venta</Button>
                        </div>
                        <br /> */}
                    </Paper>
                </Grid>
            </Grid>
            <ModalNewItem open={modal} onClose={closemodal} />
        </React.Fragment>
    )
}

export default Route2;