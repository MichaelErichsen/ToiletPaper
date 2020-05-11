package net.myerichsen.toiletpaper.ui.suppliers;
/*
 * Copyright (c) 2020. Michael Erichsen.
 */

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Class to encapsulate all toilet paper supplier data
 */
public class SupplierModel {
    private String supplier = "";
    private String chain = "";
    private String timestamp;

    /**
     * no-arg constructor
     */
    public SupplierModel() {
        timestamp =
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    /**
     * Constructor
     */
    public SupplierModel(String supplier, String chain) {
        this.supplier = supplier;
        this.chain = chain;
        timestamp =
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
