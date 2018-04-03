/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alejandro
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "com.mycompany_projectdb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario validateLogIn(String username, String password){
        Usuario userLogin = new Usuario();
        try {
            userLogin = (Usuario) getEntityManager().createNamedQuery("Usuario.logIn").setParameter("username", username).setParameter("password", password).getSingleResult();
        } catch (Exception e) {
            System.err.println("Error LogIn: " + e.getLocalizedMessage());
        }
        return userLogin;
    }
    
}
