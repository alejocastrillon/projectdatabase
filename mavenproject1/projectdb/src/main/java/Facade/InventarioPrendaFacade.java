/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entities.Articulo;
import Entities.InventarioPrenda;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alejandro
 */
@Stateless
public class InventarioPrendaFacade extends AbstractFacade<InventarioPrenda> {

    @PersistenceContext(unitName = "com.mycompany_projectdb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InventarioPrendaFacade() {
        super(InventarioPrenda.class);
    }
    
    public InventarioPrenda prendasArticulo(Articulo a, Integer cantidad){
        try {
            List<InventarioPrenda> prenda = getEntityManager().createNamedQuery("InventarioPrenda.findByArticleandQuantity").setParameter("articulo", a).setParameter("quantity", cantidad).getResultList();
            return prenda.get(0);
        } catch (Exception e) {
            System.out.println("Error al conseguir prenda ..." + e.getLocalizedMessage());
        }
        return null;
    }
    
}
