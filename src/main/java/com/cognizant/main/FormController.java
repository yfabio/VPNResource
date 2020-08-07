package com.cognizant.main;

import com.cognizant.dialog.Dialogs;
import com.cognizant.model.Data;
import com.cognizant.model.DataCallBack;
import com.cognizant.persistence.DAO;
import com.cognizant.persistence.DAOData;
import com.cognizant.persistence.DAOException;
import com.cognizant.util.StageAwareController;
import com.cognizant.util.StringUtil;
import com.cognizant.util.ValidationState;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class FormController implements StageAwareController, ValidationState {

	@FXML
	private Button mSave;

	@FXML
	private Button mCancel;

	@FXML
	private TextField mProject;

	@FXML
	private TextField mSource;

	@FXML
	private TextField mTarget;

	@FXML
	private TextField mPort;

	@FXML
	private TextField mService;

	@FXML
	private TextArea mInfo;

	private DataCallBack callBack;

	private Stage stage;

	private Data data;

	private DAO<Data> dataDao = new DAOData();

	@FXML
	public void initialize() {

		setInvalidationListener(mProject, mSource, mTarget, mPort, mService);
		Platform.runLater(() -> {
			mProject.requestFocus();
		});

	}

	@FXML
	public void onCancel() {
		stage.close();
	}

	@FXML
	public void onSave() {
		try {
			if (StringUtil.validate(data, this)) {
				if (data.getId() == null) {
					int id = dataDao.getMaxID();
					data.setId(id);
					dataDao.save(data);
					Dialogs.dialogInfo("Saving", "Data has been saved!");
					unbind(data);
					callBack.onCallBack(data, false);
					stage.close();
				} else {
					dataDao.update(data);
					Dialogs.dialogInfo("Saving", "Data has been update!");
					unbind(data);
					callBack.onCallBack(data, true);
					stage.close();
				}
			}
		} catch (DAOException e) {
			Dialogs.dialogError("Error", "Action Error", e.getMessage());
		}
	}

	public void bind(Data data) {
		mProject.textProperty().bindBidirectional(data.projectProperty());
		mSource.textProperty().bindBidirectional(data.IPSourceProperty());
		mTarget.textProperty().bindBidirectional(data.IPTargetProperty());
		mPort.textProperty().bindBidirectional(data.portProperty());
		mService.textProperty().bindBidirectional(data.serviceProperty());
		mInfo.textProperty().bindBidirectional(data.infoProperty());
	}

	public void unbind(Data data) {
		mProject.textProperty().unbindBidirectional(data.projectProperty());
		mSource.textProperty().unbindBidirectional(data.IPSourceProperty());
		mTarget.textProperty().unbindBidirectional(data.IPTargetProperty());
		mPort.textProperty().unbindBidirectional(data.portProperty());
		mService.textProperty().unbindBidirectional(data.serviceProperty());
		mInfo.textProperty().unbindBidirectional(data.infoProperty());
	}

	@Override
	public void setStage(Stage stage) {
		if (stage != null)
			this.stage = stage;
	}

	@Override
	public void setDataCallBack(DataCallBack callBack) {
		if (callBack != null)
			this.callBack = callBack;
	}

	@Override
	public void setData(Data data) {
		this.data = data;
		bind(this.data);
	}

	@Override
	public void change(Field field) {
		if (Field.PROJECT == field) {
			mProject.getStyleClass().add("border-error");
			showToolTip(stage, mProject, field.getMessages());
		} 
		
		if (Field.IPSOURCE == field) {
			mSource.getStyleClass().add("border-error");
			showToolTip(stage, mSource, field.getMessages());
		} 
		
		if (Field.IPTARGET == field) {
			mTarget.getStyleClass().add("border-error");
			showToolTip(stage, mTarget, field.getMessages());
		}
		
		if (Field.PORT == field) {
			mPort.getStyleClass().add("border-error");
			showToolTip(stage, mPort, field.getMessages());
		}
		
		if (Field.SERVICE == field) {
			mService.getStyleClass().add("border-error");
			showToolTip(stage, mService, field.getMessages());
		}
	}
	

	private void showToolTip(Stage stage, Control control,String message) {
		
		Point2D p = control.localToScene(0.0, 0.0);
		
		final Tooltip toolTip = new Tooltip(message);
		toolTip.setAutoHide(true);
				
		toolTip.show(stage,p.getX() + 
				control.getScene().getX() + control.getScene().getWindow().getX()  + 200,
				p.getY() + control.getScene().getY() + control.getScene().getWindow().getY());
				
	}

	private void setInvalidationListener(TextInputControl... fields) {
		for (TextInputControl field : fields) {
			field.textProperty().addListener(e -> {
				if (field.getStyleClass().contains("border-error")) {
					field.getStyleClass().remove("border-error");
				}
			});
		}
	}

}
