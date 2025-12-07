package com.example.price.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Data
public class PriceRecord {

    String id;
    LocalDateTime asOf;
    Map<String, Object> payload;
}
