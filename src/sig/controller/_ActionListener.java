package sig.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import sig.view.SaleInvoice;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import sig.view.HeaderFrame;
import sig.view.LinesFrame;
import sig.model.invoiceHeader;
import sig.model.HeaderTable;
import sig.model.invoiceLine;
import sig.model.LinesTable;





 public class _ActionListener implements ActionListener{
         private SaleInvoice _frame;
         private HeaderFrame _headerDialog;
         private LinesFrame _lineDialog;

    public _ActionListener(SaleInvoice frame) {
         
        this._frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      
        switch(e.getActionCommand()){
            
            case "Create New Invoice" : 
            
                CreateNewInvoice();
            break;
    
          case "Delete Invoice" : 
         
            DeleteInvoice();
              break;
    
              case "Save Changes" : 
         
                 SaveChanges();
              break;
    
                 case "Cancel" : 
           
                    cancel();
                  break;
             case "newInvoiceSave":
                newInvoiceDialogOK();
                break;

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineSave":
                newLineDialogOK();
                break;
    
              case "load file" : 
          
                     loadfile();
                  break;
    
                 case "save file" : 
                  System.out.println("saved");
                     savefile();
                    break;

       
}
    }
 
 
    
      private void loadfile() {
        JFileChooser fileChooser = new JFileChooser();
        try {
            int result = fileChooser.showOpenDialog(_frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                ArrayList<invoiceHeader> Headers = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] arr = headerLine.split(",");
                    String str1 = arr[0];
                    String str2 = arr[1];
                    String str3 = arr[2];
                    int code = Integer.parseInt(str1);
                    Date invoiceDate = SaleInvoice.dateFormat.parse(str2);
                    invoiceHeader header = new invoiceHeader(code, str3, invoiceDate);
                    Headers.add(header);
                }
                _frame.setInvoicesArray(Headers);

                result = fileChooser.showOpenDialog(_frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fileChooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<invoiceLine> invoiceLines = new ArrayList<>();
                    for (String line : lineLines) {
                        String[] l = line.split(",");
                        String s1 = l[0];    // invoice num (int)
                        String s2 = l[1];    // item name   (String)
                        String s3 = l[2];    // price       (double)
                        String s4 = l[3];    // count       (int)
                        int invCode = Integer.parseInt(s1);
                        double price = Double.parseDouble(s3);
                        int count = Integer.parseInt(s4);
                        invoiceHeader inv = _frame.getInvObject(invCode);
                        invoiceLine invoiceLine = new invoiceLine(s2, price, count, inv);
                        inv.getLines().add(invoiceLine);
                    }
                }
                HeaderTable headerTable = new HeaderTable(Headers);
                _frame.setInvoiceheaderTable(headerTable);
                _frame.getheaderTable().setModel(headerTable);
                System.out.println("files read");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(_frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(_frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CreateNewInvoice() {
        _headerDialog = new HeaderFrame(_frame);
        _headerDialog.setVisible(true);
    }

    private void DeleteInvoice() {
        int selectedInvoiceIndex = _frame.getheaderTable().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            _frame.getInvoicesArray().remove(selectedInvoiceIndex);
            _frame.getInvoiceheaderTable().fireTableDataChanged();

            _frame.getlineTable().setModel(new LinesTable(null));
            _frame.setLinesArray(null);
            _frame.getCustNameLbl().setText("");
            _frame.getInvNumLbl().setText("");
            _frame.getInvTotalIbl().setText("");
            _frame.getDateLbl().setText("");
        }
    }

    private void SaveChanges() {
        _lineDialog = new LinesFrame(_frame);
        _lineDialog.setVisible(true);
    }

    private void cancel() {
        int selectedLineIndex = _frame.getlineTable().getSelectedRow();
        int selectedInvoiceIndex = _frame.getheaderTable().getSelectedRow();
        if (selectedLineIndex != -1) {
            _frame.getLinesArray().remove(selectedLineIndex);
            LinesTable lineTableModel = (LinesTable) _frame.getlineTable().getModel();
            lineTableModel.fireTableDataChanged();
            _frame.getInvTotalIbl().setText("" + _frame.getInvoicesArray().get(selectedInvoiceIndex).getItemTotal());
            _frame.getInvoiceheaderTable().fireTableDataChanged();
            _frame.getheaderTable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void savefile() {
        ArrayList<invoiceHeader> invoicesArray = _frame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showSaveDialog(_frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                String headers = "";
                String lines = "";
                for (invoiceHeader invoice : invoicesArray) {
                    headers += invoice.toString();
                    headers += "\n";
                    for (invoiceLine line : invoice.getLines()) {
                        lines += line.toString();
                        lines += "\n";
                    }
                }
                
                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                result = fc.showSaveDialog(_frame);
                File lineFile = fc.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(_frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void  newLineDialogCancel() {
        _lineDialog.setVisible(false);
        _lineDialog.dispose();
        _lineDialog = null;
    }
  

    private void newInvoiceDialogOK() {
        _headerDialog.setVisible(false);

        String custName = _headerDialog.getCustNameField().getText();
        String str = _headerDialog.getDateField().getText();
        Date d = new Date();
        try {
             d = SaleInvoice.dateFormat.parse(str);
        } 
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(_frame, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (invoiceHeader inv : _frame.getInvoicesArray()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        invoiceHeader newInv = new invoiceHeader(invNum, custName, d);
        _frame.getInvoicesArray().add(newInv);
       _frame.getInvoiceheaderTable().fireTableDataChanged();
       _headerDialog.dispose();
        _headerDialog = null;
    }

    private void newInvoiceDialogCancel() {
        _headerDialog.setVisible(false);
        _headerDialog.dispose();
       _headerDialog = null;
    }

    private void newLineDialogOK() {
        _lineDialog.setVisible(false);

        String name = _lineDialog.getItemNameField().getText();
        String str1 = _lineDialog.getItemCountField().getText();
        String str2 = _lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(_frame, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(str2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(_frame, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = _frame.getheaderTable().getSelectedRow();
                 if (selectedInvHeader != -1) {
            invoiceHeader invHeader = _frame.getInvoicesArray().get(selectedInvHeader);
            invoiceLine line = new invoiceLine(name, price, count, invHeader);
            //invHeader.getLines().add(line);
            _frame.getLinesArray().add(line);
            LinesTable lineTable = (LinesTable) _frame.getlineTable().getModel();
            lineTable.fireTableDataChanged();
            _frame.getInvoiceheaderTable().fireTableDataChanged();
        }
        _frame.getheaderTable().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        _lineDialog.dispose();
        _lineDialog = null;
    }

    

  

}