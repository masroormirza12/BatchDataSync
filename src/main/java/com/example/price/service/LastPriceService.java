package com.example.price.service;

import com.example.price.model.PriceRecord;
import org.springframework.http.ResponseEntity;

import java.util.*;

public interface LastPriceService {
    UUID startBatch();
    ResponseEntity<?> uploadChunk(UUID batchId, List<PriceRecord> records);
    ResponseEntity<?> completeBatch(UUID batchId);
    ResponseEntity<?> cancelBatch(UUID batchId);
    ResponseEntity<?> getLastPrice(String id);
}
