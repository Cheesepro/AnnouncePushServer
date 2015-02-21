package net.cheesepro.announcepushserver;

import net.cheesepro.announcepushserver.api.Logger;

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

    public ThreadHandler(Socket client){
        this.client = client;
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
                    out.println("Input> ");
                    out.println("Server> ");
                    out.println("quit");
                    out.println("true");
                    out.println("Username> ");
                    out.println("Password> ");
                    if(!buf.readLine().equalsIgnoreCase("cheesepro youmadbro2013")){
                        out.println("Incorrect login detail!");
                        client.close();
                        flag = false;
                    }else{
                        out.println("Successfully logged in!");
                    }
                }
                String str = buf.readLine() ;		// ���տͻ��˷��͵�����
                if(str==null||"".equals(str)){	// ��ʾû������
                    flag = false ;	// �˳�ѭ��
                }else{
                    if("quit".equals(str)){	// ������������Ϊbye��ʾ����
                        Logger.write("Client disconnected from " + client.getInetAddress().getHostName());
                        flag = false ;
                    }else{
                        out.println(str) ;	// ��Ӧ��Ϣ
                    }
                }
            }
        }catch (Exception e){

        }

    }

}
