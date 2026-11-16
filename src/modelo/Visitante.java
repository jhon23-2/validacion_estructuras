package modelo;

/**
 * Representa un visitante del Parque Nacional Natural Tayrona.
 * Contiene información básica de contacto.
 */
public class Visitante implements Comparable<Visitante> {
    private String documento;
    private String nombre;
    private String email;
    private String telefono;

    public Visitante(String documento, String nombre, String email, String telefono) {
        this.documento = documento;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public int compareTo(Visitante otro) {
        return this.documento.compareTo(otro.documento);
    }

    @Override
    public String toString() {
        return String.format("Documento: %s | Nombre: %s | Email: %s | Tel: %s",
                documento, nombre, email, telefono);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Visitante that = (Visitante) obj;
        return documento.equals(that.documento);
    }

    @Override
    public int hashCode() {
        return documento.hashCode();
    }
}
