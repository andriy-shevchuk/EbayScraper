package actions;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.List;

public class MainApp extends Application {

    TableView<Product> table;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10,10,10,10));
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(0,0,10,0));
        VBox vBox = new VBox(10);

        //Name column
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(550);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Number column
        TableColumn<Product, Integer> numberColumn = new TableColumn<>("№");
        numberColumn.setMinWidth(15);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        //Price column
        TableColumn<Product, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setPrefWidth(150);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table = new TableView<>();
        table.getColumns().addAll(numberColumn, nameColumn, priceColumn);

        TextField textField = new TextField();
        textField.setPrefWidth(450);
        textField.setPromptText("Ebay store URL");
        Button button = new Button("Scrape");




        hBox.getChildren().setAll(textField,button);
        vBox.getChildren().setAll(table);

        root.setTop(hBox);
        root.setCenter(vBox);

        button.setOnAction(e -> {
            EbayStoreAction storeAction = new EbayStoreAction(textField.getText());
            List[] productsData = storeAction.doScrape();
            int elementNumber = 1;
            for (Object title : productsData[0]) {
                Product product = new Product();
                product.setName(title.toString());
                product.setNumber(elementNumber);
                product.setPrice((String) productsData[1].get(elementNumber-1));
                table.getItems().add(product);
                elementNumber++;
            }
            storeAction.closeBrowser();
        });

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {

        launch(args);
    }
}
