import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import axios from "axios";
import { Grid, Paper } from "@material-ui/core";
import { isMobile } from "../../utils/functions";

function ProductsSupplier(props: any) {
    const [loading, setLoading] = useState(false);
    const [data, setData] = useState<any[]>([]);

    const example = [
        { codigo_suplidor: "1", articulo: "TEC-001", tiempo_entrega: "2,00", precio_costo: "300,00" },
        { codigo_suplidor: "2", articulo: "TEC-001", tiempo_entrega: "3,00", precio_costo: "250,00" },
        { codigo_suplidor: "3", articulo: "TEC-001", tiempo_entrega: "5,00", precio_costo: "220,00" },
    ]

    useEffect(() => {
        getData();
    }, []);

    const getData = () => {
        axios.get("/suplidor")
            .then(result => setData([...result.data]))
            .catch(error => alert(error));
    }

    return (
        <React.Fragment>
            <Grid container justify="flex-start" >
                <Grid item>
                    <Paper>
                        <MaterialTable
                            style={{ width: isMobile() ? 350 : "100%" }}
                            title="Articulo Suplidor"
                            options={{
                                exportButton: true,
                                search: false
                            }}
                            columns={[
                                { title: "Suplidor", field: "codigoSuplidor" },
                                { title: "Articulo", field: "codigoArticulo" },
                                { title: "Tiempo Entrega", field: "tiempoEntrega" },
                                { title: "Precio/Costo", field: "precioCompra", },
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