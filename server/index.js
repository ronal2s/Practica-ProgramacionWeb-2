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
  { codigoArticulo: 1, descripcion: "Keyboard", balanceActual: "5", unidadCompra: "100" },
  { codigoArticulo: 2, descripcion: "Mouse", balanceActual: "7", unidadCompra: "100" },
  { codigoArticulo: 3, descripcion: "Monitor", balanceActual: "3", unidadCompra: "2500" },
  { codigoArticulo: 4, descripcion: "Cable USB", balanceActual: "8", unidadCompra: "100" },
  { codigoArticulo: 5, descripcion: "Cable HDMI", balanceActual: "19", unidadCompra: "100" },
  { codigoArticulo: 6, descripcion: "Headphones", balanceActual: "11", unidadCompra: "800" },
  { codigoArticulo: 7, descripcion: "Alcatel", balanceActual: "23", unidadCompra: "2500" },
]

const data_articulo_suplidor = [
  { codigoArticulo: 1, codigoSuplidor: 1, tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: 1, codigoSuplidor: 2, tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: 1, codigoSuplidor: 3, tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: 2, codigoSuplidor: 1, tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: 2, codigoSuplidor: 2, tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: 2, codigoSuplidor: 3, tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: 3, codigoSuplidor: 1, tiempoEntrega: 3, precioCompra: 2100 },
  { codigoArticulo: 3, codigoSuplidor: 2, tiempoEntrega: 1, precioCompra: 2400 },
  { codigoArticulo: 3, codigoSuplidor: 3, tiempoEntrega: 5, precioCompra: 1700 },
  { codigoArticulo: 4, codigoSuplidor: 1, tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: 4, codigoSuplidor: 2, tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: 4, codigoSuplidor: 3, tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: 5, codigoSuplidor: 1, tiempoEntrega: 3, precioCompra: 50 },
  { codigoArticulo: 5, codigoSuplidor: 2, tiempoEntrega: 1, precioCompra: 85 },
  { codigoArticulo: 5, codigoSuplidor: 3, tiempoEntrega: 5, precioCompra: 50 },
  { codigoArticulo: 6, codigoSuplidor: 1, tiempoEntrega: 3, precioCompra: 700 },
  { codigoArticulo: 6, codigoSuplidor: 2, tiempoEntrega: 1, precioCompra: 750 },
  { codigoArticulo: 6, codigoSuplidor: 3, tiempoEntrega: 5, precioCompra: 600 },
  { codigoArticulo: 7, codigoSuplidor: 1, tiempoEntrega: 3, precioCompra: 2200 },
  { codigoArticulo: 7, codigoSuplidor: 2, tiempoEntrega: 1, precioCompra: 2400 },
  { codigoArticulo: 7, codigoSuplidor: 3, tiempoEntrega: 5, precioCompra: 1800 },
]


MongoClient.connect(url, function (err, db) {
  if (err) throw err;
  const dbo = db.db("test");

  createCollection(COLLECTIONS.ARTICULOS, dbo);
  deleteCollection(COLLECTIONS.ARTICULOS, dbo);
  putData(COLLECTIONS.ARTICULOS, dbo, data_articulos);
  putData(COLLECTIONS.ARTICULOS_SUPLIDOR, dbo, data_articulo_suplidor);

  app.get("/inventario", (req, res) => {
    getData(COLLECTIONS.ARTICULOS, dbo, (result) => res.send(result));
  });

  app.get("/suplidor", (req, res) => {
    getData(COLLECTIONS.ARTICULOS_SUPLIDOR, dbo, (result) => res.send(result));
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

const getData = (nameCollection, dbo, callback) => {
  dbo.collection(nameCollection).find().toArray((error, result) => {
    if (error) throw error;
    // console.log(`${nameCollection} results: `, result);
    if (callback) callback(result);
  })
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