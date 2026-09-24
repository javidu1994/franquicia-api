package co.com.accenture.model.producto.dto;

public record ProductoStockMayorDTO(
        String nombreSucursal,
        String nombreProducto,
        Integer cantidad)
{}