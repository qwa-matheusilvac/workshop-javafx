package github.matheusilvac.workshopjavafx.gui;

import github.matheusilvac.workshopjavafx.Main;
import github.matheusilvac.workshopjavafx.model.entity.Department;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {

    @FXML
    private Button btNew;

    @FXML
    private TableView<Department> departmentTable;

    @FXML
    private TableColumn<Department, Integer> departmentId;

    @FXML
    private TableColumn<Department, String> departmentName;

    public void onBtNewAction(){
        System.out.println("onBtNewAction");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        departmentId.setCellValueFactory(new PropertyValueFactory<>("id"));


        Stage stage = (Stage) Main.getMainScene().getWindow();
        departmentTable.prefHeightProperty().bind(stage.heightProperty());
    }
}
