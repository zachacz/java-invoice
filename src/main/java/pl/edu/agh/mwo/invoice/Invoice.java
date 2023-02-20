package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Collection<Product> products;

    public Invoice() {

        this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {

        if (product == null) {

            throw new IllegalArgumentException("Product can not be null.");
        }

        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {

        if (quantity <= 0) {

            throw new IllegalArgumentException("Quantity can not be 0 or negative.");
        }

        for (int i=0; i<quantity; i++) {

            this.products.add(product);
        }
    }

    public BigDecimal getSubtotal() {

        BigDecimal subtotal = BigDecimal.ZERO;

        if (products.isEmpty()) {

            return subtotal;
        }

        for (Product product : products) {

            subtotal = subtotal.add(product.getPrice());

        }

        return subtotal;
    }

    public BigDecimal getTax() {

        BigDecimal taxAmount = BigDecimal.ZERO;

        if (products.isEmpty()) {

            return taxAmount;
        }

        for (Product product : products) {

            taxAmount = taxAmount.add(product.getPrice().multiply(product.getTaxPercent()));
        }

        return taxAmount;
    }

    public BigDecimal getTotal() {

        BigDecimal total = BigDecimal.ZERO;

        if (products.isEmpty()) {

            return total;
        }

        for (Product product : products) {

            total = total.add(product.getPriceWithTax());
        }

        return total;
    }
}
