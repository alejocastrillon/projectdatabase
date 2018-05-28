/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alejandro
 */
@Entity
@Table(name = "inventario_prenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InventarioPrenda.findAll", query = "SELECT i FROM InventarioPrenda i")
    , @NamedQuery(name = "InventarioPrenda.findByIdinventarioPrenda", query = "SELECT i FROM InventarioPrenda i WHERE i.idinventarioPrenda = :idinventarioPrenda")
    , @NamedQuery(name = "InventarioPrenda.findByCantidadInicial", query = "SELECT i FROM InventarioPrenda i WHERE i.cantidadInicial = :cantidadInicial")
    , @NamedQuery(name = "InventarioPrenda.findByCantidadActual", query = "SELECT i FROM InventarioPrenda i WHERE i.cantidadActual = :cantidadActual")
    , @NamedQuery(name = "InventarioPrenda.findByArticleandQuantity", query = "SELECT i FROM InventarioPrenda i WHERE i.articuloIdarticulo = :articulo AND i.cantidadActual >= :quantity")})
public class InventarioPrenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinventario_prenda")
    private Integer idinventarioPrenda;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_inicial")
    private int cantidadInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_actual")
    private int cantidadActual;
    @JoinColumn(name = "articulo_idarticulo", referencedColumnName = "idarticulo")
    @ManyToOne(optional = false)
    private Articulo articuloIdarticulo;

    public InventarioPrenda() {
    }

    public InventarioPrenda(Integer idinventarioPrenda) {
        this.idinventarioPrenda = idinventarioPrenda;
    }

    public InventarioPrenda(Integer idinventarioPrenda, int cantidadInicial, int cantidadActual) {
        this.idinventarioPrenda = idinventarioPrenda;
        this.cantidadInicial = cantidadInicial;
        this.cantidadActual = cantidadActual;
    }

    public Integer getIdinventarioPrenda() {
        return idinventarioPrenda;
    }

    public void setIdinventarioPrenda(Integer idinventarioPrenda) {
        this.idinventarioPrenda = idinventarioPrenda;
    }

    public int getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(int cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Articulo getArticuloIdarticulo() {
        return articuloIdarticulo;
    }

    public void setArticuloIdarticulo(Articulo articuloIdarticulo) {
        this.articuloIdarticulo = articuloIdarticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinventarioPrenda != null ? idinventarioPrenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventarioPrenda)) {
            return false;
        }
        InventarioPrenda other = (InventarioPrenda) object;
        if ((this.idinventarioPrenda == null && other.idinventarioPrenda != null) || (this.idinventarioPrenda != null && !this.idinventarioPrenda.equals(other.idinventarioPrenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.InventarioPrenda[ idinventarioPrenda=" + idinventarioPrenda + " ]";
    }
    
}
