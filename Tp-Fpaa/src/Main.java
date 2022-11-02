import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    static int tamanhoConjunto = 3;

    // Gera conjunto aleatorio, recebe o tamanho
    private static int[] gerarConjunto(int tamanho){
        Random gerador = new Random();
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = gerador.nextInt(9) + 1;
        }
        return dados;
    }

    // Recebe um tamnho 'size' e retorna lista com todos os possiveis de subconjuntos de tamanho == size 
    private static List<List<Integer>> gerarSubconjuntosPorTamanho(int[] conjunto, int size) {
        List<List<Integer>> result = new ArrayList<>();
        if (size == 1) {
            for (int i = 0; i < conjunto.length; i++) {
                result.add(Arrays.asList(conjunto[i]));
            }
        } else {
            for (int i = 0; i < conjunto.length - size + 1; i++) {
                int[] head = Arrays.copyOfRange(conjunto, i, i + 1);
                int[] rest = Arrays.copyOfRange(conjunto, i + 1, conjunto.length);
                for (List<Integer> set : gerarSubconjuntosPorTamanho(rest, size - 1)) {
                    List<Integer> newSet = new ArrayList<>();
                    newSet.addAll(Arrays.asList(head[0]));
                    newSet.addAll(set);
                    result.add(newSet);
                }
            }
        }
        return result;
    }

    // Gera todos os possíveis subconjuntos de um conjunto
    public static List<List<Integer>> gerarTodosSubconjuntos(int[] conjunto){
        List<List<Integer>> subconjuntos = new ArrayList<>();
        for (int i = 1; i <= conjunto.length; i++) {
            subconjuntos.addAll(gerarSubconjuntosPorTamanho(conjunto, i));
        }
        return subconjuntos;
    }

    // Retorna os subconjuntos que somados resultam em value (parametro) 
    public static List<List<Integer>> forcaBruta(int[] conjunto, int value){
        return gerarTodosSubconjuntos(conjunto).stream()
                .filter(subconjunto -> subconjunto.stream().mapToInt(Integer::intValue).sum() == value)
                .collect(Collectors.toList());
    } 

    // Calcula o valur de V (enunciado)
    private static int calcularV(int[] conjunto){
        return Arrays.stream(conjunto).sum() / 2;
    }


    // Gera arquivo CSV com os resultados
    // TamanhoDoVetor;TempoMedio; 
    private static void mediaVetorToCsv(List<Double> list, int tamanhoInicial){
        try {
            FileWriter fileWriter = new FileWriter("resultados.csv");
            for (int i = 0; i < list.size(); i++) {
                String tamanhoVetor = Integer.toString(i+tamanhoInicial);
                String tempo = String.format("%.5f", list.get(i));
                fileWriter.write(tamanhoVetor +';' + tempo + "\n");
            }

            fileWriter.close();
            System.out.println("CSV gerado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        long mediaTempos = 0;
        int contador = 0;

        List<Double> mediaTempoConjuntos = new ArrayList<>(); 

        try {
            double somaTempoConjunto = 0;
            int tamanho = 2;
            while((somaTempoConjunto/150.0) <4000) {
                int[] conjunto = gerarConjunto(tamanho);
                int v = calcularV(conjunto);
                somaTempoConjunto = 0;
                for (int i = 0; i < 150; i++) {
                    long tempoInicial = System.currentTimeMillis();
                    List<List<Integer>> subconjuntos = forcaBruta(conjunto, v);
                    long tempoFinal = System.currentTimeMillis();
                    
                    long tempoExecucao = tempoFinal - tempoInicial;
                    mediaTempos+= tempoExecucao;
                    System.out.printf("%.3f s%n", (tempoExecucao) / 1000d);
                    somaTempoConjunto += tempoExecucao;
                    contador++;
                }
                System.out.printf("Media Conjunto de tamanho %d: %.3f ms%n \n", tamanho, (somaTempoConjunto / 150.0));
                
                mediaTempoConjuntos.add(somaTempoConjunto / 150.0);
                tamanho++;
            }    
        } catch (Exception e) {
            System.out.println(e.getMessage());        
        } finally{
            System.out.println("Media: " + mediaTempos / contador);
            mediaVetorToCsv(mediaTempoConjuntos, 2);
        }

    }
    
    


}