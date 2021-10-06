/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.bot;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author 59169
 */
public class Bot extends TelegramLongPollingBot{
    private BancoRespuesta banco;

    @Override
    public String getBotToken() {
       return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getChatId().toString()+ ": " + update.getMessage().getText());
        if(update.hasMessage()){
            SendMessage message = new SendMessage();
            String id = update.getMessage().getChatId().toString();
            message.setChatId(id);
            List<String> mensajes = banco.obtenerRespuesta(update);
            if(banco.getEstadoTeclado()){
                tecladoNumeros(message);
            }
            for(int i=0; i<mensajes.size();i++){
                message.setText(mensajes.get(i));
                mandarMensaje(message);
            }  
        }
    }
    
    public void tecladoNumeros(SendMessage message) {

        // Create a keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Create a list of keyboard rows
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow primeraFila = new KeyboardRow();
        primeraFila.add(new KeyboardButton("1"));
        primeraFila.add(new KeyboardButton("2"));
        primeraFila.add(new KeyboardButton("3"));
        
        KeyboardRow segundaFila = new KeyboardRow();
        segundaFila.add(new KeyboardButton("4"));
        segundaFila.add(new KeyboardButton("5"));
        segundaFila.add(new KeyboardButton("6"));
        
        KeyboardRow tercerFila = new KeyboardRow();
        tercerFila.add(new KeyboardButton("7"));
        tercerFila.add(new KeyboardButton("8"));
        tercerFila.add(new KeyboardButton("9"));
        

        keyboard.add(primeraFila);
        keyboard.add(segundaFila);
        keyboard.add(tercerFila);

        replyKeyboardMarkup.setKeyboard(keyboard);
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
