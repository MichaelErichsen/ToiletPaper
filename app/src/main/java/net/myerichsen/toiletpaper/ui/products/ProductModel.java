/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper.ui.products;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to encapsulate all toilet paper product data.
 * Boolean values are stored as integers 0 (false) and 1 (true).
 */
public class ProductModel {
    private int uid;
    private int layers = 0;
    private int packageRolls = 0;
    private int rollSheets = 0;
    private int sheetWidth = 0;
    private int sheetLength = 0;
    private int sheetLength_c = 0;
    private float rollLength = 0;
    private int rollLength_c = 0;
    private float packagePrice = 0;
    private int packagePrice_c = 0;
    private float rollPrice = 0;
    private int rollPrice_c = 0;
    private float paperWeight = 0;
    private int paperWeight_c = 0;
    private float kiloPrice = 0;
    private int kiloPrice_c = 0;
    private float meterPrice = 0;
    private int meterPrice_c = 0;
    private float sheetPrice = 0;
    private int sheetPrice_c = 0;
    private String supplier = "";
    private String comments = "";
    private String itemNo = "";
    private String brand = "";
    private String timestamp;

    /**
     * No arg constructor
     */
    public ProductModel() {
        Long tsLong = System.currentTimeMillis();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        timestamp = s.format(new Date());
    }

    /**
     * Constructor
     *
     * @param itemNo
     * @param brand
     * @param layers
     * @param packageRolls
     * @param rollSheets
     * @param sheetWidth
     * @param sheetLength
     * @param sheetLength_c
     * @param rollLength
     * @param rollLength_c
     * @param packagePrice
     * @param packagePrice_c
     * @param rollPrice
     * @param rollPrice_c
     * @param paperWeight
     * @param paperWeight_c
     * @param kiloPrice
     * @param kiloPrice_c
     * @param meterPrice
     * @param meterPrice_c
     * @param sheetPrice
     * @param sheetPrice_c
     * @param supplier
     * @param comments
     */
    public ProductModel(String itemNo, String brand, int layers, int packageRolls, int rollSheets, int sheetWidth,
                        int sheetLength, int sheetLength_c, float rollLength, int rollLength_c,
                        float packagePrice, int packagePrice_c, float rollPrice, int rollPrice_c,
                        float paperWeight, int paperWeight_c, float kiloPrice, int kiloPrice_c,
                        float meterPrice, int meterPrice_c, float sheetPrice, int sheetPrice_c,
                        String supplier, String comments) {
        this.layers = layers;
        this.packageRolls = packageRolls;
        this.rollSheets = rollSheets;
        this.sheetWidth = sheetWidth;
        this.sheetLength = sheetLength;
        this.sheetLength_c = sheetLength_c;
        this.rollLength = rollLength;
        this.rollLength_c = rollLength_c;
        this.packagePrice = packagePrice;
        this.packagePrice_c = packagePrice_c;
        this.rollPrice = rollPrice;
        this.rollPrice_c = rollPrice_c;
        this.paperWeight = paperWeight;
        this.paperWeight_c = paperWeight_c;
        this.kiloPrice = kiloPrice;
        this.kiloPrice_c = kiloPrice_c;
        this.meterPrice = meterPrice;
        this.meterPrice_c = meterPrice_c;
        this.sheetPrice = sheetPrice;
        this.sheetPrice_c = sheetPrice_c;
        this.supplier = supplier;
        this.comments = comments;
        this.itemNo = itemNo;
        this.brand = brand;
        Long tsLong = System.currentTimeMillis();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        this.timestamp = s.format(new Date());
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public int getPackageRolls() {
        return packageRolls;
    }

    public void setPackageRolls(int packageRolls) {
        this.packageRolls = packageRolls;
    }

    public int getRollSheets() {
        return rollSheets;
    }

    public void setRollSheets(int rollSheets) {
        this.rollSheets = rollSheets;
    }

    public int getSheetWidth() {
        return sheetWidth;
    }

    public void setSheetWidth(int sheetWidth) {
        this.sheetWidth = sheetWidth;
    }

    public int getSheetLength() {
        return sheetLength;
    }

    public void setSheetLength(int sheetLength) {
        this.sheetLength = sheetLength;
    }

    public int getSheetLength_c() {
        return sheetLength_c;
    }

    public void setSheetLength_c(int sheetLength_c) {
        this.sheetLength_c = sheetLength_c;
    }

    public float getRollLength() {
        return rollLength;
    }

    public void setRollLength(float rollLength) {
        this.rollLength = rollLength;
    }

    public int getRollLength_c() {
        return rollLength_c;
    }

    public void setRollLength_c(int rollLength_c) {
        this.rollLength_c = rollLength_c;
    }

    public float getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(float packagePrice) {
        this.packagePrice = packagePrice;
    }

    public int getPackagePrice_c() {
        return packagePrice_c;
    }

    public void setPackagePrice_c(int packagePrice_c) {
        this.packagePrice_c = packagePrice_c;
    }

    public float getRollPrice() {
        return rollPrice;
    }

    public void setRollPrice(float rollPrice) {
        this.rollPrice = rollPrice;
    }

    public int getRollPrice_c() {
        return rollPrice_c;
    }

    public void setRollPrice_c(int rollPrice_c) {
        this.rollPrice_c = rollPrice_c;
    }

    public float getPaperWeight() {
        return paperWeight;
    }

    public void setPaperWeight(float paperWeight) {
        this.paperWeight = paperWeight;
    }

    public int getPaperWeight_c() {
        return paperWeight_c;
    }

    public void setPaperWeight_c(int paperWeight_c) {
        this.paperWeight_c = paperWeight_c;
    }

    public float getKiloPrice() {
        return kiloPrice;
    }

    public void setKiloPrice(float kiloPrice) {
        this.kiloPrice = kiloPrice;
    }

    public int getKiloPrice_c() {
        return kiloPrice_c;
    }

    public void setKiloPrice_c(int kiloPrice_c) {
        this.kiloPrice_c = kiloPrice_c;
    }

    public float getMeterPrice() {
        return meterPrice;
    }

    public void setMeterPrice(float meterPrice) {
        this.meterPrice = meterPrice;
    }

    public int getMeterPrice_c() {
        return meterPrice_c;
    }

    public void setMeterPrice_c(int meterPrice_c) {
        this.meterPrice_c = meterPrice_c;
    }

    public float getSheetPrice() {
        return sheetPrice;
    }

    public void setSheetPrice(float sheetPrice) {
        this.sheetPrice = sheetPrice;
    }

    public int getSheetPrice_c() {
        return sheetPrice_c;
    }

    public void setSheetPrice_c(int sheetPrice_c) {
        this.sheetPrice_c = sheetPrice_c;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
