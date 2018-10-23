/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.ConnectionFactory;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import models.Genero;

/**
 *
 * @author Janquiel Kappler
 */
public class GeneroController {
    
    Genero objGenero;
    JTable jTableGenero = null;
    
    public GeneroController(Genero objGenero, JTable jTableGenero) {
        this.objGenero = objGenero;
        this.jTableGenero = jTableGenero;
    
    }
    
    public boolean incluirGenero(Genero objGenero){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO genero (nomegenero)VALUES(?)");
            stmt.setString(1, objGenero.getNomegenero());            
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public boolean excluirGenero(Genero objGenero){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM genero WHERE idgenero = ? ");
            stmt.setInt(1, objGenero.getIdgenero());
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public void PreencheGenero() {
        
        ConnectionFactory.abreConexao();
        
        Vector<String> cabecalhos = new Vector<String>();
        Vector dadosTabela = new Vector();
        cabecalhos.add("ID");
        cabecalhos.add("Nome");
        
        ResultSet result = null;
        
        try{
            
            String SQL = "";
            SQL = " SELECT  g.idgenero, g.nomegenero";
            SQL += " FROM genero g ";
            SQL += " ORDER BY g.idgenero ";
            
            result = ConnectionFactory.stmt.executeQuery(SQL);
            
            while (result.next()) {
                Vector<Object> linha = new Vector<Object>();
                linha.add(result.getString(1));
                linha.add(result.getString(2));
                dadosTabela.add(linha);
            }
        } catch (SQLException e) {
            System.out.println("Problemas para popular tabela!");
            System.out.println(e);
        }
        
        jTableGenero.setModel(new DefaultTableModel(dadosTabela, cabecalhos) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            //Não permite edição no jTable
        });
        
        //Permite seleção de apenas uma linha da tabela
        jTableGenero.setSelectionMode(0);
        
        //Redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < 2; i++) {
            column = jTableGenero.getColumnModel().getColumn(i);
            switch (1) {
                case 0:
                column.setPreferredWidth(80);
                break;
                case 1:
                column.setPreferredWidth(150);
                break;
            }
        }
        
        jTableGenero.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            
            @Override
            public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    setBackground(Color.LIGHT_GRAY);
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }
        });
    }
    
    public models.Genero buscarGenero(String id){
        
        try {
            ConnectionFactory.abreConexao();
            ResultSet rs = null;

            String SQL = "";
            SQL = " SELECT idgenero, nomegenero";
            SQL += " FROM genero";
            SQL += " WHERE idgenero = '" + id + "'";
            //stm.executeQuery(SQL);

            try{
                System.out.println("Vai Executar Conexão em buscar genero");
                rs = ConnectionFactory.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar genero");
                
               objGenero = new models.Genero();
               
                if(rs.next() == true)
                {
                    objGenero.setIdgenero(rs.getInt(1));
                    objGenero.setNomegenero(rs.getString(2));                    
                }
            }

            catch (SQLException ex )
            {
                System.out.println("ERRO de SQL: " + ex.getMessage().toString());
                return null;
            }

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage().toString());
            return null;
        }
        
        System.out.println ("Executou buscar genero com sucesso");
        return objGenero;
    }

    
}
