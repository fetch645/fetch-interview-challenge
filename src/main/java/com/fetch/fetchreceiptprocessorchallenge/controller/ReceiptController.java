package com.fetch.fetchreceiptprocessorchallenge.controller;

import com.fetch.fetchreceiptprocessorchallenge.domain.GetPointsComponent;
import com.fetch.fetchreceiptprocessorchallenge.domain.ProcessReceiptComponent;
import com.fetch.fetchreceiptprocessorchallenge.exception.BadRequestException;
import com.fetch.fetchreceiptprocessorchallenge.exception.NotFoundException;
import com.fetch.fetchreceiptprocessorchallenge.model.Receipt;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReceiptController {

    private final ProcessReceiptComponent processReceiptComponent;

    private final GetPointsComponent getPointsComponent;

    @PostMapping("/process")
    public ResponseEntity<ProcessReceiptResponse> processReceipt(final @RequestBody Receipt receipt) {

        final String id;

        try {
            id = processReceiptComponent.processReceipt(receipt);
        } catch (final BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (final Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().body(ProcessReceiptResponse.builder().id(id).build());
    }

    @Data
    @Builder
    public static class ProcessReceiptResponse {
        private String id;
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<GetPointsResponse> getPoints(final @PathVariable String id) {

        final long points;

        try {
            points = getPointsComponent.getPoints(id);
        } catch (final NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (final Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().body(GetPointsResponse.builder().points(points).build());
    }

    @Data
    @Builder
    public static class GetPointsResponse {
        private long points;
    }
}
