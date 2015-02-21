package net.cheesepro.announcepushserver;

import net.cheesepro.announcepushserver.api.Logger;
import net.cheesepro.announcepushserver.config.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Mark on 2015-02-20.
 */
public class ThreadHandler implements Runnable{

    private Socket client = null ;
    private int count = 0;
    Config config;

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
            while(flag){
                if(count==0){
                    out.println("5");
                    out.println("exitcommand#"+config.get("commands.exit"));
                    out.println("inputprefix#"+config.get("prefix.input"));
                    out.println("serverprefix#"+config.get("prefix.server"));
                    out.println("requirelogin#"+config.get("variables.requirelogin"));
                    out.println("usernameprefix#"+config.get("prefix.username"));
                    out.println("passwordprefix#" + config.get("prefix.password"));
                    if(config.get("variables.requirelogin").equalsIgnoreCase("true")){
                        if(!buf.readLine().equalsIgnoreCase("cheesepro youmadbro2013")){
                            out.println("Incorrect login detail!");
                            client.close();
                            flag = false;
                        }else{
                            out.println("Successfully logged in!");
                        }
                    }else{
                        out.println("Successfully logged in!");
                    }
                    count++;
                }
                String str = buf.readLine() ;
                if(str==null||"".equals(str)){
                    flag = false ;
                }else{
                    if(config.get("commands.exit").equals(str)){
                        Logger.write("Client disconnected from " + client.getInetAddress().getHostName());
                        flag = false ;
                    }else{
                        out.println(str) ;
                    }
                }
            }
        }catch (Exception e){

        }

    }

}
