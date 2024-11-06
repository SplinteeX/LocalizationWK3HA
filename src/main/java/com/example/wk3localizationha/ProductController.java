package com.example.wk3localizationha;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductController {
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button loadButton;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    public ProductController() {
    }

    public void initialize() {
        this.languageComboBox.getItems().addAll(new String[]{"English", "French"});
        this.languageComboBox.setValue("English");
        this.idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        this.loadButton.setOnAction((event) -> {
            this.loadProducts();
        });
    }

    private void loadProducts() {
        String selectedLanguage = (String)this.languageComboBox.getValue();
        String query;
        if (selectedLanguage.equals("French")) {
            query = "SELECT id, name_fr AS name, description_fr AS description FROM product";
        } else {
            query = "SELECT id, name_en AS name, description_en AS description FROM product";
        }

        this.productList.clear();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/WK3DbLocalizationHC", "root", "Salasana1");

            try {
                Statement stmt = conn.createStatement();

                try {
                    ResultSet rs = stmt.executeQuery(query);

                    try {
                        while(rs.next()) {
                            Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                            this.productList.add(product);
                        }
                    } catch (Throwable var11) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var10) {
                                var11.addSuppressed(var10);
                            }
                        }

                        throw var11;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var12) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var9) {
                            var12.addSuppressed(var9);
                        }
                    }

                    throw var12;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var13) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var8) {
                        var13.addSuppressed(var8);
                    }
                }

                throw var13;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        this.productTable.setItems(this.productList);
    }
}
