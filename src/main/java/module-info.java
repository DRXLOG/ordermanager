module com.drxlog.java.ordermanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.drxlog.java.ordermanager to javafx.fxml;
    exports com.drxlog.java.ordermanager;
}
