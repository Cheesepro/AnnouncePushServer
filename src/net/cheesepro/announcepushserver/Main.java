package net.cheesepro.announcepushserver;

import net.cheesepro.announcepushserver.api.Logger;
import net.cheesepro.announcepushserver.config.Config;

import java.io.File;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static boolean start;
    public static List<String> logged = new ArrayList<String>();

    public static void main(String args[]) throws Exception {

        Config config = new Config();


        File f = new File("APS.properties");
        if(!f.exists()) {
            config.set("prefix.input", "Input>");
            config.set("prefix.server", "Server>");
            config.set("prefix.username", "Username>");
            config.set("prefix.password", "Passsword>");
            config.set("commands.exit", "quit");
            config.set("variables.requirelogin", "true");
        }

        ServerSocket server = null;
        Socket client = null;
        server = new ServerSocket(2000);
        start = true;
        Logger.write("Server Running... Accepting clients.");
        while (start) {
            client = server.accept();
            logged.add(client.getInetAddress().getHostName());
            new Thread(new ThreadHandler(client)).start();
        }
        server.close();

    }

    public List<String> getLogged(){
        return logged;
    }
}
