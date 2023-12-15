import java.io.Serializable;

public class Container implements Serializable {
    private static final long serialVersionUID = 1L;
    private String proprietario;
    private String tipoCarga;
    private double pesoCarga;
    private String tipoOperacao;

    public Container(String proprietario, String tipoCarga, double pesoCarga, String tipoOperacao) {
        this.proprietario = proprietario;
        this.tipoCarga = tipoCarga;
        this.pesoCarga = pesoCarga;
        this.tipoOperacao = tipoOperacao;
    }

    public String toString() {

        return this.getProprietario() + " " +
               this.getTipoCarga() + " " +
               this.getPesoCarga() + " " +
               this.getTipoOperacao();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public double getPesoCarga() {
        return pesoCarga;
    }

    public void setPesoCarga(double pesoCarga) {
        this.pesoCarga = pesoCarga;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

}
