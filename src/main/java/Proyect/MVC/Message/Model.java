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
package Proyect.MVC.Message;

import Protocol.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Message> list;
    int changedProps = NONE;

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
        list = new ArrayList<>();
    }

    public List<Message> getList() {
        return list;
    }

    public void addMessage(Message message){
        list.add(message);
        changedProps += LIST;
    }

    public void clear(){
        list.clear();
        changedProps += LIST;
    }

    public static int NONE=0;
    public static int LIST=1;
}
