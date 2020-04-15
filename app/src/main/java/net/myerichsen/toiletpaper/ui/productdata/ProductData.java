package net.myerichsen.toiletpaper.ui.productdata;

/**
 * Class to encapsulate all toilet paper product data.
 * Boolean values are stored as integers 0 (false) and 1 (true).
 */
public class ProductData {
    private static int uid = 0;
    private static int layers = 0;
    private static int packageRolls = 0;
    private static int rollSheets = 0;
    private static int sheetWidth = 0;
    private static int sheetLength = 0;
    private static int sheetLength_c = 0;
    private static float rollLength = 0;
    private static int rollLength_c = 0;
    private static float packagePrice = 0;
    private static int packagePrice_c = 0;
    private static float rollPrice = 0;
    private static int rollPrice_c = 0;
    private static float paperWeight = 0;
    private static int paperWeight_c = 0;
    private static float kiloPrice = 0;

    public static float getRollLength() {
        return rollLength;
    }

    public static void setRollLength(float rollLength) {
        ProductData.rollLength = rollLength;
    }

    public static int getRollLength_c() {
        return rollLength_c;
    }

    public static void setRollLength_c(int rollLength_c) {
        ProductData.rollLength_c = rollLength_c;
    }

    private static int kiloPrice_c = 0;
    private static float meterPrice = 0;
    private static int meterPrice_c = 0;
    private static float sheetPrice = 0;
    private static int sheetPrice_c = 0;
    private static String supplier = "";
    private static String comments = "";
    private static String itemNo = "";
    private static String brand = "";
    private static String timestamp;

    public static int getLayers() {
        return layers;
    }

    public static void setLayers(int layers) {
        ProductData.layers = layers;
    }

    public static int getPackageRolls() {
        return packageRolls;
    }

    public static void setPackageRolls(int packageRolls) {
        ProductData.packageRolls = packageRolls;
    }

    public static int getRollSheets() {
        return rollSheets;
    }

    public static void setRollSheets(int rollSheets) {
        ProductData.rollSheets = rollSheets;
    }

    public static int getSheetWidth() {
        return sheetWidth;
    }

    public static void setSheetWidth(int sheetWidth) {
        ProductData.sheetWidth = sheetWidth;
    }

    public static int getSheetLength() {
        return sheetLength;
    }

    public static void setSheetLength(int sheetLength) {
        ProductData.sheetLength = sheetLength;
    }

    public static int getSheetLength_c() {
        return sheetLength_c;
    }

    public static void setSheetLength_c(int sheetLength_c) {
        ProductData.sheetLength_c = sheetLength_c;
    }

    public static float getPackagePrice() {
        return packagePrice;
    }

    public static void setPackagePrice(float packagePrice) {
        ProductData.packagePrice = packagePrice;
    }

    public static int getPackagePrice_c() {
        return packagePrice_c;
    }

    public static void setPackagePrice_c(int packagePrice_c) {
        ProductData.packagePrice_c = packagePrice_c;
    }

    public static float getRollPrice() {
        return rollPrice;
    }

    public static void setRollPrice(float rollPrice) {
        ProductData.rollPrice = rollPrice;
    }

    public static int getRollPrice_c() {
        return rollPrice_c;
    }

    public static void setRollPrice_c(int rollPrice_c) {
        ProductData.rollPrice_c = rollPrice_c;
    }

    public static float getPaperWeight() {
        return paperWeight;
    }

    public static void setPaperWeight(float paperWeight) {
        ProductData.paperWeight = paperWeight;
    }

    public static int getPaperWeight_c() {
        return paperWeight_c;
    }

    public static void setPaperWeight_c(int paperWeight_c) {
        ProductData.paperWeight_c = paperWeight_c;
    }

    public static float getKiloPrice() {
        return kiloPrice;
    }

    public static void setKiloPrice(float kiloPrice) {
        ProductData.kiloPrice = kiloPrice;
    }

    public static int getKiloPrice_c() {
        return kiloPrice_c;
    }

    public static void setKiloPrice_c(int kiloPrice_c) {
        ProductData.kiloPrice_c = kiloPrice_c;
    }

    public static float getMeterPrice() {
        return meterPrice;
    }

    public static void setMeterPrice(float meterPrice) {
        ProductData.meterPrice = meterPrice;
    }

    public static int getMeterPrice_c() {
        return meterPrice_c;
    }

    public static void setMeterPrice_c(int meterPrice_c) {
        ProductData.meterPrice_c = meterPrice_c;
    }

    public static float getSheetPrice() {
        return sheetPrice;
    }

    public static void setSheetPrice(float sheetPrice) {
        ProductData.sheetPrice = sheetPrice;
    }

    public static int getSheetPrice_c() {
        return sheetPrice_c;
    }

    public static void setSheetPrice_c(int sheetPrice_c) {
        ProductData.sheetPrice_c = sheetPrice_c;
    }

    public static String getSupplier() {
        return supplier;
    }

    public static void setSupplier(String supplier) {
        ProductData.supplier = supplier;
    }

    public static String getComments() {
        return comments;
    }

    public static void setComments(String comments) {
        ProductData.comments = comments;
    }

    public static String getItemNo() {
        return itemNo;
    }

    public static void setItemNo(String itemNo) {
        ProductData.itemNo = itemNo;
    }

    public static String getBrand() {
        return brand;
    }

    public static void setBrand(String brand) {
        ProductData.brand = brand;
    }

    public static String getTimestamp() {
        return timestamp;
    }

    public static void setTimestamp(String timestamp) {
        ProductData.timestamp = timestamp;
    }

    public static int getUid() {
        return uid;
    }

    public static void setUid(int uid) {
        ProductData.uid = uid;
    }
}
