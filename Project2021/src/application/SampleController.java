/*
* Author: cy.andersen
* Date: May 7, 2021
* Description:
* Contrôleur pour programme de facturation
*/
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

	//variable pour le total
	private Double total1=0.0;

	// liste pour les departements du l'hopital qui permettra de donner les valeurs au combobox
	private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Service ambulatoire (OPD)", "Service d'hospitalisation (IP)", "Service médical", "Service d'urgence", "Service infirmier", "Service paramédical", "Service de médecine physique");

	// Placer les donnes dans une observable list
	public ObservableList<Sample> SampleData=FXCollections.observableArrayList();

	// Creer une methode pour acceder a la liste des donnes
	public ObservableList<Sample> getSampleData()
	{
		return SampleData;
	}

	//attribuer les valeurs
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		combodept.setItems(list);
		//attribuer les valeurs aux colonnes du tableView
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
		//Mettre a jour l'affichage d'un valeur selectionné
		table.getSelectionModel().selectedItemProperty().addListener((
				observable, oldValue, newValue)-> showInfo(newValue));
	}

	//ajouter les valeurs au tableau 
	@FXML
	void ajouter()
	{
		//verifier si un champ n'est pas vide
		if(noEmptyInput())
		{
			Sample tmp=new Sample();

			tmp=new Sample();
			tmp.setDate(txtdate.getText());
			tmp.setProcedure(txtpro.getText());
			tmp.setNumber(Double.parseDouble(txtnum.getText()));
			tmp.setCharges(Double.parseDouble(txtcharge.getText()));
			tmp.setDepartment(combodept.getValue());
			Double Charges=(Double.parseDouble(txtcharge.getText()));
			total1+= Charges;
			txtotal.setText(Double.toString(total1));
			SampleData.add(tmp);
			clearFields();
		}
	}

	//Effacer le contenu des champs
	@FXML
	void clearFields() 
	{
		combodept.setValue(null);
		txtdate.setText("");
		txtpro.setText("");
		txtcharge.setText("");
		txtnum.setText("");	
	}

	// Afficher les valeurs au tableau
	public void showInfo(Sample sample)
	{
		if(sample !=null)
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

	//Modifier un valeur deja dans le tableau
	@FXML
	public void updateInfo()
	{
		{
			//verifier si un champ n'est pas vide
			if(noEmptyInput())
			{
				Sample sample=table.getSelectionModel().getSelectedItem();
				sample.setDate(txtdate.getText());
				sample.setProcedure(txtpro.getText());
				sample.setCharges(Double.parseDouble(txtcharge.getText()));
				sample.setNumber(Double.parseDouble(txtnum.getText()));
				sample.setDepartment(combodept.getValue());
				Double montant1 = sample.getCharges();
				total1= total1 +- montant1;
				txtotal.setText(Double.toString(montant1));
				table.refresh();
				refreshTotal();
			}
		}
	}

	//Effacer un valeur du tableau
	@FXML
	public void deleteInfo()
	{
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		if (selectedIndex >=0)
		{
			Sample sample=table.getSelectionModel().getSelectedItem();
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Effacer");
			alert.setContentText("Confirm la surpression");
			Optional<ButtonType> result=alert.showAndWait();
			if(result.get()==ButtonType.OK)
				{ table.getItems().remove(selectedIndex);
			Double montant = sample.getCharges();
			total1= total1 - montant;
			txtotal.setText(Double.toString(total1));}
		}
	}

	//mettre à jour le total avec la valeur modifiée
	void refreshTotal()
	{
		Double tot=0.0;
		for(int i=0;i<table.getItems().size();i++)
		{
			tot = tot + Double.valueOf(String.valueOf(table.getColumns().get(4).getCellObservableValue(i).getValue()));
		}
		txtotal.setText(Double.toString(tot));

	}
	
	//Sauvegarde de données
	
	//Recuperer le chemin path des fichiers si ca existe
	public File getInfoFilePath()
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

	//Attribuer un chemin (path) de fichiers
	public void setInfoFilePath(File file)
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

	//Prendre les donnees de type XML et les convertit en donnees de type javaFX
	public void loadInfoDataFromFile(File file)
	{
		try {
			JAXBContext context = JAXBContext.newInstance(SampleListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			SampleListWrapper wrapper = (SampleListWrapper) um.unmarshal(file);
			SampleData.clear();
			SampleData.addAll(wrapper.getEtudiants());
			setInfoFilePath(file);
			//Donner le titre du ficher chargé
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
	
	//Prendre les donnees de type JavaFx et les convertit en type XML
	public void saveInfoDatatoFile(File file)
	{
		try {
			JAXBContext context = JAXBContext.newInstance(SampleListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			SampleListWrapper wrapper = new SampleListWrapper();
			wrapper.setEtudiants(SampleData);

			m.marshal(wrapper, file);

			//Sauvegarde dans le registre
			setInfoFilePath(file);
			//Donner le titre du ficher sauvegardé
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

	//Commencer un nouveau ficher
	@FXML
	private void handleNew()
	{
		getSampleData().clear();
		total1=0.0;
		txtotal.setText(Double.toString(total1));		
		setInfoFilePath(null);
	}

	//ouvrir un nouveau ficher
	@FXML
	private void handleOpen() {

		FileChooser fileChooser = new FileChooser();
		//Permettre un filtre sur l'extension du fichier a chercher
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		// Montrer le dialogue
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showOpenDialog(null);

		if (file !=null)
		{
			loadInfoDataFromFile(file);
		}
	}

	@FXML
	private void handleSave() {

		File etudiantFile = getInfoFilePath();
		if (etudiantFile != null){
			saveInfoDatatoFile(etudiantFile);} 
		else {
			handleSaveAs();
		}
	}

	//Ouvrir le FileChooser pour trouver le chemin
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

		//Sauvegarde
		File file = fileChooser.showSaveDialog(null);

		if (file !=null) {
			//Verification de l'extension
			if(!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");

			}
			saveInfoDatatoFile(file);
		}
	}

	//mehode pour trouvr des input non mumeriques
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
	
	//Verfifier les champs vides
	@FXML
	private Boolean noEmptyInput()
	{
		String errorMessage="";
		if(txtdate.getText().trim().equals(""))
		{
			errorMessage+="Le champ date de la procédure  ne doit pas etre vide! \n";
		}
		if(txtnum.getText().trim().equals(""))
		{
			errorMessage+="Le champ numéro du patient ne doit pas etre vide! \n";
		}
		if(txtcharge.getText().trim().equals(""))
		{
			errorMessage+="Le champ frais ne doit pas etre vide! \n";
		}
		if(txtpro.getText().trim().equals(""))
		{
			errorMessage+="Le champ procédure ne doit pas etre vide! \n";
		}
		if(combodept.getValue()==null)
		{
			errorMessage+="Le champ département de supervision ne doit pas etre vide! \n";
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