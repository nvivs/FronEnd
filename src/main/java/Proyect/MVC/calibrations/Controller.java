/*
 *  Derek Rojas Mendoza
 *  604740973
 *  Nicole Vivas Montero
 *  402510851
 *  Universidad Nacional de Costa Rica
 *  Escuela de informatica
 *  EIF 206-01 - Programacion 3
 *  Proyecto 2
 *  Fecha de entrega: 11/11/2023
 */
package Proyect.MVC.calibrations;


import Proyect.logic.Service;
import Protocol.Calibrations;
import Protocol.Instrument;
import Protocol.Measures;
import Proyect.Application;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) throws Exception {
        model.init(Service.instance().search(new Calibrations()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Calibrations filter) throws Exception {
        if(filter.getNumber() == 0){
            model.setCalibrations(Service.instance().search(filter));
            model.setCurrent(new Calibrations());
            model.commit();
        }else {
            model.setCalibrations(Service.instance().search(filter));
            model.setCurrent(new Calibrations());
            model.commit();
        }
    }

    public void setEditMode(int row) {//setea el current
        Calibrations e = model.getCalibrations().get(row);
        try {
            model.setMode(2);
            model.setCurrent(Service.instance().read(e));
            model.commit();
        } catch (Exception ignored) {
        }
    }

    public void edit() {
        try {
            Service.instance().update(model.getMeasures(), model.getCurrent().getNumber());
            model.setMode(1);
            model.setCurrent(new Calibrations());
            model.commit();
        } catch (Exception ignored) {
        }
    }

    public void save(Calibrations x){
        x.setInstrument(getCurrentInstrument());
        x.setMeasures(createMeasures(x));
        try{
            Service.instance().create(x);
            refresh();
        }catch (Exception ignored){
            System.out.println(-1);
        }
    }

    public void clear() {//limpia los Text field
        try {
            model.setMode(1);
            model.setCurrent(new Calibrations());
            model.commit();
        } catch (Exception ignored) {
        }
    }

    public void delete(){
        try {
            Service.instance().delete(model.getCurrent());
            refresh();
        } catch (Exception ignored) {
        }
    }

    public void shown(){
        try {
            Calibrations c = new Calibrations();
            c.setInstrument(getCurrentInstrument());
            model.setCalibrations(Service.instance().search(c));
            model.commit();
        } catch (Exception ignored) {

        }
    }

    public Instrument getCurrentInstrument(){
        return Application.instrumentsController.getCurrent();
    }

    public int getCalibrationIndex() throws Exception {
        return Service.instance().getCalibrationIndex();
    }

    private List<Measures> createMeasures(Calibrations c) {
        int x = (getCurrentInstrument().getMax() - getCurrentInstrument().getMin()) / c.getCantMeasures();
        int y = 0;
        List<Measures> list = new ArrayList<>();

        for(int i = 0; i < c.getCantMeasures(); i++){
            list.add(new Measures(i+1, y, 0));
            y += x;
        }
        return list;
    }

    public void imprimir() throws Exception {
        Service.instance().imprimir(Collections.singletonList(TableModelCalibrations.rows), "Calibraciones.pdf");
    }

    public void refresh() throws Exception{
        model.setMode(1);
        Calibrations x = new Calibrations(0, getCurrentInstrument(), null, null, null);
        List<Calibrations> rows = Service.instance().search(x);
        model.setCalibrations(rows);
        model.setCurrent(new Calibrations());
        model.commit();
    }
}
