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

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import InstrumentosProtocol.InstrumentTypes;

public class View implements Observer {
    private JPanel panel;
    private JTextField searchNombre;
    private JButton save;
    private JTable list;
    private JButton delete;
    private JLabel searchNombreLbl;
    private JButton report;
    private JTextField codigo;
    private JTextField nombre;
    private JTextField unidad;
    private JLabel codigoLbl;
    private JLabel nombreLbl;
    private JLabel unidadLbl;
    private JButton clear;
    private JButton actualizar;

    public View() {

        searchNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                InstrumentTypes filter= new InstrumentTypes();
                filter.setNombre(searchNombre.getText());
                try {
                    controller.search(filter);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyPressed(KeyEvent e) {
                InstrumentTypes filter= new InstrumentTypes();
                filter.setNombre(searchNombre.getText());
                try {
                    controller.search(filter);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void keyReleased(KeyEvent e) {
                InstrumentTypes filter= new InstrumentTypes();
                filter.setNombre(searchNombre.getText());
                try {
                    controller.search(filter);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.imprimir();
                    if(Desktop.isDesktopSupported()){
                        File pdf = new File("TiposDeInstrumentos.pdf");
                        Desktop.getDesktop().open(pdf);
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error:", JOptionPane.INFORMATION_MESSAGE);
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
                        controller.edit(take());
                    }
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

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();

                codigoLbl.setText("Codigo");
                codigoLbl.setForeground(Color.BLACK);
                codigo.setToolTipText(null);
                codigoLbl.setToolTipText(null);

                unidadLbl.setText("Unidad");
                unidadLbl.setForeground(Color.BLACK);
                unidad.setToolTipText(null);
                unidadLbl.setToolTipText(null);

                nombreLbl.setText("Nombre");
                nombreLbl.setForeground(Color.BLACK);
                nombre.setToolTipText(null);
                nombreLbl.setToolTipText(null);
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
    }


    InstrumentTypes take(){
        InstrumentTypes x = new InstrumentTypes();
        x.setCodigo(codigo.getText());
        x.setUnidad(unidad.getText());
        x.setNombre(nombre.getText());
        return x;
    }

    private boolean isValid(){
        boolean valid = true;

        if(codigo.getText().isEmpty()){
            valid = false;
            codigoLbl.setText("<html><u><font color='red'>Codigo</font></u></html>");
            codigoLbl.setHorizontalAlignment(SwingConstants.CENTER);
            codigo.setToolTipText("Codigo requerido");
            codigoLbl.setToolTipText("Codigo requerido");
        }else{
            codigoLbl.setText("Codigo");
            codigo.setToolTipText(null);
            codigoLbl.setToolTipText(null);
        }

        if(unidad.getText().isEmpty()){
            valid = false;
            unidadLbl.setText("<html><u><font color='red'>Unidad</font></u></html>");
            unidadLbl.setHorizontalAlignment(SwingConstants.CENTER);
            unidad.setToolTipText("Unidad requerida");
            unidadLbl.setToolTipText("Unidad requerida");
        }else{
            unidadLbl.setText("Unidad");
            unidad.setToolTipText(null);
            unidadLbl.setToolTipText(null);
        }

        if(nombre.getText().isEmpty()){
            valid = false;
            nombreLbl.setText("<html><u><font color='red'>Nombre</font></u></html>");
            nombreLbl.setHorizontalAlignment(SwingConstants.CENTER);
            nombre.setToolTipText("Nombre requerido");
            nombreLbl.setToolTipText("Nombre requerido");
        }else{
            nombreLbl.setText("Nombre");
            nombre.setToolTipText(null);
            nombreLbl.setToolTipText(null);
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
            int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.UNIDAD};
            list.setModel(new TableModel(cols, model.getList()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(2).setPreferredWidth(200);
        }
        if ((changedProps & Model.CURRENT) == Model.CURRENT) {//Pone los datos del current en los Text field
            codigo.setText(model.getCurrent().getCodigo());
            nombre.setText(model.getCurrent().getNombre());
            unidad.setText(model.getCurrent().getUnidad());
        }

        if(model.getMode() == 1){//agregar
            codigo.setEnabled(true);
            delete.setEnabled(false);
            save.setText("Guardar");
            searchNombre.setEnabled(true);
        }else{//editar
            codigo.setEnabled(false);
            delete.setEnabled(true);
            save.setText("Editar");
            searchNombre.setEnabled(false);
        }
        this.panel.revalidate();
    }
}
