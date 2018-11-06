/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.ConnectionFactory;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author janquielkappler
 */
public class RelatoriosController {
    
    public ResultSet relatorioClientes(String wSelect){
        ResultSet rs = null;
        try {
            ConnectionFactory.abreConexao();
            
            String SQL = "";
            SQL = wSelect;

            try{
                System.out.println("Vai Executar Conexão em buscar visitante");
                rs = ConnectionFactory.stmt.executeQuery(SQL);
            }

            catch (SQLException ex )
            {
                System.out.println("ERRO de SQL: " + ex.getMessage().toString());
                return rs;
            }

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage().toString());
            return rs;
        }
        
        System.out.println ("Executou buscar visitante com sucesso");
        return rs;
    }
    
    public ResultSet relatorioCidades(String wSelect){
        ResultSet rs = null;
        try {
            ConnectionFactory.abreConexao();
            
            String SQL = "";
            SQL = wSelect;

            try{
                System.out.println("Vai Executar Conexão em buscar visitante");
                rs = ConnectionFactory.stmt.executeQuery(SQL);
            }

            catch (SQLException ex )
            {
                System.out.println("ERRO de SQL: " + ex.getMessage().toString());
                return rs;
            }

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage().toString());
            return rs;
        }
        
        System.out.println ("Executou buscar visitante com sucesso");
        return rs;
    }
    
}
