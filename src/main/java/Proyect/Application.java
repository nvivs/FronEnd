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

public class Application {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        Proyect.MVC.types.Model tiposModel= new Proyect.MVC.types.Model();
        Proyect.MVC.instruments.Model instrumentsModel = new Proyect.MVC.instruments.Model();
        Proyect.MVC.calibrations.Model calibrationsModel = new Proyect.MVC.calibrations.Model();
        Proyect.MVC.Message.Model messageModel = new Proyect.MVC.Message.Model();

        Proyect.MVC.types.View types = new Proyect.MVC.types.View();
        Proyect.MVC.instruments.View instruments = new Proyect.MVC.instruments.View();
        Proyect.MVC.calibrations.View calibrations = new Proyect.MVC.calibrations.View();
        Proyect.MVC.Message.View messages = new Proyect.MVC.Message.View();

        tiposController = new Proyect.MVC.types.Controller(types,tiposModel);
        instrumentsController = new Proyect.MVC.instruments.Controller(instruments, instrumentsModel);
        calibrationsController = new Proyect.MVC.calibrations.Controller(calibrations, calibrationsModel);
        messageController = new Proyect.MVC.Message.Controller(messages, messageModel);

        window = new JFrame();
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(5, 5, 770, 450);
        tabs.setBorder(BorderFactory.createTitledBorder("Mantenimiento"));
        tabs.add("Tipos de Instrumento", types.getPanel());
        tabs.add("Instrumentos", instruments.getPanel());
        tabs.add("Calibraciones", calibrations.getPanel());

        JPanel mensajes = messages.getPanel();
        mensajes.setBorder(BorderFactory.createTitledBorder("Mensajes"));
        mensajes.setBounds(780, 5, 300, 450);

        window.setLayout(null);
        window.add(tabs);
        window.add(mensajes);
        window.setSize(1085,500);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("SILAB: Sistema de Laboratorio Industrial");
        window.setVisible(true);
    }

    public static Proyect.MVC.types.Controller tiposController;
    public static Proyect.MVC.instruments.Controller instrumentsController;
    public static Proyect.MVC.calibrations.Controller calibrationsController;
    public static Proyect.MVC.Message.Controller messageController;

    public static JFrame window;
}
