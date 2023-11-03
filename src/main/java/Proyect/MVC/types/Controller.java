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
package Proyect.MVC.types;

import InstrumentosProtocol.Instrument;
import InstrumentosProtocol.InstrumentTypes;
import BackEnd.logic.Service;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;

public class Controller {
    View intrumentTypesView;
    Model model;

    public Controller(View intrumentTypesView, Model model) throws Exception {
        model.init(Service.instance().search(new InstrumentTypes()));
        this.intrumentTypesView = intrumentTypesView;
        this.model = model;
        intrumentTypesView.setController(this);
        intrumentTypesView.setModel(model);
    }

    public void search(InstrumentTypes filter) throws Exception {
        List<InstrumentTypes> rows = Service.instance().search(filter);
        model.setList(rows);
        model.setCurrent(new InstrumentTypes());
        model.commit();
    }

    public void setEditMode(int row) {//setea el current
        InstrumentTypes e = model.getList().get(row);
        try {
            model.setMode(2);
            model.setCurrent(Service.instance().read(e));
            model.commit();
        } catch (Exception ex) {
        }
    }

    public void edit(InstrumentTypes x) {
        try {
            Service.instance().update(x);
            model.setMode(1);
            search(new InstrumentTypes());
        } catch (Exception ex) {
        }
    }

    public void save(InstrumentTypes x) throws Exception{
        Service.instance().create(x);
        model.setMode(1);
        search(new InstrumentTypes());
    }
    
    public void clear() {//limpia los Text field
        try {
            model.setMode(1);
            model.setCurrent(new InstrumentTypes());
            model.commit();
        } catch (Exception ex) {
        }
    }

    public void delete() throws Exception{
        for(Instrument i : (Service.instance().searchSerialNumber(new Instrument()))) {//recorre los instrumentos
            if (i.getType().equals(model.getCurrent())) {//si algun instrumento tiene el tipo a borrar asociado
                throw new Exception("Error. El tipo a borrar tiene instrumentos asociados!");
            }
        }
        Service.instance().delete(model.getCurrent());
        model.setMode(1);
        search(new InstrumentTypes());
    }

    public void imprimir() throws Exception {
        Service.instance().imprimir(Collections.singletonList(TableModel.rows), "TiposDeInstrumentos.pdf");
    }

    public InstrumentTypes searchName(String name) throws Exception {
        return Service.instance().readName(new InstrumentTypes("", name, ""));
    }
    public void refresh() throws Exception{
        List<InstrumentTypes> rows = Service.instance().refresh();
        model.setList(rows);
        model.setCurrent(new InstrumentTypes());
        model.commit();
    }
}
