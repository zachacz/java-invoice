package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private static int nextNumber = 0;

    private final int invoiceNumber;


    public Invoice() {

        this.invoiceNumber = ++nextNumber;

    }

    public void addProduct(Product product) {

        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {

        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        Integer foundQuantity = products.get(product);

        if (foundQuantity == null) {

            products.put(product, quantity);
        } else {

            products.put(product, foundQuantity + quantity);
        }

    }


    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getInvoiceNumber() {

        return invoiceNumber;
    }

    public int getNumberOfItems() {

        return products.size();
    }

    public int getProductQuantity(Product product) {

        return products.get(product);
    }

    public void printInvoice() {

        System.out.println("Invoice number: " + getInvoiceNumber());

        for (Product product : products.keySet()) {

            System.out.println(product.getName() + " " + products.get(product) + " "
                    + product.getPrice() + "zl");
        }
        System.out.println("Number of items: " + getNumberOfItems());
    }
}
