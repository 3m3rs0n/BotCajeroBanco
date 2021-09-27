package bo.edu.ucb.est;

import bo.edu.ucb.est.bot.BancoRespuesta;
import bo.edu.ucb.est.bot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BancoRespuesta fortuna = new BancoRespuesta("Banco de la Fortuna");
        Bot bot = new Bot();
        bot.setBanco(fortuna);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
