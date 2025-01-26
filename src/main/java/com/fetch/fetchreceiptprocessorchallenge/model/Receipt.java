package com.fetch.fetchreceiptprocessorchallenge.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Receipt {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private String total;
}
