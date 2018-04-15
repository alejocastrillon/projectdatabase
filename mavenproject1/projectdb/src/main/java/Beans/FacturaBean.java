/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.ArticulosFactura;
import Entities.Factura;
import Entities.Usuario;
import Facade.FacturaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author alejandro
 */
@ManagedBean
@ViewScoped
public class FacturaBean implements Serializable {

    @EJB
    private FacturaFacade facturaFacade;
    private Factura factura;
    private ArticulosFactura articulosFactura;
    private List<ArticulosFactura> articulosFacturas;
    private int total = 0;
    private ArticulosFactura removeArticulosFactura;

    /**
     * Creates a new instance of FacturaBean
     */
    public FacturaBean() {
    }

    public FacturaFacade getFacturaFacade() {
        return facturaFacade;
    }

    public void setFacturaFacade(FacturaFacade facturaFacade) {
        this.facturaFacade = facturaFacade;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public ArticulosFactura getArticulosFactura() {
        return articulosFactura;
    }

    public void setArticulosFactura(ArticulosFactura articulosFactura) {
        this.articulosFactura = articulosFactura;
    }

    public List<ArticulosFactura> getArticulosFacturas() {
        return articulosFacturas;
    }

    public void setArticulosFacturas(List<ArticulosFactura> articulosFacturas) {
        this.articulosFacturas = articulosFacturas;
    }

    public ArticulosFactura getRemoveArticulosFactura() {
        return removeArticulosFactura;
    }

    public void setRemoveArticulosFactura(ArticulosFactura removeArticulosFactura) {
        this.removeArticulosFactura = removeArticulosFactura;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Add article to bill
     */
    public void addArticulo() {
        articulosFactura.setFacturaIdfactura(factura);
        articulosFacturas.add(articulosFactura);
        factura.setArticulosFacturaList(articulosFacturas);
        total = total + (articulosFactura.getCantidad() * articulosFactura.getArticuloIdarticulo().getPrecioVenta());
        articulosFactura = new ArticulosFactura();
    }

    /**
     * Remove article to bill
     *
     * @param af
     */
    public void rmArticulo(ArticulosFactura af) {
        af.setFacturaIdfactura(factura);
        total = total - (af.getCantidad() * af.getArticuloIdarticulo().getPrecioVenta());
        factura.getArticulosFacturaList().remove(af);
    }

    public void makeFactura() {
        Usuario userCurrent = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("current");
        if (userCurrent != null) {
            factura.setIdfactura(facturaFacade.findAll().size() + 1);
            factura.setFechaVenta(new Date());
            factura.setUsuarioIdusuario(userCurrent);
            factura.setHabilitada(true);
            try {
                facturaFacade.create(factura);
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura creada exitosamente", null));
                factura = new Factura();
                articulosFacturas = new ArrayList<>();
            } catch (Exception e) {
                System.err.println("Error en la creacion de la factura: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), null));
            }
        }
    }

    @PostConstruct
    public void init() {
        factura = new Factura();
        articulosFacturas = new ArrayList<>();
        articulosFactura = new ArticulosFactura();
        removeArticulosFactura = new ArticulosFactura();
    }
}
