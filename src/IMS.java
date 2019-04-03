import javafx.application.Application;
import javafx.application.Platform;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.util.ArrayList;



/**
 * Created by gwjense on 6/10/17.
 */
public class IMS extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */



    public static int numberOfParts = 0;
    public static int numberOfProducts = 0;
    private static Inventory myInventory;


    @Override
    public void start(Stage primaryStage) throws Exception {


        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 1260,580);




        scene.getStylesheets().add("stylesheet.css");

        Text title = new Text("Inventory Management System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setId("mainTitle");



        // Button box
        ArrayList<Part> partList = new ArrayList<Part>();
        ObservableList<Part> partsList = FXCollections.observableArrayList(partList);
        myInventory = new Inventory();


        root.setTop(title);
        root.setMargin(title, new Insets(20,20,50,0));

        root.setLeft(partsViewGridPane(scene.getWindow(), partsList));
        root.setRight(productsViewGridPane(scene.getWindow(), partsList, myInventory));


        Button exit = new Button("Exit");

        root.setBottom(exit);
        root.setAlignment(exit,Pos.CENTER_RIGHT);
        root.setMargin(exit, new Insets(40,40,80,80));


        root.setPadding(new Insets(20,20,20,20));
        exit.setOnAction((ActionEvent e)->{
            Platform.exit();
        });






        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public static void main(String[] args)
    {
        launch(args);
    }


    /**
     * Generates the Parts View GridPane on the main UI
     * @param owner the parent window
     * @param partsList the parts list to be displayed
     * @return the generated UI element
     */
    public static Node partsViewGridPane(Window owner, ObservableList<Part> partsList){
    // Method to generate the Parts view GridPane


        // Parts Table View
        Group partsView = new Group();

        partsList.add(new Outsourced("Hard Plate",1,200.00,20,1,30, "Acme"));
        numberOfParts++;
        partsList.add(new Outsourced("Connector",2,20.20,23,33,44, "Acme"));
        numberOfParts++;

        ArrayList<Part> arraySearchResultList = new ArrayList<Part>();
        ObservableList<Part> searchResultList = FXCollections.observableList(arraySearchResultList);
        BorderPane outterBox = new BorderPane();
        outterBox.setPadding(new Insets(0,20,20,20));
        outterBox.setId("roundBox");
        // Outer Container for Parts List




        // Inner Container for title and        HBox buttons = new HBox();

        GridPane topBox = new GridPane();

            HBox topBoxLeft = new HBox();

                // Label of Table View
                Label partsListLabel = new Label("Parts");
                partsListLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                topBoxLeft.getChildren().add(partsListLabel);


            GridPane topBoxRight = new GridPane();

                Button searchButton = new Button("Search");
                searchButton.setId("searchButton");
                Button endSearch = new Button("End Search");
                endSearch.setId("searchButton");
                TextField searchString = new TextField();
                topBoxRight.add(searchButton,0,0);
                topBoxRight.add(searchString,1,0);
                topBoxRight.add(endSearch,0,0);

                endSearch.setVisible(false);

                topBoxRight.setHgap(20);


            topBox.add(topBoxLeft,0,0);
            topBox.add(topBoxRight,1,0);


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        topBox.getColumnConstraints().addAll(col1,col2);
        topBox.setPadding(new Insets(0,0,20,0));




            topBox.setHalignment(topBoxLeft, HPos.LEFT);
            topBox.setHalignment(topBoxRight, HPos.RIGHT);





        // Table View for Parts List



            TableView<Part> tvParts;

            tvParts = new TableView<Part>(partsList);


            searchButton.setOnAction((ActionEvent s)->{
                Part searchResult = null;
                if(!searchResultList.isEmpty())
                {
                    for (Part p: searchResultList)
                    {
                        searchResultList.remove(p);
                    }
                }

                for (Part p : partsList)
                {
                    if (p.getPartID() == Integer.parseInt(searchString.getText()))
                    {
                        searchResult = p;
                        searchResultList.add(searchResult);
                        tvParts.setItems(searchResultList);
                        searchButton.setVisible(false);
                        endSearch.setVisible(true);
                    }
                }

            });

            endSearch.setOnAction((ActionEvent e)->{
                endSearch.setVisible(false);
                searchButton.setVisible(true);
                tvParts.setItems(partsList);
            });


            TableColumn<Part, Integer> partID = new TableColumn<>("Part ID");
            partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
            tvParts.getColumns().add(partID);


            TableColumn<Part, String> partName = new TableColumn<>("Part Name");
            partName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tvParts.getColumns().add(partName);

            TableColumn<Part, Integer> inStock = new TableColumn<>("Inventory Level");
            inStock.setCellValueFactory(new PropertyValueFactory<>("instock"));
            tvParts.getColumns().add(inStock);

            TableColumn<Part, Double> price = new TableColumn<>("Price/Cost per Unit");
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            tvParts.getColumns().add(price);


            tvParts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tvParts.setPrefWidth(200);
            tvParts.setPrefHeight(200);


            TableView.TableViewSelectionModel<Part> tvSelPart = tvParts.getSelectionModel();



        // Bottom inner box to contain buttons
        HBox bottomBox = new HBox();



            Button addPart = new Button("Add");
            Button modifyPart = new Button("Modify");
            Button deletePart = new Button("Delete");

            bottomBox.getChildren().addAll(addPart,modifyPart,deletePart);



            addPart.setOnAction((ActionEvent e)->{
                final Stage addPartStage = new Stage();
                addPartStage.setResizable(false);
                addPartStage.initModality(Modality.APPLICATION_MODAL);
                addPartStage.initOwner(owner);

                Pane addPartDialog = new Pane();
                Scene dialogScene = new Scene(addPartDialog, 550,350);
                addPartDialog.getChildren().add(addPartDialog(dialogScene.getWindow(), addPartStage, partsList));
                dialogScene.getStylesheets().add("stylesheet.css");
                addPartStage.setScene(dialogScene);
                addPartStage.show();
            });



            modifyPart.setOnAction((ActionEvent e)->{
            if(tvSelPart.getSelectedItem() != null)
            {
                Part selectedPart = tvSelPart.getSelectedItem();
                final Stage modifyPartStage = new Stage();
                modifyPartStage.initModality(Modality.APPLICATION_MODAL);
                modifyPartStage.initOwner(owner);
                modifyPartStage.setResizable(false);



                Pane addPartDialog = new Pane();
                Scene dialogScene = new Scene(addPartDialog, 550,350);
                addPartDialog.getChildren().add(modifyPartDialog(dialogScene.getWindow(), modifyPartStage, selectedPart, partsList));
                dialogScene.getStylesheets().add("stylesheet.css");
                modifyPartStage.setScene(dialogScene);
                modifyPartStage.show();
                }
            });


            deletePart.setOnAction((ActionEvent e)->{
                if(tvSelPart.getSelectedItem() != null)
                {
                    final Stage confirmDialog = new Stage();
                    confirmDialog.initModality(Modality.APPLICATION_MODAL);
                    confirmDialog.initOwner(owner);
                    confirmDialog.setResizable(false);
                    VBox confirmPane = new VBox();

                    Text title = new Text("Are you sure you want to delete part: ");
                    title.setFont(Font.font("Arial",FontWeight.BOLD, 24));
                    Text selectedPart = new Text(tvSelPart.getSelectedItem().getName()+"?");
                    selectedPart.setFont(Font.font("Arial", FontWeight.BOLD, 18));
                    Button confirmButton = new Button("Confirm");
                    Button cancelButton = new Button("Cancel");

                    Scene dialogScene = new Scene(confirmPane,450,150);


                    HBox confirmButtons = new HBox();
                    confirmButtons.setSpacing(40);
                    confirmButtons.getChildren().addAll(confirmButton,cancelButton);
                    confirmButtons.setAlignment(Pos.CENTER);

                    confirmPane.getChildren().addAll(title,selectedPart,confirmButtons);
                    confirmPane.setSpacing(20);
                    confirmPane.setAlignment(Pos.CENTER);

                    confirmButton.setOnAction((ActionEvent a)->{
                        partsList.remove(tvSelPart.getSelectedItem());
                        if(endSearch.isVisible())
                        {
                            searchResultList.remove(tvSelPart.getSelectedItem());
                            tvParts.setItems(searchResultList);  // Manually update observable list
                        }
                        confirmDialog.close();
                    });

                    cancelButton.setOnAction((ActionEvent a)->{
                        confirmDialog.close();
                    });

                    confirmDialog.setScene(dialogScene);
                    confirmDialog.show();

                }

            });



            bottomBox.setSpacing(20);
            bottomBox.setPadding(new Insets(30,0,0,60));
            bottomBox.setAlignment(Pos.CENTER_RIGHT);




        tvParts.setMaxWidth(600);

        outterBox.setTop(topBox);
        outterBox.setCenter(tvParts);
        outterBox.setBottom(bottomBox);

        outterBox.setMargin(topBox,new Insets(24,0,0,0));

        partsView.getChildren().add(outterBox);
        return partsView;
    }


    /**
     * Generates the Product View GridPane on the main UI
     * @param owner the parent window
     * @param myPartList the product list to be displayed
     * @param myInventory the
     * @return
     */
    public static Node productsViewGridPane(Window owner, ObservableList<Part> myPartList, Inventory myInventory){
        // Method to generate the Parts view GridPane


        ArrayList<Product> searchList = new ArrayList<Product>();
        ObservableList<Product> searchResultList = FXCollections.observableList(searchList);
        ObservableList<Product> productList = FXCollections.observableList(myInventory.getProducts());



        // Parts Table View
        Group productView = new Group();


        BorderPane outterBox = new BorderPane();
        outterBox.setPadding(new Insets(0,20,20,20));
        outterBox.setId("roundBox");

        // Outer Container for Parts List




        // Inner Container for title and        HBox buttons = new HBox();

        GridPane topBox = new GridPane();

        HBox topBoxLeft = new HBox();

        // Label of Table View
        Label partsListLabel = new Label("Products");
        partsListLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        topBoxLeft.getChildren().add(partsListLabel);


        GridPane topBoxRight = new GridPane();

        Button searchButton = new Button("Search");
        searchButton.setId("searchButton");
        searchButton.setId("searchButton");
        Button endSearch = new Button("End Search");
        endSearch.setId("searchButton");
        TextField searchString = new TextField();
        topBoxRight.add(searchButton,0,0);
        topBoxRight.add(searchString,1,0);
        topBoxRight.add(endSearch,0,0);

        endSearch.setVisible(false);

        topBoxRight.setHgap(20);





        topBox.add(topBoxLeft,0,0);
        topBox.add(topBoxRight,1,0);


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        topBox.getColumnConstraints().addAll(col1,col2);
        topBox.setPadding(new Insets(0,0,20,0));




        topBox.setHalignment(topBoxLeft, HPos.LEFT);
        topBox.setHalignment(topBoxRight, HPos.RIGHT);




        // Table View for Parts List



        TableView<Product> tvProducts;

        tvProducts = new TableView<Product>(productList);

        TableColumn<Product, Integer> productID = new TableColumn<>("Product ID");
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));


        TableColumn<Product, String> productName = new TableColumn<>("Product Name");
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> inStock = new TableColumn<>("Inventory Level");
        inStock.setCellValueFactory(new PropertyValueFactory<>("instock"));

        TableColumn<Product, Double> price = new TableColumn<>("Price/Cost per Unit");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        tvProducts.getColumns().addAll(productID,productName,inStock,price);


        tvProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvProducts.setPrefWidth(400);
        tvProducts.setPrefHeight(200);


        TableView.TableViewSelectionModel<Product> tvSelProduct = tvProducts.getSelectionModel();


        searchButton.setOnAction((ActionEvent s)->{
            Product searchResult = myInventory.lookupProduct(Integer.parseInt(searchString.getText()));
            searchResultList.add(searchResult);
            searchButton.setVisible(false);
            endSearch.setVisible(true);
            tvProducts.setItems(FXCollections.observableArrayList(searchResultList));

        });

        endSearch.setOnAction((ActionEvent e)->{
            endSearch.setVisible(false);
            searchButton.setVisible(true);
            tvProducts.setItems(FXCollections.observableArrayList(productList));
        });






        // Bottom inner box to contain buttons
        HBox bottomBox = new HBox();


        // Buttons to interact with parts list
        Button addProduct = new Button("Add");
        Button modifyProduct = new Button("Modify");
        Button deleteProduct = new Button("Delete");

        bottomBox.getChildren().addAll(addProduct,modifyProduct,deleteProduct);

        addProduct.setOnAction((ActionEvent e)->{
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(addProductDialog(dialog.getOwner(),dialog, myPartList, productList));
            Scene dialogScene = new Scene(dialogVbox,1200,800);
            dialogScene.getStylesheets().add("stylesheet.css");
            dialog.setScene(dialogScene);
            dialog.setResizable(false);
            dialog.show();
        });


        modifyProduct.setOnAction((ActionEvent f)->{
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(modifyProductDialog(dialog.getOwner(),dialog, tvSelProduct.getSelectedItem(), myPartList, productList));
            Scene dialogScene = new Scene(dialogVbox,1200,800);
            dialog.setResizable(false);
            dialogScene.getStylesheets().add("stylesheet.css");
            dialog.setScene(dialogScene);
            dialog.show();
        });

        deleteProduct.setOnAction((ActionEvent e)->{
            if(tvSelProduct.getSelectedItem() != null)
            {
                final Stage confirmDialog = new Stage();
                confirmDialog.initModality(Modality.APPLICATION_MODAL);
                confirmDialog.initOwner(owner);
                confirmDialog.setResizable(false);
                VBox confirmPane = new VBox();

                confirmPane.setPadding(new Insets(20,20,20,20));

                Text title = new Text("Are you sure you want to delete Part: "+ tvSelProduct.getSelectedItem().getName() +" and all associated parts? ");
                title.setFont(Font.font("Arial",FontWeight.BOLD, 24));
                Button confirmButton = new Button("Confirm");
                Button cancelButton = new Button("Cancel");

                Scene dialogScene = new Scene(confirmPane);
                confirmDialog.sizeToScene();


                HBox confirmButtons = new HBox();
                confirmButtons.setSpacing(40);
                confirmButtons.getChildren().addAll(confirmButton,cancelButton);
                confirmButtons.setAlignment(Pos.CENTER);

                confirmPane.getChildren().addAll(title,confirmButtons);
                confirmPane.setSpacing(20);
                confirmPane.setAlignment(Pos.CENTER);

                confirmButton.setOnAction((ActionEvent a)->{
                    // remove all parts from product to be deleted
                    tvSelProduct.getSelectedItem().getParts().clear();
                    myInventory.removeProduct(tvSelProduct.getSelectedItem().getProductID());
                    if(endSearch.isVisible())
                    {
                        searchResultList.remove(tvSelProduct.getSelectedItem());
                        tvProducts.setItems(searchResultList);  // Manually update observable list
                    }
                    confirmDialog.close();
                });

                cancelButton.setOnAction((ActionEvent a)->{
                    confirmDialog.close();
                });

                confirmDialog.setScene(dialogScene);
                confirmDialog.show();

            }

        });




        bottomBox.setSpacing(20);
        bottomBox.setPadding(new Insets(30,0,0,60));
        bottomBox.setAlignment(Pos.CENTER_RIGHT);



        outterBox.setTop(topBox);
        outterBox.setCenter(tvProducts);
        outterBox.setBottom(bottomBox);
        outterBox.setMargin(topBox,new Insets(24,0,0,0));

        productView.getChildren().add(outterBox);
        return productView;
    }


    /**
     * Generates the Add Part Dialog
     * @param owner the parent window
     * @param stage the parent stage
     * @param partsList the list of parts to be added to
     * @return the generated UI element Node
     * @throws RuntimeException
     */

    public static Node addPartDialog(Window owner, Stage stage, ObservableList<Part> partsList) throws RuntimeException{

        Group dialogBox = new Group();
        dialogBox.setId("addPartDialogBox");

        GridPane dialog = new GridPane();
            dialog.setPadding(new Insets(24,24,24,24));
            dialog.setHgap(10);
            dialog.setVgap(10);
            dialog.setAlignment(Pos.CENTER);


            Text dialogTitle = new Text("Add Part");
            dialogTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

            final ToggleGroup group = new ToggleGroup();

            RadioButton inHouse = new RadioButton("In-House");
            inHouse.setToggleGroup(group);
            inHouse.setSelected(true);

            RadioButton outSourced = new RadioButton("Outsourced");
            outSourced.setToggleGroup(group);

            Label partId = new Label("ID");
            Label name = new Label("Name");
            Label inv = new Label("Inv");
            Label price = new Label("Price/Cost");
            Label max = new Label("Max");
            Label min = new Label("Min");
            Label companyName = new Label("Company Name");

            TextField partIdField = new TextField();
            partIdField.setText("Auto Gen - Disabled");
            partIdField.setEditable(false);
            partIdField.setId("autoTextField");

            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            TextField invField = new TextField();
            invField.setPromptText("Inv");
            TextField priceField = new TextField();
            priceField.setPromptText("Price/Cost");
            TextField maxField = new TextField();
            maxField.setPromptText("Max");
            maxField.setMaxWidth(50);
            TextField minField = new TextField();
            minField.setPromptText("Min");
            minField.setMaxWidth(50);

            // default toggle is set to inhouse.
            TextField companyNameField = new TextField();
            companyName.setVisible(false);
            companyNameField.setVisible(false);

            companyNameField.setPromptText("Company Name");

            Label machineID = new Label("Machine Id");
            TextField machineIDField = new TextField();


            if (outSourced.isSelected())
            {
                companyName.setVisible(true);
                companyNameField.setVisible(true);
            }

            Button save = new Button("Save");
            Button cancel = new Button("Cancel");

            outSourced.setOnAction((ActionEvent h)->{
                companyName.setVisible(true);
                companyNameField.setVisible(true);
                machineID.setVisible(false);
                machineIDField.setVisible(false);
            });

            inHouse.setOnAction((ActionEvent h)->{
                companyName.setVisible(false);
                companyNameField.setVisible(false);
                machineID.setVisible(true);
                machineIDField.setVisible(true);
            });




        // Numeric validation coming soon
        
        


            cancel.setOnAction((ActionEvent e)->{
                VBox cancelDialog = new VBox();
                Stage confirmCancel = new Stage();
                Scene confirmCancelScene = new Scene(cancelDialog);

                cancelDialog.setSpacing(30);

                cancelDialog.setPadding(new Insets(24,24,24,24));

                Text areYouSure = new Text("Are you sure you want to cancel?");
                areYouSure.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                HBox buttons = new HBox();

                Button confirm = new Button("Confirm");
                Button back = new Button("Back");

                buttons.getChildren().addAll(confirm,back);
                buttons.setAlignment(Pos.CENTER);
                buttons.setSpacing(40);

                cancelDialog.getChildren().addAll(areYouSure,buttons);
                cancelDialog.setAlignment(Pos.CENTER);



                confirmCancel.setScene(confirmCancelScene);
                confirmCancel.sizeToScene();
                confirmCancel.show();




                confirm.setOnAction((ActionEvent c)->{
                 confirmCancel.close();
                 stage.close();


                });

                back.setOnAction((ActionEvent b)->{
                    confirmCancel.close();
                });



            });


            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(15);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(25);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(10);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setPercentWidth(10);
            ColumnConstraints col6 = new ColumnConstraints();
            col6.setPercentWidth(10);
            ColumnConstraints col7 = new ColumnConstraints();
            col7.setPercentWidth(10);


        dialog.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7);



            dialog.add(dialogTitle, 0,0,2,1);
            dialog.add(inHouse,2,0,2,1);
            dialog.add(outSourced,4,0,2,1);
            dialog.add(partId,1,1);
            dialog.add(partIdField,2,1,2,2);
            dialog.add(name,1,3);
            dialog.add(nameField,2,3,2,2);
            dialog.add(inv,1,5);
            dialog.add(invField,2,5,2,2);
            dialog.add(price,1,7);
            dialog.add(priceField,2,7,2,2);
            dialog.add(max,1,9);
            dialog.add(maxField, 2,9,1,2);
            dialog.add(min,3,9);
            dialog.add(minField,4,9,1,2);
            dialog.add(companyName,1,11);
            dialog.add(companyNameField,2,11,2,2);
            dialog.add(machineID,1,11);
            dialog.add(machineIDField,2,11,2,2);

            HBox buttons = new HBox();
            buttons.getChildren().addAll(save,cancel);
            buttons.setAlignment(Pos.CENTER_RIGHT);
            buttons.setSpacing(20);

            dialog.add(buttons,0,13,7,2);




        save.setOnAction((ActionEvent e)->{

            try {
                if (nameField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Name field can not be empty!");
                }
                if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText()))
                {
                    throw new RuntimeException("Min can not be greater than Max");
                }
                if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText()))
                {
                    throw new RuntimeException("Max can not be less than Min");
                }
                if (Integer.parseInt(invField.getText()) < 0)
                {
                    throw new RuntimeException("Inv can not be negative");
                }
                if (Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText()) || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText()))
                {
                    throw new RuntimeException("Inv value must fall between Min and Max");
                }


                numberOfParts++;
                if (outSourced.isSelected()) {
                    partsList.add(new Outsourced(
                            nameField.getText(),
                            numberOfParts,
                            Double.parseDouble(priceField.getText()),
                            Integer.parseInt(invField.getText()),
                            Integer.parseInt(minField.getText()),
                            Integer.parseInt(maxField.getText()),
                            companyName.getText()));
                }

                if (inHouse.isSelected()) {
                    partsList.add(new Inhouse(
                            nameField.getText(),
                            numberOfParts,
                            Double.parseDouble(priceField.getText()),
                            Integer.parseInt(invField.getText()),
                            Integer.parseInt(minField.getText()),
                            Integer.parseInt(maxField.getText()),
                            Integer.parseInt(machineIDField.getText())));
                }


                stage.close();

            } catch (RuntimeException r)
            {
                final Stage exceptionDialog = new Stage();
                exceptionDialog.initModality(Modality.APPLICATION_MODAL);
                exceptionDialog.initOwner(owner);
                exceptionDialog.setResizable(false);
                VBox exceptionPane = new VBox();
                exceptionPane.setSpacing(20);

                Text exceptionTitle = new Text(r.getMessage());
                exceptionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

                Button okButton = new Button("Ok");
                okButton.setPrefSize(100,25);

                Scene exceptionDialogScene = new Scene(exceptionPane,550,150);
                exceptionPane.setPadding(new Insets(20,20,20,20));
                exceptionPane.getChildren().addAll(exceptionTitle,okButton);
                exceptionPane.setAlignment(Pos.CENTER);



                okButton.setOnAction((ActionEvent m)->{
                    exceptionDialog.close();
                });


                exceptionDialog.setResizable(false);
                exceptionDialog.setScene(exceptionDialogScene);
                exceptionDialog.show();

            }
        });





        dialogBox.getChildren().add(dialog);
        return dialogBox;
    }


    /**
     * Generates the Modify Part Dialog
     * @param owner the parent window
     * @param stage the parent stage
     * @param selectedPart the selected part to be modified
     * @param partsList the list of available parts
     * @return the generated UI element Node
     */

    public static Node modifyPartDialog(Window owner, Stage stage, Part selectedPart, ObservableList<Part> partsList) {




        Group dialogBox = new Group();
        dialogBox.setId("addPartDialogBox");

        GridPane dialog = new GridPane();
        dialog.setPadding(new Insets(24, 24, 24, 24));
        dialog.setHgap(10);
        dialog.setVgap(10);
        dialog.setAlignment(Pos.CENTER);


        Text dialogTitle = new Text("Modify Part");
        dialogTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        final ToggleGroup group = new ToggleGroup();

        RadioButton inHouse = new RadioButton("In-House");
        inHouse.setToggleGroup(group);
        inHouse.setSelected(true);

        RadioButton outSourced = new RadioButton("Outsourced");
        outSourced.setToggleGroup(group);





        Label partId = new Label("ID");
        Label name = new Label("Name");
        Label inv = new Label("Inv");
        Label price = new Label("Price/Cost");
        Label max = new Label("Max");
        Label min = new Label("Min");
        Label companyName = new Label("Company Name");
        Label machineID = new Label("Machine ID");

        TextField partIdField = new TextField();
        partIdField.setText(""+selectedPart.getPartID());
        partIdField.setEditable(false);
        partIdField.setId("autoTextField");
        TextField nameField = new TextField();
        nameField.setText(selectedPart.getName());
        TextField invField = new TextField();
        invField.setText("" + selectedPart.getInstock());
        TextField priceField = new TextField();
        priceField.setText("" + selectedPart.getPrice());
        TextField maxField = new TextField();
        maxField.setText("" + selectedPart.getMax());
        maxField.setMaxWidth(50);
        TextField minField = new TextField();
        minField.setText("" + selectedPart.getMin());
        minField.setMaxWidth(50);
        TextField companyNameField = new TextField();
        TextField machineIDField = new TextField();

        if (selectedPart instanceof Outsourced) {
            outSourced.setSelected(true);
            companyNameField.setText( ((Outsourced) selectedPart).getCompanyName());
            
            companyNameField.setVisible(true);
            companyName.setVisible(true);
            machineID.setVisible(false);
            machineIDField.setVisible(false);
        }

        if (selectedPart instanceof Inhouse) {
            inHouse.setSelected(true);
            machineIDField.setText( ""+((Inhouse) selectedPart).getMachineID());

            companyNameField.setVisible(false);
            companyName.setVisible(false);
            machineID.setVisible(true);
            machineID.setVisible(true);
        }


        Button save = new Button("Save");
        Button cancel = new Button("Cancel");


        cancel.setOnAction((ActionEvent e) -> {
                    cancel.setOnAction((ActionEvent f) -> {
                        VBox cancelDialog = new VBox();
                        Stage confirmCancel = new Stage();
                        Scene confirmCancelScene = new Scene(cancelDialog);

                        cancelDialog.setSpacing(30);

                        cancelDialog.setPadding(new Insets(24, 24, 24, 24));

                        Text areYouSure = new Text("Are you sure you want to cancel?");
                        areYouSure.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                        HBox buttons = new HBox();

                        Button confirm = new Button("Confirm");
                        Button back = new Button("Back");

                        buttons.getChildren().addAll(confirm, back);
                        buttons.setAlignment(Pos.CENTER);
                        buttons.setSpacing(40);

                        cancelDialog.getChildren().addAll(areYouSure, buttons);
                        cancelDialog.setAlignment(Pos.CENTER);


                        confirmCancel.setScene(confirmCancelScene);
                        confirmCancel.sizeToScene();
                        confirmCancel.show();


                        confirm.setOnAction((ActionEvent c) -> {
                            confirmCancel.close();
                            stage.close();


                        });

                        back.setOnAction((ActionEvent b) -> {
                            confirmCancel.close();
                        });
                    });
                });


            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(15);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(25);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(10);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setPercentWidth(10);
            ColumnConstraints col6 = new ColumnConstraints();
            col6.setPercentWidth(10);
            ColumnConstraints col7 = new ColumnConstraints();
            col7.setPercentWidth(10);


            dialog.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7);


            dialog.add(dialogTitle, 0, 0, 2, 1);
            dialog.add(inHouse, 2, 0, 2, 1);
            dialog.add(outSourced, 4, 0, 2, 1);
            dialog.add(partId, 1, 1);
            dialog.add(partIdField, 2, 1, 2, 2);
            dialog.add(name, 1, 3);
            dialog.add(nameField, 2, 3, 2, 2);
            dialog.add(inv, 1, 5);
            dialog.add(invField, 2, 5, 2, 2);
            dialog.add(price, 1, 7);
            dialog.add(priceField, 2, 7, 2, 2);
            dialog.add(max, 1, 9);
            dialog.add(maxField, 2, 9, 1, 2);
            dialog.add(min, 3, 9);
            dialog.add(minField, 4, 9, 1, 2);
            dialog.add(companyName, 1, 11);
            dialog.add(companyNameField, 2, 11, 2, 2);
            dialog.add(machineID, 1, 11);
            dialog.add(machineIDField, 2, 11, 2, 2);

            HBox buttons = new HBox();
            buttons.getChildren().addAll(save, cancel);
            buttons.setAlignment(Pos.CENTER_RIGHT);
            buttons.setSpacing(20);

            dialog.add(buttons, 0, 13, 7, 2);


            outSourced.setOnAction((ActionEvent h) -> {
                companyName.setVisible(true);
                companyNameField.setVisible(true);
                machineID.setVisible(false);
                machineIDField.setVisible(false);
            });

            inHouse.setOnAction((ActionEvent h) -> {
                companyName.setVisible(false);
                companyNameField.setVisible(false);
                machineID.setVisible(true);
                machineIDField.setVisible(true);
            });


            save.setOnAction((ActionEvent h) -> {

                // Check to see if the source of the part has not changed and update fields.

                if (selectedPart instanceof Outsourced && outSourced.isSelected()) {

                    selectedPart.setPartID(Integer.parseInt(partIdField.getText()));
                    selectedPart.setName(nameField.getText());
                    selectedPart.setInstock(Integer.parseInt(invField.getText()));
                    selectedPart.setPrice(Double.parseDouble(priceField.getText()));
                    selectedPart.setMax(Integer.parseInt(maxField.getText()));
                    selectedPart.setMin(Integer.parseInt(minField.getText()));
                    ((Outsourced) selectedPart).setCompanyName(companyNameField.getText());
                } else if (selectedPart instanceof Inhouse && inHouse.isSelected()) {

                    selectedPart.setPartID(Integer.parseInt(partIdField.getText()));
                    selectedPart.setName(nameField.getText());
                    selectedPart.setInstock(Integer.parseInt(invField.getText()));
                    selectedPart.setPrice(Double.parseDouble(priceField.getText()));
                    selectedPart.setMax(Integer.parseInt(maxField.getText()));
                    selectedPart.setMin(Integer.parseInt(minField.getText()));
                    ((Inhouse) selectedPart).setMachineID(Integer.parseInt(machineIDField.getText()));
                } else {
                    partsList.remove(selectedPart);

                    if (outSourced.isSelected()) {
                        partsList.add(new Outsourced(
                                nameField.getText(),
                                Integer.parseInt(partIdField.getText()),
                                Double.parseDouble(priceField.getText()),
                                Integer.parseInt(invField.getText()),
                                Integer.parseInt(minField.getText()),
                                Integer.parseInt(maxField.getText()),
                                companyName.getText()));
                    }

                    if (inHouse.isSelected()) {
                        partsList.add(new Inhouse(
                                nameField.getText(),
                                Integer.parseInt(partIdField.getText()),
                                Double.parseDouble(priceField.getText()),
                                Integer.parseInt(invField.getText()),
                                Integer.parseInt(minField.getText()),
                                Integer.parseInt(maxField.getText()),
                                Integer.parseInt(machineIDField.getText())));
                    }


                }


                stage.close();
            });

            dialogBox.getChildren().add(dialog);
            return dialogBox;
        }

    /**
     * Generates the Add Product Dialog
     * @param owner the parent window
     * @param stage the parent stage
     * @param partsList the list of available parts
     * @param productList the list of products to be added to.
     * @return the generated UI element Node
     */

    public static Node addProductDialog(Window owner, Stage stage, ObservableList<Part> partsList, ObservableList<Product> productList)
    {




        Group dialogBox = new Group();
        GridPane searchBar = new GridPane();

        Button searchButton = new Button("Search");
        searchButton.setId("searchButton");
        Button endSearch = new Button("End Search");
        endSearch.setId("searchButton");
        TextField searchString = new TextField();

        searchBar.setAlignment(Pos.CENTER_RIGHT);
        searchBar.setHgap(30);
        searchBar.setVgap(20);
        searchBar.add(searchButton,0,0);
        endSearch.setVisible(false);
        searchBar.add(endSearch,0,0);
        searchBar.add(searchString,1,0);
        searchBar.setPadding(new Insets(20,20,20,20));


        dialogBox.setId("addPartDialogBox");


        GridPane dialog = new GridPane();

            dialog.setPadding(new Insets(24, 24, 24, 24));
            dialog.setHgap(10);
            dialog.setVgap(10);
            dialog.setAlignment(Pos.CENTER);


            Text dialogTitle = new Text("Add Product");
            dialogTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));


            Label productID = new Label("ID");
            Label name = new Label("Name");
            Label inv = new Label("Inv");
            Label priceLabel = new Label("Price/Cost");
            Label max = new Label("Max");
            Label min = new Label("Min");


            TextField productIdField = new TextField();
            productIdField.setText("Auto Gen - Disabled");
            productIdField.setId("autoTextField");
            productIdField.setEditable(false);
            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            TextField invField = new TextField();
            invField.setPromptText("Inv");
            TextField priceField = new TextField();
            priceField.setPromptText("Price/Cost");
            TextField maxField = new TextField();
            maxField.setPromptText("Max");
            maxField.setMaxWidth(50);
            TextField minField = new TextField();
            minField.setPromptText("Min");
            minField.setMaxWidth(50);


            Button saveProduct = new Button("Save");
            Button cancel = new Button("Cancel");



        // Product associated parts

    VBox partsVBox = new VBox();


        ObservableList<Part> observablePartsList = FXCollections.observableArrayList(partsList);


        TableView<Part> tvParts = new TableView<>(observablePartsList);

        TableColumn<Part, Integer> partID = new TableColumn<>("Part ID");
        partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        tvParts.getColumns().add(partID);


        TableColumn<Part, String> partName = new TableColumn<>("Part Name");
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvParts.getColumns().add(partName);

        TableColumn<Part, Integer> inStock = new TableColumn<>("Inventory Level");
        inStock.setCellValueFactory(new PropertyValueFactory<>("instock"));
        tvParts.getColumns().add(inStock);

        TableColumn<Part, Double> price = new TableColumn<>("Price/Cost per Unit");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvParts.getColumns().add(price);


        tvParts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvParts.setPrefWidth(600);
        tvParts.setPrefHeight(200);


        TableView.TableViewSelectionModel<Part> tvSelPart = tvParts.getSelectionModel();




        // Search Results


        searchButton.setOnAction((ActionEvent s)->{
            Part searchResult = null;
            for (Part p : partsList)
            {
                if (p.getPartID() == Integer.parseInt(searchString.getText()))
                {
                    searchResult = p;
                    ArrayList<Part> searchResultList = new ArrayList<Part>();
                    searchResultList.add(searchResult);
                    tvParts.setItems(FXCollections.observableArrayList(searchResultList));
                    searchButton.setVisible(false);
                    endSearch.setVisible(true);
                }
            }

        });

        endSearch.setOnAction((ActionEvent e)->{
            endSearch.setVisible(false);
            searchButton.setVisible(true);
            tvParts.setItems(FXCollections.observableArrayList(partsList));
        });








        ArrayList<Part> newProductPartList = new ArrayList<Part>();

        ObservableList<Part> observableAssociatedPartsList = FXCollections.observableArrayList(newProductPartList);

        TableView<Part> tvAssociatedParts;

        tvAssociatedParts = new TableView<>(observableAssociatedPartsList);

        TableColumn<Part, Integer> associatedPartID = new TableColumn<>("Part ID");
        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn<Part, String> associatedPartName = new TableColumn<>("Part Name");
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Part, Integer> associatedInStock = new TableColumn<>("Inventory Level");
        associatedInStock.setCellValueFactory(new PropertyValueFactory<>("instock"));
        TableColumn<Part, Double> associatedPrice = new TableColumn<>("Price/Cost per Unit");
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvAssociatedParts.getColumns().addAll(associatedPartID,associatedPartName,associatedInStock,associatedPrice);

        tvAssociatedParts.setPrefWidth(600);
        tvAssociatedParts.setPrefHeight(200);

        tvAssociatedParts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableView.TableViewSelectionModel<Part> tvAssociatedSelPart = tvAssociatedParts.getSelectionModel();

        Button addAssociatedPart = new Button("Add");
        Button deleteAssociatedPart = new Button("Delete");

        addAssociatedPart.setOnAction((ActionEvent v)->{
            observableAssociatedPartsList.add(tvSelPart.getSelectedItem());
        });

        deleteAssociatedPart.setOnAction((ActionEvent rm)->{
            observableAssociatedPartsList.remove(tvAssociatedSelPart.getSelectedItem());
        });




        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(10);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(10);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(10);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(10);


        dialog.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7);



        dialog.add(dialogTitle, 0,0,2,1);
        dialog.add(productID,1,1);
        dialog.add(productIdField,2,1,2,2);
        dialog.add(name,1,3);
        dialog.add(nameField,2,3,2,2);
        dialog.add(inv,1,5);
        dialog.add(invField,2,5,2,2);
        dialog.add(priceLabel,1,7);
        dialog.add(priceField,2,7,2,2);
        dialog.add(max,1,9);
        dialog.add(maxField, 2,9,1,2);
        dialog.add(min,3,9);
        dialog.add(minField,4,9,1,2);

        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(saveProduct,cancel);
        buttons.setAlignment(Pos.CENTER_RIGHT);




        saveProduct.setOnAction((ActionEvent e)->{
            double lowestPrice = 0.0;
            if (observableAssociatedPartsList.size() > 0) {


                for (Part p : observableAssociatedPartsList) {
                    lowestPrice += p.getPrice();
                }
            }
            try {
                if (observableAssociatedPartsList.size() == 0)
                {
                    throw new RuntimeException("Every product must have at least one associated part");
                }
                if (nameField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Name field can not be empty!");
                }
                if (maxField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Max field can not be empty!");
                }
                if (priceField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Price field can not be empty!");
                }
                if (minField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Min field can not be empty!");
                }
                if (invField.getText().compareTo("") == 0)
                {
                    invField.setText(minField.getText());
                }
                if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText()))
                {
                    throw new RuntimeException("Min can not be greater than Max");
                }
                if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText()))
                {
                    throw new RuntimeException("Max can not be less than Min");
                }
                if (Integer.parseInt(invField.getText()) < 0)
                {
                    throw new RuntimeException("Inv can not be negative");
                }
                if (Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText()) || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText()))
                {
                    throw new RuntimeException("Inv value must fall between Min and Max");
                }
                double totalCostOfParts = 0;

                for (Part p : observableAssociatedPartsList)
                {
                    totalCostOfParts += p.getPrice();
                }

                if (Double.parseDouble(priceField.getText()) < totalCostOfParts)
                {

                    throw new RuntimeException("The price of the product can not be less than the total cost of the associated parts which is: $"+totalCostOfParts);
                }

                numberOfProducts++;

                Product newProduct = new Product(numberOfProducts,nameField.getText(),Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()), Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()));

                ArrayList<Part> newProductParts = new ArrayList<Part>();

                for (Part p : observableAssociatedPartsList)
                {
                    newProductParts.add(p);
                }

                newProduct.setParts(newProductParts);

                myInventory.addProduct(newProduct);


                stage.close();

            } catch (RuntimeException r)
            {
                final Stage exceptionDialog = new Stage();
                exceptionDialog.initModality(Modality.APPLICATION_MODAL);
                exceptionDialog.initOwner(owner);

                VBox exceptionPane = new VBox();
                exceptionPane.setSpacing(20);

                Text exceptionTitle = new Text(r.getMessage());
                exceptionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

                Button okButton = new Button("Ok");
                okButton.setPrefSize(100,25);

                Scene exceptionDialogScene = new Scene(exceptionPane);
                exceptionDialog.sizeToScene();
                exceptionPane.setPadding(new Insets(20,20,20,20));
                exceptionPane.getChildren().addAll(exceptionTitle,okButton);
                exceptionPane.setAlignment(Pos.CENTER);
                exceptionDialog.setResizable(false);



                okButton.setOnAction((ActionEvent m)->{
                    exceptionDialog.close();
                });


                exceptionDialog.setResizable(false);
                exceptionDialog.setScene(exceptionDialogScene);
                exceptionDialog.show();

            }
        });

        // Numeric validation coming soon



            cancel.setOnAction((ActionEvent f) -> {
                VBox cancelDialog = new VBox();
                Stage confirmCancel = new Stage();
                Scene confirmCancelScene = new Scene(cancelDialog);

                cancelDialog.setSpacing(30);

                cancelDialog.setPadding(new Insets(24, 24, 24, 24));

                Text areYouSure = new Text("Are you sure you want to cancel?");
                areYouSure.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                HBox confirmButtons = new HBox();

                Button confirm = new Button("Confirm");
                Button back = new Button("Back");

                confirmButtons.getChildren().addAll(confirm, back);
                confirmButtons.setAlignment(Pos.CENTER);
                confirmButtons.setSpacing(40);

                cancelDialog.getChildren().addAll(areYouSure, confirmButtons);
                cancelDialog.setAlignment(Pos.CENTER);


                confirmCancel.setScene(confirmCancelScene);
                confirmCancel.sizeToScene();
                confirmCancel.show();


                confirm.setOnAction((ActionEvent c) -> {
                    confirmCancel.close();
                    stage.close();


                });

                back.setOnAction((ActionEvent b) -> {
                    confirmCancel.close();
                });
            });

        HBox deleteAssociatedPartBox = new HBox();
        deleteAssociatedPartBox.getChildren().add(deleteAssociatedPart);
        deleteAssociatedPartBox.setAlignment(Pos.CENTER_RIGHT);


        HBox addAssociatedPartBox = new HBox();
        addAssociatedPartBox.getChildren().add(addAssociatedPart);
        addAssociatedPartBox.setAlignment(Pos.CENTER_RIGHT);


        partsVBox.getChildren().addAll(tvParts,addAssociatedPartBox,tvAssociatedParts, deleteAssociatedPartBox, buttons);
        partsVBox.setSpacing(20);
        partsVBox.setAlignment(Pos.CENTER);

        BorderPane outerBox = new BorderPane();
        outerBox.setTop(searchBar);
        outerBox.setAlignment(searchBar,Pos.CENTER_RIGHT);
        outerBox.setLeft(dialog);
        outerBox.setCenter(partsVBox);
        dialogBox.getChildren().add(outerBox);




        return dialogBox;
    }

    /**
     * Generates the Modify Product Dialog
     * @param owner the parent window
     * @param stage the parent stage
     * @param product the product to be modified
     * @param partsList the list of available parts
     * @param productList the list of the products
     * @return the generated UI element Node
     */
    public static Node modifyProductDialog(Window owner, Stage stage, Product product, ObservableList<Part> partsList, ObservableList<Product> productList)
    {




        Group dialogBox = new Group();

        dialogBox.setId("addPartDialogBox");


        GridPane dialog = new GridPane();

        dialog.setPadding(new Insets(24, 24, 24, 24));
        dialog.setHgap(10);
        dialog.setVgap(10);
        dialog.setAlignment(Pos.CENTER);


        Text dialogTitle = new Text("Modify Product");
        dialogTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));


        Label productID = new Label("ID");
        Label name = new Label("Name");
        Label inv = new Label("Inv");
        Label priceLabel = new Label("Price/Cost");
        Label max = new Label("Max");
        Label min = new Label("Min");


        TextField productIdField = new TextField();
        productIdField.setText(""+product.getProductID());
        productIdField.setEditable(false);
        productIdField.setId("autoTextField");
        TextField nameField = new TextField();
        nameField.setText(product.getName());
        TextField invField = new TextField();
        invField.setText(""+product.getInstock());
        TextField priceField = new TextField();
        priceField.setText(""+product.getPrice());
        TextField maxField = new TextField();
        maxField.setText(""+product.getMax());
        maxField.setMaxWidth(50);
        TextField minField = new TextField();
        minField.setText(""+product.getMin());
        minField.setMaxWidth(50);


        Button saveProduct = new Button("Save");
        Button cancel = new Button("Cancel");



        // Product associated parts TableView

        VBox partsVBox = new VBox();


        ObservableList<Part> observablePartsList = FXCollections.observableArrayList(partsList);


        TableView<Part> tvParts;

        tvParts = new TableView<Part>(observablePartsList);

        TableColumn<Part, Integer> partID = new TableColumn<>("Part ID");
        partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        tvParts.getColumns().add(partID);


        TableColumn<Part, String> partName = new TableColumn<>("Part Name");
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvParts.getColumns().add(partName);

        TableColumn<Part, Integer> inStock = new TableColumn<>("Inventory Level");
        inStock.setCellValueFactory(new PropertyValueFactory<>("instock"));
        tvParts.getColumns().add(inStock);

        TableColumn<Part, Double> price = new TableColumn<>("Price/Cost per Unit");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvParts.getColumns().add(price);


        tvParts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvParts.setPrefWidth(600);
        tvParts.setPrefHeight(200);


        TableView.TableViewSelectionModel<Part> tvSelPart = tvParts.getSelectionModel();


        ObservableList<Part> observableAssociatedPartsList = FXCollections.observableArrayList(product.getParts());

        TableView<Part> tvAssociatedParts;

        tvAssociatedParts = new TableView<>(observableAssociatedPartsList);

        TableColumn<Part, Integer> associatedPartID = new TableColumn<>("Part ID");
        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn<Part, String> associatedPartName = new TableColumn<>("Part Name");
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Part, Integer> associatedInStock = new TableColumn<>("Inventory Level");
        associatedInStock.setCellValueFactory(new PropertyValueFactory<>("instock"));
        TableColumn<Part, Double> associatedPrice = new TableColumn<>("Price/Cost per Unit");
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvAssociatedParts.getColumns().addAll(associatedPartID,associatedPartName,associatedInStock,associatedPrice);


        tvAssociatedParts.setPrefWidth(600);
        tvAssociatedParts.setPrefHeight(200);
        tvAssociatedParts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



        TableView.TableViewSelectionModel<Part> tvAssociatedSelPart = tvAssociatedParts.getSelectionModel();

        Button addAssociatedPart = new Button("Add Part");
        Button deleteAssociatedPart = new Button("Remove Part");

        addAssociatedPart.setOnAction((ActionEvent v)->{
            observableAssociatedPartsList.add(tvSelPart.getSelectedItem());
        });

        deleteAssociatedPart.setOnAction((ActionEvent rm)->{
            observableAssociatedPartsList.remove(tvAssociatedSelPart.getSelectedItem());
        });









        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(10);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(10);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(10);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(10);


        dialog.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6,col7);



        dialog.add(dialogTitle, 0,0,2,1);
        dialog.add(productID,1,1);
        dialog.add(productIdField,2,1,2,2);
        dialog.add(name,1,3);
        dialog.add(nameField,2,3,2,2);
        dialog.add(inv,1,5);
        dialog.add(invField,2,5,2,2);
        dialog.add(priceLabel,1,7);
        dialog.add(priceField,2,7,2,2);
        dialog.add(max,1,9);
        dialog.add(maxField, 2,9,1,2);
        dialog.add(min,3,9);
        dialog.add(minField,4,9,1,2);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(saveProduct,cancel);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(20);




        saveProduct.setOnAction((ActionEvent e)->{

            try {
                if (observableAssociatedPartsList.size() == 0)
                {
                    throw new RuntimeException("Every product must have at least one associated part");
                }
                if (nameField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Name field can not be empty!");
                }
                if (maxField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Max field can not be empty!");
                }
                if (priceField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Price field can not be empty!");
                }
                if (minField.getText().compareTo("") == 0)
                {
                    throw new RuntimeException("Min field can not be empty!");
                }
                if (invField.getText().compareTo("") == 0)
                {
                    invField.setText(minField.getText());
                }
                if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText()))
                {
                    throw new RuntimeException("Min can not be greater than Max");
                }
                if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText()))
                {
                    throw new RuntimeException("Max can not be less than Min");
                }
                if (Integer.parseInt(invField.getText()) < 0)
                {
                    throw new RuntimeException("Inv can not be negative");
                }
                if (Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText()) || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText()))
                {
                    throw new RuntimeException("Inv value must fall between Min and Max");
                }
                double totalCostOfParts = 0;

                for (Part p : observableAssociatedPartsList)
                {
                    totalCostOfParts += p.getPrice();
                }

                if (Double.parseDouble(priceField.getText()) < totalCostOfParts)
                {
                    throw  new RuntimeException("The price of the product can not be less than the total cost of the associated parts which is: "+totalCostOfParts);
                }

                Product modifiedProduct = new Product(1,"",1.0,1,1,1);

                modifiedProduct.setProductID(Integer.parseInt(productIdField.getText()));
                modifiedProduct.setName(nameField.getText());
                modifiedProduct.setPrice(Double.parseDouble(priceField.getText()));
                modifiedProduct.setInstock(Integer.parseInt(invField.getText()));
                modifiedProduct.setMin(Integer.parseInt(minField.getText()));
                modifiedProduct.setMax(Integer.parseInt(maxField.getText()));


                ArrayList<Part> newPartList = new ArrayList<Part>();

                newPartList.addAll(observableAssociatedPartsList);

                product.setParts(newPartList);

                myInventory.updateProduct(product.getProductID(), modifiedProduct);

                stage.close();

            } catch (RuntimeException r)
            {

                final Stage exceptionDialog = new Stage();
                exceptionDialog.initModality(Modality.APPLICATION_MODAL);
                exceptionDialog.initOwner(owner);
                exceptionDialog.setResizable(false);
                VBox exceptionPane = new VBox();
                exceptionPane.setSpacing(20);


                Text exceptionTitle = new Text(r.getMessage());

                exceptionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

                Button okButton = new Button("Ok");
                okButton.setPrefSize(100,25);

                Scene exceptionDialogScene = new Scene(exceptionPane);
                exceptionDialog.sizeToScene();
                exceptionPane.setPadding(new Insets(20,20,20,20));
                exceptionPane.getChildren().addAll(exceptionTitle,okButton);
                exceptionPane.setAlignment(Pos.CENTER);



                okButton.setOnAction((ActionEvent m)->{
                    exceptionDialog.close();
                });


                exceptionDialog.setResizable(false);
                exceptionDialog.setScene(exceptionDialogScene);
                exceptionDialog.show();

            }
        });





            cancel.setOnAction((ActionEvent f) -> {
                VBox cancelDialog = new VBox();
                Stage confirmCancel = new Stage();
                Scene confirmCancelScene = new Scene(cancelDialog);

                cancelDialog.setSpacing(30);

                cancelDialog.setPadding(new Insets(24, 24, 24, 24));

                Text areYouSure = new Text("Are you sure you want to cancel?");
                areYouSure.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                HBox confirmButtons = new HBox();

                Button confirm = new Button("Confirm");
                Button back = new Button("Back");

                confirmButtons.getChildren().addAll(confirm, back);
                confirmButtons.setAlignment(Pos.CENTER);
                confirmButtons.setSpacing(40);

                cancelDialog.getChildren().addAll(areYouSure, confirmButtons);
                cancelDialog.setAlignment(Pos.CENTER);


                confirmCancel.setScene(confirmCancelScene);
                confirmCancel.sizeToScene();
                confirmCancel.show();


                confirm.setOnAction((ActionEvent c) -> {
                    confirmCancel.close();
                    stage.close();


                });

                back.setOnAction((ActionEvent b) -> {
                    confirmCancel.close();
                });
            });


        HBox deleteAssociatedPartBox = new HBox();
        deleteAssociatedPartBox.getChildren().add(deleteAssociatedPart);
        deleteAssociatedPartBox.setAlignment(Pos.CENTER_RIGHT);


        HBox addAssociatedPartBox = new HBox();
        addAssociatedPartBox.getChildren().add(addAssociatedPart);
        addAssociatedPartBox.setAlignment(Pos.CENTER_RIGHT);

        partsVBox.getChildren().addAll(tvParts,addAssociatedPartBox,tvAssociatedParts, deleteAssociatedPartBox, buttons);
        partsVBox.setSpacing(20);
        partsVBox.setAlignment(Pos.CENTER);

        BorderPane outerBox = new BorderPane();
        outerBox.setLeft(dialog);
        outerBox.setCenter(partsVBox);
        dialogBox.getChildren().add(outerBox);




        return dialogBox;
    }


}













