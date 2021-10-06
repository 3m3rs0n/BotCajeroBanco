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

    public Cuenta(int codigo, String moneda, String tipo, double saldo) {
        this.codigo = codigo;
        this.moneda = moneda;
        this.tipo = tipo;
        this.saldo = saldo;
    }
    
    public void setTipo(String cuenta){
        this.tipo = cuenta;
    }
    
    public int getCodigo(){
        return codigo;
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
    
    public void setSaldo(double nuevoMonto){
        this.saldo = nuevoMonto;
    }
    
    public String toString(){
        return "Número: "+codigo+"\n"+
               "Tipo: "+tipo+"\n"+
               "Moneda: "+moneda+"\n"+
               "Saldo: "+saldo;
    }
    
    

    
}
