package com.example.price.service;

import com.example.price.model.BatchContext;
import com.example.price.model.PriceRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LastPriceServiceImpl implements LastPriceService {

    public static final Map<String, PriceRecord> globalPrices = new ConcurrentHashMap<>();
    private final Map<UUID, BatchContext> batches = new ConcurrentHashMap<>();

    @Override
    public UUID startBatch(){
        UUID id = UUID.randomUUID();
        batches.put(id, new BatchContext(id));
        return id;
    }

    @Override
    public ResponseEntity<?> uploadChunk(UUID batchId, List<PriceRecord> records){
        BatchContext ctx = batches.get(batchId);
        if(ctx != null){
            try {
                ctx.addRecords(records);
                return ResponseEntity.ok("Records Added" + "\n" + records);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        log.info("No data for the given batch id");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> completeBatch(UUID batchId){
        BatchContext ctx = batches.remove(batchId);
        if(ctx != null){
            ctx.getRecords()
                    .forEach((k,v)-> {
                PriceRecord existing = globalPrices.get(k);
                if(existing==null || existing.getAsOf().isBefore(v.getAsOf())){
                    globalPrices.put(k, v);
                }
            });
           return ResponseEntity.ok().body("Batch completed");
        }
        return ResponseEntity.badRequest().body("Invalid Batch ID provided");
    }

    @Override
    public ResponseEntity<?> cancelBatch(UUID batchId){
        if (!globalPrices.containsKey(batchId)) {
          return ResponseEntity.badRequest().body("Invalid Batch ID provided");
        }
        batches.remove(batchId);
        return ResponseEntity.ok().body("Data processing for the given batch is cancelled");
    }

    @Override
    public ResponseEntity<?> getLastPrice(String id){
       if (globalPrices.containsKey(id)) {
           return ResponseEntity.ok(globalPrices.get(id));
       }
       return ResponseEntity.badRequest().body("No prices for the given product avaliable now ");
    }
}
