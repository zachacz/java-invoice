package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class FuelCanister extends Product {

    public static final int DAY = 5;
    public static final int MONTH = 3;

    private final BigDecimal excise = new BigDecimal("5.56");

    public static Supplier<LocalDateTime> localDateTime = () -> LocalDateTime.now();

    public FuelCanister(String name, BigDecimal price) {

        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {

        if (localDateTime.get().getDayOfMonth() == DAY
                && localDateTime.get().getMonthValue() == MONTH) {
            return getPrice().add(excise);
        } else {

            return super.getPriceWithTax().add(excise);

        }
    }
}
