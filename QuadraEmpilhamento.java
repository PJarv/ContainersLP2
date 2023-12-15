import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class QuadraEmpilhamento implements Serializable, Interface {
    private static final long serialVersionUID = 1L;
    private Map<String, Stack<Container>> quadra;
    private Map<String, Integer> sequenciaIdentificadores;

    public QuadraEmpilhamento() {
        quadra = new HashMap<>();
        sequenciaIdentificadores = new HashMap<>();
        for (char ch = 'A'; ch <= 'J'; ch++) {
            quadra.put(String.valueOf(ch), new Stack<>());
            sequenciaIdentificadores.put(String.valueOf(ch), 0);
        }
    }
    

    public Map<String, Stack<Container>> obterPilhaCompleta() {
        return quadra;
    }
       

    public void empilhar(Container container, String posicao) {
        Stack<Container> pilha = quadra.get(posicao); 
        int sequencia = sequenciaIdentificadores.getOrDefault(posicao, 0) + 1;
        String idContainer = posicao + "." + sequencia; 
        
        if (pilha.size() < 5) { 
            pilha.push(container);
            sequenciaIdentificadores.put(posicao, sequencia);
            System.out.println("Contêiner empilhado com sucesso. ID: " + idContainer);
        } else {
            System.out.println("Posição de empilhamento cheia. Não é possível empilhar mais contêineres."); 
              }
    }

    public Container desempilhar(String idContainer) {
        String[] parts = idContainer.split("\\.");
        String posicao = parts[0];
    
        Stack<Container> pilha = quadra.get(posicao);
        Stack<Container> temporaryStack = new Stack<>();
        Container containerRemovido = null;
    
        while (!pilha.isEmpty()) {
            Container container = pilha.pop();
            if ((posicao + "." + (pilha.size() + 1)).equals(idContainer)) {
                containerRemovido = container;
            } else {
                temporaryStack.push(container);
            }
        }
    
        while (!temporaryStack.isEmpty()) {
            pilha.push(temporaryStack.pop());
        }
    
        return containerRemovido;
    }

    public String consultarTopo(String posicao) {
        Stack<Container> pilha = quadra.get(posicao);
        if (!pilha.isEmpty()) {
            Container topo = pilha.peek();
            return "ID: " + posicao + "." + pilha.size() + ", Proprietário: " + topo.getProprietario() +
                    ", Tipo de Carga: " + topo.getTipoCarga() + ", Peso: " + topo.getPesoCarga() +
                    ", Tipo de Operação: " + topo.getTipoOperacao();
        } else {
            return "Posição vazia";
        }
    }

    public int quantidadeConteineresPorTipoCarga(String tipoCarga) {
        int count = 0;
        for (Stack<Container> pilha : quadra.values()) {
            for (Container container : pilha) {
                if (container.getTipoCarga().equalsIgnoreCase(tipoCarga)) {
                    count++;
                }
            }
        }
        return count;
    }

    public double pesoTotalCargasPorTipo(String tipoCarga) {
        double pesoTotal = 0;
        for (Stack<Container> pilha : quadra.values()) {
            for (Container container : pilha) {
                if (container.getTipoCarga().equalsIgnoreCase(tipoCarga)) {
                    pesoTotal += container.getPesoCarga();
                }
            }
        }
        return pesoTotal;
    }

    public int quantidadeConteineresPorTipoOperacao(String tipoOperacao) {
        int count = 0;
        for (Stack<Container> pilha : quadra.values()) {
            for (Container container : pilha) {
                if (container.getTipoOperacao().equalsIgnoreCase(tipoOperacao)) {
                    count++;
                }
            }
        }
        return count;
    }

    public List<String> posicoesVazias() {
        List<String> posicoesVazias = new ArrayList<>();
        for (Map.Entry<String, Stack<Container>> entry : quadra.entrySet()) {
            if (entry.getValue().isEmpty()) {
                posicoesVazias.add(entry.getKey());
            }
        }
        return posicoesVazias;
    }

}
