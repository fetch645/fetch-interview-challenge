package com.fetch.fetchreceiptprocessorchallenge.domain;

import com.fetch.fetchreceiptprocessorchallenge.dao.ReceiptDao;
import com.fetch.fetchreceiptprocessorchallenge.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetPointsComponent {

    private final ReceiptDao receiptDao;

    public long getPoints(final String id) {
        return receiptDao.getPointsForReceiptId(id).orElseThrow(NotFoundException::new);
    }
}
