package eblcg;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application{
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Eclipsing Binaries Light Curve Generator");
		//GUI Declarations
		BorderPane rootMain = new BorderPane();
		VBox controlPaneLeft = new VBox();
		Text inputTitle = new Text("Input");
		Label presetsLabel = new Label("Presets: ");
		ComboBox<String> presetsChoiceBox = new ComboBox<String>();
		Text star1Title = new Text("Star 1: ");
		Label star1MassLabel = new Label("Star 1 Mass (Solar Mass): ");
		TextField star1MassText = new TextField();
		Label star1RadiusLabel = new Label("Star 1 Radius (Solar Radius): ");
		TextField star1RadiusText = new TextField();
		Label star1TempLabel = new Label("Star 1 Temperature (K): ");
		TextField star1TempText = new TextField();
		
		Text star2Title = new Text("Star 2: ");
		Label star2MassLabel = new Label("Star 2 Mass (Solar Mass): ");
		TextField star2MassText = new TextField();
		Label star2RadiusLabel = new Label("Star 2 Radius (Solar Radius): ");
		TextField star2RadiusText = new TextField();
		Label star2TempLabel = new Label("Star 2 Temperature (K): ");
		TextField star2TempText = new TextField();
		
		Label orbitLabel = new Label("Orbit: ");
		Label eccentricityLabel = new Label("Eccentricity: ");
		TextField eccentricityText = new TextField();
		Label maxSeparationLabel = new Label("Max Separation Distance (AU): ");
		TextField maxSeparationText = new TextField();
		
		Button advancedBtn = new Button("Advanced Settings...");
		Text advancedTitle = new Text("Advanced Settings");
		
		VBox star1Settings = new VBox();
		Label star1RingsLabel = new Label("Star 1 Rings: ");
		TextField star1RingsText = new TextField();
		Label star1SectorsLabel = new Label("Star 1 Sectors: ");
		TextField star1SectorsText = new TextField();
		VBox star2Settings = new VBox();
		Label star2RingsLabel = new Label("Star 2 Rings: ");
		TextField star2RingsText = new TextField();
		Label star2SectorsLabel = new Label("Star 2 Sectors: ");
		TextField star2SectorsText = new TextField();
		Label imgWidthLabel = new Label("Image Width: ");
		TextField imgWidthText = new TextField();
		Label imgHeightLabel = new Label("Image Height: ");
		TextField imgHeightText = new TextField();
		Button advancedConfirmBtn = new Button("Confirm");
		Button advancedCancelBtn = new Button("Cancel");
		
		TabPane graphPane = new TabPane();
		
		VBox controlPaneRight = new VBox();
		Text controlsTitle = new Text("Controls");
		Label imageDirLabel = new Label("Images Directory");
		TextArea dirTextArea = new TextArea();
		Button browseBtn = new Button("Browse...");
		Button generateGraphBtn = new Button("Generate Graph");
		Label consoleLabel = new Label("Console: ");
		TextArea consoleTextArea = new TextArea();
		
		GridPane advancedRoot = new GridPane();
		
		FileChooser dirChooser = new FileChooser();
		
		
		Stage advancedStage = new Stage();
		//Initialization & Setup
		
		controlPaneLeft.setAlignment(Pos.CENTER_LEFT);
		controlPaneLeft.setPadding(new Insets(10,25,10,25));
		
		inputTitle.setFont(Font.font(30));
		presetsChoiceBox.getItems().addAll(
				"Custom",
				"Algol AB"
				);
		presetsChoiceBox.setValue("Custom");
		presetsChoiceBox.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if(presetsChoiceBox.getValue() == "Algol AB"){
					star1MassText.setText("3.59");
					star1RadiusText.setText("4.13");
					star1TempText.setText("9200");
					star2MassText.setText("0.79");
					star2RadiusText.setText("3.0");
					star2TempText.setText("4500");
					eccentricityText.setText("0.0");
					maxSeparationText.setText("0.062");
				}
			}
		});
		advancedTitle.setFont(Font.font(30));
		
		advancedBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
		        advancedStage.show();
		        
		        advancedCancelBtn.setOnAction(new EventHandler<ActionEvent>(){
		        	@Override
		        	public void handle(ActionEvent event){
		        		advancedStage.hide();
		        	}
		        });
		        advancedConfirmBtn.setOnAction(new EventHandler<ActionEvent>(){
		        	@Override
		        	public void handle(ActionEvent event){
		        		try{
		        			LightCurveGenerator.star1Rings = Integer.parseInt(star1RingsText.getText());
		        			LightCurveGenerator.star1Sectors = Integer.parseInt(star1SectorsText.getText());
		        			LightCurveGenerator.star2Rings = Integer.parseInt(star2RingsText.getText());
		        			LightCurveGenerator.star2Sectors = Integer.parseInt(star2SectorsText.getText());
		        			LightCurveGenerator.imgWidth = Integer.parseInt(imgWidthText.getText());
		        			LightCurveGenerator.imgHeight = Integer.parseInt(imgHeightText.getText());
		        			advancedStage.hide();
		        		}catch(Exception e){
		        			Alert advancedAlert = new Alert(AlertType.ERROR);
		        			advancedAlert.setContentText("Please input valid integers.");
		        			advancedAlert.showAndWait();
		        		}	
		        	}
		        });
		    }
		});
		
		controlsTitle.setFont(Font.font(30));
		
		controlPaneRight.setAlignment(Pos.CENTER_RIGHT);
		controlPaneRight.setPadding(new Insets(10,25,10,25));
		dirTextArea.setEditable(false);
		dirTextArea.setPrefSize(200, 100);
		consoleTextArea.setEditable(false);
		consoleTextArea.setPrefSize(200, 100);
		
		advancedRoot.setHgap(10);
        advancedRoot.setVgap(10);
        advancedRoot.setPadding(new Insets(10,10,10,10));
        advancedRoot.add(advancedTitle,0, 0,2,1);
        advancedRoot.add(star1Settings,0,1,1,4);
        star1Settings.getChildren().add(star1RingsLabel);
        star1Settings.getChildren().add(star1RingsText);
        star1Settings.getChildren().add(star1SectorsLabel);
        star1Settings.getChildren().add(star1SectorsText);
        star1Settings.getChildren().add(imgWidthLabel);
        star1Settings.getChildren().add(imgWidthText);
        imgWidthText.setText("1280");
        advancedRoot.add(star2Settings,1,1,1,4);
        star2Settings.getChildren().add(star2RingsLabel);
        star2Settings.getChildren().add(star2RingsText);
        star2Settings.getChildren().add(star2SectorsLabel);
        star2Settings.getChildren().add(star2SectorsText);
        star2Settings.getChildren().add(imgHeightLabel);
        star2Settings.getChildren().add(imgHeightText);
        imgHeightText.setText("720");
        advancedRoot.add(advancedConfirmBtn,0,5);
        advancedRoot.add(advancedCancelBtn,1,5);
        advancedStage.setTitle("Advanced Settings");
        advancedStage.setScene(new Scene(advancedRoot,400,250));
        
        star1RingsText.setText("1000");
        star1SectorsText.setText("1000");
        star2RingsText.setText("1000");
        star2SectorsText.setText("1000");

		rootMain.setLeft(controlPaneLeft);
		controlPaneLeft.getChildren().add(inputTitle);
		controlPaneLeft.getChildren().add(presetsLabel);
		controlPaneLeft.getChildren().add(presetsChoiceBox);
		controlPaneLeft.getChildren().add(star1Title);
		controlPaneLeft.getChildren().add(star1MassLabel);
		controlPaneLeft.getChildren().add(star1MassText);
		controlPaneLeft.getChildren().add(star1RadiusLabel);
		controlPaneLeft.getChildren().add(star1RadiusText);
		controlPaneLeft.getChildren().add(star1TempLabel);
		controlPaneLeft.getChildren().add(star1TempText);
		controlPaneLeft.getChildren().add(star2Title);
		controlPaneLeft.getChildren().add(star2MassLabel);
		controlPaneLeft.getChildren().add(star2MassText);
		controlPaneLeft.getChildren().add(star2RadiusLabel);
		controlPaneLeft.getChildren().add(star2RadiusText);
		controlPaneLeft.getChildren().add(star2TempLabel);
		controlPaneLeft.getChildren().add(star2TempText);
		controlPaneLeft.getChildren().add(orbitLabel);
		controlPaneLeft.getChildren().add(eccentricityLabel);
		controlPaneLeft.getChildren().add(eccentricityText);
		controlPaneLeft.getChildren().add(maxSeparationLabel);
		controlPaneLeft.getChildren().add(maxSeparationText);
		
		controlPaneLeft.getChildren().add(advancedBtn);
		
		rootMain.setCenter(graphPane);
		
		rootMain.setRight(controlPaneRight);
		controlPaneRight.getChildren().add(controlsTitle);
		controlPaneRight.getChildren().add(imageDirLabel);
		controlPaneRight.getChildren().add(dirTextArea);
		controlPaneRight.getChildren().add(browseBtn);
		controlPaneRight.getChildren().add(generateGraphBtn);
		controlPaneRight.getChildren().add(consoleLabel);
		controlPaneRight.getChildren().add(consoleTextArea);
		
		dirChooser.setTitle("Choose a Directory");
		dirChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("All Images", "*.*")
            );
		browseBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				File imgDir = dirChooser.showSaveDialog(primaryStage);
				if(imgDir != null){
					dirTextArea.setText(imgDir.toString());
					LightCurveGenerator.imgDirMain = imgDir;
					consoleTextArea.appendText("Set image save directory to " + imgDir.toString() + " ;\n");
				}
				
			}
		});
		
		generateGraphBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				if(LightCurveGenerator.imgDirMain == null){
					Alert dirVoidAlert = new Alert(AlertType.ERROR);
					dirVoidAlert.setContentText("Please choose a directory to store light curve!\nClick the 'Browse' Button above to choose a directory.");
					dirVoidAlert.showAndWait();
					return;
				}
				try{
					LightCurveGenerator.star1Mass = Double.parseDouble(star1MassText.getText());
					LightCurveGenerator.star1Radius = Double.parseDouble(star1RadiusText.getText());
					LightCurveGenerator.star1Temp = Double.parseDouble(star1TempText.getText());
					LightCurveGenerator.star2Mass = Double.parseDouble(star2MassText.getText());
					LightCurveGenerator.star2Radius = Double.parseDouble(star2RadiusText.getText());
					LightCurveGenerator.star2Temp = Double.parseDouble(star2TempText.getText());
					LightCurveGenerator.systemEccentricity = Double.parseDouble(eccentricityText.getText());
					LightCurveGenerator.separationDistance = Double.parseDouble(maxSeparationText.getText());
				}catch(Exception e1){
					Alert generateGraphAlert = new Alert(AlertType.ERROR);
					generateGraphAlert.setContentText("Please input valid values.");
					generateGraphAlert.showAndWait();
					return;
				}
				LightCurveGenerator.beginGraph();
			}
		});
		primaryStage.setScene(new Scene(rootMain, 1280, 720));
		primaryStage.show();
	}
	
	public void init(String[] args){
		launch(args);
	}
}
