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
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author alejandro
 */
@ManagedBean
@ViewScoped
@SessionScoped
public class FacturaBean implements Serializable {

    @EJB
    private FacturaFacade facturaFacade;
    private Factura factura;
    private ArticulosFactura articulosFactura;
    private List<ArticulosFactura> articulosFacturas;
    private List<Factura> filteredFacturas;
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

    public List<Factura> getFilteredFacturas() {
        return filteredFacturas;
    }

    public void setFilteredFacturas(List<Factura> filteredFacturas) {
        this.filteredFacturas = filteredFacturas;
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
        ELContext elc = FacesContext.getCurrentInstance().getELContext();
        inventarioBean b = (inventarioBean) elc.getELResolver().getValue(elc, null, "inventarioBean");
        if (articulosFactura.getCantidad() <= b.getPrendasArticulo(articulosFactura.getArticuloIdarticulo())) {
            articulosFactura.setFacturaIdfactura(factura);
            articulosFacturas.add(articulosFactura);
            factura.setArticulosFacturaList(articulosFacturas);
            total = total + (articulosFactura.getCantidad() * articulosFactura.getArticuloIdarticulo().getPrecioVenta());
            articulosFactura = new ArticulosFactura();
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, "No hay tantas unidades disponibles de " + articulosFactura.getArticuloIdarticulo().getNombre(), null));
        }
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

    /**
     * Return the article attached to bills for view implementation
     *
     * @param f
     * @return
     */
    public List<ArticulosFactura> getArticulosFactura(Factura f) {
        return f.getArticulosFacturaList();
    }

    public Integer generateTotalFactura(List<ArticulosFactura> afs) {
        Integer totalFactura = 0;
        for (ArticulosFactura af : afs) {
            totalFactura += af.getCantidad() * af.getArticuloIdarticulo().getPrecioVenta();
        }
        return totalFactura;
    }

    /**
     * Insert a bill into database
     */
    public void makeFactura() {
        Usuario userCurrent = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("current");
        if (userCurrent != null) {
            Calendar c = Calendar.getInstance();
            factura.setIdfactura(facturaFacade.findAll().size() + 1);
            factura.setFechaVenta(c.getTime());
            factura.setUsuarioIdusuario(userCurrent);
            factura.setHabilitada(true);
            try {
                facturaFacade.create(factura);
                ELContext elc = FacesContext.getCurrentInstance().getELContext();
                inventarioBean b = (inventarioBean) elc.getELResolver().getValue(elc, null, "inventarioBean");
                for (ArticulosFactura articulosFactura1 : factura.getArticulosFacturaList()) {
                    b.updatePrendasArticulos(articulosFactura1.getArticuloIdarticulo(), articulosFactura1.getCantidad());
                }
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura creada exitosamente", null));
                factura = new Factura();
                articulosFacturas = new ArrayList<>();
                total = 0;
            } catch (Exception e) {
                System.err.println("Error en la creacion de la factura: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), null));
            }
        }
        getAllFacturas();
    }

    /**
     * Return the bills generated in this month and year
     *
     * @param month
     * @param year
     * @return
     */
    public List<Factura> getFacturasByMonthandYear(int month, int year) {
        return facturaFacade.getFacturaByMonthandYear(month, year);
    }

    /**
     * Return the enabled or disabled bills
     *
     * @param enable
     * @return
     */
    public List<Factura> getEnabledorDisabledFacturas(boolean enable) {
        return facturaFacade.getEnabledorDisabledFacturas(enable);
    }

    /**
     * Return all the bills
     *
     * @return
     */
    public List<Factura> getAllFacturas() {
        return facturaFacade.findAll();
    }

    /**
     * Disable a bill
     *
     * @param factura
     */
    public void disabledFactura(Factura factura) {
        factura.setHabilitada(false);
        try {
            facturaFacade.edit(factura);
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura deshabilitada", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), null));
            System.err.println("Error al deshabilitar factura: " + e.getMessage());
        }
    }

    /**
     * Enable a bill
     *
     * @param factura
     */
    public void enabledFactura(Factura factura) {
        factura.setHabilitada(true);
        try {
            facturaFacade.edit(factura);
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura habilitada", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), null));
            System.err.println("Error al deshabilitar factura: " + e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        ELContext elc = FacesContext.getCurrentInstance().getELContext();
        UsuarioBean usuarioBean = (UsuarioBean) elc.getELResolver().getValue(elc, null, "usuarioBean");
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("current") == null) {
            usuarioBean.redirect("index.xhtml");
        }
        factura = new Factura();
        articulosFacturas = new ArrayList<>();
        articulosFactura = new ArticulosFactura();
        removeArticulosFactura = new ArticulosFactura();
        return;
    }
}
