package ExamenExtra2;

import java.util.ArrayList;

public class Pregunta5E2 {

    public static boolean puntoEnArea(Punto m, ArrayList<Punto> puntos) {
        int intersectCount = 0;
        for (int i = 0; i < puntos.size(); i++) {
            Punto punto1 = puntos.get(i);
            Punto punto2 = puntos.get((i + 1) % puntos.size());

            if (interseccionHorizontal(m, punto1, punto2)) {
                intersectCount++;
            }
        }
        return intersectCount % 2 != 0;
    }

    private static boolean interseccionHorizontal(Punto m, Punto punto1, Punto punto2) {
        if ((punto1.y <= m.y && punto2.y > m.y) || (punto2.y <= m.y && punto1.y > m.y)) {
            double interseccionX = (punto1.x + (m.y - punto1.y) / (punto2.y - punto1.y) * (punto2.x - punto1.x));
            return m.x < interseccionX;
        }
        return false;
    }

    public static void main(String[] args) {
        //puntos del primer poligono
        ArrayList<Punto> poligono1 = new ArrayList<>();
        poligono1.add(new Punto(0, 0));
        poligono1.add(new Punto(0, 5));
        poligono1.add(new Punto(5, 5));
        poligono1.add(new Punto(5, 0));

        //puntos del segundo poligono
        ArrayList<Punto> poligono2 = new ArrayList<>();
        poligono2.add(new Punto(0, 0));
        poligono2.add(new Punto(0, 4));
        poligono2.add(new Punto(3, 4));
        poligono2.add(new Punto(3, 0));


        Punto punto = new Punto(2, 1);

        boolean resultado1 = puntoEnArea(punto,poligono1);
        boolean resultado2 = puntoEnArea(punto, poligono2);


        if(resultado1 && resultado2){
            System.out.println("el punto esta dentro de los dos poligonos");
        }
        else{
            System.out.println("el punto NO esta dentro de los dos poligonos");
        }
    }
}

class Punto {
    int x, y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
