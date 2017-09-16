package actions;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainApp extends Application {

    TableView<Product> table;
    private static final String FILE_NAME = "C:\\Users\\Demonologist\\Documents\\MyFirstExcel.xlsx";


    @Override
    public void start(Stage primaryStage) throws Exception {

        final String[] directory = {""};

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10,10,10,10));

        VBox vBoxTop = new VBox(10);

        HBox hBoxFirstLine = new HBox(10);
        hBoxFirstLine.setPadding(new Insets(0,0,10,0));

        HBox hBoxSecondLine = new HBox(10);
        hBoxSecondLine.setPadding(new Insets(0,0,10,0));

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

        //ImageUrl column
        TableColumn<Product, String> urlColumn = new TableColumn<>("ImageUrl");
        urlColumn.setPrefWidth(50);
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        table = new TableView<>();
        table.getColumns().addAll(numberColumn, nameColumn, priceColumn, urlColumn);

        TextField txtFldStore = new TextField();
        txtFldStore.setPrefWidth(450);
        txtFldStore.setPromptText("Ebay store URL");

        TextField txtFldFilePath = new TextField();
        txtFldFilePath.setPrefWidth(450);
        txtFldFilePath.setPromptText("Choose directory to save...");



        Button buttonScrape = new Button("Scrape");
        Button buttonExportXLS = new Button("Export to Excel");
        Button btnChooseDir = new Button("Choose directory");




        hBoxFirstLine.getChildren().setAll(txtFldStore,buttonScrape);
        hBoxSecondLine.getChildren().setAll(txtFldFilePath,btnChooseDir,buttonExportXLS);
        vBoxTop.getChildren().setAll(hBoxFirstLine,hBoxSecondLine);

        vBox.getChildren().setAll(table);

        root.setTop(vBoxTop);
        root.setCenter(vBox);

        buttonScrape.setOnAction(e -> {
            EbayStoreAction storeAction = new EbayStoreAction(txtFldStore.getText());
            List[] productsData = storeAction.doScrape();
            int elementNumber = 1;
            for (Object title : productsData[0]) {
                Product product = new Product();
                product.setName(title.toString());
                product.setNumber(elementNumber);
                product.setPrice((String) productsData[1].get(elementNumber-1));
                product.setImageUrl((String) productsData[2].get(elementNumber-1));
                table.getItems().add(product);
                elementNumber++;
            }
            storeAction.closeBrowser();
        });

        final int[] rowNum = {0};
        buttonExportXLS.setOnAction(e -> {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Ebay Store");

            table.getItems().forEach(c -> {
                Row row = sheet.createRow(rowNum[0]++);
                Cell cellNumber = row.createCell(0);
                cellNumber.setCellValue(c.getNumber());
                Cell cellName = row.createCell(1);
                cellName.setCellValue(c.getName());
                Cell cellPrice = row.createCell(2);
                cellPrice.setCellValue(c.getPrice());
                Cell cellImageUrl = row.createCell(3);
                cellImageUrl.setCellValue(c.getImageUrl());

                URL url = null;
                try {
                    url = new URL(c.getImageUrl());
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                BufferedImage img = null;
                try {
                    img = ImageIO.read(url);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                File file = new File(directory[0]+"\\ProductPictures\\"+c.getNumber()+".jpg");
                file.mkdirs();
                try {
                    ImageIO.write(img, "jpg", file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            });

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            try {
                FileOutputStream outputStream = new FileOutputStream(txtFldFilePath.getText());
                workbook.write(outputStream);
                workbook.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnChooseDir.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory =
                    directoryChooser.showDialog(primaryStage);

            if(selectedDirectory == null){
                txtFldFilePath.setText("No Directory selected");
            }else{
                directory[0] = selectedDirectory.getAbsolutePath();
                txtFldFilePath.setText(selectedDirectory.getAbsolutePath()+"\\ProductsList.xlsx");
            }
        });



        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {

        launch(args);
    }
}
