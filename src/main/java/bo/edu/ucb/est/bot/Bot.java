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
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author 59169
 */
public class Bot extends TelegramLongPollingBot{
    private BancoRespuesta banco;
    private List<String> stickers = List.of("",
            "CAACAgIAAxkBAAIG-WFeHBg2PJ_wV6qZvuD_4U4I2cK7AAJrAQACB4YVB_7YGzpWmOdUIQQ", //Mucho dinero
            "CAACAgEAAxkBAAIG42FeGrIn3pLc7EouLuu8M7Go-VaWAAIIAAOYAAEZT8SZsEjTzYtbIQQ", //jake
            "CAACAgIAAxkBAAIG_mFeHNjTISMlRX4LdVok8FIR2Hw_AAJsAQACB4YVB_JjxVv2ew8ZIQQ", //corazon dinero
            "CAACAgIAAxkBAAIHA2FeHQqpMlqJh7kgvDKMRXghedWBAAJ7AQACB4YVBxNydMP0kAc1IQQ",//bankarota
            "CAACAgEAAxkBAAIHCGFeHYeZG9K1epq9RRQaIJJOCpTzAAIGAANaqglMgm7zm6zHiCohBA",//despedida
            "CAACAgIAAxkBAAIHPGFeIW2U8aWXGFT_dti29qOegE98AAJ6AQACB4YVB_LBP_Q8ec-sIQQ",  //crearCuenta
            "CAACAgIAAxkBAAIHQGFeIahG7VGj1kgJQlpzyhuELBd5AAJ4AQACB4YVB_8jluzyCO7OIQQ",  //verificar monto
            "CAACAgIAAxkBAAIHd2FeIxatHFi-eHrAQtr4zrJyP9t4AAKEAQACB4YVB7SeW0xFQf2HIQQ",//no hay cuentas
            "CAACAgIAAxkBAAIHlGFeI6LUZI8xEYrzwYGz04QdYgmFAAJuAQACB4YVB8X6AjLffinDIQQ",//falla pin
            "CAACAgIAAxkBAAIHl2FeI-Nb5ET7gGtz4j4pz9jbXu9iAAKDAQACB4YVB2wfAAG72wzAyCEE"); //transaccion completa
    @Override
    public String getBotToken() {
       return "2018134799:AAGtWTi3STuR3rcOhbkMUPbRyyL7JZBYEyk";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getChatId().toString()+ ": " + update.getMessage().getText());
        System.out.println(update);
        if(update.hasMessage()){
            SendMessage message = new SendMessage();
            String id = update.getMessage().getChatId().toString();
            message.setChatId(id);
            List<String> mensajes = banco.obtenerRespuesta(update);
            if(banco.getEstadoTeclado()){
                tecladoNumeros(message);
            }
            seleccionarSticker(id,banco.getEstadoStiker());
            for(int i=0; i<mensajes.size();i++){
                message.setText(mensajes.get(i));
                mandarMensaje(message);
                
            }  
        }
    }
    
    public void seleccionarSticker(String id,int indice){
        if(indice !=0){
            mandarSticker(id,stickers.get(indice));
        }
    }
    
    public void mandarSticker(String id, String id_sticker){
        InputFile stiker = new InputFile(id_sticker);
        SendSticker mandar = new SendSticker(id, stiker);
        try{
            execute(mandar);
        }catch(TelegramApiException e){
            
        }
    }
    
    public void tecladoNumeros(SendMessage message) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

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
