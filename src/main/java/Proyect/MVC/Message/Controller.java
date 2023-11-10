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
import Proyect.logic.ITarget;
import Proyect.logic.Service;

import java.util.ArrayList;

public class Controller implements ITarget {
    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        Service.instanceListener().addTarget(this);
        Service.instanceListener().start();
    }

    public void clear() {
        model.clear();
        model.commit();
    }

    public void stop(){
        Service.instanceListener().stop();
    }

    @Override
    public void deliver(Message message) {
        model.addMessage(message);
        model.commit();
    }
}
