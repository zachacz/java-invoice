package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private static int nextNumber = 0;

    private final int invoiceNumber;

    private int numberOfItems;

    public Invoice() {

        this.invoiceNumber = ++nextNumber;

        this.numberOfItems = getItemsTotal();


    }

    public void addProduct(Product product) {

        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {

        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        if (checkIfInvoiceHasProduct(product)) {

                for (Map.Entry<Product, Integer> entry : products.entrySet()) {

                    Product foundProduct = entry.getKey();

                    Integer foundQuantity = entry.getValue();

                    if (foundProduct.equals((product))) {

                        entry.setValue(foundQuantity + quantity);
                    }
                }
        }

        else {

            products.put(product, quantity);
        }

    }

    public boolean checkIfInvoiceHasProduct(Product product) {

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {

            Product foundProduct = entry.getKey();

            if (product.equals(foundProduct)) {

                return true;
            }
        }
        return false;
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

    public int getItemsTotal() {

        numberOfItems = this.products.size();

        return numberOfItems;
    }
}
