module com.codex.projeFX {	
	requires transitive java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.web;
    requires transitive javafx.graphics;
	requires transitive org.hibernate.orm.core;
	requires spring.context;
	requires spring.beans;
	requires jakarta.persistence;
	requires spring.tx;
	requires transitive java.desktop;
	requires javafx.swing;
	requires transitive org.apache.pdfbox;
	requires jdk.jsobject;
	requires transitive icepdf.viewer;
	requires transitive icepdf.core;
	requires aspose.pdf;

    opens com.codex.projeFX to javafx.fxml;
    opens com.codex.projeFX.entity to org.hibernate.orm.core;    

    exports com.codex.projeFX.entity;
    exports com.codex.projeFX.dao;
    exports com.codex.projeFX.repository;
    exports com.codex.projeFX;
}
