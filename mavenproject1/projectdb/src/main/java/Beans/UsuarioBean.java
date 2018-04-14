/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.Usuario;
import Facade.UsuarioFacade;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author alejandro
 */
@ManagedBean
public class UsuarioBean {

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
     * @return 
     */
    public boolean validateLogin() {
        currentUser = usuarioFacade.validateLogIn(username, password);
        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Login exitoso", null));
        return currentUser != null;
    }

    /**
     * Return the encrypted password
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
    @PostConstruct
    public void init(){
        username = new String();
        password = new String();
        usuario = new Usuario();
        currentUser = new Usuario();
    }
    
}
