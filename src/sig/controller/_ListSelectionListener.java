
package sig.controller;
    
import sig.model.invoiceHeader;
import sig.model.invoiceLine;
import sig.model.LinesTable;
import sig.view.SaleInvoice;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class _ListSelectionListener implements ListSelectionListener {

    private  SaleInvoice InvoiceFrame;

    public _ListSelectionListener(SaleInvoice frame) {
        this.InvoiceFrame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = InvoiceFrame.getheaderTable().getSelectedRow();
        System.out.println("Invoice selected: " + selectedInvIndex);
        if (selectedInvIndex != -1) {
            invoiceHeader selectedInv = InvoiceFrame.getInvoicesArray().get(selectedInvIndex);
            ArrayList<invoiceLine> lines = selectedInv.getLines();
            LinesTable lineTable = new LinesTable(lines);
            InvoiceFrame.setLinesArray(lines);
            InvoiceFrame.getlineTable().setModel(lineTable);
            InvoiceFrame.getCustNameLbl().setText(selectedInv.getCustomer());
            InvoiceFrame.getInvNumLbl().setText("" + selectedInv.getNum());
            InvoiceFrame.getInvTotalIbl().setText("" + selectedInv.getItemTotal());
            InvoiceFrame.getDateLbl().setText(SaleInvoice.dateFormat.format(selectedInv.getDate()));
        }
    }

}

    

