/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.ArticulosFactura;
import Facade.ArticulosFacturaFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author alejandro
 */
@ManagedBean
@ViewScoped
@SessionScoped
public class ArticuloFacturaBean implements Serializable{

    @EJB
    private ArticulosFacturaFacade aff;
    
    
    /**
     * Creates a new instance of ArticuloFacturaBean
     */
    public ArticuloFacturaBean() {
    }
    
    /**
     * Return the articulosFactura list
     * @return 
     */
    public List<ArticulosFactura> getAllArticulosFactura(){
        return aff.findAll();
    }
    
}
