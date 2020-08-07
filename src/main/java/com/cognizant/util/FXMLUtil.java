package com.cognizant.util;

import java.io.IOException;

import com.cognizant.model.Data;
import com.cognizant.model.DataCallBack;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class FXMLUtil {

	public static <T> T load(Stage stage,DataCallBack callback,Data data, String layout) throws IOException {
		FXMLLoader loader = new FXMLLoader(FXMLUtil.class.getResource(layout));
		T root = loader.load();		
		Object controller = loader.getController();		
		if(controller != null && controller instanceof StageAwareController) {
			StageAwareController stageAware = (StageAwareController) controller;
			stageAware.setStage(stage);
			stageAware.setDataCallBack(callback);
			stageAware.setData(data);
		}		
		return root;
	}
}
