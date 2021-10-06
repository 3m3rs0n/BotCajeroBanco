/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.bot;

import bo.edu.ucb.est.clases.Cuenta;
import bo.edu.ucb.est.clases.Respuesta;
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
    int numeroCuenta;
    boolean estadoTeclado;
    int estadoStiker;
   
    public BancoRespuesta(String nombre){
        this.nombre = nombre;
        this.usuarios = new HashMap<>();
        this.numeroCuenta = 100001;
        this.estadoTeclado = false;
        this.estadoStiker = 0;
    }
    
    public int getEstadoStiker(){
        return estadoStiker;
    }
    
    public boolean getEstadoTeclado(){
        return estadoTeclado;
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
        Respuesta respuesta =new Respuesta();
        String mensaje = update.getMessage().getText();
        Usuario usuario = usuarios.get(id);
        estadoTeclado = false;
        estadoStiker = 0;
        if(usuario == null){
            usuario = new Usuario();
            usuarios.put(id, usuario);
            usuario.setEstadoConversacion(1);
            mensajes = respuesta.respuestaPrimeraVez(nombre);
        }else{
            int estado = usuario.getEstadoConversacion();
            switch(estado){
                case 1: //Registro nombre, Pedir Pin
                    mensajes = respuesta.RegistroPin();
                    usuario.setEstadoConversacion(2);
                    usuario.setNombre(mensaje);
                    System.out.println(usuarios.get(id).getNombre());
                    break;
                case 2: //RegistrarPin, Mostrar registro completo, Pedir Pin
                    //TODO verificar ingreso correcto para registro de PIN
                    estadoStiker = 2;
                    usuario.setPinDeSeguridad(mensaje);
                    mensajes = new ArrayList<>(respuesta.registroCorrecto());
                    mensajes.addAll(respuesta.construirMensajeBienvenida(usuario, id));
                    usuario.setEstadoConversacion(3);
                    System.out.println(usuarios.get(id).getPinDeSeguridad());
                    break;
                case 3: //Verificar Pin. Muestra menu principal o mensaje de error
                    if(verificarPin(usuario, mensaje)){
                        mensajes = respuesta.menuPrincipal();
                        usuario.setEstadoConversacion(4);
                        estadoTeclado = true;
                        estadoStiker = 3;
                    }else{
                        mensajes = new ArrayList<>(respuesta.errorPin());
                        mensajes.addAll(respuesta.construirMensajeBienvenida(usuario, id));
                        estadoStiker = 9;
                    }
                    break;
                case 4: // Controla respuesta de menu de opciones
                    mensajes = menuOpciones(usuario, mensaje,id);
                    break;
                case 10: //mostrar cuenta seleccionada
                    if(controlarIndice(usuario, mensaje)!=0){
                        Cuenta c = usuario.getCuentas().get(controlarIndice(usuario, mensaje)-1);
                        mensajes = new ArrayList<>(respuesta.construirMensajeSaldo(c)); 
                        mensajes.addAll(respuesta.menuPrincipal());
                        usuario.setEstadoConversacion(4);
                    }else{
                        mensajes = respuesta.menuPrincipal();
                        usuario.setEstadoConversacion(4);
                    }
                    estadoTeclado = true;
                    break;
                case 20: //mostrar saldo y pedir cuanto retirar
                    if(controlarIndice(usuario, mensaje) != 0){
                        Cuenta c = usuario.getCuentas().get(controlarIndice(usuario, mensaje)-1);
                        usuario.setUltimaCuentaConsultada(c.getCodigo());
                        mensajes = new ArrayList<>(respuesta.construirMensajeSaldo(c)); 
                        mensajes.addAll(respuesta.respuestaCuantoRetirar());
                        usuario.setEstadoConversacion(21);
                    }else{
                        mensajes = respuesta.menuPrincipal();
                        usuario.setEstadoConversacion(4);
                        estadoTeclado = true;
                    }
                    break;
                case 21: //verificar cuanto retirar y luego mostrar menu principal
                    if(verificarMontoRetiro(usuario, mensaje)){
                        mensajes = new ArrayList<>(respuesta.transaccionCorrecta());
                        mensajes.addAll(respuesta.menuPrincipal());
                        usuario.setEstadoConversacion(4);
                        estadoTeclado = true;
                        estadoStiker = 10;
                    }else{
                        Cuenta c = usuario.obtenerCuenta(usuario.getUltimaCuentaConsultada());
                        if(c.getSaldo()==0 || Double.parseDouble(mensaje)>c.getSaldo()){
                            mensajes = new ArrayList<>(respuesta.respuestaSaldoInsuficiente());
                            mensajes.addAll(respuesta.menuPrincipal());
                            usuario.setEstadoConversacion(4);
                            estadoStiker = 4;
                            estadoTeclado = true;
                        }else{
                            mensajes = new ArrayList<>(respuesta.transaccionIncorrecta());
                            mensajes.addAll(respuesta.respuestaCuantoRetirar()); 
                            estadoStiker = 7;
                        }                       
                    }
                    break;
                case 30: //mostrar saldo y pedir cuanto depositar
                    if(controlarIndice(usuario, mensaje)!=0){
                        Cuenta c = usuario.getCuentas().get(controlarIndice(usuario, mensaje)-1);
                        usuario.setUltimaCuentaConsultada(c.getCodigo());
                        mensajes = new ArrayList<>(respuesta.construirMensajeSaldo(c)); 
                        mensajes.addAll(respuesta.respuestaCuantoDepositar());
                        usuario.setEstadoConversacion(31);
                    }else{
                        mensajes = respuesta.menuPrincipal();
                        estadoTeclado = true;
                        usuario.setEstadoConversacion(4);
                    }
                    break;
                case 31: //verificar cuanto depositar y mostrar menu principal
                    if(verificarMontoDeposito(usuario, mensaje)){
                        mensajes = new ArrayList<>(respuesta.transaccionCorrecta());
                        mensajes.addAll(respuesta.menuPrincipal());
                        usuario.setEstadoConversacion(4);
                        estadoStiker = 10;
                        estadoTeclado = true;
                    }else{
                        mensajes = new ArrayList<>(respuesta.transaccionIncorrecta());
                        mensajes.addAll(respuesta.respuestaCuantoDepositar());
                        estadoStiker = 7;
                    }
                    break;
                case 40: //Obtener tipo de moneda para crear cuenta
                    mensajes = crearCuenta(usuario, mensaje);
                    estadoTeclado = true;
                    break;
                case 41: //Ingresa el tipo de cuenta que se selecciono
                    mensajes = indicarTipoCuenta(usuario, mensaje);
                    estadoTeclado = true;
                    break;
                
                default:
                    break;
            }
        }
        return mensajes;
    }
    
    public int controlarIndice(Usuario u, String m){
        int ind = (int) verificarIndice(m);
        if(ind>u.getCuentas().size() ){
            ind = 0;
        }
        return ind;
    }
    
    public boolean verificarPin(Usuario u, String pin){
        boolean flag = false;
        if(u.getPinDeSeguridad().equals(pin)){
            flag = true;
        }
        return flag;
    }
    
    public double verificarIndice(String m){
        double opc;
        try{
            opc = Double.parseDouble(m);
        }catch (NumberFormatException e){
            opc = 0;
        }
        return opc;
    }
    
    public List menuOpciones(Usuario u,String m, String id){
        int opc = (int) verificarIndice(m);
        List<String> menu = new ArrayList<>();
        Respuesta respuesta = new Respuesta();
        switch (opc){
            case 1: //Ver saldo
                menu = respuesta.verificarSiTieneCuentas(u, "Ver Saldo",10);
                estadoTeclado = true;
                break;
            case 2: //Retirar Dinero
                menu = respuesta.verificarSiTieneCuentas(u, "Retirar Dinero",20);
                estadoTeclado = true;
                break;
            case 3: // Depositar dinero
                menu = respuesta.verificarSiTieneCuentas(u, "Depositar Dinero",30);
                estadoTeclado = true;
                break;
            case 4: // Crear Cuenta
                menu = respuesta.seleecionarMoneda();
                u.setEstadoConversacion(40);
                estadoTeclado = true;
                break;
            case 5: // Salir
                menu = respuesta.construirMensajeBienvenida(u, id);
                u.setEstadoConversacion(3);
                estadoStiker = 5;
                break;
            default:
                menu = respuesta.menuPrincipal();
                u.setEstadoConversacion(4);
                estadoTeclado = true;
                break;
        }
        return menu;
    }
    
     public List<String> crearCuenta(Usuario u, String mensaje){
        int opc = (int) verificarIndice(mensaje);
        List<String> res ;
        Respuesta respuesta = new Respuesta();
        switch(opc){
            case 1: //crear cuenta en dolares
                Cuenta cuenta = new Cuenta(numeroCuenta, "Dólares", "",0);
                u.setUltimaCuentaConsultada(numeroCuenta);
                u.agregarCuenta(cuenta);
                numeroCuenta++;
                res = respuesta.seleecionarTipo();
                u.setEstadoConversacion(41);
                break;
            case 2: //crear cuenta en bolivianos
                Cuenta c = new Cuenta(numeroCuenta, "Bolivianos", "",0);
                u.agregarCuenta(c);
                u.setUltimaCuentaConsultada(numeroCuenta);
                numeroCuenta++;
                res = respuesta.seleecionarTipo();
                u.setEstadoConversacion(41);
                break;
            default:
                res = respuesta.menuPrincipal();
                u.setEstadoConversacion(4);
                break;
                
        }
        return res;
    }
    
    public List<String> indicarTipoCuenta(Usuario u, String mensaje){
        int opc;
        int codigo;
        List<String> res;
        Respuesta respuesta = new Respuesta();
        try{
            opc = Integer.parseInt(mensaje);
        }catch (NumberFormatException e){
            opc = 0;
        }
        switch(opc){
            case 1: //ingresar Cuenta corriente mostrar mensaje de cuenta creada
                codigo = u.getUltimaCuentaConsultada();
                Cuenta cuenta = u.obtenerCuenta(codigo);
                cuenta.setTipo("Cuenta Corriente");
                res = new ArrayList<>(respuesta.construirMensajeCuentaCreada(cuenta));
                res.addAll(respuesta.menuPrincipal());
                u.setEstadoConversacion(4);
                estadoStiker = 6;
                break;
            case 2: //ingresar caja de ahorros mostrar mensaje de cuenta creada
                codigo = u.getUltimaCuentaConsultada();
                Cuenta c = u.obtenerCuenta(codigo);
                c.setTipo("Caja de Ahorros");
                res = new ArrayList<>(respuesta.construirMensajeCuentaCreada(c));
                res.addAll(respuesta.menuPrincipal());
                u.setEstadoConversacion(4);
                estadoStiker = 6;
                break;
            default:
                res = new ArrayList<>(respuesta.mensajeError());
                res.addAll(respuesta.seleecionarTipo());
                estadoStiker = 0;
                break;
        }
        return res;
    }
    
    public boolean verificarMontoRetiro(Usuario u, String m){
        boolean flag = false;
        double cantidad = verificarIndice(m);
        Cuenta c = u.obtenerCuenta(u.getUltimaCuentaConsultada());
        double saldo = c.getSaldo();
        if(cantidad > 0 && cantidad<saldo){
            c.setSaldo(saldo-cantidad);
            flag = true;
        }
        return flag;
    }
    
    public boolean verificarMontoDeposito(Usuario u, String m){
        boolean flag = false;
        double cantidad = verificarIndice(m);
        Cuenta c = u.obtenerCuenta(u.getUltimaCuentaConsultada());
        double saldo = c.getSaldo();
        if(cantidad > 0){
            c.setSaldo(saldo+cantidad);
            flag = true;
        }
        return flag;
    }
    

}
