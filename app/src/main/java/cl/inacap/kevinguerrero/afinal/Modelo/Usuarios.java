package cl.inacap.kevinguerrero.afinal.Modelo;

public class Usuarios {

    private int codigo;
    private String nombre;
    private String email;
    private String imagen;

    public Usuarios() {
    }

    public Usuarios(int codigo, String nombre, String email, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.email = email;
        this.imagen = imagen;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getImagen() {
        return imagen;
    }
}
