package model;

public class Empleado {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String usuario;
    private String password;
    private String direccion;
    private String mail;
    private int salario;

    public Empleado(String nombre, String apellido, String dni, String telefono, String usuario, String password, String direccion, String mail, int salario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.usuario = usuario;
        this.password = password;
        this.direccion = direccion;
        this.mail = mail;
        this.salario = salario;
    }

    public Empleado(int id, String nombre, String apellido, String dni, String telefono, String usuario, String password, String direccion, String mail, int salario) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.usuario = usuario;
        this.password = password;
        this.direccion = direccion;
        this.mail = mail;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }
}
