package com.fetch.fetchreceiptprocessorchallenge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String shortDescription;
    private String price;
}
