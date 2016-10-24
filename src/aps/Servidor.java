/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lsfo
 */
public class Servidor extends Thread {

    private final Socket clientSocket;

    public Servidor(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        ObjectInputStream in = null;

        try {

            in = new ObjectInputStream(clientSocket.getInputStream());
            String string = (String) in.readObject();
            System.out.println("comando: "+string);

            if (string.equals("listar")) {
                enviaLista(in);
            } else {
                recebeEmail(in);
            }

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void enviaLista(ObjectInputStream in) throws IOException, ClassNotFoundException, SQLException {

        in = new ObjectInputStream(clientSocket.getInputStream());
        String string = (String) in.readObject();
        System.out.println("usuário: "+string+'\n');

        MensagemDAO msg = new MensagemDAO();
        ArrayList<Mensagem> lista = msg.listar(string);
        
        ObjectOutputStream outObj = new ObjectOutputStream(clientSocket.getOutputStream());
        outObj.writeObject(lista);
    }

    private void recebeEmail(ObjectInputStream in) throws IOException, ClassNotFoundException, SQLException {
        
        Mensagem mensagem = (Mensagem) in.readObject();
        System.out.println("usuário: "+mensagem.getRemetente()+'\n');
        
        MensagemDAO msgDAO = new MensagemDAO();
        msgDAO.inserir(mensagem);
    }
}
