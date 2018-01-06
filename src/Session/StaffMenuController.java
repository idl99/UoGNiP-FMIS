package Session;

import Entities.BuildingFloor;
import Entities.FmoReport;
import Forms.MessageDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StaffMenuController implements Initializable{

    @FXML
    private Button btnGenerateFmoReport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SessionController.sessionUser.getRole().equals("Superior"))
                btnGenerateFmoReport.setVisible(true);
    }

    @FXML
    void makeRelocationRequest(){
        newFormWindow("/Forms/MoveRequestForm.fxml");
    }

    @FXML
    void makeMaintenanceRequest(){
        newFormWindow("/Forms/MaintenanceRequestForm.fxml");
    }

    @FXML
    void modifyMoveRequest(){
        newFormWindow("/Forms/ModifyMoveForm.fxml");
    }

    @FXML
    void modifyMaintenanceRequest(){
        newFormWindow("/Forms/ModifyMaintenanceForm.fxml");
    }

    @FXML
    void viewFloorPlans(ActionEvent event){
        newFormWindow("/Forms/ViewFloorPlan.fxml");
    }

    @FXML
    void generateFmoReport(){

        List<String> buildingFloors = new ArrayList<>();

        for(BuildingFloor floor : BuildingFloor.getAll()){
            buildingFloors.add(floor.getBuildingNumber()+"-"+floor.getFloorNumber());
        }

        String response = new MessageDialog.MessageDialogBuilder().choiceDialogType(buildingFloors
        ).withTitle("GENERATE FMO REPORT").withHeader("Generate FMO Report").
                withContentText("Select the building floor (Building Number - Floor Number)").build().show();

        BuildingFloor selected = BuildingFloor.getById(response);

        FmoReport report = new FmoReport(selected);

    }

    void newFormWindow(String fxmlResource){
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxmlResource))));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
