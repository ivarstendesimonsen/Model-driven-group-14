open module cardgame {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.eclipse.emf.common;
	requires org.eclipse.emf.ecore;
	requires org.eclipse.emf.ecore.xmi;

	exports cardgame.core;
	exports cardgame.rules;
	exports cardgame.ui;
}
