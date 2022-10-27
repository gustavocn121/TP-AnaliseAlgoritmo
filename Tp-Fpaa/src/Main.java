import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    static int seed = 1;
    static final int TAMANHO_CONJUNTO = 10;

    private static int[] geraSubConjunto(int[] conjunto, int inicio, int tamanho){
        int[] subconjunto = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            subconjunto[i] = conjunto[inicio + i];
        }


        return subconjunto;
    }

    private static int[] forcaBruta(int[] conjunto, int valor){
        int[] subconjunto = null;

        for (int i=0; i< conjunto.length; i++) { // inicio
            for (int j = 1; j < conjunto.length - i; j++) { // tam conjunto
                int[] s = geraSubConjunto(conjunto, i, j);
                System.out.println("-------------------------");
                Arrays.stream(s).forEach(System.out::println);
                System.out.println(s);

                System.out.println("-------------------------");

                if (Arrays.stream(s).sum() == valor){
                    subconjunto = s;
                }


            }

        }

        return subconjunto;
    }


    private static int[] geraConjunto(int tamanho){
        Random gerador = new Random(seed);
        seed +=1;
        int[] dados = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = gerador.nextInt(9) + 1;
        }
        return dados;
    }


    public static void main(String[] args) throws Exception {
//        int[] conjunto = geraConjunto(TAMANHO_CONJUNTO);
        int[] conjunto = {1, 2, 3};
        int v = 10;
        int[] subconjunto = forcaBruta(conjunto, v);
        if (subconjunto == null){

        }

    }
}