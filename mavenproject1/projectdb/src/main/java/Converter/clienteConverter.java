/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import Beans.ClienteBean;
import Entities.Cliente;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author alejandro
 */
@FacesConverter("clienteConverter")
public class clienteConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ELContext elc = FacesContext.getCurrentInstance().getELContext();
        ClienteBean clienteBean = (ClienteBean) elc.getELResolver().getValue(elc, null, "clienteBean");
        for (Cliente cliente : clienteBean.getAllClientes()) {
            if (("" + cliente.hashCode()).equalsIgnoreCase(value)) {
                return cliente;
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
