package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public void addProduct(Product product) {
        this.products.put(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        this.products.put(product, quantity);
    }

    public BigDecimal getSubtotal() {
        return getTotal().subtract(getTax());
    }

    public BigDecimal getTax() {
        BigDecimal totalTax = new BigDecimal(0);
        Iterator<Map.Entry<Product, Integer>> it = products.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Product, Integer> pair = it.next();
            BigDecimal productTax = pair.getKey().getTaxOnly();
            BigDecimal quantity = new BigDecimal(pair.getValue());
            totalTax = totalTax.add(productTax.multiply(quantity));
        }
        return totalTax;
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        Iterator<Map.Entry<Product, Integer>> it = products.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Product, Integer> pair = it.next();
            BigDecimal productPriceWithTax = pair.getKey().getPriceWithTax();
            BigDecimal quantity = new BigDecimal(pair.getValue());
            total = total.add(productPriceWithTax.multiply(quantity));
        }
        return total;
    }
}
