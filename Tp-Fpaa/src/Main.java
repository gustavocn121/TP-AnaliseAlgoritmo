import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    static int seed = 1;
    static final int TAMANHO_CONJUNTO = 3;

    private static int[] gerarConjunto(int tamanho){
        Random gerador = new Random(seed);
        seed +=1;
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = gerador.nextInt(9) + 1;
        }
        return dados;
    }

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

    public static List<List<Integer>> gerarTodosSubconjuntos(int[] conjunto){
        List<List<Integer>> subconjuntos = new ArrayList<>();
        for (int i = 1; i <= conjunto.length; i++) {
            subconjuntos.addAll(gerarSubconjuntosPorTamanho(conjunto, i));
        }
        return subconjuntos;
    }

    public static List<List<Integer>> forcaBruta(int[] subconjuntos, int value){
        return gerarTodosSubconjuntos(subconjuntos).stream()
                .filter(subconjunto -> subconjunto.stream().mapToInt(Integer::intValue).sum() == value)
                .collect(Collectors.toList());
    } 

    public static void main(String[] args) throws Exception {
        int[] conjunto = gerarConjunto(TAMANHO_CONJUNTO);
        int[] conjunto2 = {1,2,3};
        int v = 10;
        List<List<Integer>> subconjuntos = forcaBruta(conjunto, v);

    }

    
}