import java.util.List;

interface Interface {

    void empilhar(Container a, String b);

    Container desempilhar(String a);

    String consultarTopo(String a);

    int quantidadeConteineresPorTipoCarga(String a);

    double pesoTotalCargasPorTipo(String a);

    int quantidadeConteineresPorTipoOperacao(String a);

    List<String> posicoesVazias();

}
