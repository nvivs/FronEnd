package Proyect.MVC.Message;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JTable list;
    private JButton clear;

    private Controller controller;
    private Model model;

    public View() {

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.LIST) == Model.LIST) {//imprime la lista
            int col = Proyect.MVC.Message.TableModel.MESSAGE;
            list.setModel(new TableModel(col, model.getList()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(200);
        }
        this.panel.revalidate();
    }
}
