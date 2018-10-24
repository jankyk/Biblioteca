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
import models.Livro;

/**
 *
 * @author Janquiel Kappler
 */
public class LivroController {
    
    Livro objLivro;
    JTable jTableLivro = null;
    
    public LivroController(Livro objLivro, JTable jTableLivro) {
        this.objLivro = objLivro;
        this.jTableLivro = jTableLivro;
    
    }
    
    public boolean incluirLivro(Livro objLivro){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO livros (titulolivro, autorlivro, numpaginas, idgenero)VALUES(?,?,?,?)");
            stmt.setString(1, objLivro.getTitulolivro());
            stmt.setString(2, objLivro.getAutorlivro());
            stmt.setInt(3, objLivro.getNumpaginas());
            stmt.setInt(4, objLivro.getIdgenero());
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public boolean excluirLivro(Livro objLivro){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM livros WHERE idlivro = ? ");
            stmt.setInt(1, objLivro.getIdlivro());
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public void PreencheLivro() {
        
        ConnectionFactory.abreConexao();
        
        Vector<String> cabecalhos = new Vector<String>();
        Vector dadosTabela = new Vector();
        cabecalhos.add("ID");
        cabecalhos.add("Titulo");
        cabecalhos.add("Autor");
        cabecalhos.add("Paginas");
        cabecalhos.add("Genero");
        
        ResultSet result = null;
        
        try{
            
            String SQL = "";
            SQL = " SELECT  l.idlivro, l.titulolivro, l.autorlivro, l.numpaginas, g.nomegenero";
            SQL += " FROM livros l, genero g ";
            SQL += " WHERE l.idgenero = g.idgenero";
            SQL += " ORDER BY l.titulolivro ";
            
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
        
        jTableLivro.setModel(new DefaultTableModel(dadosTabela, cabecalhos) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            //Não permite edição no jTable
        });
        
        //Permite seleção de apenas uma linha da tabela
        jTableLivro.setSelectionMode(0);
        
        //Redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < 2; i++) {
            column = jTableLivro.getColumnModel().getColumn(i);
            switch (1) {
                case 0:
                column.setPreferredWidth(80);
                break;
                case 1:
                column.setPreferredWidth(150);
                break;
            }
        }
        
        jTableLivro.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            
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
    
    public boolean alterarLivro(){
 
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
 
        try {
            stmt = con.prepareStatement("UPDATE livros SET titulolivro=?, autorlivro=?, numpaginas=? idgenero=? WHERE idlivro=?");
            stmt.setString(1, objLivro.getTitulolivro());
            stmt.setString(2, objLivro.getAutorlivro());
            stmt.setInt(3, objLivro.getNumpaginas());
            stmt.setInt(4, objLivro.getIdgenero());
            stmt.setInt(5, objLivro.getIdlivro());
 
            stmt.executeUpdate();
 
            return true;
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
 
    }
    
    public models.Livro buscarLivro(String id){
        
        try {
            ConnectionFactory.abreConexao();
            ResultSet rs = null;

            String SQL = "";
            SQL = " SELECT l.idlivro, l.titulolivro, l.autorlivro, g.nomegenero";
            SQL += " FROM livros l, genero g";
            SQL += " WHERE l.idgenero = g.idgenero";
            SQL += " AND idlivro = '" + id + "'";
            //stm.executeQuery(SQL);

            try{
                System.out.println("Vai Executar Conexão em buscar livro");
                rs = ConnectionFactory.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar livro");
                
               objLivro = new models.Livro();
               
                if(rs.next() == true)
                {
                    objLivro.setIdlivro(rs.getInt(1));
                    objLivro.setAutorlivro(rs.getString(2));                    
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
        return objLivro;
    }

    
}
