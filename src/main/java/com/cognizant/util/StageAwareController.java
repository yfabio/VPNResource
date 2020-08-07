package com.cognizant.util;

import com.cognizant.model.Data;
import com.cognizant.model.DataCallBack;

import javafx.stage.Stage;

public interface StageAwareController {
	public void setStage(Stage stage);
	public void setDataCallBack(DataCallBack callBack);
	public void setData(Data data);
}
