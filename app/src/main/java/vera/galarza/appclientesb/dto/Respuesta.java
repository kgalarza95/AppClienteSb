package vera.galarza.appclientesb.dto;

public class Respuesta {


    private String codRespuesta;
    private String msjRespuesta;
    private Object datos;
    private boolean isExito;

    private String cod;
    private String sms;
    private String user;
    private String pwd;
    private String token;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public boolean getIsExito() {
        return codRespuesta.equals("00");
    }

    public String getCodRespuesta() {
        return codRespuesta;
    }

    public void setCodRespuesta(String codRespuesta) {
        this.codRespuesta = codRespuesta;
    }

    public String getMsjRespuesta() {
        return msjRespuesta;
    }

    public void setMsjRespuesta(String msjRespuesta) {
        this.msjRespuesta = msjRespuesta;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
}
