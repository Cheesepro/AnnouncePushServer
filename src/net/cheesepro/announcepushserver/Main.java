package net.cheesepro.announcepushserver;

import net.cheesepro.announcepushserver.api.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static boolean start;

    public static void main(String args[]) throws Exception {

        ServerSocket server = null;
        Socket client = null;
        server = new ServerSocket(2000);
        start = true;
        Logger.write("Server Running... Accepting clients.");
        while (start) {
            client = server.accept();
            new Thread(new ThreadHandler(client)).start();
        }
        server.close();

    }
}
