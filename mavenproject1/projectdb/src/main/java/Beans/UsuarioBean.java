/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.Usuario;
import Facade.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UsuarioBean implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;
    private Usuario currentUser;
    private String username, password;

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
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

    /**
     * Validate logIn
     *
     * @return
     */
    public boolean validateLogin() {
        password = getMD5(password);
        currentUser = usuarioFacade.validateLogIn(username, password);
        if (currentUser != null) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Login exitoso", null));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("current", currentUser);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", currentUser.getUsername());
            redirect("articulos/create.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login erroneo: username o contraseña erroneos", null));
        }
        return currentUser != null;
    }
    
    public void redirect(String url){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/projectdb/faces/" + url);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logOut(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        redirect("index.xhtml");
    }
    /**
     * Return the encrypted password
     *
     * @param input
     * @return
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert a user in database
     */
    public void makeUser() {
        usuario.setPassword(getMD5(usuario.getPassword()));
        try {
            usuarioFacade.create(usuario);
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario creado exitosamente", null));
        } catch (Exception e) {
            System.err.println("Error de creacion de usuario: " + e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
        }
    }

    /**
     * Return all the users
     *
     * @return
     */
    public List<Usuario> getAllUsers() {
        return usuarioFacade.findAll();
    }

    @PostConstruct
    public void init() {
        username = new String();
        password = new String();
        usuario = new Usuario();
    }

}
