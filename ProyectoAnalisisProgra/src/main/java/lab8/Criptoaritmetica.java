package lab8;

//voraz
//Falta todas las probabilidades posibles
/* 
Mynell Myers Hall
Complejidad: O(n!)
*/
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String p1 = scanner.nextLine();
        String p2 = scanner.nextLine();
        String p3 = scanner.nextLine();
        criptoAritm(p1,p2,p3);
        scanner.close();
    }

    public static void criptoAritm(String p1,String p2, String p3){
        List<Character> abced = IntStream.rangeClosed('A', 'Z').mapToObj(c -> (char) c).collect(Collectors.toList());
        p1=p1.toUpperCase();p2=p2.toUpperCase();p3=p3.toUpperCase();
        List<Character> letras=new ArrayList<>();
        List<Integer> valLetras=new ArrayList<>();
        Integer[] valores = new Integer[abced.size()];
        Arrays.fill(valores, 0);
        for(int i=0; i<p1.length();i++){
            int valor1= abced.indexOf(p1.charAt(i));
            int valor2= abced.indexOf(p2.charAt(i));
            int valor3= abced.indexOf(p3.charAt(i));
            if((valores[valor1]!=0||valores[valor2]!=0)&&valores[valor3]!=0){
                valores[valor1]= (valores[valor1]<1) ? encontrarValor(valores[valor1], valores[valor2], valores[valor3]) : valores[valor1];
                valores[valor2]= (valores[valor2]<1) ? encontrarValor(valores[valor1], valores[valor2], valores[valor3]) : valores[valor2];
                if((valores[valor1]!=0&&Arrays.stream(valores).filter(numero -> numero == valores[valor1]).count()>1)||(valores[valor2]!=0&&Arrays.stream(valores).filter(numero -> numero == valores[valor2]).count()>1)||valores[valor1]>10||valores[valor1]>10){System.out.println("No es Posible");return;}
            }else{
                for(int j=1;j<10;j++){
                    int c=j;
                    if(Arrays.stream(valores).filter(numero -> numero == c).count()<1){
                        valores[valor1]=(valores[valor1]==0)? j:valores[valor1];
                        valores[valor2]= (valores[valor2]<0)? j : valores[valor2];
                        valores[valor2]= (valores[valor2]==0)? encontrarValor(valores[valor1], valores[valor2], valores[valor3]):valores[valor2];
                        if(valores[valor2]>0){break;}else{j--;}
                    }
                }
            }
            valores[valor3]= (valores[valor3]==0) ? encontrarValor(valores[valor1], valores[valor2], valores[valor3]) : valores[valor3];
            if((p1.charAt(i)==p3.charAt(i)||p2.charAt(i)==p3.charAt(i))||valores[valor3]>10||(valores[valor3]!=0&&Arrays.stream(valores).filter(numero -> numero == valores[valor3]).count()>1)){
                System.out.println("No es Posible");
                return;
            }
            if (!letras.contains(p1.charAt(i))) {letras.add(p1.charAt(i));valLetras.add(valores[valor1]);}
            if (!letras.contains(p2.charAt(i))) {letras.add(p2.charAt(i));valLetras.add(valores[valor2]);}
        }
        List<List<Integer>> permutaciones = new ArrayList<>();
        permutar(valLetras,letras,p1,p2, 0, permutaciones);
        for(List<Integer> valor:permutaciones){
            for(int j=1;j<5;j++){
                List<Integer> copia= new ArrayList<>(valor);
                int c=j;
                copia=copia.stream().map(elemento -> elemento * c).collect(Collectors.toList());
                if(copia.stream().anyMatch(numero -> numero > 9)){break;}
                for(int i=0; i<p1.length();i++){
                    System.out.print(copia.get(letras.indexOf(p1.charAt(i))));
                }System.out.println();
                for(int i=0; i<p2.length();i++){
                    System.out.print(copia.get(letras.indexOf(p2.charAt(i))));
                }System.out.println();
                for(int i=0; i<p3.length();i++){
                    System.out.print(copia.get(letras.indexOf(p1.charAt(i)))+copia.get(letras.indexOf(p2.charAt(i))));
                }System.out.println();
                System.out.println();
            }
        }
    }
    public static int encontrarValor(int p1, int p2, int p3) {
        if (p1 == 0) {return p3 - p2;
        }if (p2 == 0) {return p3 - p1;
        }if (p3 == 0) {return p1 + p2;
        }return -1;
    }
    public static void permutar(List<Integer> elementos,List<Character> letras,String p1,String p2, int indice, List<List<Integer>> permutaciones) {
        if (indice == elementos.size() - 1 ) {
            boolean con=true;
            for(int i=0; i<p1.length();i++){
                int valor1= letras.indexOf(p1.charAt(i));
                int valor2= letras.indexOf(p2.charAt(i));
                if(encontrarValor(elementos.get(valor1),elementos.get(valor2),0)>9){con=false;break;}
            }
            if(con){permutaciones.add(new ArrayList<>(elementos));
            return;}
        }
        for (int i = indice; i < elementos.size(); i++) {
            Collections.swap(elementos, indice, i);
            permutar(elementos,letras,p1,p2, indice + 1, permutaciones);
            Collections.swap(elementos, indice, i);
        }
    }
}
