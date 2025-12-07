package com.example.price.model;

import com.example.price.service.LastPriceService;
import com.example.price.service.LastPriceServiceImpl;
import lombok.Data;

import java.util.*;
import java.util.function.Consumer;

@Data
public class BatchContext {
    private  UUID batchId;
    private  Map<String, PriceRecord> records = new HashMap<>();

    public BatchContext(UUID id){ this.batchId=id; }

    public void addRecords(List<PriceRecord> recs) throws RuntimeException{
        for(PriceRecord r: recs){
            PriceRecord existing = records.get(r.getId());
            if(existing==null || existing.getAsOf().isBefore(r.getAsOf())){
                records.put(r.getId(), r);
                updatePrice().accept(r);
            }else{
                throw new RuntimeException(" data is older then the current record");
            }
        }
    }

    //ADDED FOR CONSUMER TO VIEW PRICES EVEN BEFORE THE BATCH IS COMPLETED
    public Consumer<PriceRecord> updatePrice() {
        return priceRecord -> LastPriceServiceImpl.globalPrices
                .computeIfAbsent(priceRecord.getId(), id -> priceRecord);
    }

}
