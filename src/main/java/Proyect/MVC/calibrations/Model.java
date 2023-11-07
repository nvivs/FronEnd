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

import BackEnd.logic.Service;
import Protocol.Calibrations;
import Protocol.Measures;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Calibrations> calibrations;
    Calibrations current;
    int changedProps = NONE;
    int mode = 1;
    List<Measures> measures;

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

    public void init(List<Calibrations> list){
        setCalibrations(list);
        setMeasures(new ArrayList<Measures>());
        setCurrent(new Calibrations());
    }

    public List<Calibrations> getCalibrations() {
        return calibrations;
    }
    public void setCalibrations(List<Calibrations> calibrations){
        this.calibrations = calibrations;
        changedProps += CALIBRATIONS;
    }

    public List<Measures> getMeasures(){
        return measures;
    }
    public void setMeasures(List<Measures> measures){
        this.measures = measures;
        changedProps += MEASURES;
    }

    public Calibrations getCurrent() {
        return current;
    }
    public void setCurrent(Calibrations current) {
        changedProps += CURRENT;
        if(current.getMeasures() != null) setMeasures(current.getMeasures());
        this.current = current;
    }

    public static int NONE=0;
    public static int CALIBRATIONS =1;
    public static int CURRENT=2;
    public static int MEASURES = 4;
}
