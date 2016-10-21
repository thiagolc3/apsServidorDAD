/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author lsfo
 */
public class MensagemDAO {

    private final Connection banco;

    public MensagemDAO() throws ClassNotFoundException, SQLException {

        FabricaConexoes fabrica = new FabricaConexoes();
        banco = fabrica.Conexao();
    }

    public int inserir(Mensagem m) throws SQLException {

        PreparedStatement stmt = banco.prepareStatement("INSERT INTO mensagens "
                + "(remetente, destinatario, assunto, mensagem) "
                + "VALUES(?,?,?,?)");

        stmt.setString(1, m.getRemetente());
        stmt.setString(2, m.getDestinatario());
        stmt.setString(3, m.getAssunto());
        stmt.setString(4, m.getMensagem());

        return stmt.executeUpdate();
    }

    public ArrayList listar(String destinatario) throws SQLException {

        PreparedStatement stmt = banco.prepareStatement("SELECT * FROM mensagens WHERE destinatario LIKE ?");
        stmt.setString(1, destinatario);
        ArrayList<Mensagem> lista = new ArrayList<>();

        ResultSet resultado = stmt.executeQuery();

        while (resultado.next()) {

            Mensagem m = new Mensagem();

            m.setId(resultado.getInt("id"));
            m.setRemetente(resultado.getString("remetente"));
            m.setDestinatario(resultado.getString("destinatario"));
            m.setAssunto(resultado.getString("assunto"));
            m.setMensagem(resultado.getString("mensagem"));

            lista.add(m);
        }

        return lista;
    }
}
