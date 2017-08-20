package actions;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.List;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10,10,10,10));
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(0,0,10,0));
        VBox vBox = new VBox(10);

        TextField textField = new TextField();
        textField.setPrefWidth(450);
        textField.setPromptText("Ebay store URL");
        Button button = new Button("Scrape");
        TextArea textArea = new TextArea();
        textArea.setPrefSize(500,600);


        hBox.getChildren().setAll(textField,button);
        vBox.getChildren().setAll(textArea);

        root.setTop(hBox);
        root.setCenter(vBox);

        button.setOnAction(e -> {
            EbayStoreAction storeAction = new EbayStoreAction(textField.getText());
            List<String> titles = storeAction.doScrape();
            for (String title : titles) {
                textArea.appendText(title+"\n");
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
