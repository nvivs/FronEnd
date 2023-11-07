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

import Protocol.InstrumentTypes;
import Protocol.Instrument;

import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Instrument> instruments;
    Instrument current;
    int changedProps = NONE;
    int mode = 1;
    List<InstrumentTypes> types;

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
    public void setList(List<Instrument> list){
        this.instruments = list;
        changedProps += LIST;
    }

    public void init(List<Instrument> list, List<InstrumentTypes> types){
        setInstruments(list);
        setTypes(types);
        setCurrent(new Instrument());
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }
    public void setInstruments(List<Instrument> instruments){
        this.instruments = instruments;
        changedProps += LIST;
    }

    public List<InstrumentTypes> getTypes(){
        return types;
    }
    public void setTypes(List<InstrumentTypes> types){
        this.types = types;
        changedProps += TYPES;
    }

    public Instrument getCurrent() {
        return current;
    }
    public void setCurrent(Instrument current) {
        changedProps += CURRENT;
        this.current = current;
    }

    public static int NONE=0;
    public static int LIST=1;
    public static int CURRENT=2;
    public static int TYPES = 4;
}
