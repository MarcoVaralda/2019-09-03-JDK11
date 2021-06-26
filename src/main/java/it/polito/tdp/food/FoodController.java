/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	String lunghezzaCammino = this.txtPassi.getText();
    	int N = 0;
    	try {
    		N = Integer.parseInt(lunghezzaCammino);
    	}
    	catch(NumberFormatException nbe) {
    		this.txtResult.setText("Devi inserire la lunghezza del cammino da calcolare e deve essere un numero intero!");
    		return;
    	}
    	
    	String scelto = this.boxPorzioni.getValue();
    	if(scelto == null) {
    		this.txtResult.setText("Non hai inserito nessun tipo di porzione!");
    		return;
    	}
    	
    	this.txtResult.appendText(this.model.cercaCammino(N, scelto));
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	String scelto = this.boxPorzioni.getValue();
    	if(scelto == null) {
    		this.txtResult.setText("Non hai inserito nessun tipo di porzione!");
    		return;
    	}
    	
    	String result = "\n\nTipi di porzione correlati a "+scelto+":\n";
    	
    	for(Adiacenza a: this.model.getCorrelati(scelto)) {
    		result += a.getP1()+" - "+a.getPeso()+"\n";
    	}
    	
    	this.txtResult.appendText(result);
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String stringaCalorie = this.txtCalorie.getText();
    	int calorie;
    	try {
    		calorie = Integer.parseInt(stringaCalorie);
    	}
    	catch(NumberFormatException nbe) {
    		this.txtResult.setText("Devi inserire un valore di tipo double nella casella delle calorie!");
    		return;
    	}
    	
    	this.txtResult.setText(this.model.creaGrafo(calorie));
    	
    	this.boxPorzioni.getItems().addAll(this.model.getVertici());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
