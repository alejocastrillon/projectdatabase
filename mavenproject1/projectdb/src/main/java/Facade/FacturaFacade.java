/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entities.Factura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alejandro
 */
@Stateless
public class FacturaFacade extends AbstractFacade<Factura> {

    @PersistenceContext(unitName = "com.mycompany_projectdb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }
    
    public List<Factura> getFacturaByMonthandYear(int month, int year){
        try {
            List<Factura> facturas = getEntityManager().createNamedQuery("Factura.findByMonthandYear").setParameter("month", month).setParameter("year", year).getResultList();
            return facturas;
        } catch (Exception e) {
            System.err.println("Error al conseguir las facturas por mes: " + e.getMessage());
        }
        return null;
    }
    
    public List<Factura> getEnabledorDisabledFacturas(boolean enable){
        try {
            List<Factura> facturas = getEntityManager().createNamedQuery("Factura.findByHabilitada").setParameter("habilitada", enable).getResultList();
            return facturas;
        } catch (Exception e) {
            System.err.println("Error al conseguir las listas habilitadas o deshabilitadas: " + e.getMessage());
        }
        return null;
    }

    
}
