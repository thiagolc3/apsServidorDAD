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
                trataLista(in);
            } else {
                enviaEmail(in);
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

    private ArrayList GeraLista(String usuario) throws ClassNotFoundException, SQLException {

        MensagemDAO msg = new MensagemDAO();
        ArrayList<Mensagem> lista = msg.listar(usuario);
        return lista;
    }

    private void trataLista(ObjectInputStream in) throws IOException, ClassNotFoundException, SQLException {

        in = new ObjectInputStream(clientSocket.getInputStream());
        String string = (String) in.readObject();
        System.out.println("usuário: "+string+'\n');

        ObjectOutputStream outObj = new ObjectOutputStream(clientSocket.getOutputStream());
        outObj.writeObject(GeraLista(string));
    }

    private void enviaEmail(ObjectInputStream in) throws IOException, ClassNotFoundException, SQLException {
        
        String string = (String) in.readObject();
        System.out.println("usuário: "+string+'\n');
        
        Mensagem mensagem = (Mensagem) in.readObject();
        MensagemDAO msgDAO = new MensagemDAO();
        msgDAO.inserir(mensagem);
    }
}
