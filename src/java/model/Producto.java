package model;

public class Producto {
    private int id;
    private Categoria categoria;
    private Marca marca;
    private String nombre;
    private int costo;
    private int precioUnitario;
    private int stock;

    public Producto(Categoria categoria, Marca marca, String nombre, int costo, int precioUnitario, int stock) {
        this.categoria = categoria;
        this.marca = marca;
        this.nombre = nombre;
        this.costo = costo;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
    }

    public Producto(int id, Categoria categoria, Marca marca, String nombre, int costo, int precioUnitario, int stock) {
        this.id = id;
        this.categoria = categoria;
        this.marca = marca;
        this.nombre = nombre;
        this.costo = costo;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
