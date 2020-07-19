import React, { useState } from "react";
import MaterialTable from "material-table";
import { Grid, Paper } from "@material-ui/core";
import { isMobile } from "../../utils/functions";

function ProductsSupplier(props: any) {
    const [loading, setLoading] = useState(false);

    const example = [
        { codigo_suplidor: "1", articulo: "TEC-001", tiempo_entrega: "2,00", precio_costo: "300,00" },        
        { codigo_suplidor: "2", articulo: "TEC-001", tiempo_entrega: "3,00", precio_costo: "250,00" },        
        { codigo_suplidor: "3", articulo: "TEC-001", tiempo_entrega: "5,00", precio_costo: "220,00" },        
    ]

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
                                { title: "Suplidor", field: "codigo_suplidor" },
                                { title: "Articulo", field: "articulo" },
                                { title: "Tiempo Entrega", field: "tiempo_entrega" },
                                { title: "Precio/Costo", field: "precio_costo", },
                            ]}
                            isLoading={loading}
                            data={example}
                        />
                    </Paper>
                </Grid>
            </Grid>
        </React.Fragment>
    )
}

export default ProductsSupplier;