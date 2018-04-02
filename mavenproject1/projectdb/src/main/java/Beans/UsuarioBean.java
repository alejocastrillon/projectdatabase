/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.Usuario;
import Facade.UsuarioFacade;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author alejandro
 */
@Named(value = "usuarioBean")
@Dependent
@ManagedBean
public class UsuarioBean {
    
    private String username, password;
    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;
    private Usuario currentUser;
    

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public boolean validateLogin(){
        currentUser = usuarioFacade.validateLogIn(username, password);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login exitoso", null));
        return currentUser != null;
    }
    
    public void makeUser(){
        try {
            usuarioFacade.create(usuario);
        } catch (Exception e) {
            System.err.println("Error de creacion de usuario: " + e.getLocalizedMessage());
        }
    }
    
    @PostConstruct
    public void init(){
        username = new String();
        password = new String();
        usuario = new Usuario();
    }
}
