import React, { useState } from "react";
import MaterialTable from "material-table";
import { Grid, Paper, Button } from "@material-ui/core";
//Modals
import ModalNewSale from "./newSale";
//Utils
import { isMobile } from "../../utils/functions";

function Route2(props: any) {
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(false);

    const example = [
        { codigo: "1", fecha: "####", articulo: "ART-001", cantidad_salida: "5", cantidad_entrada: 100 },
    ]

    const openmodal = () => setModal(true);

    const closemodal = () => setModal(false);

    return (
        <React.Fragment>
            <Grid container justify="flex-start" >
                <Grid item>
                    <Paper>
                        {/* <Typography variant="h2">Inventario</Typography> */}
                        <MaterialTable
                            style={{ width: isMobile() ? 350 : "100%" }}
                            title="Ventas"
                            options={{
                                exportButton: true,
                                search: false
                            }}
                            columns={[
                                { title: "Código Movimiento", field: "codigo" },
                                { title: "Fecha", field: "fecha" },
                                { title: "Articulo", field: "articulo" },
                                { title: "Cantidad Salida", field: "cantidad_salida", },
                                { title: "Cantidad Entrada", field: "cantidad_entrada", },
                            ]}
                            isLoading={loading}
                            data={example}
                        />
                        <br />
                        <div style={{ marginLeft: 10 }} >
                            <Button variant="contained" color="primary" onClick={openmodal} >Nueva venta</Button>
                        </div>
                        <br />
                    </Paper>
                </Grid>
            </Grid>
            <ModalNewSale open={modal} onClose={closemodal} />
        </React.Fragment>
    )
}

export default Route2;