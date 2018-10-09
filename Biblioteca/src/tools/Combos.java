package tools;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import java.sql.ResultSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pretto
 */

public class Combos {
    private String codigo;
    private String descricao;
    JComboBox cbCombo = null;
    ResultSet result = null;

    public Combos(JComboBox cbCombo) {
        this.cbCombo = cbCombo;
    }
    public Combos() {

    }
    /**
     * @return the coodigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString () {
        return descricao;
    }

    public int SetaComboBox(String wValue){

        for(int i = 0; i < cbCombo.getItemCount() ;  i++ ){
            Combos c = new Combos();
            cbCombo.setSelectedIndex(i);
            c = (Combos) cbCombo.getSelectedItem();
            if (c.getCodigo().equals(wValue)){
                return 0;
            }
        }
        return 1;
    }

    public int PreencheCombo(String wSQL) throws SQLException{
            
        ConnectionFactory.abreConexao();
        result = ConnectionFactory.stmt.executeQuery(wSQL);
        
        //ConnectionFactory.closeConnection(ConnectionFactory.con, ConnectionFactory.stmt);
        
        cbCombo.removeAllItems();

        Combos combo = new Combos();
        combo.setCodigo("");
        combo.setDescricao("Selecione...");
        cbCombo.addItem(combo);
        while (result.next()) {
            combo = new Combos();
            combo.setCodigo(result.getString(1));
            combo.setDescricao(result.getString(2));
            cbCombo.addItem(combo);
        }
        return 0;
    }

}
