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
package Proyect.MVC.instruments;
import Protocol.Calibrations;
import Protocol.InstrumentTypes;
import Protocol.Instrument;
import Proyect.logic.Service;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) throws Exception {
        model.init(Service.instance().searchSerialNumber(new Instrument()),
                Service.instance().search(new InstrumentTypes()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void searchDescription(Instrument filter) throws Exception {
        List<Instrument> rows = Service.instance().searchDescription(filter);
        model.setInstruments(rows);
        model.setCurrent(new Instrument());
        model.commit();
    }

    public void searchSerialNumber(Instrument filter) throws Exception {
        List<Instrument> rows = Service.instance().searchSerialNumber(filter);
        model.setInstruments(rows);
        model.setCurrent(new Instrument());
        model.commit();
    }

    public void setEditMode(int row) {//setea el current
        Instrument e = model.getInstruments().get(row);
        try {
            model.setMode(2);
            model.setCurrent(Service.instance().read(e));
            model.commit();
        } catch (Exception ex) {
        }
    }

    public void edit(Instrument x) {
        try {
            Service.instance().update(x);
            model.setMode(1);
            searchDescription(new Instrument());
        } catch (Exception ex) {
        }
    }

    public void save(Instrument x) throws Exception{
        Service.instance().create(x);
        model.setMode(1);
        clear();
        searchDescription(new Instrument());
    }
    
    public void clear() {//limpia los Text field
        try {
            model.setMode(1);
            model.setCurrent(new Instrument());
            model.commit();
        } catch (Exception ex) {
        }
    }

    public void delete() throws Exception{
        Calibrations c = new Calibrations();
        c.setInstrument(model.getCurrent());
        for(Calibrations i : (Service.instance().getList(c))) {
            if (i.getInstrument().equals(model.getCurrent())) {
                throw new Exception("Error. El instrumento a borrar tiene calibraciones asociadas!");
            }
        }
        Service.instance().delete(model.getCurrent());
        model.setMode(1);
        searchDescription(new Instrument());
    }

    public void shown() throws Exception {
        model.setTypes(Service.instance().search(new InstrumentTypes()));
        model.commit();
    }

    public Instrument getCurrent(){
        return model.getCurrent();
    }

    public void imprimir() throws Exception {
        Service.instance().imprimir(Collections.singletonList(TableModel.rows), "Instrumentos.pdf");
    }
    public void refresh() throws Exception{
        List<Instrument> rows = Service.instance().refreshInstrument();
        model.setList(rows);
        model.setCurrent(new Instrument());
        model.commit();
    }
}
