
package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import sig.view.SaleInvoice;


public class HeaderTable extends AbstractTableModel {

    private ArrayList<invoiceHeader> invoicesArray;
    private String[] cols = {"Invoice Num", "Invoice Date", "Customer Name", "Invoice Total"};
    
    public HeaderTable(ArrayList<invoiceHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int getRow, int getCol) {
        invoiceHeader inv = invoicesArray.get(getRow);
        switch (getCol) {
        case 0: 
            return
                    inv.getNum();
        case 1: 
            return 
                    SaleInvoice.dateFormat.format(inv.getDate());
        case 2: 
            return 
                    inv.getCustomer();
        case 3:
            return
                    inv.getItemTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int col) {
        return cols[col];
    }
}
