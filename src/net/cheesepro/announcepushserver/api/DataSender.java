package net.cheesepro.announcepushserver.api;

import net.cheesepro.announcepushserver.Main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by mark on 21/02/15.
 */
public class DataSender {

    static List<String> logged = Main.logged;

    public static void sendtoAll(String msg){
        try{
            for(String cache : logged){
                Socket client = new Socket(cache, 2000);
                PrintStream out = new PrintStream(client.getOutputStream()) ;
                out.println(msg);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
