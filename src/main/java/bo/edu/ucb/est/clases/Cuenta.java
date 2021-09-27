/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.clases;

/**
 *
 * @author 59169
 */
public class Cuenta {
    private int codigo;
    private String moneda;
    private String tipo;
    private double saldo;

    public Cuenta(String moneda, String tipo, double saldo) {
        this.moneda = moneda;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getTipo() {
        return tipo;
    }

    public double getSaldo() {
        return saldo;
    }
    
    
    public int generarCodigo(){
        return 0;
    }
    
}
