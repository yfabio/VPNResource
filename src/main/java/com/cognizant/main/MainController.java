package com.cognizant.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.cognizant.dialog.DialogConfirmationListener;
import com.cognizant.dialog.Dialogs;
import com.cognizant.file.FileExport;
import com.cognizant.file.FileImport;
import com.cognizant.file.FileStreamCallBack;
import com.cognizant.file.FileTaskImport;
import com.cognizant.model.Data;
import com.cognizant.model.DataCallBack;
import com.cognizant.persistence.DAO;
import com.cognizant.persistence.DAOData;
import com.cognizant.persistence.DAOException;
import com.cognizant.util.FXMLUtil;
import com.cognizant.util.StageAwareController;
import com.cognizant.util.StringUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements StageAwareController, DataCallBack, DialogConfirmationListener {

	@FXML
	private Button mPlus;

	@FXML
	private Button mMinus;

	@FXML
	private Button mSearch;

	@FXML
	private TextArea mInfo;

	@FXML
	private TextField mSeachText;

	@FXML
	private TableView<Data> mTable;

	@FXML
	private ProgressIndicator mProgress;

	@FXML
	private Button mUpdate;
	
	@FXML 
	private BorderPane mBorderPane;

	private DAO<Data> dataDao = new DAOData();

	private ObservableList<Data> list = FXCollections.observableArrayList();

	private Data data;

	private Stage stage;

	private FileChooser fileChooser = new FileChooser();


	@FXML
	public void initialize() {

		fileChooser.setInitialFileName("vpnresource.txt");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));

		mMinus.disableProperty().bind(mTable.getSelectionModel().selectedItemProperty().isNull());
		mUpdate.disableProperty().bind(mTable.getSelectionModel().selectedItemProperty().isNull());
		mSearch.disableProperty().bind(mSeachText.textProperty().isEmpty());

		mTable.getSelectionModel().selectedItemProperty().addListener((obs, older, newer) -> {
			if (newer != null) {
				this.data = newer;				
				mInfo.textProperty().bind(newer.infoProperty());				
			}
		});

		try {
			list.addAll(dataDao.get());
			mTable.setItems(list);

			mSeachText.textProperty().addListener((obs, older, newer) -> {
				if (StringUtil.isEmpty(newer)) {
					try {
						list.clear();
						list.addAll(dataDao.get());
					} catch (DAOException e) {
						Dialogs.dialogError("", "Error", e.getMessage());
					}
				}
			});

		} catch (DAOException e) {
			Dialogs.dialogError("Loading", "Error", e.getMessage());
		}
		
		
		
		
		
	}

	@FXML
	void onAbout(ActionEvent event) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/About_ui.fxml"));
			Dialogs.dialogAbout("About","Application Description","Power by Cognizant", root);
		} catch (IOException e) {
			Dialogs.dialogError("", "Error","Unable to load layout file!");
		}
	}

	@FXML
	void onClose(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void onMinus() {
		Dialogs.dialogConfirm("Deleting", "Delete Item", "Are you sure(Y/N)", this);
	}

	@Override
	public void onConfirm() {			
		try {
			if (data != null) {				
				dataDao.delete(data);
				Dialogs.dialogInfo("", "Data has been deleted successfully!");
				loadTableFromDataBase();
			}
		} catch (DAOException e) {
			Dialogs.dialogError("", "Error", e.getMessage());
		}
	}

	@FXML
	void onPlus() {
		loadForm(new Data());
	}

	@FXML
	void onExport() {

		FileStreamCallBack fileStreamCaBack = new FileStreamCallBack() {

			@Override
			public void succeeded() {
				mProgress.setVisible(false);
				mBorderPane.setDisable(false);
				Dialogs.dialogInfo("Complete", "Export has been completed!");
			}

			@Override
			public void scheduled() {
				mProgress.setVisible(true);
				mProgress.setProgress(-1);
				mBorderPane.setDisable(true);
			}

			@Override
			public void failed() {
				Dialogs.dialogError("", "Error", "Unable to export!");
				mProgress.setVisible(false);
				mBorderPane.setDisable(false);
			}
			
		};

		try {

			File file = fileChooser.showSaveDialog(stage);

			if (file != null) {
				FileExport fileExport = new FileExport(fileStreamCaBack, dataDao.get(), file);
				fileExport.start();

			}
		} catch (Exception e) {
			Dialogs.dialogError("", "Error", "Error It was tried to export " + e.getMessage());
		}

	}

	@FXML
	void onImport() {
		
		
		FileStreamCallBack fileStreamCaBack = new FileStreamCallBack() {

			@Override
			public void succeeded() {
				mProgress.setVisible(false);
				mBorderPane.setDisable(false);
				Dialogs.dialogInfo("Complete", "Import has been completed!");
			}

			@Override
			public void scheduled() {
				mProgress.setVisible(true);
				mProgress.setProgress(-1);
				mBorderPane.setDisable(true);
			}

			@Override
			public void failed() {
				Dialogs.dialogError("", "Error", "Unable to Import!");
				mProgress.setVisible(false);
				mBorderPane.setDisable(false);
			}
			
		};

		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {			
			FileTaskImport<List<Data>> fileImport = new FileImport(fileStreamCaBack, file);
			fileImport.restart();
			
			fileImport.valueProperty().addListener((obs,older,newer) -> {
				if(newer!=null) {
					for (Data data : newer) {						
						try {							
							int id = dataDao.getMaxID();							
							data.setId(id);							
							dataDao.save(data);							
						} catch (DAOException e) {
							Dialogs.dialogError("", "Error",e.getMessage());
						}						
					}					
				}
				loadTableFromDataBase();
			});
		}
	}

	@FXML
	public void onSearch() {
		try {
			List<Data> datas = dataDao.get(null, mSeachText.getText());
			list.clear();
			this.list.addAll(datas);
			loadTable();
		} catch (DAOException e) {
			Dialogs.dialogError("", "Error", e.getMessage());
		}
	}

	@Override
	public void onCallBack(Data data, boolean isUpdate) {
		if (isUpdate) {
			int index = list.indexOf(data);
			list.set(index, data);
		} else {
			list.add(data);
		}
		loadTable();
	}

	@FXML
	public void onUpdate() {
		loadForm(data);
	}

	private void loadTable() {
		mTable.setItems(list);
	}
	
	private void loadTableFromDataBase() {
		try {
			list.clear();
			list.addAll(dataDao.get());
			loadTable();
		} catch (DAOException e) {
			Dialogs.dialogError("","Error",e.getMessage());
		}
	}

	private void loadForm(Data data) {
		try {

			Stage stage = new Stage();

			Pane root = FXMLUtil.load(stage, this, data, "/fxml/Form_ui.fxml");

			Scene scene = new Scene(root);

			stage.setTitle("New Item");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();

		} catch (IOException e) {
			Dialogs.dialogError("", "Error","Unable to load layout file!");
		}
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void setDataCallBack(DataCallBack callBack) {
	}

	@Override
	public void setData(Data data) {
	}

}
