// Consultar taula https://en.wikipedia.org/wiki/Barcode#Linear_barcodes
// Code11: https://en.wikipedia.org/wiki/Code_11

// Generadors de codis:
//     https://barcode.tec-it.com/en/Code11
//     https://www.free-barcode-generator.net/code-11/
//     https://products.aspose.app/barcode/generate

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Code11 {

    // Codifica un String amb Code11
    static String encode(String s) {
        String resultat = "";
        for (int i = 0; i < s.length(); i++) {
            char caracter = s.charAt(i);

            if (caracter == '*' && i == s.length()-1) {
                resultat += "█ ██  █";
                continue;
            }

            if (caracter == '*') {
                resultat += "█ ██  █ ";
                continue;
            }

            if (caracter == '0') {
                resultat += "█ █ ██ ";
                continue;
            }

            if (caracter == '1') {
                resultat += "██ █ ██ ";
                continue;
            }

            if (caracter == '2') {
                resultat += "█  █ ██ ";
                continue;
            }

            if (caracter == '3') {
                resultat += "██  █ █ ";
                continue;
            }

            if (caracter == '4') {
                resultat += "█ ██ ██ ";
                continue;
            }

            if (caracter == '5') {
                resultat += "██ ██ █ ";
                continue;
            }

            if (caracter == '6') {
                resultat += "█  ██ █ ";
                continue;
            }

            if (caracter == '7') {
                resultat += "█ █  ██ ";
                continue;
            }

            if (caracter == '8') {
                resultat += "██ █  █ ";
                continue;
            }

            if (caracter == '9') {
                resultat += "██ █ █ ";
                continue;
            }

            if (caracter == '-') {
                resultat += "█ ██ █ ";
            }
        }
        return resultat;
    }

    // Descodifica amb Code11
    static String decode(String s) {
        // Quitar espacios
        s = s.trim();

        List<Integer> grossors = calculGrossors(s);
        System.out.println(grossors);

        int major = Collections.max(grossors);
        int menor = Collections.min(grossors);

        if (menor * 15 < major) {
            return null;
        }

        while (major >= 1) {
            String resultat = provarGrosores(grossors, major);
            if (resultat != null) {
                return resultat;
            }
            major--;
        }

        return null;
    }

    private static String provarGrosores(List<Integer> grossors, int major) {
        int limit = major;
        String codi = "";
        String resultat = "";
        for (int i = 0; i < grossors.size(); i++) {
            if (grossors.get(i) >= limit) {
                codi += "1";
            } else {
                codi += "0";
            }

            if (codi.length() == 5) {
                codi = codificarCaracter(codi);
                if (codi == null) {
                    return null;
                }
                resultat += codi;
                codi = "";
                i++;
            }
        }

        if (resultat.charAt(0) != '*') {
            return null;
        }

        if (resultat.charAt(resultat.length()-1) != '*') {
            return null;
        }

        return resultat;

    }

    private static List<Integer> calculGrossors(String s) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length();) {
            char caracter = s.charAt(i);
            int contador = 0;
            char actual = caracter;
            if (i == s.length()-1) {
                contador = 1;
                i++;
            } else {
                i++;
                while (caracter == actual && i < s.length()) {
                    actual = s.charAt(i);
                    i++;
                    contador++;
                }
                i--;
            }
            list.add(contador);
        }
        return list;
    }

    private static String codificarCaracter(String codi) {
        if (codi.equals("00110")) {
            return "*";
        }

        if (codi.equals("00001")) {
            return "0";
        }

        if (codi.equals("10001")) {
            return "1";
        }

        if (codi.equals("01001")) {
            return "2";
        }

        if (codi.equals("11000")) {
            return "3";
        }

        if (codi.equals("00101")) {
            return "4";
        }

        if (codi.equals("10100")) {
            return "5";
        }

        if (codi.equals("01100")) {
            return "6";
        }

        if (codi.equals("00011")) {
            return "7";
        }

        if (codi.equals("10010")) {
            return "8";
        }

        if (codi.equals("10000")) {
            return "9";
        }

        if (codi.equals("00100")) {
            return "-";
        }

        return null;
    }

    // Descodifica una imatge. La imatge ha d'estar en format "ppm".
    public static String decodeImage(String str) {
        /*Hacer un array y guardar el String que nos pasan. (Separar por líneas)
          Obtener primera línea de pixeles.
          LLamar a decode.*/

        String[] ar = str.split("\n");
        int dimensioVertical = treureDimensioVertical(ar);
        int nLinia = 0;
        String resultat = "";
        for (int i = 0; i < dimensioVertical; i++) {
            String codi = obtenirLiniaPixelsN(ar, nLinia);
            resultat = decode(codi);
            if (resultat == null) {
                nLinia++;
            }
        }
        return resultat;
    }

    private static String obtenirLiniaPixelsN(String[] ar, int nLinea) {
        int dimensioHoritzontal = treureDimensioHoritzontal(ar);
        int llegirLiniaN = dimensioHoritzontal * 3 * nLinea;
        int index = llegirLiniaN + 4;
        String resultat = "";
        for (int i = 0; i < dimensioHoritzontal; i++) {
            int a = Integer.parseInt(ar[index]);
            index++;
            int b = Integer.parseInt(ar[index]);
            index++;
            int c = Integer.parseInt(ar[index]);
            index++;

            if (esBlanc(a,b,c)) {
                resultat += " ";
            } else {
                resultat += "█";
            }
        }
        return resultat;
    }


    //private static String obtenir1LiniaPixels(String[] ar) {
        /*Obtener la dimensión horizontal.
          Leer tres líneas para un pixel:
            Si los números son menores a 100 el color será blanco.
            Si los numeros son mayores a 100 el color será negro.*/

        /*int dimensioHoritzontal = treureDimensioHoritzontal(ar);
        int index = 4;
        String resultat = "";
        for (int i = 0; i < dimensioHoritzontal; i++) {
            int a = Integer.parseInt(ar[index]);
            index++;
            int b = Integer.parseInt(ar[index]);
            index++;
            int c = Integer.parseInt(ar[index]);
            index++;

            if (esBlanc(a,b,c)) {
                resultat += " ";
            } else {
                resultat += "█";
            }
        }
        //System.out.println(resultat);
        return resultat;
    }*/

    private static boolean esBlanc(int a, int b, int c) {
        if (a > 150 && b > 150 && c > 150) {
            return true;
        }
        return false;
    }

    private static int treureDimensioHoritzontal(String[] ar) {
        String dimensio = ar[2];
        String[] arDimensio = dimensio.split(" ");
        return Integer.parseInt(arDimensio[0]);
    }

    private static int treureDimensioVertical(String[] ar) {
        String dimensio = ar[2];
        String[] arDimensio = dimensio.split(" ");
        return Integer.parseInt(arDimensio[1]);
    }

    // Genera imatge a partir de codi de barres:
    // Alçada: 100px
    // Marges: vertical 4px, horizontal 8px
    public static String generateImage(String s) {
        return "";
    }
}
