package co.com.accenture.model.sucursal;

public class Sucursal {

    private Long idSucursal;
    private String nombre;
    private Long idFranquicia;

    public Sucursal() {
    }

    public Sucursal(Long idSucursal, String nombre, Long idFranquicia) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.idFranquicia = idFranquicia;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdFranquicia() {
        return idFranquicia;
    }

    public void setIdFranquicia(Long idFranquicia) {
        this.idFranquicia = idFranquicia;
    }
}
