/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.Material;
import Facade.MaterialFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author alejandro
 */

/*Debe de comenzar a programar */
@ManagedBean
@ViewScoped
@SessionScoped
public class materialBean implements Serializable{
    
    @EJB
    private MaterialFacade materialFacade;
    private Material material;

    /**
     * Creates a new instance of materialBean
     */
    public materialBean() {
    }

    public MaterialFacade getMaterialFacade() {
        return materialFacade;
    }

    public void setMaterialFacade(MaterialFacade materialFacade) {
        this.materialFacade = materialFacade;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    
    @PostConstruct
    public void init(){
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("current") == null) {
            ELContext elc = FacesContext.getCurrentInstance().getELContext();
            UsuarioBean usuarioBean = (UsuarioBean) elc.getELResolver().getValue(elc, null, "usuarioBean");
            usuarioBean.redirect("index.xhtml");
        }
        material = new Material();
    }
    
}
