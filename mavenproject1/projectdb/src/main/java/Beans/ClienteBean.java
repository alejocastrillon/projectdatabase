/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.Cliente;
import Facade.ClienteFacade;
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
public class ClienteBean implements Serializable{

    @EJB
    private ClienteFacade clienteFacade;
    private Cliente cliente;
    /**
     * Creates a new instance of ClienteBean
     */
    public ClienteBean() {
    }

    public ClienteFacade getClienteFacade() {
        return clienteFacade;
    }

    public void setClienteFacade(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    /**
     * Insert a client into database
     */
    public void makeClient(){
        try {
            clienteFacade.create(cliente);
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente creado exitosamente", null));
        } catch (Exception e) {
            System.err.println("Error en la creacion del usuario: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), null));
        }
    }
    
    public List<Cliente> getAllClientes(){
        return clienteFacade.findAll();
    }
    
    @PostConstruct
    public void init(){
        cliente = new Cliente();
    }
}
