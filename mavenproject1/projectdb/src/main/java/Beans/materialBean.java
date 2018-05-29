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
@ManagedBean
@ViewScoped
@SessionScoped
public class materialBean implements Serializable {

    @EJB
    private MaterialFacade materialFacade;
    private Material material, editarMateriales, removeMaterial;

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
    public void makeMaterial() {
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

    public void editMaterial() {
        try {
            materialFacade.edit(editarMateriales);
            editarMateriales = new Material();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Material editado exitosamente", null));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }

    public Material getEditarMateriales() {
        return editarMateriales;
    }

    public void setEditarMateriales(Material editarMateriales) {
        this.editarMateriales = editarMateriales;
    }

    public Material getRemoveMaterial() {
        return removeMaterial;
    }

    public void setRemoveMaterial(Material removeMaterial) {
        this.removeMaterial = removeMaterial;
    }

    /**
     * Remove a material into database
     *
     * @param m
     */
    public void deleteMaterial(Material m) {
        if (m != null) {
            try {
                materialFacade.remove(m);
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Material borrado exitosamente", null));
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
            }
        }
    }

    public List<Material> getAllMaterial() {
        return materialFacade.findAll();
    }

    @PostConstruct
    public void init() {
        ELContext elc = FacesContext.getCurrentInstance().getELContext();
        UsuarioBean usuarioBean = (UsuarioBean) elc.getELResolver().getValue(elc, null, "usuarioBean");
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("current") == null) {
            usuarioBean.redirect("index.xhtml");
        }
        material = new Material();
        editarMateriales = new Material();
        removeMaterial = new Material();
    }

}
