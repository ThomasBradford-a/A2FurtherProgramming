module A2 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires org.xerial.sqlitejdbc;
	
	opens application to javafx.graphics, javafx.fxml;
}
