package application;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SampleController implements Initializable {
	@FXML
	private Button buttonmod;

	@FXML
	private Button buttoneff;

	@FXML
	private Button buttonajo;

	@FXML
	private TableColumn<Sample, Double> colcharge;

	@FXML
	private MenuBar menu;

	@FXML
	private Button buttonrec;

	@FXML
	private TableColumn<Sample, Double> colpro;

	@FXML
	private ComboBox<String> combodept;

	@FXML
	private TextField txtnum;

	@FXML
	private TableColumn<Sample, Double> coldepart;

	@FXML
	private TextField txtdate;

	@FXML
	private TableColumn<Sample, Double> colnum;

	@FXML
	private TableColumn<Sample, Double> coldate;

	@FXML
	private TextField txtcharge;

	@FXML
	private TableView<Sample> table;

	@FXML
	private TextField txtpro;
	
	@FXML
    private Label lbltotal;

    @FXML
    private TextField txtotal;

    @FXML
    private TextField txtusername;

	private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Service ambulatoire (OPD)", "Service d'hospitalisation (IP)", "Service médical", "Service d'urgence", "Service infirmier", "Service paramédical", "Service de médecine physique");

	public ObservableList<Sample> SampleData=FXCollections.observableArrayList();

	public ObservableList<Sample> getSampleData()
	{
		return SampleData;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		combodept.setItems(list);
		coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
		colpro.setCellValueFactory(new PropertyValueFactory<>("procedure"));
		colcharge.setCellValueFactory(new PropertyValueFactory<>("charges"));
		coldepart.setCellValueFactory(new PropertyValueFactory<>("department"));
		colnum.setCellValueFactory(new PropertyValueFactory<>("number"));
		table.setItems(SampleData);

		buttonmod.setDisable(true);
		buttoneff.setDisable(true);
		buttonrec.setDisable(true);

		showInfo(null);
		table.getSelectionModel().selectedItemProperty().addListener((
				observable, oldValue, newValue)-> showInfo(newValue));
	}

	@FXML
	void ajouter()
	{
		if(noEmptyInput())
		{
			Sample tmp=new Sample();

			tmp=new Sample();
			tmp.setDate(txtdate.getText());
			tmp.setProcedure(txtpro.getText());
			tmp.setNumber(Double.parseDouble(txtnum.getText()));
			tmp.setCharges(Double.parseDouble(txtcharge.getText()));
			tmp.setDepartment(combodept.getValue());
			SampleData.add(tmp);
			clearFields();
		}
	}
	
	@FXML
	void clearFields() 
	{
		combodept.setValue(null);
		txtdate.setText("");
		txtpro.setText("");
		txtcharge.setText("");
		txtnum.setText("");
	}

	@FXML
	public void showInfo(Sample sample)
	{
		if(sample!=null)
		{
			combodept.setValue(sample.getDepartment());
			txtdate.setText(sample.getDate());
			txtpro.setText(sample.getProcedure());
			txtcharge.setText(Double.toString(sample.getCharges()));
			txtnum.setText(Double.toString(sample.getNumber()));
			buttonmod.setDisable(false);
			buttoneff.setDisable(false);
			buttonrec.setDisable(false);

		}
		else
		{
			clearFields();
		}
	}
	
	@FXML
	public void updateEtudiant()
	{
		{
			if(noEmptyInput())
			{
				Sample sample=table.getSelectionModel().getSelectedItem();

				sample.setDate(txtdate.getText());
				sample.setProcedure(txtpro.getText());
				sample.setCharges(Double.parseDouble(txtcharge.getText()));
				sample.setNumber(Double.parseDouble(txtnum.getText()));
				sample.setDepartment(combodept.getValue());
				table.refresh();
			}
		}
		}

		@FXML
		public void deleteEtudiant()
		{
			int selectedIndex = table.getSelectionModel().getSelectedIndex();
			if (selectedIndex >=0)
			{
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Effacer");
				alert.setContentText("Confirm la surpression");
				Optional<ButtonType> result=alert.showAndWait();
				if(result.get()==ButtonType.OK)
					table.getItems().remove(selectedIndex);
			}
		}

		public File getEtudiantFilePath()
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			String filePath = prefs.get("filePath", null);

			if (filePath != null)
			{
				return new File(filePath);
			}
			else
			{
				return null;
			}
		}

		public void setEtudiantFilePath(File file)
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			if (file !=null)
			{
				prefs.put("filePath", file.getPath());
			}
			else
			{
				prefs.remove("filePath");
			}
		}

		public void loadEtudiantDataFromFile(File file)
		{
			try {
				JAXBContext context = JAXBContext.newInstance(SampleListWrapper.class);
				Unmarshaller um = context.createUnmarshaller();

				SampleListWrapper wrapper = (SampleListWrapper) um.unmarshal(file);
				SampleData.clear();
				SampleData.addAll(wrapper.getEtudiants());
				setEtudiantFilePath(file);
				Stage pStage=(Stage)table.getScene().getWindow();
				pStage.setTitle(file.getName());

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Les données n'ont pas éte trouvées");
				alert.setContentText("Les données ne pouvaient pas étre trouvées dans le fichier : \n" +file.getPath());
				alert.showAndWait();
			}
		}

		public void saveEtudiantDatatoFile(File file)
		{
			try {
				JAXBContext context = JAXBContext.newInstance(SampleListWrapper.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				SampleListWrapper wrapper = new SampleListWrapper();
				wrapper.setEtudiants(SampleData);

				m.marshal(wrapper, file);

				setEtudiantFilePath(file);

				Stage pStage=(Stage)table.getScene().getWindow();
				pStage.setTitle(file.getName());

			} catch (Exception e) {


				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Données non sauvegardées");
				alert.setContentText("Les données ne pouvaient pas étre sauvegardées dans le fichier:\n" + file.getPath());
				alert.showAndWait();
			}
		}

		@FXML
		private void handleNew()
		{
			getSampleData().clear();
			setEtudiantFilePath(null);
		}

		@FXML
		private void handleOpen() {

			FileChooser fileChooser = new FileChooser();

			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showOpenDialog(null);

			if (file !=null)
			{
				loadEtudiantDataFromFile(file);
			}
		}

		@FXML
		private void handleSave() {

			File etudiantFile = getEtudiantFilePath();
			if (etudiantFile != null){
				saveEtudiantDatatoFile(etudiantFile);
			} else {
				handleSaveAs();
			}
		}

		@FXML
		private void handleSaveAs() {
			FileChooser fileChooser = new FileChooser();

			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

			File file = fileChooser.showSaveDialog(null);

			if (file !=null) {

				if(!file.getPath().endsWith(".xml")) {
					file = new File(file.getPath() + ".xml");

				}
				saveEtudiantDatatoFile(file);
			}
		}

		@FXML
		public void verifNum()
		{
			txtcharge.textProperty().addListener((observable,oldValue,newValue)->
			{
				if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))
				{
					txtcharge.setText(newValue.replaceAll("[^\\d*\\.]", ""));
				}
			});

			txtnum.textProperty().addListener((observable,oldValue,newValue)->
			{
				if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))
				{
					txtnum.setText(newValue.replaceAll("[^\\d*\\.]", ""));
				}
			});	

		}

		@FXML
		private Boolean noEmptyInput()
		{
			String errorMessage="";
			if(txtdate.getText().trim().equals(""))
			{
				errorMessage+="Le champ nom ne doit pas etre vide! \n";
			}
			if(txtnum.getText().trim().equals(""))
			{
				errorMessage+="Le champ number ne doit pas etre vide! \n";
			}
			if(txtcharge.getText().trim().equals(""))
			{
				errorMessage+="Le champ charge ne doit pas etre vide! \n";
			}
			if(txtpro.getText().trim().equals(""))
			{
				errorMessage+="Le champ pro ne doit pas etre vide! \n";
			}
			if(combodept.getValue()==null)
			{
				errorMessage+="Le champ dept ne doit pas etre vide! \n";
			}
			if(errorMessage.length()==0)
			{
				return true;
			}
			else
			{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setTitle("Champs Manquants");
				alert.setHeaderText("Complete Champs Manquants");
				alert.setContentText(errorMessage);
				alert.showAndWait();
				return false;
			}
		}
	}	