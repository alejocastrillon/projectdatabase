/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


import Entities.Articulo;
import Facade.ArticuloFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
public class ArticuloBean implements Serializable{

    @EJB
    private ArticuloFacade articuloFacade;
    private Articulo articulo, editArticulo, removeArticulo;
    /**
     * Creates a new instance of ArticuloBean
     */
    public ArticuloBean() {
    }

    public ArticuloFacade getArticuloFacade() {
        return articuloFacade;
    }

    public void setArticuloFacade(ArticuloFacade articuloFacade) {
        this.articuloFacade = articuloFacade;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Articulo getEditArticulo() {
        return editArticulo;
    }

    public void setEditArticulo(Articulo editArticulo) {
        this.editArticulo = editArticulo;
    }

    public Articulo getRemoveArticulo() {
        return removeArticulo;
    }

    public void setRemoveArticulo(Articulo removeArticulo) {
        this.removeArticulo = removeArticulo;
    }
    
    /**
     * Insert a new article in database
     */
    public void makeArticle(){
        try {
            articuloFacade.create(articulo);
            articulo = new Articulo();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Articulo creado exitosamente", null));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }
    
    /**
     * Edit an article in database
     */
    public void editArticle(){
        try {
            articuloFacade.edit(editArticulo);
            editArticulo = new Articulo();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Articulo editado exitosamente", null));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }
    
    /**
     * Remove an article in database
     */
    public void deleteArticle(){
        try {
            articuloFacade.remove(removeArticulo);
            removeArticulo = new Articulo();
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Articulo editado exitosamente", null));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }
    
    /**
     * Return all the articles
     * @return 
     */
    public List<Articulo> getAllArticles(){
        return articuloFacade.findAll();
    }
    
    @PostConstruct
    public void init(){
        articulo = new Articulo();
        editArticulo = new Articulo();
        removeArticulo = new Articulo();
    }
}
