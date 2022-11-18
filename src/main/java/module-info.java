module com.monkeygang.MyTunes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.monkeygang.MyTunes to javafx.fxml;
    exports com.monkeygang.MyTunes;
}