package clases;

public class r_compras {
	private int ID_COMPRA;
    private double PRECIO_TOTAL;
    private String DNI_USUARIO;
    private String NOMBRE_EVENTO;
    private int ID_EVENTO;
    private int N_ENTRADAS;
    private String FECHA_EVENTO;
    private String HORA_EVENTO;

    public int getID_COMPRA() {
        return this.ID_COMPRA;
    }

    public void setID_COMPRA(int ID_COMPRA) {
        this.ID_COMPRA = ID_COMPRA;
    }

    public double getPRECIO_TOTAL() {
        return this.PRECIO_TOTAL;
    }

    public void setPRECIO_TOTAL(double PRECIO_TOTAL) {
        this.PRECIO_TOTAL = PRECIO_TOTAL;
    }

    public String getDNI_USUARIO() {
        return this.DNI_USUARIO;
    }

    public void setDNI_USUARIO(String DNI_USUARIO) {
        this.DNI_USUARIO = DNI_USUARIO;
    }

    public String getNOMBRE_EVENTO() {
        return this.NOMBRE_EVENTO;
    }

    public void setNOMBRE_EVENTO(String NOMBRE_EVENTO) {
        this.NOMBRE_EVENTO = NOMBRE_EVENTO;
    }

    public int getID_EVENTO() {
        return this.ID_EVENTO;
    }

    public void setID_EVENTO(int ID_EVENTO) {
        this.ID_EVENTO = ID_EVENTO;
    }

    public int getN_ENTRADAS() {
        return this.N_ENTRADAS;
    }

    public void setN_ENTRADAS(int N_ENTRADAS) {
        this.N_ENTRADAS = N_ENTRADAS;
    }

    public String getFECHA_EVENTO() {
        return this.FECHA_EVENTO;
    }

    public void setFECHA_EVENTO(String FECHA_EVENTO) {
        this.FECHA_EVENTO = FECHA_EVENTO;
    }

    public String getHORA_EVENTO() {
        return this.HORA_EVENTO;
    }

    public void setHORA_EVENTO(String HORA_EVENTO) {
        this.HORA_EVENTO = HORA_EVENTO;
    }
}
