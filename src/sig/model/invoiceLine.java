
package sig.model;

public class invoiceLine {
    private String itemName;
    private double itemPrice;
    private int count;
    private invoiceHeader header;

    public invoiceLine() {
    }

    public invoiceLine(String item, double price, int count, invoiceHeader header) {
        this.itemName = item;
        this.itemPrice = price;
        this.count = count;
        this.header = header;
    }

    public invoiceHeader getHeader() {
        return header;
    }

    public void setHeader(invoiceHeader header) {
        this.header = header;
    }

    public String getItem() {
        return itemName;
    }

    public void setItem(String item) {
        this.itemName = item;
    }

    public double getPrice() {
        return itemPrice;
    }

    public void setPrice(double price) {
        this.itemPrice = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public double getLineTotal() {
        return itemPrice * count;
    }

   

    
    
}
