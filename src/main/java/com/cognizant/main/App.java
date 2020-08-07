package com.cognizant.main;

import java.io.IOException;
import java.nio.file.Files;

import com.cognizant.dialog.Dialogs;
import com.cognizant.util.FXMLUtil;
import com.cognizant.util.FileStorage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		Pane root = FXMLUtil.load(stage, null,null,"/fxml/Main_ui.fxml");
		
		Scene scene = new Scene(root);		
		Image icon = new Image(getClass().getResourceAsStream("/images/vpn.png"));		
		stage.getIcons().add(icon);
		stage.setTitle("VPNResource");
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();		
		
	}

	@Override
	public void init() throws Exception {		
		try {
			if(!Files.exists(FileStorage.FILE_NAME.getParent())) {
				Files.createDirectory(FileStorage.FILE_NAME.getParent());
			}		
			if(!Files.exists(FileStorage.FILE_NAME)) {
				Files.createFile(FileStorage.FILE_NAME);	
			}
		} catch (IOException e) {
			Dialogs.dialogError("","Error","Unable to create a file " + FileStorage.FILE_NAME);
		}
	}

	

}
