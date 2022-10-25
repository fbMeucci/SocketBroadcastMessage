package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

public class ClientHandler extends Thread {
    private static int contatore = 0;
    private int id;
    private Socket s;
    private List<ClientHandler> clients;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public ClientHandler(Socket s, List<ClientHandler> clients) {
        contatore++;
        this.id = contatore;
        this.s = s;
        this.clients = clients;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String request, nomeClient = "";
        LocalDateTime dt;
        int firstSpace;

        boolean running = true;
        while (running) {
            try {
                request = in.readLine();
                // COMANDO DATA
                if (request.equalsIgnoreCase("data")) {
                    dt = LocalDateTime.now();
                    String data = dt.getDayOfMonth() + "/" + dt.getMonthValue() + "/" + dt.getYear();
                    out.println(data);
                    // COMANDO ORA
                } else if (request.equalsIgnoreCase("ora")) {
                    dt = LocalDateTime.now();
                    String ora = dt.getHour() + ":" + dt.getMinute() + ":" + dt.getSecond();
                    out.println(ora);
                    // COMANDO SERVER (nome del server)
                } else if (request.equalsIgnoreCase("server")) {
                    out.println("Il mio nome è: Serverone XYZ123");
                    // COMANDO CLIENT (segue nome del client)
                } else if (request.startsWith("client")) {
                    firstSpace = request.indexOf(" ");
                    nomeClient = "";
                    if (firstSpace != 1)
                        nomeClient = request.substring(firstSpace + 1);
                    nomeClient = nomeClient.toUpperCase();
                    out.println("Benvenuto " + nomeClient + " sei l'utente n." + id);
                    // COMANDO ECHO
                } else if (request.startsWith("echo")) {
                    String msg = "";
                    firstSpace = request.indexOf(" ");
                    if (firstSpace != 1)
                        msg = request.substring(firstSpace + 1);
                    out.println(msg);
                    // COMANDO ALL (segue messaggio in broadcast)
                } else if (request.startsWith("all")) {
                    firstSpace = request.indexOf(" ");
                    if (firstSpace != 1)
                        sendToAll(request.substring(firstSpace + 1));
                    // COMANDO CLOSE (chiusura connessione)
                } else if (request.startsWith("chiudi")) {
                    out.println("Hai chiuso la connessione");
                    running = false;
                } else
                    out.println("Comando non valido");
            } catch (IOException e) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
                out.close();
            }
        }

    }

    private void sendToAll(String msg) {
        for (ClientHandler client : clients) {
            client.out.println(msg);
        }
    }

}
