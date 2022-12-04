package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LinesTable extends AbstractTableModel {

    private ArrayList<invoiceLine> invoiceLines;
    private String[] cols = {"Item Name", "Unit Price", "Count", "Line Total"};

    public LinesTable(ArrayList<invoiceLine> linesArray) {
        this.invoiceLines = linesArray;
    }

    @Override
    public int getRowCount() {
        return invoiceLines == null ? 0 : invoiceLines.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int getRow, int getCol) {
        if (invoiceLines == null) {
            return "";
        } else {
            invoiceLine line = invoiceLines.get(getRow);
            switch (getCol) {
                case 0:
                    return line.getItem();
                case 1:
                    return line.getPrice();
                case 2:
                    return line.getCount();
                case 3:
                    return line.getLineTotal();
                default:
                    return "";
            }
        }
    }

    @Override
    public String getColumnName(int col) {
        return cols[col];
    }

}


