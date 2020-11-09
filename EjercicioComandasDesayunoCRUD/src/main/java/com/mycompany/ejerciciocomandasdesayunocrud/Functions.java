/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ejerciciocomandasdesayunocrud;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Pablo Martínez y Santiago Ucero. 2ºDAM Cesur. Acceso a Datos.
 */
public class Functions {

    //Pilla el día actual.
    public boolean isFromToday(Date fechaComanda) {

        boolean fromToday;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();

        if (sdf.format(currentDate).equals(sdf.format(fechaComanda))) {
            fromToday = true;
        } else {
            fromToday = false;
        }

        return fromToday;
    }

    //Suma el precio si se pide una comida y una bebida
    //y el precio total aparecerá en la lista de comandas.
    double precioTotal = 0;

    public void resetPrecioTotal() {
        this.precioTotal = 0;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setBebida(String bebida) {

        switch (bebida) {
            case "Café Solo":
                this.precioTotal += 0.8;
                break; // Café Solo
            case "Café Latte":
                this.precioTotal += 1;
                break;   // Café Latte
            case "Colacao":
                this.precioTotal += 1;
                break; // Colacao
            case "Refresco":
                this.precioTotal += 1.5;
                break; // Colacao
            case "Botella agua":
                this.precioTotal += 1.5;
                break; // Colacao
            default:
                break;
        }
    }

    public void setComida(String comida) {

        switch (comida) {
            case "Pitufo Mixto":
                this.precioTotal += 1.5;
                break; // Pitufo Mixto
            case "Pitufo Bacon":
                this.precioTotal += 1.7;
                break; // Pitufo Bacon
            case "Pitufo Lomo":
                this.precioTotal += 2;
                break;   // Pitufo Lomo 
            case "Bocata Paté":
                this.precioTotal += 2.5;
                break; // Bocata Paté
            case "Bocata Bacon":
                this.precioTotal += 3;
                break;   // Bocata Bacon 
            case "Bocata Chorizo":
                this.precioTotal += 3.5;
                break; // Bocata Chorizo
            default:
                break;
        }
    }

    //La carta.
    public void showMenu() {
        System.out.println(
                "|------- BEBIDAS --------|\n"
                + "| Café Solo      - 0'80€ |\n"
                + "| Café Latte     - 1'00€ |\n"
                + "| Refresco       - 1'50€ |\n"
                + "| Colacao        - 1'50€ |\n"
                + "| Botella agua   - 1'50€ |\n"
                + "|------------------------|\n"
                + "\n"
                + "|------- COMIDAS --------|\n"
                + "| Pitufo Mixto   - 1'50€ |\n"
                + "| Pitufo Bacon   - 1'70€ |\n"
                + "| Pitufo Lomo    - 2'00€ |\n"
                + "| Bocata Paté    - 2'50€ |\n"
                + "| Bocata Bacon   - 3'00€ |\n"
                + "| Bocata Chorizo - 3'50€ |\n"
                + "|------------------------|"
        );
    }

    public void showMenuComidas() {
        System.out.println(
                "|------- COMIDAS --------|\n"
                + "| Pitufo Mixto   - 1'50€ |\n"
                + "| Pitufo Bacon   - 1'70€ |\n"
                + "| Pitufo Lomo    - 2'00€ |\n"
                + "| Bocata Paté    - 2'50€ |\n"
                + "| Bocata Bacon   - 3'00€ |\n"
                + "| Bocata Chorizo - 3'50€ |\n"
                + "|------------------------|"
        );
    }

    public void showMenuBebidas() {
        System.out.println(
                "|------- BEBIDAS --------|\n"
                + "| Café Solo      - 0'80€ |\n"
                + "| Café Latte     - 1'00€ |\n"
                + "| Refresco       - 1'50€ |\n"
                + "| Colacao        - 1'50€ |\n"
                + "| Botella agua   - 1'50€ |\n"
                + "|------------------------|\n"
        );
    }

}
