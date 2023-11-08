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
package Proyect;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Application {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        window = new JFrame();
        window.setContentPane(new JTabbedPane());

        Proyect.MVC.types.Model tiposModel= new Proyect.MVC.types.Model();
        Proyect.MVC.instruments.Model instrumentsModel = new Proyect.MVC.instruments.Model();
        Proyect.MVC.calibrations.Model calibrationsModel = new Proyect.MVC.calibrations.Model();

        Proyect.MVC.types.View types = new Proyect.MVC.types.View();
        Proyect.MVC.instruments.View instruments = new Proyect.MVC.instruments.View();
        Proyect.MVC.calibrations.View calibrations = new Proyect.MVC.calibrations.View();

        tiposController = new Proyect.MVC.types.Controller(types,tiposModel);
        instrumentsController = new Proyect.MVC.instruments.Controller(instruments, instrumentsModel);
        calibrationsController = new Proyect.MVC.calibrations.Controller(calibrations, calibrationsModel);

        window.getContentPane().add("Tipos de Instrumento", types.getPanel());
        window.getContentPane().add("Instrumentos", instruments.getPanel());
        window.getContentPane().add("Calibraciones", calibrations.getPanel());

        window.setSize(900,450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("SILAB: Sistema de Laboratorio Industrial");
        window.setVisible(true);
    }

    public static Proyect.MVC.types.Controller tiposController;
    public static Proyect.MVC.instruments.Controller instrumentsController;
    public static Proyect.MVC.calibrations.Controller calibrationsController;

    public static JFrame window;
}
