package vera.galarza.appclientesb.dto;

public class Usuario {

    private Long id;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String pass;

    public Usuario(String nombres, String apellidos, String usuario, String pass) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.pass = pass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
