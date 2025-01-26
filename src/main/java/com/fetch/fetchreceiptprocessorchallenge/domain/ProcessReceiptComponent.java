package com.fetch.fetchreceiptprocessorchallenge.domain;

import com.fetch.fetchreceiptprocessorchallenge.dao.ReceiptDao;
import com.fetch.fetchreceiptprocessorchallenge.exception.BadRequestException;
import com.fetch.fetchreceiptprocessorchallenge.model.Item;
import com.fetch.fetchreceiptprocessorchallenge.model.Receipt;
import com.fetch.fetchreceiptprocessorchallenge.util.Price;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ProcessReceiptComponent {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final ReceiptDao receiptDao;

    public String processReceipt(final @NonNull Receipt receipt) {

        long points = 0;

        for (char c : receipt.getRetailer().toCharArray()) {
            if (Character.isLetter(c) || Character.isDigit(c)) {
                points++;
            }
        }

        final Price totalPrice;
        try {
            totalPrice = Price.fromString(receipt.getTotal());
        } catch (final Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        if (totalPrice.isRoundDollarAmount()) {
            points += 50;
        }


        if (totalPrice.isMultipleOf(25)) {
            points += 25;
        }

        points += (receipt.getItems().size() / 2) * 5L;

        for (final Item item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                final Price price;
                try {
                    price = Price.fromString(item.getPrice());
                } catch (final Exception e) {
                    throw new BadRequestException(e.getMessage(), e);
                }
                final double value = (price.dollars() * 1.0) + (price.cents() * 0.01);
                final int score = (int)Math.ceil(value * 0.2);

                points += score;
            }
        }

        final Date purchaseDate;
        try {
            purchaseDate = DATE_FORMAT.parse(receipt.getPurchaseDate());
        } catch (final Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseDate);

        if (calendar.get(Calendar.DAY_OF_MONTH) % 2 == 1) {
            points += 6;
        }

        final LocalTime localTime;

        try {
            localTime = LocalTime.parse(receipt.getPurchaseTime());
        } catch (final Exception e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        if (localTime.isAfter(LocalTime.of(14, 0)) && localTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        final String uuid = UUID.randomUUID().toString();

        receiptDao.storePointsForReceiptId(uuid, points);

        return uuid;
    }
}
