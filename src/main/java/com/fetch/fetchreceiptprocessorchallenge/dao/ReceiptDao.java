package com.fetch.fetchreceiptprocessorchallenge.dao;

import com.fetch.fetchreceiptprocessorchallenge.model.Receipt;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ReceiptDao {

    private final Map<String, Long> pointMap;

    public ReceiptDao() {
        pointMap = new HashMap<>();
    }

    public void storePointsForReceiptId(final @NonNull String id, final long points) {
        pointMap.put(id, points);
    }

    public Optional<Long> getPointsForReceiptId(final @NonNull String id) {
        return Optional.ofNullable(pointMap.get(id));
    }
}
