package com.fetch.fetchreceiptprocessorchallenge.util;

import lombok.NonNull;

public record Price(int dollars, int cents) {

    public static Price fromString(final @NonNull String string) {
        final int decimalIndex = string.indexOf(".");
        final int dollars = Integer.parseInt(string.substring(0, decimalIndex));
        final int cents = Integer.parseInt(string.substring(decimalIndex + 1));
        return new Price(dollars, cents);
    }

    public boolean isRoundDollarAmount() {
        return cents == 0;
    }

    public boolean isMultipleOf(final int value) {
        return cents % 25 == 0;
    }
}
