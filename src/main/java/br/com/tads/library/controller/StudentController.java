package br.com.tads.library.controller;

import br.com.tads.library.repository.DBConnexion;
import br.com.tads.library.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
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
    private Button btngotoCrud;

    @FXML
    private TextField searchfield;

    @FXML
    private TextField tCourse;

    @FXML
    private TextField tFName;

    @FXML
    private TextField tLastName;
    @FXML
    private TableColumn<Student, String> colCourse;

    @FXML
    private TableColumn<Student, String> colfName;

    @FXML
    private TableColumn<Student, Integer> colid;

    @FXML
    private TableColumn<Student, String> collName;

    @FXML
    private TableView<Student> table;
    int id = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStudents();
        //if (btngotoCrud != null) {
          //  System.out.println("Botão Go to Books foi inicializado corretamente.");
        //} else {
           // System.out.println("Erro: btngotoCrud é null.");
        //}

    }

    public ObservableList<Student> searchStudentsByName(String searchTerm) {
        ObservableList<Student> students = FXCollections.observableArrayList();

        String query = "SELECT * FROM students WHERE FirstName LIKE ? OR LastName LIKE ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            st.setString(1, "%" + searchTerm + "%");  // Busca pelo primeiro nome
            st.setString(2, "%" + searchTerm + "%");  // Busca pelo sobrenome
            rs = st.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("FirstName"));
                student.setLastName(rs.getString("LastName"));
                student.setCourse(rs.getString("COURSE"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }


    public ObservableList<Student> getStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();

        String query = "select* from students";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Student st = new Student();
                st.setId(rs.getInt("id"));
                st.setFirstName(rs.getString("FirstName"));
                st.setLastName((rs.getString("lastName")));
                st.setCourse(rs.getString("COURSE"));
                students.add(st);
                clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public void showStudents() {
        ObservableList<Student> list = getStudents();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colfName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        collName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Student, String>("course"));
    }


    @FXML
    void clearField(ActionEvent event) {
        clear();

    }

    @FXML
    void creatStudent(ActionEvent event) {
        String insert = "insert into students(FirstName,LastName,COURSE) values(?,?,?)";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, tFName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, tCourse.getText());
            st.executeUpdate();
            clear();
            showStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getData(MouseEvent event) {
        Student student = table.getSelectionModel().getSelectedItem();
        id = student.getId();
        tFName.setText(student.getFirstName());
        tLastName.setText(student.getLastName());
        tCourse.setText(student.getCourse());
        btnSave.setDisable(true);
    }

    void clear() {
        tFName.setText(null);
        tLastName.setText(null);
        tCourse.setText(null);
        btnSave.setDisable(false);

    }

    @FXML
    void deleteStudent(ActionEvent event) {
        String delete = "delete from students where id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showStudents();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void searchStudent(ActionEvent event) {
        String searchTerm = searchfield.getText().trim();
        ObservableList<Student> result = searchStudentsByName(searchTerm);
        table.setItems(result);

    }

    @FXML
    void updateStudent(ActionEvent event) {

        String update = "update students set FirstName = ? , LastName = ? , Course = ? where id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, tFName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, tCourse.getText());
            st.setInt(4, id);
            st.executeUpdate();
            showStudents();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void showAllStudents(ActionEvent event) {
        showStudents();  // Chama o método que já exibe todos os estudantes na tabela
    }

    @FXML
     void gotoBooks() {
        try {
            // Carregar a tela de Books (CRUD)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Books.fxml"));
            Parent root = loader.load();

            // Criar a nova cena
            Scene scene = new Scene(root);

            // Obter o stage atual e trocar a cena
            Stage stage = (Stage) btngotoCrud.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Books - Library");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






