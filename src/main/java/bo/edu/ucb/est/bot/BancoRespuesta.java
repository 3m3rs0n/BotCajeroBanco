/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.bot;

import bo.edu.ucb.est.clases.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author 59169
 */
public class BancoRespuesta {
    String nombre;
    Map<String,Usuario> usuarios;
    List<String> primerVezUsuario = List.of("Bienvenido al "+ "banco de la fortuna"+".",
            "He notado que aún no eres cliente, procedamos a registrarte",
            "¿Cual es tu nombre completo?");
    List<String> pedirRegistroPin = List.of("Por favor elige un PIN de seguridad,"+
            " este te sera requerido cada que ingreses al sistema.");
    List<String> registroCorrecto = List.of("Te hemos resgistrado correctamente");
    List<String> errorPin =  List.of("Lo siento, el código es incorrecto");
    List<String> menuPrincipal = List.of("Bienvenido",
            "Elige una opción:\n"+
                    "1. Ver Saldo.\n"+
                    "2. Retirar Dinero.\n"+
                    "3. Depositar Dinero.\n"+
                    "4. Crear Cuenta.\n"+
                    "5. Salir.");
    List<String> sinCuentas = List.of("Usted no tiene cuentas, cree una primero");
    List<String> seleccionarMoneda =  List.of("Seleccione la moneda:\n"+
            "1. Dólares"+
            "2. Bolivianos");
    
    
    public BancoRespuesta(String nombre){
        this.nombre = nombre;
        this.usuarios = new HashMap<>();
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public void agregarUsuario(Usuario u){
        usuarios.put(nombre, u);
    }
    
    public List<String> obtenerRespuesta(Update update){
        List<String> mensajes = new ArrayList();
        String id = update.getMessage().getChatId().toString();
        String mensaje = update.getMessage().getText();
        Usuario usuario = usuarios.get(id);
        if(usuario == null){
            usuario = new Usuario();
            usuarios.put(id, usuario);
            usuario.setEstadoConversacion(1);
            mensajes = primerVezUsuario;
        }else{
            int estado = usuario.getEstadoConversacion();
            switch(estado){
                case 1: //Registro nombre, Pedir Pin
                    mensajes = pedirRegistroPin;
                    usuario.setEstadoConversacion(2);
                    usuario.setNombre(mensaje);
                    System.out.println(usuarios.get(id).getNombre());
                    break;
                case 2: //RegistrarPin, Mostrar registro completo, Pedir Pin
                    //TODO verificar ingreso correcto de PIN
                    usuario.setPinDeSeguridad(mensaje);
                    mensajes = new ArrayList<>(registroCorrecto);
                    mensajes.addAll(construirMensajeBienvenida(id));
                    usuario.setEstadoConversacion(3);
                    System.out.println(usuarios.get(id).getPinDeSeguridad());
                    break;
                case 3: //Verificar Pin
                    if(verificarPin(usuario, mensaje)){
                        mensajes = menuPrincipal;
                        usuario.setEstadoConversacion(4);
                    }else{
                        mensajes = errorPin;
                        mensajes.addAll(construirMensajeBienvenida(id));
                    }
                    break;
                case 4: // Menu de opciones
                    mensajes = menuPrincipal;
                    break;
                default:
                    break;
            }
        }
        return mensajes;
    }
    
    public boolean verificarPin(Usuario u, String pin){
        boolean flag = false;
        if(u.getPinDeSeguridad().equals(pin)){
            flag = true;
        }
        return flag;
    }
    
    public List<String> construirMensajeBienvenida(String id){
        List<String> bienvenida = new ArrayList<>();
        Usuario u = usuarios.get(id);
        bienvenida.add("Hola de nuevo "+ u.getNombre());
        bienvenida.add("Solo por seguridad ¿cuál es tu PIN?");
        return bienvenida;
    }
}
