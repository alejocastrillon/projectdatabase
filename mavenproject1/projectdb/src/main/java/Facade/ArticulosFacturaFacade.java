/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entities.Articulo;
import Entities.ArticulosFactura;
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
public class ArticulosFacturaFacade extends AbstractFacade<ArticulosFactura> {

    @PersistenceContext(unitName = "com.mycompany_projectdb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticulosFacturaFacade() {
        super(ArticulosFactura.class);
    }
    
    /**
     * Return the article sale list
     * @param articulo
     * @return 
     */
    public List<ArticulosFactura> getAllForArticle(Articulo articulo){
        try {
            List<ArticulosFactura> afs = getEntityManager().createNamedQuery("ArticulosFactura.findByArticulo").setParameter("idarticulo", articulo.getIdarticulo()).getResultList();
            return afs;
        } catch (Exception e) {
            System.err.println("Error al conseguir la lista de Articulos en Factura: " + e.getMessage());
        }
        return null;
    }
    
}
