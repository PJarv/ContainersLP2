import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;


public class Main {
    public static void main(String[] args) {
        QuadraEmpilhamento quadra = carregarQuadra(); 

        Scanner scanner = new Scanner(System.in);
        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    empilharConteiner(quadra, scanner);
                    break;
                case 2:
                    desempilharConteiner(quadra, scanner);
                    break;
                case 3:
                    consultarTopo(quadra, scanner);
                    break;
                case 4:
                    consultarQuantidadePorTipoCarga(quadra, scanner);
                    break;
                case 5:
                    consultarPesoPorTipoCarga(quadra, scanner);
                    break;
                case 6:
                    consultarQuantidadePorTipoOperacao(quadra, scanner);
                    break;
                case 7:
                    consultarPosicoesVazias(quadra);
                    break;
                case 8:
                    salvarQuadra(quadra);
                    break;
                case 9:
                    System.out.println("Encerrando...");
                    salvarQuadra(quadra);
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\nEscolha a operação:");
        System.out.println("1 - Empilhar contêiner");
        System.out.println("2 - Desempilhar contêiner");
        System.out.println("3 - Consultar topo da posição");
        System.out.println("4 - Quantidade de contêineres por tipo de carga");
        System.out.println("5 - Peso total de cargas por tipo de carga");
        System.out.println("6 - Quantidade de contêineres por tipo de operação");
        System.out.println("7 - Posições de empilhamento vazias");
        System.out.println("8 - Salvar dados da quadra");
        System.out.println("9 - Sair");
        System.out.println("Escolha a opção: ");
    }

    private static void empilharConteiner(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o nome do proprietário:");
        String proprietario = scanner.nextLine();
        System.out.println("Informe o tipo de carga:");
        String tipoCarga = scanner.nextLine();
        System.out.println("Informe o peso da carga:");
        double pesoCarga = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.println("Informe o tipo de operação (embarque/desembarque):");
        String tipoOperacao = scanner.nextLine();
        System.out.println("Informe a posição de empilhamento (A-J):");
        String posicao = scanner.nextLine().toUpperCase();

        Container container = new Container(proprietario, tipoCarga, pesoCarga, tipoOperacao);
        quadra.empilhar(container, posicao);
    }

    private static void desempilharConteiner(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o ID do contêiner a ser desempilhado:");
        String idContainer = scanner.nextLine().toUpperCase();
        Container containerRemovido = quadra.desempilhar(idContainer);
        
        if (containerRemovido != null) {
            System.out.println("Contêiner removido: " + containerRemovido);
            removerDoArquivo(idContainer);
        } else {
            System.out.println("Contêiner não encontrado ou posição vazia.");
        }
    }
    
    private static void removerDoArquivo(String idContainer) {
        File file = new File("quadras.txt");
        File tempFile = new File("tempQuadras.txt");
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
            String lineToRemove = idContainer;
            String currentLine;
    
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.trim().startsWith(lineToRemove)) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao manipular arquivo: " + e.getMessage());
        }
    
        if (!file.delete()) {
            System.out.println("Não foi possível remover o arquivo original.");
            return;
        }
    
        if (!tempFile.renameTo(file)) {
            System.out.println("Não foi possível renomear o arquivo temporário.");
        }
    }

    private static void consultarTopo(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe a posição para consultar o topo:");
        String posConsulta = scanner.nextLine().toUpperCase();
        System.out.println("Informações do topo da posição " + posConsulta + ":");
        System.out.println(quadra.consultarTopo(posConsulta));
    }

    private static void consultarQuantidadePorTipoCarga(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o tipo de carga para consultar a quantidade:");
        String tipoConsulta = scanner.nextLine();
        int quantidade = quadra.quantidadeConteineresPorTipoCarga(tipoConsulta);
        System.out.println("Quantidade de contêineres do tipo " + tipoConsulta + ": " + quantidade);
    }

    private static void consultarPesoPorTipoCarga(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o tipo de carga para consultar o peso total:");
        String tipoConsulta = scanner.nextLine();
        double pesoTotal = quadra.pesoTotalCargasPorTipo(tipoConsulta);
        System.out.println("Peso total das cargas do tipo " + tipoConsulta + ": " + pesoTotal);
    }

    private static void consultarQuantidadePorTipoOperacao(QuadraEmpilhamento quadra, Scanner scanner) {
        System.out.println("Informe o tipo de operação para consultar a quantidade:");
        String tipoConsulta = scanner.nextLine();
        int quantidade = quadra.quantidadeConteineresPorTipoOperacao(tipoConsulta);
        System.out.println("Quantidade de contêineres com operação " + tipoConsulta + ": " + quantidade);
    }

    private static void consultarPosicoesVazias(QuadraEmpilhamento quadra) {
        List<String> posicoesVazias = quadra.posicoesVazias();
        if (posicoesVazias.isEmpty()) {
            System.out.println("Todas as posições estão ocupadas.");
        } else {
            System.out.println("Posições de empilhamento vazias:");
            for (String posicao : posicoesVazias) {
                System.out.println(posicao);
            }
        }
    }

    public static void salvarQuadra(QuadraEmpilhamento quadra) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("quadras.txt"))) {
            Map<String, Stack<Container>> pilhaCompleta = quadra.obterPilhaCompleta(); 
    
        
            for (Map.Entry<String, Stack<Container>> entry : pilhaCompleta.entrySet()) {
                String posicao = entry.getKey();
                Stack<Container> pilha = entry.getValue();
    
                writer.println("Posição: " + posicao);
    
           
                for (Container container : pilha) {
                    writer.println(container.toString()); 
                }
                writer.println("---------");
            }
    
            System.out.println("Dados da quadra salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados da quadra: " + e.getMessage());
        }
    }

    private static QuadraEmpilhamento carregarQuadra() {
        QuadraEmpilhamento quadra = new QuadraEmpilhamento();
    
        try (BufferedReader reader = new BufferedReader(new FileReader("quadras.txt"))) {
            String line;
            String posicao = null;
    
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Posição: ")) {
    
                    posicao = line.substring(9); 
                } else if (line.equals("---------")) {
             
                } else {
   
                    if (posicao != null) {
                        Container container = interpretarLinhaComoContainer(line);
                        if (container != null) {
                            quadra.empilhar(container, posicao);
                        }
                    }
                }
            }
    
            System.out.println("Dados da quadra carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Não foi possível carregar os dados da quadra: " + e.getMessage());
        }
    
        return quadra;
    }

    private static Container interpretarLinhaComoContainer(String line) {
        if (!line.startsWith("Posição: ") && !line.equals("---------")) {
            String[] dados = line.split("\\s+");
    
            if (dados.length == 4) {
                String proprietario = dados[0].trim();
                String tipoCarga = dados[1].trim();
                double peso = Double.parseDouble(dados[2].trim());
                String tipoOperacao = dados[3].trim();
    
                return new Container(proprietario, tipoCarga, peso, tipoOperacao);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}