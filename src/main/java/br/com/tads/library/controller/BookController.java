package br.com.tads.library.controller;

import br.com.tads.library.model.Book;
import br.com.tads.library.repository.DBConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnShowAll;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private TextField searchField;

    @FXML
    private TextField tTitle;

    @FXML
    private TextField tAuthor;

    @FXML
    private TextField tGenre;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colGenre;

    @FXML
    private TableView<Book> table;

    int id = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBooks();

    }

    public ObservableList<Book> searchBooksByTitle(String searchTerm) {
        ObservableList<Book> books = FXCollections.observableArrayList();

        String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            st.setString(1, "%" + searchTerm + "%");
            st.setString(2, "%" + searchTerm + "%");
            rs = st.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public ObservableList<Book> getBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();

        String query = "SELECT * FROM books";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                books.add(book);
                clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public void showBooks() {
        ObservableList<Book> list = getBooks();
        table.setItems(list);
        colId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void createBook(ActionEvent event) {
        String insert = "INSERT INTO books(title, author, genre) VALUES(?, ?, ?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, tTitle.getText());
            st.setString(2, tAuthor.getText());
            st.setString(3, tGenre.getText());
            st.executeUpdate();
            clear();
            showBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getData(MouseEvent event) {
        Book book = table.getSelectionModel().getSelectedItem();
        id = book.getId();
        tTitle.setText(book.getTitle());
        tAuthor.setText(book.getAuthor());
        tGenre.setText(book.getGenre());
        btnSave.setDisable(true);
    }

    void clear() {
        tTitle.setText(null);
        tAuthor.setText(null);
        tGenre.setText(null);
        btnSave.setDisable(false);
    }

    @FXML
    void deleteBook(ActionEvent event) {
        String delete = "DELETE FROM books WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showBooks();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchBook(ActionEvent event) {
        String searchTerm = searchField.getText().trim();
        ObservableList<Book> result = searchBooksByTitle(searchTerm);
        table.setItems(result);
    }

    @FXML
    void updateBook(ActionEvent event) {
        String update = "UPDATE books SET title = ?, author = ?, genre = ? WHERE id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, tTitle.getText());
            st.setString(2, tAuthor.getText());
            st.setString(3, tGenre.getText());
            st.setInt(4, id);
            st.executeUpdate();
            showBooks();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showAllBooks(ActionEvent event) {
        showBooks();
    }
    @FXML
    private void goToStudents() {
        try {
            // Carregar a cena de Students.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Students.fxml"));
            Parent root = loader.load();

            // Obter o estágio atual (se btnBack não for nulo)
            Stage stage = (Stage) btnBack.getScene().getWindow();

            // Definir a nova cena
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
