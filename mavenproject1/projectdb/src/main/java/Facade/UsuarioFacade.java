/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entities.Usuario;
import java.util.ArrayList;
import java.util.List;
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
    
    public Usuario validateLogIn(String username, String password) {
        try {
            List<Usuario> userLogin = new ArrayList<Usuario>();
            userLogin = getEntityManager().createNamedQuery("Usuario.logIn").setParameter("username", username).setParameter("password", password).setParameter("habilitado", true).getResultList();
            if (userLogin.size() > 0) {
                return userLogin.get(0);
            }
        } catch (Exception e) {
            System.err.println("Error Login: " + e.getMessage());
        }
        return null;
    }
    
    public List<Usuario> getEnabledUsers(boolean habilitado){
        List<Usuario> users = new ArrayList<Usuario>();
        try {
            users = getEntityManager().createNamedQuery("Usuario.findByHabilitado").setParameter("habilitado", habilitado).getResultList();
        } catch (Exception e) {
            System.err.println("Error conseguir usuarios: " + e.getLocalizedMessage());
        }
        return users;
    }
    
}
