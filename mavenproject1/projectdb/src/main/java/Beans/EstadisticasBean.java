/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entities.Articulo;
import Entities.ArticulosFactura;
import Entities.Factura;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author alejandro
 */
@ManagedBean
@ViewScoped
@SessionScoped
public class EstadisticasBean implements Serializable {

    private PieChartModel model;
    private BarChartModel modelMonthYear;

    /**
     * Creates a new instance of EstadisticasBean
     */
    public EstadisticasBean() {
    }

    public PieChartModel getModel() {
        return model;
    }

    public BarChartModel getModelMonthYear() {
        return modelMonthYear;
    }

    /**
     * Create the model chart of general article sale
     */
    private void createModelForArticle() {
        model = new PieChartModel();
        //Get all ArticulosFactura item
        ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
        ArticuloBean articuloBean = (ArticuloBean) eLContext.getELResolver().getValue(eLContext, null, "articuloBean");
        List<Articulo> articulos = articuloBean.getAllArticles();
        for (Articulo articulo : articulos) {
            model.set(articulo.getNombre(), cantidadPorArticulo(articulo));
        }
        model.setTitle("Diagrama de ventas totales por articulo");
        model.setLegendPosition("w");
    }

    /**
     * Return the general article sale quantity
     *
     * @param articulo
     * @return
     */
    public Integer cantidadPorArticulo(Articulo articulo) {
        Integer cantidad = 0;
        ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
        ArticuloFacturaBean articuloFacturaBean = (ArticuloFacturaBean) eLContext.getELResolver().getValue(eLContext, null, "articuloFacturaBean");
        List<ArticulosFactura> afs = articuloFacturaBean.getAllArticulosFactura();
        for (ArticulosFactura af : afs) {
            if ((af.getArticuloIdarticulo().equals(articulo)) && (af.getFacturaIdfactura().getHabilitada() == true)) {
                cantidad += af.getCantidad();
            }
        }
        return cantidad;
    }

    public void createModelForMonthandYear() {
        ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
        FacturaBean facturaBean = (FacturaBean) eLContext.getELResolver().getValue(eLContext, null, "facturaBean");
        modelMonthYear = new BarChartModel();
        BarChartSeries chartSeries = new BarChartSeries();
        chartSeries.setLabel("Cantidad de ventas");
        Calendar c = Calendar.getInstance();
        int anno = c.get(Calendar.YEAR);
        for (int i = 1; i <= 12; i++) {
            List<Factura> facturas = facturaBean.getFacturasByMonthandYear(i, anno);
            chartSeries.set(getNameMonth(i), facturas.size());
        }
        modelMonthYear.addSeries(chartSeries);
        modelMonthYear.setAnimate(true);
        modelMonthYear.setTitle("Ventas por meses");
        modelMonthYear.setLegendPosition("e");
        Axis yAxis = modelMonthYear.getAxis(AxisType.Y);
        Axis xAxis = modelMonthYear.getAxis(AxisType.X);
        yAxis.setLabel("Cantidad de ventas");
        yAxis.setMin(0);
        yAxis.setMax(facturaBean.getAllFacturas().size() + 3);
        xAxis.setLabel("Meses");
    }

    public String getNameMonth(int i) {
        String mes = new String();
        switch (i) {
            case 1:
                mes = "Enero";
                break;
            case 2:
                mes = "Febrero";
                break;
            case 3:
                mes = "Marzo";
                break;
            case 4:
                mes = "Abril";
                break;
            case 5:
                mes = "Mayo";
                break;
            case 6:
                mes = "Junio";
                break;
            case 7:
                mes = "Julio";
                break;
            case 8:
                mes = "Agosto";
                break;
            case 9:
                mes = "Septiembre";
                break;
            case 10:
                mes = "Octubre";
                break;
            case 11:
                mes = "Noviembre";
                break;
            case 12:
                mes = "Diciembre";
                break;
        }
        return mes;
    }

    @PostConstruct
    public void init() {
        createModelForArticle();
        createModelForMonthandYear();
    }

}
