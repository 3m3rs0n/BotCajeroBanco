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
public class Respuesta {
    private List<String> respuesta;
    
    public Respuesta(){
        respuesta = new ArrayList<>();
    }
   
    public List<String> respuestaPrimeraVez(String nombre){
        respuesta = List.of("Bienvenido al "+ nombre +".",
            "He notado que aún no eres cliente, procedamos a registrarte",
            "¿Cual es tu nombre completo?");
        return respuesta;
    }
    
    public List<String> RegistroPin(){
        respuesta = List.of("Por favor elige un PIN de seguridad,"+
            " este te sera requerido cada que ingreses al sistema.");
        return respuesta;
    }
    
    public List<String> registroCorrecto(){
        respuesta = List.of("Te hemos resgistrado correctamente");
        return respuesta;
    }
    
    public List<String> errorPin(){
        respuesta =  List.of("Lo siento, el código es incorrecto");
        return respuesta;
    }
    
    public List<String> menuPrincipal(){
        respuesta = List.of("Bienvenido",
                    "Elige una opción:\n"+
                    "1. Ver Saldo.\n"+
                    "2. Retirar Dinero.\n"+
                    "3. Depositar Dinero.\n"+
                    "4. Crear Cuenta.\n"+
                    "5. Salir.");
        return respuesta;
    }
    
    public List<String> sinCuentas(){
        respuesta = List.of("Usted no tiene cuentas, cree una primero");
        return respuesta;
    }
    
    public List<String> seleecionarMoneda(){
           respuesta =  List.of("Seleccione la moneda:\n"+
                        "1. Dólares\n"+
                        "2. Bolivianos\n");
           return respuesta;
    }
    
    public List<String> seleecionarTipo(){
           respuesta =  List.of("Seleccione el tipo de Cuenta:\n"+
                        "1. Cuenta Corriente\n"+
                        "2. Caja de Ahorros\n");
           return respuesta;
    }
    
    public List<String> mensajeError(){
        respuesta = List.of("La opción ingresada no es válida");
        return respuesta;
    }
    
    public List<String> construirMensajeCuentaCreada(Cuenta c){
        respuesta = List.of("Se le ha creado una "+c.getTipo()+" en "+c.getMoneda()+" con saldo 0.0, cuyo número es "+c.getCodigo());
        return respuesta;
    }
    
    public List<String> construirMenuCuentas(Usuario u){
        List<String> menu = new ArrayList<>();
        List<Cuenta> cuentas = u.getCuentas();
        String mensaje = "";
        for(int i=0;i<cuentas.size();i++){
            Cuenta c = cuentas.get(i);
            mensaje = mensaje+(i+1)+". "+c.getTipo()+" "+c.getMoneda()+"\n";
        }
        menu.add(mensaje);
        menu.add("Elija una cuenta:");
        return menu;
    }
    
    public List<String> verificarSiTieneCuentas(Usuario u, String Titulo,int estado){
        List<String> m = new ArrayList<>();
        if(u.getCuentas().isEmpty()){
            sinCuentas();
            m = new ArrayList<>(respuesta);
            menuPrincipal();
            m.addAll(respuesta);
            u.setEstadoConversacion(4);
        }else{
            u.setEstadoConversacion(estado);
            m.add(Titulo);
            m.addAll(construirMenuCuentas(u));
        }
        return m;
    }
    
    public List<String> transaccionCorrecta(){
        respuesta = List.of("Transacción completada correctamente");
        return respuesta;
    }
    
    public List<String> transaccionIncorrecta(){
        respuesta = List.of("El monto ingresado no es válido");
        return respuesta;
    }

    public List<String> construirMensajeBienvenida(Usuario u, String id){
        List<String> bienvenida = new ArrayList<>();
        bienvenida.add("Hola de nuevo "+ u.getNombre());
        bienvenida.add("Solo por seguridad ¿cuál es tu PIN?");
        return bienvenida;
    }
    
    public List<String> construirMensajeSaldo(Cuenta c){
        respuesta = List.of(c.toString());
        return respuesta;
    }
    
    public List<String> respuestaCuantoRetirar(){
        respuesta = List.of("Cuánto desea retirar:");
        return respuesta;
    }
    
    public List<String> respuestaCuantoDepositar(){
        respuesta = List.of("Cuánto desea depositar:");
        return respuesta;
    }
    
     public List<String> respuestaSaldoInsuficiente(){
        respuesta = List.of("Saldo Insuficiente");
        return respuesta;
    }
    
}
