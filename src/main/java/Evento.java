public class Evento {
    private String NOMBRE;
    private int ID_EVENTO;
    private Double PRECIO_VENTA;
    private String FECHA;
    private String HORA;

    public String getNOMBRE() {
        return this.NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public int getID_EVENTO() {
        return this.ID_EVENTO;
    }

    public void setID_EVENTO(int ID_EVENTO) {
        this.ID_EVENTO = ID_EVENTO;
    }

    public Double getPRECIO_VENTA() {
        return this.PRECIO_VENTA;
    }

    public void setPRECIO_VENTA(Double PRECIO_VENTA) {
        this.PRECIO_VENTA = PRECIO_VENTA;
    }

    public String getFECHA() {
        return this.FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getHORA() {
        return this.HORA;
    }

    public void setHORA(String HORA) {
        this.HORA = HORA;
    }

}
