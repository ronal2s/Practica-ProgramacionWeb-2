import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import axios from "axios";
import { Grid, Paper } from "@material-ui/core";
import { isMobile } from "../../utils/functions";

function ProductsSupplier(props: any) {
    const [loading, setLoading] = useState(false);
    const [data, setData] = useState([]);

    useEffect(() => {
        getData();
    }, [])

    const getData = () => {
        axios.get("/consumos")
            .then(result => {
                setData(result.data)
            })
            .catch(error => alert(error));
    }

    const example = [
        { codigo_articulo: "TEC-001", cantidad: "5,00" },
    ]

    return (
        <React.Fragment>
            <Grid container justify="flex-start" >
                <Grid item>
                    <Paper>
                        <MaterialTable
                            style={{ width: isMobile() ? 350 : "100%" }}
                            title="Consumo Diario"
                            options={{
                                exportButton: true,
                                search: false
                            }}
                            columns={[
                                { title: "Codigo Articulo", field: "_id" },
                                { title: "Promedio", field: "avgQuantity", render: (rowData: any) => Math.round(rowData.avgQuantity) },
                                // { title: "Fecha", field: "fecha", render: (rowData: { _id: any }) => rowData._id.fecha },
                            ]}
                            isLoading={loading}
                            data={data}
                        />
                    </Paper>
                </Grid>
            </Grid>
        </React.Fragment>
    )
}

export default ProductsSupplier;