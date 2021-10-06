/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.bot;

import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author 59169
 */
public class Bot extends TelegramLongPollingBot{
    private BancoRespuesta banco;

    @Override
    public String getBotToken() {
       return "2018134799:AAGtWTi3STuR3rcOhbkMUPbRyyL7JZBYEyk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getChatId().toString()+ ": " + update.getMessage().getText());
        if(update.hasMessage()){
            SendMessage message = new SendMessage();
            String id = update.getMessage().getChatId().toString();
            message.setChatId(id);
            List<String> mensajes = banco.obtenerRespuesta(update);
            for(int i=0; i<mensajes.size();i++){
                message.setText(mensajes.get(i));
                mandarMensaje(message);
            }  
        }
    }

    @Override
    public String getBotUsername() {
        return "Cajero_Banco_Fortuna_bot";
    }
    
    public void mandarMensaje(SendMessage m){
        try {
            execute(m); // Envia el mensaje
        } catch (TelegramApiException e) {
           
        }
    }
    
    public void setBanco(BancoRespuesta b){
        this.banco = b;
    }
    
}
