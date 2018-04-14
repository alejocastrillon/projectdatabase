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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alejandro
 */
@Entity
@Table(name = "articulos_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticulosFactura.findAll", query = "SELECT a FROM ArticulosFactura a")
    , @NamedQuery(name = "ArticulosFactura.findById", query = "SELECT a FROM ArticulosFactura a WHERE a.id = :id")
    , @NamedQuery(name = "ArticulosFactura.findByTalla", query = "SELECT a FROM ArticulosFactura a WHERE a.talla = :talla")
    , @NamedQuery(name = "ArticulosFactura.findByCantidad", query = "SELECT a FROM ArticulosFactura a WHERE a.cantidad = :cantidad")})
public class ArticulosFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "talla")
    private String talla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "articulo_idarticulo", referencedColumnName = "idarticulo")
    @ManyToOne(optional = false)
    private Articulo articuloIdarticulo;
    @JoinColumn(name = "factura_idfactura", referencedColumnName = "idfactura")
    @ManyToOne(optional = false)
    private Factura facturaIdfactura;

    public ArticulosFactura() {
    }

    public ArticulosFactura(Integer id) {
        this.id = id;
    }

    public ArticulosFactura(Integer id, String talla, int cantidad) {
        this.id = id;
        this.talla = talla;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getArticuloIdarticulo() {
        return articuloIdarticulo;
    }

    public void setArticuloIdarticulo(Articulo articuloIdarticulo) {
        this.articuloIdarticulo = articuloIdarticulo;
    }

    public Factura getFacturaIdfactura() {
        return facturaIdfactura;
    }

    public void setFacturaIdfactura(Factura facturaIdfactura) {
        this.facturaIdfactura = facturaIdfactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticulosFactura)) {
            return false;
        }
        ArticulosFactura other = (ArticulosFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ArticulosFactura[ id=" + id + " ]";
    }
    
}
