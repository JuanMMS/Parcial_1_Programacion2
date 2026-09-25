package uniquindio.app_smartgym.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniquindio.app_smartgym.Controller.ClienteController;
import uniquindio.app_smartgym.model.Cliente;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ClienteViewController implements Initializable {

    // Instancia del Controlador de Negocio (Punto de conexión con la lógica)
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

        // 1. Configurar columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documentoIdentidad"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        colFechaRegistro.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

        actualizarTabla();

        // 2. Eventos de botones
        btnCrear.setOnAction(this::crearCliente);
        btnActualizar.setOnAction(this::actualizarCliente);
        btnEliminar.setOnAction(this::eliminarCliente);
        btnLimpiar.setOnAction(e -> limpiarCampos());
        btnBuscarPorTelefono.setOnAction(this::buscarClientePorTelefono);
        btnValidarNumeroPerfecto.setOnAction(this::validarNumeroPerfecto);

        // 3. Listener de la tabla
        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                llenarCamposConSeleccion(newSelection);
            }
        });
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

            // Delegamos la creación al Controlador
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
                // Delegamos la actualización al Controlador
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
            // Delegamos la eliminación al Controlador
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
            // Delegamos la búsqueda al Controlador
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
        // Delegamos la validación al Controlador
        if (clienteController.validarNumeroPerfecto(numero)) {
            mostrarAlerta("Validación", "El número " + numero + " ES un número perfecto.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Validación", "El número " + numero + " NO es un número perfecto.", Alert.AlertType.WARNING);
        }
    }

    private void actualizarTabla() {
        // Pedimos la lista al Controlador, no directamente al Modelo
        listaClientesObservable = FXCollections.observableArrayList(clienteController.obtenerListaClientes());
        tblClientes.setItems(listaClientesObservable);
        tblClientes.refresh();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDocumento.clear();
        txtEdad.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtTelefonoBuscar.clear();
        tblClientes.getSelectionModel().clearSelection();
    }

    private void llenarCamposConSeleccion(Cliente cliente) {
        txtNombre.setText(cliente.getNombreCompleto());
        txtDocumento.setText(cliente.getDocumentoIdentidad());
        txtEdad.setText(String.valueOf(cliente.getEdad()));
        txtTelefono.setText(cliente.getTelefono());
        txtCorreo.setText(cliente.getCorreoElectronico());
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}