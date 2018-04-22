/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import Beans.ArticuloBean;
import Entities.Articulo;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author alejandro
 */

@FacesConverter("articuloConverter")
public class articuloConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ELContext elc = FacesContext.getCurrentInstance().getELContext();
        ArticuloBean articuloBean = (ArticuloBean) elc.getELResolver().getValue(elc, null, "articuloBean");
        for (Articulo article : articuloBean.getAllArticles()) {
            if (("" + article.hashCode()).equalsIgnoreCase(value)) {
                return article;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return "" + value.hashCode();
        }
        return null;
    }
    
}
