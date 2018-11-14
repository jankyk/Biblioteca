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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import models.Cliente;

/**
 *
 * @author luis_
 */
public class ClienteController {
    
    Cliente objCliente;
    JTable jTableListaClientes = null;
    
    /**
     *
     * @param objFuncionario
     * @param jTableListaClientes
     */
    public ClienteController(Cliente objCliente, JTable jTableListaClientes) {
        this.objCliente = objCliente;
        this.jTableListaClientes = jTableListaClientes;
    }
    
    public boolean incluirCliente(Cliente objCliente){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO clientes (nomecliente, emailcliente, nascimentocliente, cpfcliente, telefonecliente, ruacliente, bairrocliente, idcidade)VALUES(?,?,?,?,?,?,?,?)");
            stmt.setString(1, objCliente.getNomecliente());
            stmt.setString(2, objCliente.getEmailciente());
            stmt.setDate(3, Date.valueOf(objCliente.getNascimentocliente()));
            stmt.setString(4, objCliente.getCpfcliente());
            stmt.setString(5, objCliente.getTelefonecliente());
            stmt.setString(6, objCliente.getRuacliente());
            stmt.setString(7, objCliente.getBairrocliente());
            stmt.setInt(8, objCliente.getIdcidade());
            
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public boolean excluirCliente(Cliente objCliente){      
        
        
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM clientes WHERE idcliente = ? ");
            stmt.setInt(1, objCliente.getIdcliente());
            
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public void PreencheCliente() {
        
        ConnectionFactory.abreConexao();
        
        Vector<String> cabecalhos = new Vector<String>();
        Vector dadosTabela = new Vector();
        cabecalhos.add("ID");
        cabecalhos.add("Nome");
        cabecalhos.add("Email");
        cabecalhos.add("Nascimento");
        cabecalhos.add("CPF");
        cabecalhos.add("Telefone");
        cabecalhos.add("Rua");
        cabecalhos.add("Bairro");
        cabecalhos.add("Cidade");
        
        ResultSet result = null;
        
        try{
            
            String SQL = "";
            SQL = " SELECT c.idcliente, c.nomecliente, c.emailcliente, c.nascimentocliente, c.cpfcliente, c.telefonecliente, c.ruacliente, c.bairrocliente, ci.nomecidade";
            SQL += " FROM clientes c, cidade ci ";
            SQL += " WHERE c.idcidade = ci.idcidade";
            SQL += " ORDER BY c.nomecliente ";
            
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
                dadosTabela.add(linha);
            }
        } catch (SQLException e) {
            System.out.println("Problemas para popular tabela!");
            System.out.println(e);
        }
        
        jTableListaClientes.setModel(new DefaultTableModel(dadosTabela, cabecalhos) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            //Não permite edição no jTable
        });
        
        //Permite seleção de apenas uma linha da tabela
        jTableListaClientes.setSelectionMode(0);
        
        //Redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < 8; i++) {
            column = jTableListaClientes.getColumnModel().getColumn(i);
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
            }
        }
        
        jTableListaClientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            
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
    
    public Cliente buscarClientes(String id){
        
        try {
            ConnectionFactory.abreConexao();
            ResultSet rs = null;

            String SQL = "";
            SQL = " SELECT c.idcliente, c.nomecliente, c.emailcliente, c.nascimentocliente, c.cpfcliente, c.telefonecliente, c.ruacliente, c.bairrocliente, c.idcidade";
            SQL += " FROM clientes c";
            SQL += " WHERE idcliente = '" + id + "'";
            //stm.executeQuery(SQL);

            try{
                System.out.println("Vai Executar Conexão em buscar clientes");
                rs = ConnectionFactory.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar clientes");
                
               objCliente = new models.Cliente();
               
                if(rs.next() == true)
                {
                    objCliente.setIdcliente(rs.getInt(1));
                    objCliente.setNomecliente(rs.getString(2));
                    objCliente.setEmailciente(rs.getString(3));
                    objCliente.setNascimentocliente(rs.getString(4));
                    objCliente.setCpfcliente(rs.getString(5));
                    objCliente.setTelefonecliente(rs.getString(6));
                    objCliente.setRuacliente(rs.getString(7));
                    objCliente.setBairrocliente(rs.getString(8));
                    objCliente.setIdcidade(rs.getInt(9));

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
        
        System.out.println ("Executou buscar cliente com sucesso");
        return objCliente;
    }
    
    public boolean alterarFuncionario(){
 
        ConnectionFactory.abreConexao();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
 
        try {
            stmt = con.prepareStatement("UPDATE clientes SET nomecliente=?, emailcliente=?, nascimentocliente=?, cpfcliente=?, telefonecliente=?, ruacliente=?, bairrocliente=?, idcidade=? WHERE idcliente=?");
            stmt.setString(1, objCliente.getNomecliente());
            stmt.setString(2, objCliente.getEmailciente());
            stmt.setDate(3, Date.valueOf(objCliente.getNascimentocliente()));
            stmt.setString(4, objCliente.getCpfcliente());
            stmt.setString(5, objCliente.getTelefonecliente());
            stmt.setString(6, objCliente.getRuacliente());
            stmt.setString(7, objCliente.getBairrocliente());
            stmt.setInt(8, objCliente.getIdcidade());
            stmt.setInt(9, objCliente.getIdcliente());
 
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
