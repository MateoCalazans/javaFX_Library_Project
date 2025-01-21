module br.com.tads.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens br.com.tads.library to javafx.fxml;
    exports br.com.tads.library;
    exports br.com.tads.library.model;
    opens br.com.tads.library.model to javafx.fxml;
    exports br.com.tads.library.controller;
    opens br.com.tads.library.controller to javafx.fxml;
    exports br.com.tads.library.repository;
    opens br.com.tads.library.repository to javafx.fxml;
}