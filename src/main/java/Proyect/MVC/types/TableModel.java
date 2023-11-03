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

import InstrumentosProtocol.InstrumentTypes;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    public static List<InstrumentTypes> rows;
    int[] cols;

    public TableModel(int[] cols, List<InstrumentTypes> rows){
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
        InstrumentTypes sucursal = rows.get(row);
        switch (cols[col]){
            case CODIGO: return sucursal.getCodigo();
            case NOMBRE: return sucursal.getNombre();
            case UNIDAD: return sucursal.getUnidad();
            default: return "";
        }
    }

    public InstrumentTypes getRowAt(int row) {
        return rows.get(row);
    }

    public static final int CODIGO=0;
    public static final int NOMBRE=1;
    public static final int UNIDAD=2;

    String[] colNames = new String[6];
    private void initColNames(){
        colNames[CODIGO]= "Codigo";
        colNames[NOMBRE]= "Nombre";
        colNames[UNIDAD]= "Unidad";
    }

}
