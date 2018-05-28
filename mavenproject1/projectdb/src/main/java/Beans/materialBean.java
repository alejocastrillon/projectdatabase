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
import java.util.List;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 *
 * @author alejandro
 */

/*Debe de comenzar a programar */
//otroas
@ManagedBean
@ViewScoped
@SessionScoped
public class materialBean implements Serializable{
    
    @EJB
    private MaterialFacade materialFacade;
    private Material material, editMateriales, removeMaterial;

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
    
    //the function creates a material
     public void makeMaterial(){
        try {
            materialFacade.create(material);
            material = new Material();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Material creado exitosamente", null));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }
     //edit a Material in database
     public void editMaterial(){
        try {
            materialFacade.edit(editMateriales);
            editMateriales = new Material();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Material editado exitosamente", null));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }

    public Material getEditMateriales() {
        return editMateriales;
    }

    public void setEditMateriales(Material editMateriales) {
        this.editMateriales = editMateriales;
    }

    public Material getRemoveMaterial() {
        return removeMaterial;
    }

    public void setRemoveMaterial(Material removeMaterial) {
        this.removeMaterial = removeMaterial;
    }
     // Remove a material in the database
          public void deleteMaterial(Material m){
        try {
            materialFacade.remove(m);
            m = new Material();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Material borrado exitosamente", null));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }
    public List<Material> getAllMaterial(){
        return materialFacade.findAll();
    }
     
    @PostConstruct
    public void init(){
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("current") == null) {
            ELContext elc = FacesContext.getCurrentInstance().getELContext();
            UsuarioBean usuarioBean = (UsuarioBean) elc.getELResolver().getValue(elc, null, "usuarioBean");
            usuarioBean.redirect("index.xhtml");
        }
        material = new Material();
        editMateriales = new Material();
        removeMaterial = new Material();
    }
    
}
