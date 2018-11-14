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
            stmt = con.prepareStatement("INSERT INTO funcionarios (nomefuncionario, emailfuncionario, telefonefuncionario, cpffuncionario, ruafuncionario, bairrofuncionario, idcidade, login, senha)VALUES(?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, objFuncionario.getNomefuncionario());
            stmt.setString(2, objFuncionario.getEmailfuncionario());
            stmt.setString(3, objFuncionario.getTelefonefuncionario());
            stmt.setString(4, objFuncionario.getCpffuncionario());
            stmt.setString(5, objFuncionario.getRuafuncionario());
            stmt.setString(6, objFuncionario.getBairrofuncionario());
            stmt.setInt(7, objFuncionario.getIdcidade());
            stmt.setString(8, objFuncionario.getLogin());
            stmt.setString(9, objFuncionario.getSenha());
            
            
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
            stmt = con.prepareStatement("DELETE FROM funcionarios WHERE idfuncionario = ? ");
            stmt.setInt(1, objFuncionario.getIdfuncionario());
            
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
        cabecalhos.add("Email");
        cabecalhos.add("Telefone");
        cabecalhos.add("CPF");
        cabecalhos.add("Rua");
        cabecalhos.add("Bairro");
        cabecalhos.add("Cidade");
        cabecalhos.add("Login");
        cabecalhos.add("Senha");
        
        ResultSet result = null;
        
        try{
            
            String SQL = "";
            SQL = " SELECT f.idfuncionario, f.nomefuncionario, f.emailfuncionario, f.telefonefuncionario, f.cpffuncionario, f.ruafuncionario, f.bairrofuncionario, c.nomecidade, f.login, f.senha";
            SQL += " FROM funcionarios f, cidade c ";
            SQL += " WHERE f.idcidade = c.idcidade";
            SQL += " ORDER BY f.nomefuncionario ";
            
            result = ConnectionFactory.stmt.executeQuery(SQL);
            
            while (result.next()) {
                Vector<Object> linha = new Vector<Object>();
                linha.add(result.getString(1));
                linha.add(result.getString(2));
                linha.add(result.getString(3));
                linha.add(result.getString(4));
                linha.add(result.getString(5));
                linha.add(result.getString(6));
                linha.add(result.getString(7));
                linha.add(result.getString(8));
                linha.add(result.getString(9));
                linha.add(result.getString(10));
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
        for (int i = 0; i < 9; i++) {
            column = jTableListaFuncionarios.getColumnModel().getColumn(i);
            switch (1) {
                case 0:
                column.setPreferredWidth(80);
                break;
                case 1:
                column.setPreferredWidth(80);
                break;
                case 2:
                column.setPreferredWidth(80);
                break;
                 case 3:
                column.setPreferredWidth(80);
                break;
                 case 4:
                column.setPreferredWidth(80);
                break;
                 case 5:
                column.setPreferredWidth(80);
                break;
                 case 6:
                column.setPreferredWidth(80);
                break;
                 case 7:
                column.setPreferredWidth(80);
                break;
                 case 8:
                column.setPreferredWidth(80);
                break;
                 case 9:
                column.setPreferredWidth(80);
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
            SQL = " SELECT f.idfuncionario, f.nomefuncionario, f.emailfuncionario, f.telefonefuncionario, f.cpffuncionario, f.ruafuncionario, f.bairrofuncionario, f.idcidade, f.login, f.senha";
            SQL += " FROM funcionarios f";
            SQL += " WHERE idfuncionario = '" + id + "'";
            //stm.executeQuery(SQL);

            try{
                System.out.println("Vai Executar Conexão em buscar funcionarios");
                rs = ConnectionFactory.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar funcionario");
                
               objFuncionario = new models.Funcionario();
               
                if(rs.next() == true)
                {
                    objFuncionario.setIdfuncionario(rs.getInt(1));
                    objFuncionario.setNomefuncionario(rs.getString(2));
                    objFuncionario.setEmailfuncionario(rs.getString(3));
                    objFuncionario.setTelefonefuncionario(rs.getString(4));
                    objFuncionario.setCpffuncionario(rs.getString(5));
                    objFuncionario.setRuafuncionario(rs.getString(6));
                    objFuncionario.setBairrofuncionario(rs.getString(7));
                    objFuncionario.setIdcidade(rs.getInt(8));
                    objFuncionario.setLogin(rs.getString(9));
                    objFuncionario.setSenha(rs.getString(10));

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
        
        System.out.println ("Executou buscar funcionario com sucesso");
        return objFuncionario;
    }
    
    public boolean alterarFuncionario(){
 
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
 
        try {
            stmt = con.prepareStatement("UPDATE funcionarios SET nomefuncionario=?, emailfuncionario=?, telefonefuncionario=?, cpffuncionario=?, ruafuncionario=?, bairrofuncionario=?, idcidade=?, login=?, senha=? WHERE idfuncionario=?");
            stmt.setString(1, objFuncionario.getNomefuncionario());
            stmt.setString(2, objFuncionario.getEmailfuncionario());
            stmt.setString(3, objFuncionario.getTelefonefuncionario());
            stmt.setString(4, objFuncionario.getCpffuncionario());
            stmt.setString(5, objFuncionario.getRuafuncionario());
            stmt.setString(6, objFuncionario.getBairrofuncionario());
            stmt.setInt(7, objFuncionario.getIdcidade());
            stmt.setString(8, objFuncionario.getLogin());
            stmt.setString(9, objFuncionario.getSenha());
            stmt.setInt(10, objFuncionario.getIdfuncionario());
 
            stmt.executeUpdate();
 
            return true;
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
 
    }
}
