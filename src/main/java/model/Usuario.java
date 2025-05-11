package model;

public class Usuario {
    private int id_usuario;
    private String correo_usuario; // Changed from nombre_usuario to correo_usuario
    private String contrasena;
    private int id_tipo_usuario;
 

    public Usuario(int id_usuario, String correo_usuario, String contrasena, int id_tipo_usuario) { // Updated constructor
        this.id_usuario = id_usuario;
        this.correo_usuario = correo_usuario; // Updated parameter name
        this.contrasena = contrasena;
        this.id_tipo_usuario = id_tipo_usuario;
    }

    // Getters y Setters
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCorreo_usuario() { // Updated getter method name
        return correo_usuario; // Updated return value
    }

    public void setCorreo_usuario(String correo_usuario) { // Updated setter method name
        this.correo_usuario = correo_usuario; // Updated parameter name
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }
}
