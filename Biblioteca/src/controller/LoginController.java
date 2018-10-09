/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.ConnectionFactory;
import tools.CaixaDeDialogo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Funcionario;

/**
 *
 * @author Janquiel Kappler
 */
public class LoginController {

      public Funcionario Login(String usuario, String senha){
        //INÍCIO CONEXÃO COM O BANCO DE DADOS
        System.out.println("Vai abrir a conexão com o banco de dados");
        ConnectionFactory.abreConexao();
        
        Funcionario user = null;
        
        ResultSet rs = null;

        StringBuilder comandoSQL = new StringBuilder();
        comandoSQL.append(" SELECT login, nomefuncionario");
        comandoSQL.append(" FROM funcionarios");
        comandoSQL.append(" WHERE login = '"+ usuario +"'");
        comandoSQL.append(" AND senha = '"+ senha +"'");
        
        try {
            System.out.println("Vai Executar Conexão em buscar area");
            rs = ConnectionFactory.stmt.executeQuery(comandoSQL.toString());
            System.out.println("Executou Conexão em buscar area");
            
            if (rs.next() == true) {
                user = new Funcionario();
                user.setLogin(rs.getString("login"));
                user.setNomefuncionario(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            System.out.println("ERRO de SQL: " + ex.getMessage().toString());
            return user;
        }finally{
            Connection con = ConnectionFactory.getConnection();
            System.out.println("Vai fechar a conexão com o banco de dados");
            ConnectionFactory.closeConnection(con);
        }
        
        return user;
        
    }
    
}
