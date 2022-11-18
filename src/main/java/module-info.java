module com.monkeygang.MyTunes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.monkeygang.MyTunes to javafx.fxml;
    exports com.monkeygang.MyTunes.Controller;
    opens com.monkeygang.MyTunes.Controller to javafx.fxml;
    exports com.monkeygang.MyTunes.Application;
    opens com.monkeygang.MyTunes.Application to javafx.fxml;
}