package net.myerichsen.toiletpaper.ui.suppliers;

/**
 * Class to encapsulate all toilet paper supplier data.
 */
public class SupplierData {
    private static int uid = 0;
    private static String supplier = "";
    private static String uri = "";
    private static String timestamp;

    public static int getUid() {
        return uid;
    }

    public static void setUid(int uid) {
        SupplierData.uid = uid;
    }

    public static String getSupplier() {
        return supplier;
    }

    public static void setSupplier(String supplier) {
        SupplierData.supplier = supplier;
    }

    public static String getUri() {
        return uri;
    }

    public static void setUri(String uri) {
        SupplierData.uri = uri;
    }

    public static String getTimestamp() {
        return timestamp;
    }

    public static void setTimestamp(String timestamp) {
        SupplierData.timestamp = timestamp;
    }
}
