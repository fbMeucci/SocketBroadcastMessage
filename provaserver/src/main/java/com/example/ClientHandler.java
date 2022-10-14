package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket s;
    private PrintWriter pr = null;
    private BufferedReader br = null;

    public ClientHandler(Socket s) {
        this.s = s;
        try {
            // per parlare
            pr = new PrintWriter(s.getOutputStream(), true);
            // per ascoltare
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println(br.readLine());
            pr.println("Dammi il tuo peso"); // invio messaggio
            String peso = br.readLine(); // ricevo: il peso
            System.out.println("peso ricevuto");
            pr.println("Dammi l'altezza"); // invio messaggio
            String altezza = br.readLine(); // ricevo: l'altezza
            System.out.println("altezza ricevuta");

            Double bmi = Double.valueOf(peso) / (Double.valueOf(altezza) * Double.valueOf(altezza));
            pr.println(bmi); // invio il bmi
            System.out.println(br.readLine()); // leggo il saluto finale e lo metto in console

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
