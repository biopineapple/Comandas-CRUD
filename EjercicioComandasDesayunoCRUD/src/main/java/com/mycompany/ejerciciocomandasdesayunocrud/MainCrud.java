/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ejerciciocomandasdesayunocrud;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author Pablo Martínez y Santiago Ucero. 2ºDAM Cesur. Acceso a Datos.
 */
public class MainCrud {

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        Properties params = new Properties();
        params.load(new FileReader("bbdd.cfg"));

        String protocol = params.getProperty("protocol");
        String server = params.getProperty("server");
        String port = params.getProperty("port");
        String database = params.getProperty("database");
        String extra = params.getProperty("extra");
        String user = params.getProperty("user");
        String password = params.getProperty("password");

        String url = protocol + server + ":" + port + "/" + database + "?" + extra;
        Functions funciones = new Functions();

        try ( Connection c = DriverManager.getConnection(url, user, password)) {

            System.out.println("Conexión establecida");

            /*
            Primero, que nos muestre la tabla de pedidos.
            Segundo, tener la opción de crear un pedido nuevo, y
            editar o borrar uno existente.
            Tercero, que muestre solo los pedidos del día de hoy.
            Cuarto, que muestre solo los pedidos aún sin entregar.        
             */
            Scanner sc = new Scanner(System.in);
            //Comienza el escáner y aparecen las primeras dos opciones principales.
            boolean salir = false;
            while (salir != true) {
                System.out.println("\nBienvenido a Desayunópolis.");
                System.out.println("¿Qué quieres hacer?"
                        + "\n 1. Mostrar/Crear/Editar/Borrar pedido(s)."
                        + "\n 2. Ver pedidos de hoy/Ver pedidos sin entregar."
                        + "\n 3. Salir.");
                //Tras elegir una opción, se nos mostrará nuevas opciones.
                int primeraRespuesta = sc.nextInt();
                switch (primeraRespuesta) {
                    //Opción de ver la lista completa de pedidos.
                    //Opciones de creación, edición y eliminación de un pedido.
                    case 1:
                        System.out.println("¿Qué quieres hacer?"
                                + "\n 1. Ver lista de pedidos."
                                + "\n 2. Hacer un pedido."
                                + "\n 3. Editar un pedido."
                                + "\n 4. Borrar un pedido."
                                + "\n 5. Salir.");
                        int pedidosOpciones = sc.nextInt();
                        switch (pedidosOpciones) {
                            //Muestra todos los pedidos.
                            case 1:
                                System.out.println("\nEsta es la lista de pedidos: ");
                                String selectSQL = "SELECT * FROM comandasdesayunos";
                                Statement ss = c.createStatement();
                                ResultSet salida = ss.executeQuery(selectSQL);
                                while (salida.next()) {
                                    System.out.println(salida.getString("id")
                                            + ", " + salida.getString("comida")
                                            + ", " + salida.getString("bebida")
                                            + ", " + salida.getString("cliente")
                                            + ", " + salida.getString("precio")
                                            + ", " + salida.getString("pendientes"));
                                }
                                break;
                            //Crea un pedido.    
                            case 2:
                                System.out.println("\nHaz un pedido: ");
                                String insertSQL = ("INSERT INTO comandasdesayunos"
                                        + "(comida,bebida,cliente,precio,pendientes) VALUES (?,?,?,?,?)");
                                PreparedStatement ps = c.prepareStatement(insertSQL);

                                funciones.resetPrecioTotal(); //Resetea el precio a 0.

                                System.out.println("\n");
                                Functions functionComida = new Functions();
                                functionComida.showMenuComidas();
                                System.out.println("\n¿Qué vas a comer?");
                                String comida = sc.nextLine();
                                comida = sc.nextLine();
                                funciones.setComida(comida);

                                System.out.println("\n");
                                Functions functionBebida = new Functions();
                                functionBebida.showMenuBebidas();
                                System.out.println("\n¿Qué vas a beber?");
                                String bebida = sc.nextLine();
                                funciones.setBebida(bebida);

                                System.out.println("\n¿Cuál es tu nombre?");
                                String cliente = sc.nextLine();

                                ps.setString(1, comida);
                                ps.setString(2, bebida);
                                ps.setString(3, cliente);
                                ps.setDouble(4, funciones.getPrecioTotal());
                                ps.setInt(5, 0);
                                ps.execute();
                                System.out.println("¡Pedido añadido!");
                                break;
                            //Edita un pedido.    
                            case 3:
                                System.out.println("\nEdita un pedido: ");
                                System.out.println("\n¿Qué pedido quieres editar?");
                                String select2SQL = "SELECT * FROM comandasdesayunos";
                                Statement ss2 = c.createStatement();
                                ResultSet salida2 = ss2.executeQuery(select2SQL);
                                while (salida2.next()) {
                                    System.out.println(salida2.getString("id")
                                            + ", " + salida2.getString("comida")
                                            + ", " + salida2.getString("bebida")
                                            + ", " + salida2.getString("cliente")
                                            + ", " + salida2.getString("precio")
                                            + ", " + salida2.getString("pendientes"));
                                }
                                //Selecciona pedido según la id.
                                String select3SQL = ("SELECT * FROM comandasdesayunos WHERE id = ?");
                                PreparedStatement stSelect3 = c.prepareStatement(select3SQL);
                                int pedidoId = sc.nextInt();
                                System.out.println("\nPedido seleccionado: ");
                                stSelect3.setInt(1, pedidoId);
                                ResultSet st1 = stSelect3.executeQuery();
                                st1.next();
                                System.out.println(st1.getString("id")
                                        + ", " + st1.getString("comida")
                                        + ", " + st1.getString("bebida")
                                        + ", " + st1.getString("cliente")
                                        + ", " + st1.getString("precio")
                                        + ", " + st1.getString("pendientes"));
                                System.out.println("\n¿Qué quieres hacer?"
                                        + "\n 1. Continuar."
                                        + "\n 2. Salir.");
                                int editorRespuesta2 = sc.nextInt();
                                switch (editorRespuesta2) {
                                    //Cambia la comida y la bebida.
                                    case 1:
                                        funciones.resetPrecioTotal();
                                        System.out.println("\nEsta es la carta.");
                                        Functions function3 = new Functions();
                                        function3.showMenu();

                                        System.out.println("\nEdita tu pedido.");
                                        String pedidoSQL = ("UPDATE comandasdesayunos SET comida = ?, bebida = ?,precio = ? WHERE id=?");

                                        PreparedStatement stPedido = c.prepareStatement(pedidoSQL);

                                        System.out.println("\nEdita tu comida.");
                                        String cambioPedidoComida = sc.nextLine();
                                        cambioPedidoComida = sc.nextLine();
                                        System.out.println("\nEdita tu bebida.");
                                        String cambioPedidoBebida = sc.nextLine();

                                        funciones.setComida(cambioPedidoComida);
                                        funciones.setBebida(cambioPedidoBebida);

                                        stPedido.setString(1, cambioPedidoComida);
                                        stPedido.setString(2, cambioPedidoBebida);
                                        stPedido.setDouble(3, funciones.getPrecioTotal());
                                        stPedido.setInt(4, pedidoId);
                                        stPedido.executeUpdate();
                                        System.out.println("¡Pedido editado!");
                                        break;
                                    //Devuelve al menú principal.    
                                    case 4:
                                        System.out.println("Has cancelado la operación.");
                                        break;
                                }
                                break;
                            //Muestra la lista para elegir un pedido.
                            //Dicho pedido será eliminado.
                            case 4:
                                System.out.println("\nBorra un pedido: ");
                                System.out.println("\n¿Qué pedido quieres borrar?");
                                String select4SQL = "SELECT * FROM comandasdesayunos";
                                Statement ss4 = c.createStatement();
                                ResultSet salida4 = ss4.executeQuery(select4SQL);
                                while (salida4.next()) {
                                    System.out.println(salida4.getString("id")
                                            + ", " + salida4.getString("comida")
                                            + ", " + salida4.getString("bebida")
                                            + ", " + salida4.getString("cliente")
                                            + ", " + salida4.getString("precio")
                                            + ", " + salida4.getString("pendientes"));
                                }

                                String deleteSQL = ("DELETE FROM comandasdesayunos WHERE id = ?");
                                PreparedStatement stDelete = c.prepareStatement(deleteSQL);
                                int numId2 = sc.nextInt();
                                stDelete.setInt(1, numId2);
                                int resSet = stDelete.executeUpdate();
                                System.out.println("¡Pedido eliminado!");
                                break;
                            //Devuelve al menú principal.
                            case 5:
                                System.out.println("Has cancelado la operación.");
                                break;
                            default:
                                System.out.println("Esa opción no está disponible");
                                break;
                        }

                        break;
                    //Opciones de ver los pedidos del día de hoy y los que no
                    //se han entregado aún.
                    case 2:
                        System.out.println("¿Qué quieres hacer?"
                                + "\n 1. Ver pedidos de hoy"
                                + "\n 2. Ver pedidos sin entregar"
                                + "\n 3. Salir.");
                        int pedidosVisualizar = sc.nextInt();
                        switch (pedidosVisualizar) {
                            //Muestra los pedidos del día.
                            case 1:
                                System.out.println("\nEstos son los pedidos de hoy: ");
                                String todaySQL = ("SELECT * FROM comandasdesayunos WHERE DATE (fecha) = CURRENT_DATE");
                                Statement stToday = c.createStatement();
                                ResultSet salidaToday = stToday.executeQuery(todaySQL);
                                while (salidaToday.next()) {
                                    System.out.println(salidaToday.getString("id")
                                            + ", " + salidaToday.getString("comida")
                                            + ", " + salidaToday.getString("bebida")
                                            + ", " + salidaToday.getString("cliente")
                                            + ", " + salidaToday.getString("precio")
                                            + ", " + salidaToday.getString("pendientes"));
                                }
                                break;
                            //Muestra los pedidos sin entregar.
                            case 2:
                                System.out.println("\nEstos son los pedidos que faltan por entregar: ");
                                String givenSQL = ("SELECT * FROM comandasdesayunos WHERE pendientes = 0");
                                Statement stGiven = c.createStatement();
                                ResultSet salidaGiven = stGiven.executeQuery(givenSQL);
                                System.out.println("\n------- COMANDAS SIN ENTREGAR -------");
                                while (salidaGiven.next()) {
                                    System.out.println(salidaGiven.getString("id")
                                            + ", " + salidaGiven.getString("comida")
                                            + ", " + salidaGiven.getString("bebida")
                                            + ", " + salidaGiven.getString("cliente")
                                            + ", " + salidaGiven.getString("precio")
                                            + ", " + salidaGiven.getString("pendientes"));
                                }
                                break;
                            //Devuelve al menú principal.    
                            case 3:
                                System.out.println("Has cancelado la operación.");
                                break;
                            default:
                                System.out.println("Esa opción no está disponible.");
                                break;
                        }
                        break;
                    //Cierra el escáner.    
                    case 3:
                        System.out.println("¡Hasta la próxima!");
                        salir = true;
                        break;
                    default:
                        System.out.println("Esa opción no está disponible.");
                        break;
                }

            }

        } catch (SQLException ex) {
            muestraErrorSQL(ex);
        }

    }

    public static void muestraErrorSQL(SQLException e) {
        System.out.println("Error SQL: " + e.getMessage());
        System.out.println("Estado: " + e.getSQLState());
        System.out.println("Código: " + e.getErrorCode());

    }
}
