package com.cognizant.dialog;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

public class Dialogs {

	/**
	 * 
	 * The overloaded dialogInfo shows only title and the content.
	 * 
	 * @param title
	 * @param header
	 * @param content
	 * 
	 */
	public static void dialogInfo(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * 
	 * The dialogWarning shows a warning dialog whose parameters are title, header
	 * and content.
	 * 
	 * @param title
	 * @param header
	 * @param content
	 * 
	 */

	public static void dialogWarning(String title, String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * 
	 * The dialogInfo shows a error dialog whose parameters are title, header and
	 * content.
	 * 
	 * @param title
	 * @param header
	 * @param content
	 * 
	 */
	public static void dialogError(String title, String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * 
	 * The dialogConfirm shows a confirmation dialog whose parameters are title,
	 * header, content and a DialogConfirmationListener which is the instance that
	 * needs to be implemented in order to be called when the dialog's ok button is
	 * pressed.
	 * 
	 * @param title
	 * @param header
	 * @param content
	 * @param listener
	 */
	public static void dialogConfirm(String title, String header, String content, DialogConfirmationListener listener) {
		Alert alert = new Alert(AlertType.CONFIRMATION,"",ButtonType.NO,ButtonType.YES);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES && listener != null) {
			listener.onConfirm();
		}
	}

	
	public static void dialogAbout(String title,String header, String content,Pane root) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setExpandableContent(root);			
		alert.show();
	}
	
}
