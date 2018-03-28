package server.model.client.impl;

import entity.impl.Client;
import server.exceptions.DbAccessException;
import server.model.client.ClientManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class ClientManagerTxt implements ClientManager {

    private File clientsFile;
    private ArrayList<Client> clients;

    public ClientManagerTxt(String clientsFilePath) {
        clientsFile = new File(clientsFilePath);
        clients = new ArrayList<>();
    }

    @Override
    public Collection<Client> getAllClients() throws DbAccessException {
        Scanner clientScanner;
        try {
            clientScanner = new Scanner(clientsFile);
        } catch (FileNotFoundException e) {
            throw new DbAccessException("Client file access denied");
        }
        Client nextClient;
        while(clientScanner.hasNextLine() && clientScanner.hasNext()){
            nextClient = new Client(clientScanner.nextInt(), clientScanner.next(), clientScanner.next());
            clients.add(nextClient);
        }
        clientScanner.close();
        return clients;
    }

    @Override
    public void commit() throws DbAccessException {
        FileWriter clientWriter;
        try {
            clientWriter = new FileWriter(clientsFile, false);
            for(Client nextClient:clients){
                StringBuffer clientString = new StringBuffer();
                clientString.append(nextClient.getId()).append(' ');
                clientString.append(nextClient.getName()).append(' ');
                clientString.append(nextClient.getInfo()).append('\n');
                clientWriter.write(clientString.toString());
            }
            clientWriter.flush();
            clientWriter.close();
        } catch (IOException e) {
            throw new DbAccessException("Client file access denied");
        }
    }

    @Override
    public Client getClientById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addClient(Client newClient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteClient(int clientId) {
        throw new UnsupportedOperationException();
    }
}
