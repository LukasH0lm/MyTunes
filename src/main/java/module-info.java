module com.monkeygang.MyTunes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.xml;
    requires mp3agic;


    exports com.monkeygang.MyTunes.Controller.Control;
    opens com.monkeygang.MyTunes.Controller.Control to javafx.fxml;
    exports com.monkeygang.MyTunes.Application.BuisnessLogic;
    opens com.monkeygang.MyTunes.Application.BuisnessLogic to javafx.fxml;
    exports com.monkeygang.MyTunes.Application.ControlObjects;
}