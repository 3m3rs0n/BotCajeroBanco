/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.clases;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 59169
 */
public class Usuario {
    private String Nombre;
    private String PinDeSeguridad;
    private int estadoConversacion;
    private Map<Integer,Cuenta> cuentas;

    public Usuario(String Nombre, String PinDeSeguridad) {
        this.Nombre = Nombre;
        this.PinDeSeguridad = PinDeSeguridad;
        this.cuentas = new HashMap<>();
        this.estadoConversacion = 1;
    }
    
    public Usuario(){
        this.Nombre = "";
        this.PinDeSeguridad = "";
        this.cuentas = new HashMap<>();
        this.estadoConversacion = 0;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getPinDeSeguridad() {
        return PinDeSeguridad;
    }

    public Map<Integer, Cuenta> getCuentas() {
        return cuentas;
    }
    
    public int getEstadoConversacion(){
        return estadoConversacion;
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
    
    public void agregarCuenta(int clave, Cuenta C){
        cuentas.put(clave, C);
    }
    
    
    
    
    
}
