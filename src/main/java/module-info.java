module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens gameoflife to javafx.fxml;
    exports gameoflife;
}