/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.InventarioPrenda;
import Facade.InventarioPrendaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
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
public class inventarioBean implements Serializable{

    private InventarioPrenda inventarioPrenda;
    
    @EJB
    private InventarioPrendaFacade facade;
    /**
     * Creates a new instance of inventarioBean
     */
    public inventarioBean() {
    }

    public InventarioPrenda getInventarioPrenda() {
        return inventarioPrenda;
    }

    public void setInventarioPrenda(InventarioPrenda inventarioPrenda) {
        this.inventarioPrenda = inventarioPrenda;
    }

    public InventarioPrendaFacade getFacade() {
        return facade;
    }

    public void setFacade(InventarioPrendaFacade facade) {
        this.facade = facade;
    }
    
    /**
     * Add inventario into db
     */
    public void makeInventario(){
        try {
            inventarioPrenda.setCantidadActual(inventarioPrenda.getCantidadInicial());
            facade.create(inventarioPrenda);
            inventarioPrenda = new InventarioPrenda();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de prendas añadido", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }
    
    public List<InventarioPrenda> getAll(){
        return facade.findAll();
    }
    
    public List<InventarioPrenda> getPrendasExistentes(){
        List<InventarioPrenda> allitems = getAll();
        List<InventarioPrenda> items = new  ArrayList<>();
        for (InventarioPrenda allitem : allitems) {
            if (allitem.getCantidadActual() > 0) {
                items.add(allitem);
            }
        }
        return items;
    }
    
    @PostConstruct
    public void init(){
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("current") == null) {
            ELContext elc = FacesContext.getCurrentInstance().getELContext();
            UsuarioBean usuarioBean = (UsuarioBean) elc.getELResolver().getValue(elc, null, "usuarioBean");
            usuarioBean.redirect("index.xhtml");
        }
        inventarioPrenda = new InventarioPrenda();
    }
    
}
