module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires io;
    requires kernel;
    requires layout;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires static lombok;
    requires slf4j.api;

    opens org.example to javafx.fxml;
    opens org.example.controllers to javafx.fxml;
    opens org.example.pdf_creator.content.abstractsclasses to com.fasterxml.jackson.databind;

    exports org.example;
    exports org.example.controllers to javafx.fxml;
    exports org.example.pdf_creator.content to com.fasterxml.jackson.databind;
    exports org.example.pdf_creator.content.abstractsclasses to com.fasterxml.jackson.databind;
    exports org.example.pdf_creator.content.sectionorganizers to com.fasterxml.jackson.databind;
    exports org.example.pdf_creator.content.enums to com.fasterxml.jackson.databind;
    exports org.example.pdf_creator.content.titlehandlers to com.fasterxml.jackson.databind;
    exports org.example.pdf_creator.factories;
}