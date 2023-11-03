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
import InstrumentosProtocol.Calibrations;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelCalibrations extends AbstractTableModel implements javax.swing.table.TableModel {
    static List<Calibrations> rows;
    int[] cols;

    public TableModelCalibrations(int[] cols, List<Calibrations> rows){
        this.cols=cols;
        TableModelCalibrations.rows =rows;
        initColNames();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int col){
        return colNames[cols[col]];
    }

    public Class<?> getColumnClass(int col){
        return super.getColumnClass(col);
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int col) {
        Calibrations sucursal = rows.get(row);
        switch (cols[col]){
            case NUMBER: return sucursal.getNumber();
            case DATE: return sucursal.getDate();
            case CANTMEASURES: return sucursal.getCantMeasures();
            default: return "";
        }
    }

    public static final int NUMBER =0;
    public static final int DATE =1;
    public static final int CANTMEASURES =2;

    String[] colNames = new String[4];

    private void initColNames(){
        colNames[NUMBER]= "Numero";
        colNames[DATE]= "Fecha";
        colNames[CANTMEASURES]= "Mediciones";
    }

}
