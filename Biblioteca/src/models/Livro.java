/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Janquiel Kappler
 */
public class Livro {
    
    private int idlivro;
    private String titulolivro;
    private String autorlivro;
    private int numpaginas;
    private int idgenero;

    public int getIdlivro() {
        return idlivro;
    }

    public void setIdlivro(int idlivro) {
        this.idlivro = idlivro;
    }

    public String getTitulolivro() {
        return titulolivro;
    }

    public void setTitulolivro(String titulolivro) {
        this.titulolivro = titulolivro;
    }

    public String getAutorlivro() {
        return autorlivro;
    }

    public void setAutorlivro(String autorlivro) {
        this.autorlivro = autorlivro;
    }

    public int getNumpaginas() {
        return numpaginas;
    }

    public void setNumpaginas(int numpaginas) {
        this.numpaginas = numpaginas;
    }

    public int getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(int idgenero) {
        this.idgenero = idgenero;
    }
    
}
