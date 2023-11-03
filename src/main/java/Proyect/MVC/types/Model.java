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

import InstrumentosProtocol.InstrumentTypes;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<InstrumentTypes> list;
    InstrumentTypes current;
    int changedProps = NONE;
    int mode = 1;

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(changedProps);
        changedProps = NONE;
    }

    public Model() {
    }

    public void setMode(int mode){
        this.mode = mode;
    }

    public int getMode(){
        return this.mode;
    }

    public void init(List<InstrumentTypes> list){
        setList(list);
        setCurrent(new InstrumentTypes());
    }

    public List<InstrumentTypes> getList() {
        return list;
    }
    public void setList(List<InstrumentTypes> list){
        this.list = list;
        changedProps += LIST;
    }

    public InstrumentTypes getCurrent() {
        return current;
    }
    public void setCurrent(InstrumentTypes current) {
        changedProps += CURRENT;
        this.current = current;
    }

    public static int NONE=0;
    public static int LIST=1;
    public static int CURRENT=2;
}
