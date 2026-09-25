package parcial.appv2_smartgym.ViewController;

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
import javafx.stage.Stage;
import parcial.appv2_smartgym.Controller.ClienteController;
import parcial.appv2_smartgym.model.Cliente;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ClienteViewController implements Initializable {

    // Instancia del Controlador de Negocio
    private ClienteController clienteController;
    private ObservableList<Cliente> listaClientesObservable;

    @FXML private Button btnVolver;
    @FXML private Button btnCrear;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnBuscarPorTelefono;
    @FXML private Button btnValidarNumeroPerfecto;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtEdad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefonoBuscar;

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colDocumento;
    @FXML private TableColumn<Cliente, Integer> colEdad;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colCorreo;
    @FXML private TableColumn<Cliente, Date> colFechaRegistro;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializamos el controlador puro
        clienteController = new ClienteController();

        // 1. Configurar columnas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documentoIdentidad"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        colFechaRegistro.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

        actualizarTabla();

        // 2. Eventos de botones (se agregó btnVolver)
        if (btnVolver != null) btnVolver.setOnAction(this::volverAlMenu);
        if (btnCrear != null) btnCrear.setOnAction(this::crearCliente);
        if (btnActualizar != null) btnActualizar.setOnAction(this::actualizarCliente);
        if (btnEliminar != null) btnEliminar.setOnAction(this::eliminarCliente);
        if (btnLimpiar != null) btnLimpiar.setOnAction(e -> limpiarCampos());
        if (btnBuscarPorTelefono != null) btnBuscarPorTelefono.setOnAction(this::buscarClientePorTelefono);
        if (btnValidarNumeroPerfecto != null) btnValidarNumeroPerfecto.setOnAction(this::validarNumeroPerfecto);

        // 3. Listener de la tabla
        if (tblClientes != null) {
            tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    llenarCamposConSeleccion(newSelection);
                }
            });
        }
    }

    private void volverAlMenu(ActionEvent event) {
        try {
            URL url = getClass().getResource("/parcial/appv2_smartgym/Main.fxml");
            if (url == null) {
                mostrarAlerta("Error", "No se encontró Main.fxml", Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setTitle("SmartGym - Menú Principal");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al menú principal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void crearCliente(ActionEvent event) {
        try {
            String nombre = txtNombre.getText();
            String documento = txtDocumento.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();

            if (nombre.isEmpty() || documento.isEmpty() || telefono.isEmpty()) {
                mostrarAlerta("Error", "Campos obligatorios vacíos", Alert.AlertType.ERROR);
                return;
            }

            boolean creado = clienteController.crearCliente(nombre, documento, edad, telefono, correo);

            if (creado) {
                mostrarAlerta("Éxito", "Cliente creado correctamente.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "El cliente con este documento ya existe.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La edad debe ser un número válido.", Alert.AlertType.ERROR);
        }
    }

    private void actualizarCliente(ActionEvent event) {
        Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado != null) {
            try {
                boolean actualizado = clienteController.actualizarCliente(
                        clienteSeleccionado.getDocumentoIdentidad(),
                        txtNombre.getText(),
                        txtDocumento.getText(),
                        Integer.parseInt(txtEdad.getText()),
                        txtTelefono.getText(),
                        txtCorreo.getText(),
                        clienteSeleccionado.getFechaRegistro()
                );

                if (actualizado) {
                    mostrarAlerta("Éxito", "Cliente actualizado correctamente.", Alert.AlertType.INFORMATION);
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el cliente.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "La edad debe ser un número.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un cliente de la tabla.", Alert.AlertType.WARNING);
        }
    }

    private void eliminarCliente(ActionEvent event) {
        Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado != null) {
            if (clienteController.eliminarCliente(clienteSeleccionado.getDocumentoIdentidad())) {
                mostrarAlerta("Éxito", "Cliente eliminado.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el cliente.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un cliente de la tabla.", Alert.AlertType.WARNING);
        }
    }

    private void buscarClientePorTelefono(ActionEvent event) {
        String telefonoBuscado = txtTelefonoBuscar.getText();

        if (!telefonoBuscado.isEmpty()) {
            Cliente cliente = clienteController.buscarClientePorTelefono(telefonoBuscado);
            if (cliente != null) {
                tblClientes.getSelectionModel().select(cliente);
                tblClientes.scrollTo(cliente);
                mostrarAlerta("Encontrado", "Cliente: " + cliente.getNombreCompleto(), Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("No encontrado", "No existe un cliente con ese teléfono.", Alert.AlertType.WARNING);
            }
        } else {
            mostrarAlerta("Error", "Ingrese un número de teléfono.", Alert.AlertType.ERROR);
        }
    }

    private void validarNumeroPerfecto(ActionEvent event) {
        String numero = txtTelefonoBuscar.getText();
        if (clienteController.validarNumeroPerfecto(numero)) {
            mostrarAlerta("Validación", "El número " + numero + " ES un número perfecto.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Validación", "El número " + numero + " NO es un número perfecto.", Alert.AlertType.WARNING);
        }
    }

    private void actualizarTabla() {
        if (clienteController != null && tblClientes != null) {
            listaClientesObservable = FXCollections.observableArrayList(clienteController.obtenerListaClientes());
            tblClientes.setItems(listaClientesObservable);
            tblClientes.refresh();
        }
    }

    private void limpiarCampos() {
        if (txtNombre != null) txtNombre.clear();
        if (txtDocumento != null) txtDocumento.clear();
        if (txtEdad != null) txtEdad.clear();
        if (txtTelefono != null) txtTelefono.clear();
        if (txtCorreo != null) txtCorreo.clear();
        if (txtTelefonoBuscar != null) txtTelefonoBuscar.clear();
        if (tblClientes != null) tblClientes.getSelectionModel().clearSelection();
    }

    private void llenarCamposConSeleccion(Cliente cliente) {
        if (cliente != null) {
            txtNombre.setText(cliente.getNombreCompleto());
            txtDocumento.setText(cliente.getDocumentoIdentidad());
            txtEdad.setText(String.valueOf(cliente.getEdad()));
            txtTelefono.setText(cliente.getTelefono());
            txtCorreo.setText(cliente.getCorreoElectronico());
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}