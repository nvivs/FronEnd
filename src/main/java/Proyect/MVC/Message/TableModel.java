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
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    public static List<Message> rows;
    int col;

    public TableModel(int col, List<Message> rows){
        this.col=col;
        this.rows=rows;
    }

    public int getColumnCount() {
        return 1;
    }

    public Class<?> getColumnClass(int col){
        return super.getColumnClass(col);
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int col) {
        Message sucursal = rows.get(row);
        String message = "(";
        if(col == MESSAGE) {
            switch (sucursal.getTipo()) {
                case Message.CREATE:
                    message += "+ ";
                    break;
                case Message.DELETE:
                    message += "- ";
                    break;
                case Message.UPDATE:
                    message += "± ";
                    break;
            }
        }

        return message + (sucursal.getEntidad() + ") " + sucursal.getTexto());
    }

    public Message getRowAt(int row) {
        return rows.get(row);
    }

    public static final int MESSAGE =0;
}
