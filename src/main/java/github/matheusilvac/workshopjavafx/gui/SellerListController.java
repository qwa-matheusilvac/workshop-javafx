package github.matheusilvac.workshopjavafx.gui;

import github.matheusilvac.workshopjavafx.Main;
import github.matheusilvac.workshopjavafx.db.DBIntegrityException;
import github.matheusilvac.workshopjavafx.gui.listeners.DataChangeListener;
import github.matheusilvac.workshopjavafx.gui.util.Alerts;
import github.matheusilvac.workshopjavafx.gui.util.Utils;
import github.matheusilvac.workshopjavafx.model.persistence.entity.Seller;
import github.matheusilvac.workshopjavafx.model.services.SellerService;
import github.matheusilvac.workshopjavafx.model.services.SellerService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerListController implements Initializable, DataChangeListener {

    private SellerService service;

    @FXML
    private TableView<Seller> tableViewSeller;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEdit;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Seller> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage stage = Utils.currentStage(event);
        Seller obj = new Seller();
        //createDialogForm(obj, "/github/matheusilvac/workshopjavafx/gui/SellerForm.fxml", stage);
    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obsList);
       // initEditButtons();
        initRemoveButtons();
    }

//    private void createDialogForm(Seller obj, String absoluteName, Stage stage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            Pane pane = loader.load();
//
//            SellerFormController controller = loader.getController();
//            controller.setSeller(obj);
//            controller.setService(new SellerService());
//            controller.subscribeDataChangeListener(this);
//            controller.updateFormData();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Create Seller");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(stage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//
//        } catch (IOException e) {
//            Alerts.showAlert("IO EXCPTION", "Error Loading View", e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }

    @Override
    public void onDataChange() {
        updateTableView();
    }

//    private void initEditButtons() {
//        tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
//        tableColumnEdit.setCellFactory(param -> new TableCell<Seller, Seller>() {
//            private final Button button = new Button("edit");
//
//            @Override
//            protected void updateItem(Seller obj, boolean empty) {
//                super.updateItem(obj, empty);
//                if (obj == null) {
//                    setGraphic(null);
//                    return;
//                }
//                setGraphic(button);
//                button.setOnAction(
//                        event -> createDialogForm(
//                                obj, "/github/matheusilvac/workshopjavafx/gui/SellerForm.fxml", Utils.currentStage(event)));
//            }
//        });
//    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            } catch (DBIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }


}
