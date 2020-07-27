//Renys De La Cruz - 20160207
//Regis Báez       - 2014-0324
const express = require("express");
const bodyParser = require("body-parser");
const app = express();
const router = express.Router();
const port = process.env.PORT || 5000;

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/mydb";

const { COLLECTIONS } = require("./utils/constants");


const data_articulos = [
  { codigoArticulo: "ART-01", descripcion: "Keyboard", balanceActual: "5", unidadCompra: "100" },
  { codigoArticulo: "ART-02", descripcion: "Mouse", balanceActual: "7", unidadCompra: "100" },
  { codigoArticulo: "ART-03", descripcion: "Monitor", balanceActual: "3", unidadCompra: "2500" },
  { codigoArticulo: "ART-04", descripcion: "Cable USB", balanceActual: "8", unidadCompra: "100" },
  { codigoArticulo: "ART-05", descripcion: "Cable HDMI", balanceActual: "19", unidadCompra: "100" },
  { codigoArticulo: "ART-06", descripcion: "Headphones", balanceActual: "11", unidadCompra: "800" },
  { codigoArticulo: "ART-07", descripcion: "Alcatel", balanceActual: "23", unidadCompra: "2500" },
]

const data_articulo_suplidor = [
  { codigoArticulo: "ART-01", codigoSuplidor: "SUP-01", tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: "ART-01", codigoSuplidor: "SUP-02", tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: "ART-01", codigoSuplidor: "SUP-03", tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: "ART-02", codigoSuplidor: "SUP-01", tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: "ART-02", codigoSuplidor: "SUP-02", tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: "ART-02", codigoSuplidor: "SUP-03", tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: "ART-03", codigoSuplidor: "SUP-01", tiempoEntrega: 3, precioCompra: 2100 },
  { codigoArticulo: "ART-03", codigoSuplidor: "SUP-02", tiempoEntrega: 1, precioCompra: 2400 },
  { codigoArticulo: "ART-03", codigoSuplidor: "SUP-03", tiempoEntrega: 5, precioCompra: 1700 },
  { codigoArticulo: "ART-04", codigoSuplidor: "SUP-01", tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: "ART-04", codigoSuplidor: "SUP-02", tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: "ART-04", codigoSuplidor: "SUP-03", tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: "ART-05", codigoSuplidor: "SUP-01", tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: "ART-05", codigoSuplidor: "SUP-02", tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: "ART-05", codigoSuplidor: "SUP-03", tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: "ART-06", codigoSuplidor: "SUP-01", tiempoEntrega: 3, precioCompra: 700 },
  { codigoArticulo: "ART-06", codigoSuplidor: "SUP-02", tiempoEntrega: 1, precioCompra: 750 },
  { codigoArticulo: "ART-06", codigoSuplidor: "SUP-03", tiempoEntrega: 5, precioCompra: 600 },
  { codigoArticulo: "ART-07", codigoSuplidor: "SUP-01", tiempoEntrega: 3, precioCompra: 2200 },
  { codigoArticulo: "ART-07", codigoSuplidor: "SUP-02", tiempoEntrega: 1, precioCompra: 2400 },
  { codigoArticulo: "ART-07", codigoSuplidor: "SUP-03", tiempoEntrega: 5, precioCompra: 1800 },
]


MongoClient.connect(url, function (err, db) {
  if (err) throw err;
  const dbo = db.db("test");

  deleteCollection(COLLECTIONS.ARTICULOS, dbo);
  deleteCollection(COLLECTIONS.ARTICULOS_SUPLIDOR, dbo);
  deleteCollection(COLLECTIONS.VENTAS, dbo);

  createCollection(COLLECTIONS.ARTICULOS, dbo);
  createCollection(COLLECTIONS.ARTICULOS_SUPLIDOR, dbo);
  putData(COLLECTIONS.ARTICULOS, dbo, data_articulos);
  putData(COLLECTIONS.ARTICULOS_SUPLIDOR, dbo, data_articulo_suplidor);

  app.get("/inventario", (req, res) => {
    getData(COLLECTIONS.ARTICULOS, dbo, (result) => res.send(result));
  });

  app.get("/suplidor", (req, res) => {
    getData(COLLECTIONS.ARTICULOS_SUPLIDOR, dbo, (result) => res.send(result));
  });

  app.get("/ventas", (req, res) => {
    getData(COLLECTIONS.VENTAS, dbo, (result) => {
      res.send(result)
    })
  })

  app.get("/ventas/nueva", (req, res) => {
    const { cliente, articulo, cantidad, fecha } = req.query;
    console.log(req.query)
    getData(COLLECTIONS.VENTAS, dbo, (result) => {
      const obj = { name: "codigoArticulo", value: articulo }
      getSingleItem(COLLECTIONS.ARTICULOS, dbo, obj, (result) => {
        if (parseInt(cantidad) < result.balanceActual) {
          const obj = { id: result._id, value: { balanceActual: result.balanceActual - 1 } };
          updateItem(COLLECTIONS.ARTICULOS, dbo, obj, (result) => {
            const obj = { codigoArticulo: articulo, cantidad, cliente, fecha }
            putData(COLLECTIONS.VENTAS, dbo, obj, (result) => {
              res.send({ error: false, ...result })
            })
          })
        } else {
          res.send({error: true, msg: `La cantidad es mayor al balance disponible: ${result.balanceActual}`})
        }
      })
    })
  });


  getData(COLLECTIONS.ARTICULOS, dbo);
  // db.close();
});


const putData = (nameCollection, dbo, obj, callback) => {
  const typeInsert = Array.isArray(obj) ? "insertMany" : "insertOne";
  dbo.collection(nameCollection)[typeInsert](obj, (error, result) => {
    if (error) {
      console.log(error)
    } else {
      console.log("Data pushed");
      if (callback) callback(result);
    }
  })
}

const getSingleItem = (nameCollection, dbo, obj, callback) => {
  console.log(obj)
  dbo.collection(nameCollection).find({ [obj.name]: obj.value }).toArray((error, result) => {
    if (error) throw error;
    // console.log(`${nameCollection} results: `, result);
    if (callback) callback(result[0]);
  })
}

const getData = (nameCollection, dbo, callback) => {
  dbo.collection(nameCollection).find().toArray((error, result) => {
    if (error) throw error;
    // console.log(`${nameCollection} results: `, result);
    if (callback) callback(result);
  })
}

const updateItem = (nameCollection, dbo, obj, callback) => {
  dbo.collection(nameCollection).update({ _id: obj.id }, { $set: { ...obj.value } })
    .then(result => {
      callback(result)
    })
    .catch(error => console.error(error))
}


const createCollection = (nameCollection, dbo, callback) => {
  if (!(dbo.collection(nameCollection).countDocuments() > 0)) {
    dbo.createCollection(nameCollection, (err, res) => {
      if (err) throw err;
      // console.log(`Collection ${nameCollection} created!`);
      if (callback) callback();
    })
  }
}


const deleteCollection = (nameCollection, dbo, callback) => {
  dbo.collection(nameCollection).remove()
    .then(result => { if (callback) callback(result); })
    .catch(error => console.error(error));
}

app.get("/", (req, res) => {
  res.render("index");
});


app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());


app.listen(port, () => {
  console.log("Server is running");
});