/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lsfo
 */
public class FabricaConexoes {
    private String servidor, base, login, senha;

    public FabricaConexoes() {
        servidor = "localhost";
        base = "apsDAD";
        login = "root";
        senha ="lsfo";
    }
 
    public Connection Conexao() throws ClassNotFoundException, SQLException {
                
        Class.forName("com.mysql.jdbc.Driver");
        return (Connection) DriverManager.getConnection("jdbc:mysql://" +getServidor()+ ":3306/" + getBase(), getLogin(), getSenha());
    }
    
    /**
     * @return the servidor
     */
    public String getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
