package net.cheesepro.announcepushserver;

import net.cheesepro.announcepushserver.api.DataSender;
import net.cheesepro.announcepushserver.api.Logger;
import net.cheesepro.announcepushserver.config.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2015-02-20.
 */
public class ThreadHandler implements Runnable{

    private Socket client = null ;
    private int count = 0;
    Config config;
//    String current = "";
//    List<String> logged = Main.logged;

    public ThreadHandler(Socket client){
        this.client = client;
        config = new Config();
    }

    public void run(){
        BufferedReader buf = null ;
        PrintStream out = null ;
        try{
            Logger.write("Client connected from " + client.getInetAddress().getHostName());
            out = new PrintStream(client.getOutputStream()) ;
            buf = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
            boolean flag = true ;
            while(flag) {
                if (count == 0) {
                    out.println("6");
                    out.println("exitcommand#" + config.get("commands.exit"));
                    out.println("inputprefix#" + config.get("prefix.input"));
                    out.println("serverprefix#" + config.get("prefix.server"));
                    out.println("requirelogin#" + config.get("variables.requirelogin"));
                    out.println("usernameprefix#" + config.get("prefix.username"));
                    out.println("passwordprefix#" + config.get("prefix.password"));
                    if (config.get("variables.requirelogin").equalsIgnoreCase("true")) {
//                        current = buf.readLine();
                        if (!buf.readLine().equalsIgnoreCase("cheesepro youmadbro2013")) {
                            out.println("Incorrect login detail!");
                            client.close();
                            flag = false;
                        } else {
//                            if (logged.contains(current)) {
//                                out.println("User is already logged in!");
//                                client.close();
//                                flag = false;
//                            } else {
                                out.println("Successfully logged in!");
//                                logged.add(current);
//                            }
                        }
                    } else {
                        out.println("Successfully Entered!");
                    }
                    count++;
                }
                try {
                    String str = buf.readLine();
                    if (!(str.equalsIgnoreCase("%"))) {
                        if (config.get("commands.exit").equals(str)) {
                            Logger.write("Client disconnected from " + client.getInetAddress().getHostName());
                            flag = false;
                        } else {
                            DataSender.sendtoAll(str);
//                            out.println(str.replaceFirst("%", ""));
                            flag = true;
                        }
                    } else {
                        out.println("Input cannot be null!");
                    }
                } catch (Exception e) {
//                    logged.remove(client.getInetAddress().getHostName());
                    Logger.write("Client disconnected from " + client.getInetAddress().getHostName());
                    flag = false;
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            Logger.write("Unexpected error!");
            e.printStackTrace();
            System.exit(0);
        }

    }

}
