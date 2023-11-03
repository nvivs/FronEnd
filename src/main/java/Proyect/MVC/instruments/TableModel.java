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

import InstrumentosProtocol.Instrument;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    public static List<Instrument> rows;
    int[] cols;

    public TableModel(int[] cols, List<Instrument> rows){
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
        switch (cols[col]){
            default: return super.getColumnClass(col);
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int col) {
        Instrument sucursal = rows.get(row);
        switch (cols[col]){
            case DESCRIPTION: return sucursal.getDescription();
            case SERIALNUMBER: return sucursal.getSerialNumber();
            case MIN: return sucursal.getMin();
            case MAX: return sucursal.getMax();
            case TOLERANCE: return sucursal.getTolerance();
            default: return "";
        }
    }

    public Instrument getRowAt(int row) {
        return rows.get(row);
    }

    public static final int SERIALNUMBER =0;
    public static final int DESCRIPTION =1;
    public static final int MIN =2;
    public static final int MAX =4;
    public static final int TOLERANCE =8;

    String[] colNames = new String[16];

    private void initColNames(){
        colNames[SERIALNUMBER]= "No. Serie";
        colNames[DESCRIPTION]= "Descripcion";
        colNames[MIN]= "Minimo";
        colNames[MAX]= "Maximo";
        colNames[TOLERANCE]= "Tolerancia";
    }

}
