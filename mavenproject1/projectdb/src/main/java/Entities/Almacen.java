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
@Table(name = "almacen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a")
    , @NamedQuery(name = "Almacen.findByIdalmacen", query = "SELECT a FROM Almacen a WHERE a.idalmacen = :idalmacen")
    , @NamedQuery(name = "Almacen.findByCantidad", query = "SELECT a FROM Almacen a WHERE a.cantidad = :cantidad")})
public class Almacen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idalmacen")
    private Integer idalmacen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private double cantidad;
    @JoinColumn(name = "material_idmaterial", referencedColumnName = "idmaterial")
    @ManyToOne(optional = false)
    private Material materialIdmaterial;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;

    public Almacen() {
    }

    public Almacen(Integer idalmacen) {
        this.idalmacen = idalmacen;
    }

    public Almacen(Integer idalmacen, double cantidad) {
        this.idalmacen = idalmacen;
        this.cantidad = cantidad;
    }

    public Integer getIdalmacen() {
        return idalmacen;
    }

    public void setIdalmacen(Integer idalmacen) {
        this.idalmacen = idalmacen;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Material getMaterialIdmaterial() {
        return materialIdmaterial;
    }

    public void setMaterialIdmaterial(Material materialIdmaterial) {
        this.materialIdmaterial = materialIdmaterial;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idalmacen != null ? idalmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.idalmacen == null && other.idalmacen != null) || (this.idalmacen != null && !this.idalmacen.equals(other.idalmacen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Almacen[ idalmacen=" + idalmacen + " ]";
    }
    
}
