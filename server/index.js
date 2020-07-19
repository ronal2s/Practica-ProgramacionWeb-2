//Renys De La Cruz - 20160207
//Regis Báez       - 2014-0324
const express = require("express");
const bodyParser = require("body-parser");
const app = express();
const router = express.Router();
const port = process.env.PORT || 5000;
const { Db } = require("mongodb");

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/mydb";

const { COLLECTIONS } = require("./utils/constants");


const data_articulos = [
  { codigoArticulo: "1", descripcion: "Keyboard", balanceActual: "5", unidadCompra: "100" },
  { codigoArticulo: "2", descripcion: "Mouse", balanceActual: "7", unidadCompra: "100" },
  { codigoArticulo: "3", descripcion: "Monitor", balanceActual: "3", unidadCompra: "2500" },
  { codigoArticulo: "4", descripcion: "Cable USB", balanceActual: "8", unidadCompra: "100" },
  { codigoArticulo: "5", descripcion: "Cable HDMI", balanceActual: "19", unidadCompra: "100" },
  { codigoArticulo: "6", descripcion: "Headphones", balanceActual: "11", unidadCompra: "800" },
  { codigoArticulo: "7", descripcion: "Alcatel", balanceActual: "23", unidadCompra: "2500" },
]


MongoClient.connect(url, function (err, db) {
  if (err) throw err;
  const dbo = db.db("test");

  createCollection(COLLECTIONS.ARTICULOS, dbo);
  deleteCollection(COLLECTIONS.ARTICULOS, dbo);
  putData(COLLECTIONS.ARTICULOS, dbo, data_articulos);

  app.get("/inventario", (req, res) => {
    getData(COLLECTIONS.ARTICULOS, dbo, (result) => res.send(result));
  })

  // app.route("/inventario")
  //   .get((req, res) => {

  //   })
  //   .post((req, res) => {
  //     const obj = req.body;
  //     putData(COLLECTIONS.ARTICULOS, dbo, obj, (result) => res.sendStatus(200))
  //   })

  getData(COLLECTIONS.ARTICULOS, dbo);
  // db.close();
});

const deleteCollection = (nameCollection, dbo, callback) => {
  dbo.collection(nameCollection).remove()
    .then(result => { if (callback) callback(result); })
    .catch(error => console.error(error));

}

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


app.get("/", (req, res) => {
  res.render("index");
});


app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());


app.listen(port, () => {
  console.log("Server is running");
});