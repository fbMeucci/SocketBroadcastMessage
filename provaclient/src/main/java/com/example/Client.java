package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 3000);
        ServerConnection serverConn = new ServerConnection(s);

        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        new Thread(serverConn).start();

        while (true) {
            System.out.println("lista comandi:data, ora, client <nome>, server, echo <msg>, chiudi: ");
            String command = keyboard.readLine();

            if (command.equalsIgnoreCase("chiudi"))
                break;

            out.println(command);
            String serverResponse = in.readLine();
            System.out.println("Il server dice: " + serverResponse);
        }
        in.close();
        out.close();
        s.close();
    }
}
