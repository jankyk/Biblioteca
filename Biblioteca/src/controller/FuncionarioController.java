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
import models.Funcionario;

/**
 *
 * @author luis_
 */
public class FuncionarioController {
    
    Funcionario objFuncionario;
    JTable jTableListaFuncionarios = null;
    
    /**
     *
     * @param objFuncionario
     * @param jTableListaUsuarios
     */
    public FuncionarioController(Funcionario objFuncionario, JTable jTableListaFuncionarios) {
        this.objFuncionario = objFuncionario;
        this.jTableListaFuncionarios = jTableListaFuncionarios;
    }
    
    public boolean incluirFuncionario(Funcionario objFuncionario){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO funcionarios (nomefuncionario, emailfuncionario, telefonefuncionario, cpffuncionario, ruafuncionario, bairrofuncionario, login, senha, nivelacesso)VALUES(?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, objFuncionario.getNomefuncionario());
            stmt.setString(2, objFuncionario.getEmailfuncionario());
            stmt.setString(3, objFuncionario.getTelefonefuncionario());
            stmt.setString(4, objFuncionario.getCpffuncionario());
            stmt.setString(5, objFuncionario.getRuafuncionario());
            stmt.setString(6, objFuncionario.getBairrofuncionario());
            stmt.setString(7, objFuncionario.getLogin());
            stmt.setString(8, objFuncionario.getSenha());
            stmt.setString(9, objFuncionario.getNivelacesso());
            
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public boolean excluirFuncionario(Funcionario objFuncionario){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM funcionario WHERE idfuncionario = ? ");
            stmt.setInt(1, objFuncionario.getIdFuncionario());
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public void PreencheFuncionario() {
        
        ConnectionFactory.abreConexao();
        
        Vector<String> cabecalhos = new Vector<String>();
        Vector dadosTabela = new Vector();
        cabecalhos.add("ID");
        cabecalhos.add("Nome");
        cabecalhos.add("Login");
        cabecalhos.add("Senha");
        
        ResultSet result = null;
        
        try{
            
            String SQL = "";
            SQL = " SELECT f.idfuncionario, f.nomefuncionario, f.login, f.senha";
            SQL += " FROM funcionarios f ";
            SQL += " ORDER BY f.nomefuncionario ";
            
            result = ConnectionFactory.stmt.executeQuery(SQL);
            
            while (result.next()) {
                Vector<Object> linha = new Vector<Object>();
                linha.add(result.getString(1));
                linha.add(result.getString(2));
                linha.add(result.getString(3));
                linha.add(result.getString(4));
                dadosTabela.add(linha);
            }
        } catch (SQLException e) {
            System.out.println("Problemas para popular tabela!");
            System.out.println(e);
        }
        
        jTableListaFuncionarios.setModel(new DefaultTableModel(dadosTabela, cabecalhos) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            //Não permite edição no jTable
        });
        
        //Permite seleção de apenas uma linha da tabela
        jTableListaFuncionarios.setSelectionMode(0);
        
        //Redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = jTableListaFuncionarios.getColumnModel().getColumn(i);
            switch (1) {
                case 0:
                column.setPreferredWidth(80);
                break;
                case 1:
                column.setPreferredWidth(150);
                break;
                case 2:
                column.setPreferredWidth(150);
                break;
            }
        }
        
        jTableListaFuncionarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            
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
    
    public models.Funcionario buscarFuncionarios(String id){
        
        try {
            ConnectionFactory.abreConexao();
            ResultSet rs = null;

            String SQL = "";
            SQL = " SELECT login, senha, nomefuncionario";
            SQL += " FROM funcionarios";
            SQL += " WHERE idfuncionario = '" + id + "'";
            //stm.executeQuery(SQL);

            try{
                System.out.println("Vai Executar Conexão em buscar visitante");
                rs = ConnectionFactory.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar aluno");
                
               objFuncionario = new models.Funcionario();
               
                if(rs.next() == true)
                {
                    objFuncionario.setLogin(rs.getString(1));
                    objFuncionario.setSenha(rs.getString(2));
                    objFuncionario.setNomefuncionario(rs.getString(3));

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
        
        System.out.println ("Executou buscar aluno com sucesso");
        return objFuncionario;
    }
}
