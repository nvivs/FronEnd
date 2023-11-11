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

import Protocol.Calibrations;
import Protocol.Instrument;
import Proyect.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JTextField searchNumber;
    private JButton save;
    private JTable list;
    private JButton delete;
    private JLabel searchNumberLbl;
    private JButton report;
    private JButton clear;
    private JComboBox tipos;
    private JTextField searchSerialNumber;
    private JLabel CalibrationsoLbl;
    private JPanel MedicionesPanel;
    private JLabel NumeroLbl;
    private JLabel cantMedicionesLbl;
    private JTextField numero;
    private JTextField cantMediciones;
    private JLabel FechaLbl;
    private JTextField day;
    private JTable medicionesList;
    private JLabel instrumentoLbl;
    private JPanel calibracionPanel;
    private JTextField month;
    private JTextField year;
    private JButton actualizar;

    public View() {

        searchNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                try {
                    search();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyPressed(KeyEvent e) {
                try {
                    search();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyReleased(KeyEvent e) {
                try {
                    search();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValid()){
                    if(model.getMode() == 1) {
                        try {
                            controller.save(take());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }else{
                        controller.edit();
                        controller.clear();
                    }
                }
            }
        });

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.imprimir();
                    if(Desktop.isDesktopSupported()){
                        File pdf = new File("Calibraciones.pdf");
                        Desktop.getDesktop().open(pdf);
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error:", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.setEditMode(row);
            }
        });
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.clear();
                    controller.refresh();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error:", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();

                cantMedicionesLbl.setText("Mediciones");
                cantMedicionesLbl.setForeground(Color.BLACK);
                cantMediciones.setToolTipText(null);
                cantMedicionesLbl.setToolTipText(null);

                FechaLbl.setText("Fecha");
                FechaLbl.setForeground(Color.BLACK);
                day.setToolTipText(null);
                day.setText(String.valueOf(LocalDate.now().getDayOfMonth()));
                month.setToolTipText(null);
                month.setText(String.valueOf(LocalDate.now().getMonthValue()));
                year.setToolTipText(null);
                year.setText(String.valueOf(LocalDate.now().getYear()));
                FechaLbl.setToolTipText(null);
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.delete();
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                try {
                    controller.shown();
                    if (!controller.getCurrentInstrument().getSerialNumber().isEmpty()) {
                        Instrument i = controller.getCurrentInstrument();
                        String x = i.getSerialNumber() + " - " + i.getDescription()
                                + " (" + i.getMin() + " - " + i.getMax()
                                + " " + i.getType().getUnidad() + ")";
                        instrumentoLbl.setText(x);
                        instrumentoLbl.setForeground(Color.RED);
                        cantMediciones.setEnabled(true);
                        day.setEnabled(true);
                        day.setText(String.valueOf(LocalDate.now().getDayOfMonth()));
                        month.setEnabled(true);
                        month.setText(String.valueOf(LocalDate.now().getMonthValue()));
                        year.setEnabled(true);
                        year.setText(String.valueOf(LocalDate.now().getYear()));
                        save.setEnabled(true);
                        clear.setEnabled(true);
                        report.setEnabled(true);
                        searchNumber.setEnabled(true);
                        numero.setText(String.valueOf(controller.getCalibrationIndex()));
                    }else{
                        instrumentoLbl.setText("<html><u><font color='red'>Seleccione un instrumento</font></u></html>");
                        cantMediciones.setEnabled(false);
                        day.setEnabled(false);
                        month.setEnabled(false);
                        year.setEnabled(false);
                        save.setEnabled(false);
                        clear.setEnabled(false);
                        report.setEnabled(false);
                        searchNumber.setEnabled(false);
                    }
                } catch (Exception ex) {
                }
            }
        });
    }

    Calibrations take(){
        Calibrations x = new Calibrations();
        x.setDate(day.getText() + "/" + month.getText() + "/" + year.getText());
        x.setCantMeasures(Integer.parseInt(cantMediciones.getText()));
        return x;
    }

    private boolean isValid(){
        boolean valid = true;
        //EN TODOS
        if(cantMediciones.getText().isEmpty()){
            valid = false;
            cantMedicionesLbl.setText("<html><u><font color='red'>Mediciones</font></u></html>");
            cantMedicionesLbl.setHorizontalAlignment(SwingConstants.CENTER);
            cantMediciones.setToolTipText("Cantidad de mediciones requerida");
            cantMedicionesLbl.setToolTipText("Cantidad de mediciones requerida");
        }else{
            try {
                if(Integer.parseInt(cantMediciones.getText()) != 0) {
                    cantMedicionesLbl.setText("Mediciones");
                    cantMediciones.setToolTipText(null);
                    cantMedicionesLbl.setToolTipText(null);
                }else{
                    throw new Exception();
                }
            }catch (NumberFormatException e){
                valid = false;
                cantMedicionesLbl.setText("<html><u><font color='red'>Mediciones</font></u></html>");
                cantMedicionesLbl.setHorizontalAlignment(SwingConstants.CENTER);
                cantMediciones.setToolTipText("Valor invalido");
                cantMedicionesLbl.setToolTipText("Valor invalido");
            } catch (Exception e) {
                valid = false;
                cantMedicionesLbl.setText("<html><u><font color='red'>Mediciones</font></u></html>");
                cantMedicionesLbl.setHorizontalAlignment(SwingConstants.CENTER);
                cantMediciones.setToolTipText("El valor ingresado debe ser distinto de 0");
                cantMedicionesLbl.setToolTipText("El valor ingresado debe ser distinto de 0");
            }
        }

        if(day.getText().isEmpty()){
            valid = false;
            FechaLbl.setText("<html><u><font color='red'>Fecha</font></u></html>");
            FechaLbl.setHorizontalAlignment(SwingConstants.CENTER);
            day.setToolTipText("Dia requerido");
            FechaLbl.setToolTipText("Dia requerido");
        } else{
            valid = isValid(valid, day);
        }

        if(month.getText().isEmpty()){
            valid = false;
            FechaLbl.setText("<html><u><font color='red'>Fecha</font></u></html>");
            FechaLbl.setHorizontalAlignment(SwingConstants.CENTER);
            month.setToolTipText("Mes requerido");
            FechaLbl.setToolTipText("Mes requerido");
        } else{
            valid = isValid(valid, month);
        }

        if(year.getText().isEmpty()){
            valid = false;
            FechaLbl.setText("<html><u><font color='red'>Fecha</font></u></html>");
            FechaLbl.setHorizontalAlignment(SwingConstants.CENTER);
            year.setToolTipText("Año requerido");
            FechaLbl.setToolTipText("Año requerido");
        } else{
            valid = isValid(valid, year);
        }

        return valid;
    }

    private boolean isValid(boolean valid, JTextField x) {
        try {
            if(Integer.parseInt(x.getText()) != 0) {
                FechaLbl.setText("Fecha");
                x.setToolTipText(null);
                FechaLbl.setToolTipText(null);
            }else{
                throw new Exception();
            }
        }catch (NumberFormatException e){
            valid = false;
            FechaLbl.setText("<html><u><font color='red'>Fecha</font></u></html>");
            FechaLbl.setHorizontalAlignment(SwingConstants.CENTER);
            x.setToolTipText("Valor invalido");
            FechaLbl.setToolTipText("El valor ingresado debe ser distinto de 0");
        } catch (Exception e) {
            valid = false;
            FechaLbl.setText("<html><u><font color='red'>Fecha</font></u></html>");
            FechaLbl.setHorizontalAlignment(SwingConstants.CENTER);
            x.setToolTipText("Valor invalido");
            FechaLbl.setToolTipText("Valor Invalido");
        }
        return valid;
    }

    public JPanel getPanel() {
        return panel;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.CALIBRATIONS) == Model.CALIBRATIONS) {//imprime la lista
            int[] cols = {TableModelCalibrations.NUMBER, TableModelCalibrations.DATE,
                    TableModelCalibrations.CANTMEASURES};
            list.setModel(new TableModelCalibrations(cols, model.getCalibrations()));
            list.setRowHeight(30);
        }

        if((changedProps & Model.MEASURES) == Model.MEASURES){
            int[] cols2 = {TableModelMeasures.NUMBER, TableModelMeasures.REFERENCE,
                    TableModelMeasures.READING};
            medicionesList.setModel(new TableModelMeasures(cols2, model.getMeasures()));
            medicionesList.setRowHeight(30);
        }

        if ((changedProps & Model.CURRENT) == Model.CURRENT) {//Pone los datos del current en los Text field
            if(model.getCurrent().getNumber() != 0) {
                numero.setText(String.valueOf(model.getCurrent().getNumber()));
            }else{
                try {
                    numero.setText(String.valueOf(controller.getCalibrationIndex()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            if(model.getCurrent().getCantMeasures() != null) {
                cantMediciones.setText((String.valueOf(model.getCurrent().getCantMeasures())));
            }else{
                cantMediciones.setText("");
            }

            if(model.getCurrent().getDate() != null) {
                int j = 1;
                StringBuilder val = new StringBuilder();
                for(int i = 0; i < model.getCurrent().getDate().length(); i++){
                    if(j == 3){
                        val.append(model.getCurrent().getDate().charAt(i));
                    }else if(model.getCurrent().getDate().charAt(i) != '/'){
                        val.append(model.getCurrent().getDate().charAt(i));
                    }else{
                        if(j == 1){
                            day.setText(String.valueOf(val));
                        } else if (j == 2) {
                            month.setText(String.valueOf(val));
                        }
                        j++;
                        val = new StringBuilder();
                    }
                }
                year.setText(String.valueOf(val));
            }else{
                day.setText(String.valueOf(LocalDate.now().getDayOfMonth()));
                month.setText(String.valueOf(LocalDate.now().getMonthValue()));
                year.setText(String.valueOf(LocalDate.now().getYear()));
            }
        }

        if(model.getMode() == 1){//agregar
            numero.setEnabled(false);
            delete.setEnabled(false);
            MedicionesPanel.setVisible(false);
            cantMediciones.setEnabled(true);
            day.setEnabled(true);
            month.setEnabled(true);
            year.setEnabled(true);
            day.setText(String.valueOf(LocalDate.now().getDayOfMonth()));
            month.setText(String.valueOf(LocalDate.now().getMonthValue()));
            year.setText(String.valueOf(LocalDate.now().getYear()));
            save.setText("Guardar");
            searchNumber.setEnabled(true);
        }else{//editar
            numero.setEnabled(false);
            delete.setEnabled(true);
            MedicionesPanel.setVisible(true);
            cantMediciones.setEnabled(false);
            day.setEnabled(false);
            month.setEnabled(false);
            year.setEnabled(false);
            save.setText("Editar");
            searchNumber.setEnabled(false);
        }
        this.panel.revalidate();
    }

    public void search() throws Exception {
        Calibrations filter= new Calibrations();
        try {
            filter.setNumber(Integer.parseInt(searchNumber.getText()));
        }catch (Exception e){
            filter.setNumber(0);
            filter.setInstrument(Application.instrumentsController.getCurrent());
            controller.search(filter);
        }
        filter.setInstrument(Application.instrumentsController.getCurrent());
        controller.search(filter);
    }

}
