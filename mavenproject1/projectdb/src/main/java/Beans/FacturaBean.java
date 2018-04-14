/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.ArticulosFactura;
import Entities.Factura;
import Facade.FacturaFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author alejandro
 */
@ManagedBean
public class FacturaBean {

    @EJB
    private FacturaFacade facturaFacade;
    private Factura factura;
    private ArticulosFactura articulosFactura;
    private List<ArticulosFactura> articulosFacturas;
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
    
    public void addArticulo(){
        articulosFacturas.add(articulosFactura);
        factura.setArticulosFacturaList(articulosFacturas);
        articulosFactura = new ArticulosFactura();
    }
    
    @PostConstruct
    public void init(){
        factura = new Factura();
        articulosFactura = new ArticulosFactura();
    }
}
