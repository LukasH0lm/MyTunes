module com.monkeygang.demo {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.monkeygang.demo to javafx.fxml;
    exports com.monkeygang.demo;
}