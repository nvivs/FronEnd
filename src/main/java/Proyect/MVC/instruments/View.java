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

import Proyect.Application;

import InstrumentosProtocol.Instrument;
import InstrumentosProtocol.InstrumentTypes;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JTextField searchDescripcion;
    private JButton save;
    private JTable list;
    private JButton delete;
    private JLabel searchDescripcionLbl;
    private JButton report;
    private JTextField serie;
    private JTextField minimo;
    private JTextField descripcion;
    private JLabel serieLbl;
    private JLabel minimoLbl;
    private JLabel descripcionLbl;
    private JButton clear;
    private JLabel toleranciaLbl;
    private JTextField tolerancia;
    private JLabel maximoLbl;
    private JTextField maximo;
    private JLabel tipoLbl;
    private JComboBox tipos;
    private JLabel searchSerialNumberLbl;
    private JTextField searchSerialNumber;
    private JButton actualizar;

    public View() {

        searchDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                try {
                    searchDescription();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyPressed(KeyEvent e) {
                try {
                    searchDescription();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyReleased(KeyEvent e) {
                try {
                    searchDescription();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        searchSerialNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                try {
                    searchSerialNumber();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyPressed(KeyEvent e) {
                try {
                    searchSerialNumber();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyReleased(KeyEvent e) {
                try {
                    searchSerialNumber();
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
                            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error:", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{
                        try {
                            controller.edit(take());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                setComboBoxModel();
            }
        });

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.imprimir();
                    if(Desktop.isDesktopSupported()){
                        File pdf = new File("Instrumentos.pdf");
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

                serieLbl.setText("Serie");
                serieLbl.setForeground(Color.BLACK);
                serie.setToolTipText(null);
                serieLbl.setToolTipText(null);

                minimoLbl.setText("Minimo");
                minimoLbl.setForeground(Color.BLACK);
                minimo.setToolTipText(null);
                minimoLbl.setToolTipText(null);

                toleranciaLbl.setText("Tolerancia");
                toleranciaLbl.setForeground(Color.BLACK);
                tolerancia.setToolTipText(null);
                toleranciaLbl.setToolTipText(null);

                descripcionLbl.setText("Descripcion");
                descripcionLbl.setForeground(Color.BLACK);
                descripcion.setToolTipText(null);
                descripcionLbl.setToolTipText(null);

                maximoLbl.setText("Maximo");
                maximoLbl.setForeground(Color.BLACK);
                maximo.setToolTipText(null);
                maximoLbl.setToolTipText(null);
                setComboBoxModel();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error:", JOptionPane.INFORMATION_MESSAGE);
                }
                setComboBoxModel();
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                try {
                    controller.shown();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Application.calibrationsController.clear();
            }
        });
    }

    Instrument take() throws Exception {
        Instrument x = new Instrument();
        x.setSerialNumber(serie.getText());
        x.setDescription(descripcion.getText());
        x.setMin(Integer.parseInt(minimo.getText()));
        x.setMax(Integer.parseInt(maximo.getText()));
        x.setTolerance(Integer.parseInt(tolerancia.getText()));
        x.setType(Application.tiposController.searchName(String.valueOf(tipos.getSelectedItem())));
        return x;
    }

    private boolean isValid(){
        boolean valid = true;

        if(serie.getText().isEmpty()){
            valid = false;
            serieLbl.setForeground(Color.RED);
            serieLbl.setHorizontalAlignment(SwingConstants.CENTER);
            serie.setToolTipText("Serie requerida");
            serieLbl.setToolTipText("Serie requerida");
        }else{
            serieLbl.setText("Serie");
            serie.setToolTipText(null);
            serieLbl.setToolTipText(null);
        }

        if(descripcion.getText().isEmpty()){
            valid = false;
            descripcionLbl.setForeground(Color.RED);
            descripcionLbl.setHorizontalAlignment(SwingConstants.CENTER);
            descripcion.setToolTipText("Descripcion requerida");
            descripcionLbl.setToolTipText("Descripcion requerida");
        }else{
            descripcionLbl.setText("Descripcion");
            descripcion.setToolTipText(null);
            descripcionLbl.setToolTipText(null);
        }

        if(minimo.getText().isEmpty()){
            valid = false;
            minimoLbl.setForeground(Color.RED);
            minimoLbl.setHorizontalAlignment(SwingConstants.CENTER);
            minimo.setToolTipText("Valor requerido");
            minimoLbl.setToolTipText("Valor requerido");
        }else{
            if(Integer.parseInt(minimo.getText()) >= Integer.parseInt(maximo.getText())) {
                valid = false;
                minimoLbl.setForeground(Color.RED);
                minimoLbl.setHorizontalAlignment(SwingConstants.CENTER);
                minimo.setToolTipText("El minimo debe ser menor al maximo");
                minimoLbl.setToolTipText("El minimo debe ser menor al maximo");
            }else{
                minimoLbl.setText("Minimo");
                minimo.setToolTipText(null);
                minimoLbl.setToolTipText(null);
            }
        }

        if(maximo.getText().isEmpty()){
            valid = false;
            maximoLbl.setForeground(Color.RED);
            maximoLbl.setHorizontalAlignment(SwingConstants.CENTER);
            maximo.setToolTipText("Valor requerido");
            maximoLbl.setToolTipText("Valor requerido");
        }else{
            if (Integer.parseInt(minimo.getText()) >= Integer.parseInt(maximo.getText())) {
                valid = false;
                maximoLbl.setForeground(Color.RED);
                maximoLbl.setHorizontalAlignment(SwingConstants.CENTER);
                maximo.setToolTipText("El maximo debe ser mayor al minimo");
                maximoLbl.setToolTipText("El maximo debe ser mayor al minimo");
            } else {
                maximoLbl.setText("Maximo");
                maximo.setToolTipText(null);
                maximoLbl.setToolTipText(null);
            }
        }

        if(tolerancia.getText().isEmpty()){
            valid = false;
            toleranciaLbl.setForeground(Color.RED);
            toleranciaLbl.setHorizontalAlignment(SwingConstants.CENTER);
            tolerancia.setToolTipText("Valor requerido");
            toleranciaLbl.setToolTipText("Valor requerido");
        }else{
            Integer.parseInt(tolerancia.getText());
            toleranciaLbl.setText("Tolerancia");
            tolerancia.setToolTipText(null);
            toleranciaLbl.setToolTipText(null);
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
        if ((changedProps & Model.LIST) == Model.LIST) {//imprime la lista
            int[] cols = {TableModel.SERIALNUMBER, TableModel.DESCRIPTION,
                    TableModel.MIN, TableModel.MAX, TableModel.TOLERANCE};
            list.setModel(new TableModel(cols, model.getInstruments()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(145);
        }
        if((changedProps & Model.TYPES) == Model.TYPES){
            setComboBoxModel();
        }
        if ((changedProps & Model.CURRENT) == Model.CURRENT) {//Pone los datos del current en los Text field
            serie.setText(model.getCurrent().getSerialNumber());
            if(model.getCurrent().getMin() != null) {
                minimo.setText((String.valueOf(model.getCurrent().getMin())));
            }else{
                minimo.setText("");
            }
            if(model.getCurrent().getMax() != null) {
                maximo.setText((String.valueOf(model.getCurrent().getMax())));
            }else{
                maximo.setText("");
            }
            if(model.getCurrent().getTolerance() != null) {
                tolerancia.setText((String.valueOf(model.getCurrent().getTolerance())));
            }else{
                tolerancia.setText("");
            }
            descripcion.setText(model.getCurrent().getDescription());
            tipos.setSelectedItem(model.getCurrent().getType().getNombre());
        }

        if(model.getMode() == 1){//agregar
            serie.setEnabled(true);
            delete.setEnabled(false);
            save.setText("Guardar");
            tipos.setEnabled(true);
            searchDescripcion.setEnabled(true);
            searchSerialNumber.setEnabled(true);
        }else{//editar
            serie.setEnabled(false);
            delete.setEnabled(true);
            save.setText("Editar");
            tipos.setEnabled(false);
            searchDescripcion.setEnabled(false);
            searchSerialNumber.setEnabled(false);
        }
        this.panel.revalidate();
    }

    public void setComboBoxModel(){
        tipos.setModel(new DefaultComboBoxModel(model.getTypes().toArray(new InstrumentTypes[0])));
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for(InstrumentTypes objeto : model.getTypes()){
            modelo.addElement(objeto.getNombre());
        }
        tipos.setModel(modelo);
    }

    public void searchSerialNumber() throws Exception {
        Instrument filter= new Instrument();
        filter.setSerialNumber(searchSerialNumber.getText());
        controller.searchSerialNumber(filter);
        setComboBoxModel();
    }

    public void searchDescription() throws Exception {
        Instrument filter= new Instrument();
        filter.setDescription(searchDescripcion.getText());
        controller.searchDescription(filter);
        setComboBoxModel();
    }
}
