module PunchProTimeClockGui {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    
    // Open 'application' package to both javafx.fxml and javafx.base for reflection access
    opens application to javafx.graphics, javafx.fxml, javafx.base;
}
