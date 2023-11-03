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

import InstrumentosProtocol.Measures;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableModelMeasures extends AbstractTableModel implements javax.swing.table.TableModel {
     List<Measures> rows;
     int[] cols;

    public TableModelMeasures(int[] cols, List<Measures> rows){
        this.cols=cols;
        this.rows=rows;
        initColNames();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int col){
        return colNames[cols[col]];
    }

    public Class<?> getColumnClass(int col){
        if (cols[col] == READING) {
            return Integer.class;
        }
        return super.getColumnClass(col);
    }

    public int getRowCount() {
        return rows.size();
    }

    public boolean isCellEditable(int rowIndex, int columIndex){
        return columIndex == READING;
    }

    public Object getValueAt(int row, int col) {
        Measures sucursal = rows.get(row);
        switch (cols[col]){
            case NUMBER: return sucursal.getNumber();
            case REFERENCE: return sucursal.getReference();
            case READING: return sucursal.getReading();
            default: return "";
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columIndex){
        Measures e = rows.get(rowIndex);
        if (cols[columIndex] == READING) {
            e.setReading((Integer) aValue);
        }
    }

//    public Measures getRowAt(int row) {
//        return rows.get(row);
//    }

    public static final int NUMBER =0;
    public static final int REFERENCE =1;
    public static final int READING =2;

    String[] colNames = new String[4];

    private void initColNames(){
        colNames[NUMBER]= "Numero";
        colNames[REFERENCE]= "Referencia";
        colNames[READING]= "Lectura";
    }
}
