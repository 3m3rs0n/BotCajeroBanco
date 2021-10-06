/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 59169
 */
public class Usuario {
    private String Nombre;
    private String PinDeSeguridad;
    private int estadoConversacion;
    private List<Cuenta> cuentas;
    private int ultimaCuentaConsultada;

    public Usuario(String Nombre, String PinDeSeguridad) {
        this.Nombre = Nombre;
        this.PinDeSeguridad = PinDeSeguridad;
        this.cuentas = new ArrayList();
        this.estadoConversacion = 1;
        this.ultimaCuentaConsultada = 0;
    }
    
    public Usuario(){
        this.Nombre = "";
        this.PinDeSeguridad = "";
        this.cuentas = new ArrayList();
        this.estadoConversacion = 1;
        this.ultimaCuentaConsultada = 0;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getPinDeSeguridad() {
        return PinDeSeguridad;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }
    
    public int getEstadoConversacion(){
        return estadoConversacion;
    }
    
    public int getUltimaCuentaConsultada(){
        return ultimaCuentaConsultada;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setPinDeSeguridad(String PinDeSeguridad) {
        this.PinDeSeguridad = PinDeSeguridad;
    }
        
    public void setEstadoConversacion(int n){
        estadoConversacion = n;
    }
    
    public void setUltimaCuentaConsultada(int numeroCuenta){
        this.ultimaCuentaConsultada = numeroCuenta;
    }
    
    public void agregarCuenta(Cuenta C){
        cuentas.add(C);
    }
    
    public Cuenta obtenerCuenta(int codigo){
        Cuenta c = null;
        for(int i=0;i<cuentas.size();i++){
            if(cuentas.get(i).getCodigo() == codigo){
                c = cuentas.get(i);
            }
        }
        return c;
    }   
    
    
    
}
