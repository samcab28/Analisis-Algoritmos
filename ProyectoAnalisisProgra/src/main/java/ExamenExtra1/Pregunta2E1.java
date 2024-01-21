package ExamenExtra1;

import java.util.ArrayList;

public class Pregunta2E1 {

    public static void main(String[] args) {
        ArrayList<Integer> T = new ArrayList<>();
        T.add(1);
        T.add(2);
        T.add(3);
        T.add(4);
        T.add(5);
        int k = 2;

        System.out.println(funcion(T,k));
    }

    public static ArrayList<Integer> funcion (ArrayList<Integer> T,int k){
        ArrayList<Integer> respuesta = new ArrayList<>();
        int contador = 0;
        for(int i = 0; i<T.size();i++ ){
            if(i<2){
                respuesta.add(T.get(i));
            }
            else{
                respuesta.add(0 + contador,T.get(i));
                contador++;
            }
        }
        return respuesta;
    }
}


