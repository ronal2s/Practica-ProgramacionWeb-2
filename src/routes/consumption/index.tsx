import React, { useState } from "react";
import MaterialTable from "material-table";
import { Grid, Paper } from "@material-ui/core";
import { isMobile } from "../../utils/functions";

function ProductsSupplier(props: any) {
    const [loading, setLoading] = useState(false);

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
                                { title: "Codigo Articulo", field: "codigo_articulo" },
                                { title: "Cantidad", field: "cantidad" },
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