package co.com.accenture.model.franquicia;

public class Franquicia {

    private Long idFranquicia;
    private String nombre;

    public Franquicia() {
    }

    public Franquicia(Long idFranquicia, String nombre) {
        this.idFranquicia = idFranquicia;
        this.nombre = nombre;
    }

    public Long getIdFranquicia() {
        return idFranquicia;
    }

    public void setIdFranquicia(Long idFranquicia) {
        this.idFranquicia = idFranquicia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Franquicia{" +
                "idFranquicia=" + idFranquicia +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
