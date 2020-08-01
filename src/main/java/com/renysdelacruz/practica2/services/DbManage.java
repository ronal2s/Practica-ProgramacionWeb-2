package com.renysdelacruz.practica2.services;

import org.h2.tools.Server;

import java.sql.SQLException;

public class DbManage {
    private static DbManage db;
    private Server tcp;

    private DbManage(){ }

    public static DbManage getInstance() {
        if(db == null){
            db = new DbManage();
        }
        return db;
    }

    public void startDB() throws SQLException {
        // Se crea el servidor
        tcp = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
    }

    public void stopDB() throws SQLException {
        // Se detiene el servidor
        //Server.shutdownTcpServer("tcp://localhost:9092", "", false, false);
        tcp.stop();
    }
}
