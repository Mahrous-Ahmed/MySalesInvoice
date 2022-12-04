
package sig.model;

import java.util.ArrayList;
import java.util.Date;

        public class invoiceHeader {
                private int invoiceNum;
                private String customerName;
                private Date invoiceDate;
                private ArrayList<invoiceLine> inoviceLines;
               

public  invoiceHeader() {

}

    public invoiceHeader(int num, String customer, Date date) {
        this.invoiceNum = num;
        this.customerName = customer;
        this.invoiceDate = date;
        }
    public int getNum() {
        return invoiceNum;
    }

    public void setNum(int num) {
        this.invoiceNum = num;
    }

    public String getCustomer() {
        return customerName;
    }

    public void setCustomer(String customer) {
        this.customerName = customer;
    }

    public Date getDate() {
        return invoiceDate;
    }

    public void setDate(Date date) {
        this.invoiceDate = date;
    }
    
  public ArrayList<invoiceLine> getLines() {
        if (inoviceLines != null) {
            return inoviceLines;
            
        }
        return inoviceLines = new ArrayList<>();
    }

    public void setLines(ArrayList<invoiceLine> lines) {
        this.inoviceLines = lines;
    }
    
    
    
    public double getItemTotal(){
        double sum = 0.0;
        int i =0;
        while(i < getLines().size() ){
            sum += getLines().get(i).getLineTotal();
            i++;
       }
        
       
        return sum;
    }
   
        }

  


    
