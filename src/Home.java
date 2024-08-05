// src/Home.java
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;
import objet.*;

import java.util.stream.Collectors;

public class Home extends Application {

    private final Magasin magasin = new Magasin();
    private TableView<Produit> tableView;
    private StackPane fragmentContainer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        magasin.chargerProduits();

        primaryStage.setTitle("Gestion de vehicules");

        StackPane root = new StackPane();
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Dégradé de fond
        Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTSKYBLUE), new Stop(1, Color.WHITE)};
        LinearGradient backgroundGradient = new LinearGradient(0, 0, 0, 1, true, null, stops);
        root.setBackground(new Background(new BackgroundFill(backgroundGradient, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setStyle("-fx-background-color: #1e1e2f; -fx-border-radius: 20; -fx-background-radius: 10; (gaussian,  10, 0, 0, 0);");

        Label titleLabel = new Label("Gestion des Produits");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        fragmentContainer = new StackPane();
        VBox productsSection = createProjectsSection();
        VBox.setVgrow(productsSection, Priority.ALWAYS);

        fragmentContainer.getChildren().add(productsSection);

        mainContainer.getChildren().addAll(titleLabel, fragmentContainer);

        root.getChildren().add(mainContainer);

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createProjectsSection() {
        VBox productsBox = new VBox(15);
        productsBox.setPadding(new Insets(10));
        productsBox.setAlignment(Pos.CENTER);
        productsBox.setStyle("-fx-background-color: #1E1E2F; -fx-border-radius: 10; -fx-background-radius: 10;");

        tableView = new TableView<>();
        tableView.setItems(magasin.getProduits());
        tableView.setStyle("-fx-background-color: #1E1E2F; -fx-border-radius: 10; -fx-background-radius: 10;");

        TableColumn<Produit, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Produit, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Produit, String> marqueColumn = new TableColumn<>("Marque");
        marqueColumn.setCellValueFactory(new PropertyValueFactory<>("marque"));

        TableColumn<Produit, Integer> cylindreColumn = new TableColumn<>("Cylindre");
        cylindreColumn.setCellValueFactory(new PropertyValueFactory<>("cylindre"));

        TableColumn<Produit, String> modeleColumn = new TableColumn<>("Modèle");
        modeleColumn.setCellValueFactory(new PropertyValueFactory<>("modele"));

        TableColumn<Produit, Integer> nombrePorteColumn = new TableColumn<>("Nombre de porte");
        nombrePorteColumn.setCellValueFactory(new PropertyValueFactory<>("nombrePorte"));

        TableColumn<Produit, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Set the style for the column headers
        idColumn.setStyle("-fx-text-fill: black;");
        nameColumn.setStyle("-fx-text-fill: black;");
        marqueColumn.setStyle("-fx-text-fill: black;");
        cylindreColumn.setStyle("-fx-text-fill: black;");
        modeleColumn.setStyle("-fx-text-fill: black;");
        nombrePorteColumn.setStyle("-fx-text-fill: black;");
        typeColumn.setStyle("-fx-text-fill: black;");

        tableView.getColumns().addAll(idColumn, nameColumn, marqueColumn, cylindreColumn, modeleColumn, nombrePorteColumn, typeColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox.setVgrow(tableView, Priority.ALWAYS);

        HBox buttonBox = createButtonBox();
        VBox.setVgrow(buttonBox, Priority.NEVER);

        productsBox.getChildren().addAll(tableView, buttonBox);
        return productsBox;
    }

    private <T> TableCell<Produit, T> createCellFactory() {
        return new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("-");
                } else {
                    setText(item.toString());
                }
            }
        };
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        Button addButton = new Button("Ajouter Produit");
        addButton.setOnAction(e -> switchFragment(createAddProductFragment()));

        Button deleteButton = new Button("Supprimer Produit");
        deleteButton.setOnAction(e -> switchFragment(createDeleteProductFragment()));

        Button modifyButton = new Button("Modifier Produit");
        modifyButton.setOnAction(e -> switchFragment(createModifyProductFragment()));

        Button searchButton = new Button("Rechercher Produit");
        searchButton.setOnAction(e -> switchFragment(createSearchProductFragment()));

        Button listButton = new Button("Lister Produits par Lettre");
        listButton.setOnAction(e -> switchFragment(createListProductsByLetterFragment()));

        buttonBox.getChildren().addAll(addButton, deleteButton, modifyButton, searchButton, listButton);
        return buttonBox;
    }

    private VBox createAddProductFragment() {
        VBox addProductBox = new VBox(15);
        addProductBox.setPadding(new Insets(10));
        addProductBox.setAlignment(Pos.CENTER);
        addProductBox.setStyle("-fx-background-color: #1E1E2F; -fx-border-radius: 10; -fx-background-radius: 10;");

        GridPane grid = createProductGrid();

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> {
            TextField idField = (TextField) grid.getChildren().get(1);
            TextField nomField = (TextField) grid.getChildren().get(3);
            TextField marqueField = (TextField) grid.getChildren().get(5);
            TextField cylindreField = (TextField) grid.getChildren().get(7);
            TextField modeleField = (TextField) grid.getChildren().get(9);
            TextField nombrePorteField = (TextField) grid.getChildren().get(11);
            ComboBox<String> typeComboBox = (ComboBox<String>) grid.getChildren().get(13);

            String id = idField.getText();
            String nom = nomField.getText();
            String marque = marqueField.getText();
            int cylindre = Integer.parseInt(cylindreField.getText());
            String modele = modeleField.getText();
            int nombrePorte = Integer.parseInt(nombrePorteField.getText());
            String type = typeComboBox.getValue();
            Produit produit = createProduct(id, nom, marque, cylindre, modele, nombrePorte, type);

            if (produit != null) {
                magasin.ajouterProduit(produit);
                tableView.setItems(FXCollections.observableArrayList(magasin.getProduits()));
                showAlert("Succès", "Produit ajouté avec succès!");
            } else {
                showAlert("Erreur", "Erreur lors de l'ajout du produit.");
            }
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> switchFragment(createProjectsSection()));

        addProductBox.getChildren().addAll(grid, addButton, backButton);

        return addProductBox;
    }

    private GridPane createProductGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        TextField marqueField = new TextField();
        marqueField.setPromptText("Marque");
        TextField cylindreField = new TextField();
        cylindreField.setPromptText("Cylindre");
        TextField modeleField = new TextField();
        modeleField.setPromptText("Modèle");
        TextField nombrePorteField = new TextField();
        nombrePorteField.setPromptText("Nombre de porte");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("moto", "camion", "voiture");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Marque:"), 0, 2);
        grid.add(marqueField, 1, 2);
        grid.add(new Label("Cylindre:"), 0, 3);
        grid.add(cylindreField, 1, 3);
        grid.add(new Label("Modèle:"), 0, 4);
        grid.add(modeleField, 1, 4);
        grid.add(new Label("Nombre de porte:"), 0, 5);
        grid.add(nombrePorteField, 1, 5);
        grid.add(new Label("Type:"), 0, 6);
        grid.add(typeComboBox, 1, 6);

        return grid;
    }

    private Produit createProduct(String id, String nom, String marque, int cylindre, String modele, int nombrePorte, String type) {
        return new Produit(id, nom, marque, cylindre, modele, nombrePorte, type);
    }

    private VBox createDeleteProductFragment() {
        // Implementation for delete product fragment
        return new VBox();
    }

    private VBox createModifyProductFragment() {
        // Implementation for modify product fragment
        return new VBox();
    }

    private VBox createSearchProductFragment() {
        // Implementation for search product fragment
        return new VBox();
    }

    private VBox createListProductsByLetterFragment() {
        // Implementation for list products by letter fragment
        return new VBox();
    }

    private void switchFragment(Node fragment) {
        if (!fragmentContainer.getChildren().isEmpty()) {
            fragmentContainer.getChildren().clear();
        }
        fragmentContainer.getChildren().add(fragment);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), fragment);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}