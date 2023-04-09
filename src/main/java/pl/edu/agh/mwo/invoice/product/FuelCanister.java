package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class FuelCanister extends Product {

    private final BigDecimal excise = new BigDecimal("5.56");

    public static Supplier<LocalDateTime> localDateTime = () -> LocalDateTime.now();

    public FuelCanister(String name, BigDecimal price) {

        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {

        if (localDateTime.get().getDayOfMonth() == 5 && localDateTime.get().getMonthValue() == 3) {
            return getPrice().add(excise);
        }

        else {

            return super.getPriceWithTax().add(excise);

        }
    }
}
