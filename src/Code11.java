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

            if (caracter == '*' && i == s.length() - 1) {
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
        s = s.trim();

        List<Integer> grossors = calcularGrossors(s);

        if (grossors.size() == 0) {
            return null;
        }

        int major = Collections.max(grossors);
        int menor = Collections.min(grossors);

        if (menor * 15 < major) {
            return null;
        }

        while (major >= 1) {
            String resultat = provarGrossors(grossors, major);
            if (resultat != null) {
                return resultat;
            } else {
                major--;
            }
        }

        return null;
    }

    private static List<Integer> calcularGrossors(String s) {
        List<Integer> llistaDeGrossors = new ArrayList<>();

        for (int i = 0; i < s.length(); ) {
            char caracter = s.charAt(i);
            char actual = caracter;
            int contador = 0;

            if (i == s.length() - 1) {
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
            llistaDeGrossors.add(contador);
        }

        return llistaDeGrossors;
    }

    private static String provarGrossors(List<Integer> grossors, int major) {
        String codi = "";
        String resultat = "";

        for (int i = 0; i < grossors.size(); i++) {
            if (grossors.get(i) >= major) {
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

        if (resultat.length() == 0) {
            return null;
        }

        if (resultat.charAt(0) != '*') {
            return null;
        }

        if (resultat.charAt(resultat.length() - 1) != '*') {
            return null;
        }

        return resultat;
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
        String[] separarStringEnLinies = str.split("\n");
        List<String> llista = new ArrayList<>(List.of(separarStringEnLinies));

        if (llista.get(1).charAt(0) != '#') {
            llista.add(1, "#");
        }

        String[] ar = llista.toArray(new String[0]);

        int dimensioVertical = treureDimensioVertical(ar);
        int dimensioHoritzontal = treureDimensioHoritzontal(ar);

        String resultat = escanerHoritzontal(ar, dimensioVertical);
        if (resultat != null) {
            return resultat;
        }

        resultat = escanerVertical(ar, dimensioHoritzontal);
        if (resultat != null) {
            return resultat;
        }

        return null;
    }

    private static int treureDimensioVertical(String[] ar) {
        String dimensioVertical = ar[2];
        String[] arDimensioVertical = dimensioVertical.split(" ");

        return Integer.parseInt(arDimensioVertical[1]);
    }

    private static int treureDimensioHoritzontal(String[] ar) {
        String dimensioHoritzontal = ar[2];
        String[] arDimensioHoritzontal = dimensioHoritzontal.split(" ");

        return Integer.parseInt(arDimensioHoritzontal[0]);
    }

    private static String escanerHoritzontal(String[] ar, int dimensioVertical) {
        String resultat = "";
        int nLinia = 0;

        for (int i = 0; i < dimensioVertical / 10; i++) {
            for (int limitColor = 0; limitColor < 255; limitColor += 10) {
                String codi = obtenirLiniaHoritzontalDePixels(ar, nLinia, limitColor);
                resultat = decode(codi);

                if (resultat != null) {
                    return resultat;
                } else {
                    codi = invertirCodi(codi);
                    resultat = decode(codi);
                    if (resultat != null) {
                        return resultat;
                    }
                }
            }
            nLinia += 10;
        }

        return null;
    }

    private static String obtenirLiniaHoritzontalDePixels(String[] ar, int nLinea, int limitColor) {
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

            if (esBlanc(a, b, c, limitColor)) {
                resultat += " ";
            } else {
                resultat += "█";
            }
        }

        return resultat;
    }

    private static String escanerVertical(String[] ar, int dimensioHoritzontal) {
        String resultat = "";
        int nLinia = 0;

        for (int i = 0; i < dimensioHoritzontal; i++) {
            for (int limitColor = 0; limitColor < 255; limitColor += 10) {
                String codi = obtenirLiniaVerticalDePixels(ar, nLinia, limitColor, dimensioHoritzontal);
                resultat = decode(codi);

                if (resultat != null) {
                    return resultat;
                } else {
                    codi = invertirCodi(codi);
                    resultat = decode(codi);
                    if (resultat != null) {
                        return resultat;
                    }
                }
            }
            nLinia += 10;
        }

        return null;
    }

    private static String obtenirLiniaVerticalDePixels(String[] ar, int nLinia, int limitColor, int dimensioHoritzontal) {
        int dimensioVertical = treureDimensioVertical(ar);
        int llegirLiniaN = 3 * nLinia;
        int index = llegirLiniaN + 4;
        String resultat = "";

        for (int i = 0; i < dimensioVertical; i++) {
            int a = Integer.parseInt(ar[index]);
            index++;
            int b = Integer.parseInt(ar[index]);
            index++;
            int c = Integer.parseInt(ar[index]);
            index++;

            if (esBlanc(a, b, c, limitColor)) {
                resultat += " ";
            } else {
                resultat += "█";
            }

            index = dimensioHoritzontal * 3 + index - 3;
        }

        return resultat;
    }

    private static boolean esBlanc(int a, int b, int c, int limitColor) {
        int mitjana = (a + b + c) / 3;

        if (mitjana > limitColor) {
            return true;
        }

        return false;
    }

    private static String invertirCodi(String codi) {
        StringBuilder codiInverit = new StringBuilder(codi);
        codi = codiInverit.reverse().toString();

        return codi;
    }


    // Genera imatge a partir de codi de barres:
    // Alçada: 100px
    // Marges: vertical 4px, horizontal 8px
    public static String generateImage(String s) {
        String codiDeBarres = crearCodiDeBarres(s);

        String fila = "        " + codiDeBarres + "        ";

        int ample = fila.length();

        String filaBuida = crearMargesDeLaImatge(ample);

        String resultat = crearImatge(fila, ample, filaBuida);

        return resultat.trim();
    }

    static String crearCodiDeBarres(String s) {
        String resultat = "";

        for (int i = 0; i < s.length(); i++) {
            resultat += dibuixarCodiBarres(s.charAt(i));
            resultat += "   ";
        }

        return resultat.trim();
    }

    static String dibuixarCodiBarres(char caracter) {
        if (caracter == '*') {
            return "███   ██████████          ███";
        }

        if (caracter == '0') {
            return "███   ███   ██████████";
        }

        if (caracter == '1') {
            return "██████████   ███   ██████████";
        }

        if (caracter == '2') {
            return "███          ███   ██████████";
        }

        if (caracter == '3') {
            return "██████████          ███   ███";
        }

        if (caracter == '4') {
            return "███   ██████████   ██████████";
        }

        if (caracter == '5') {
            return "██████████   ██████████   ███";
        }

        if (caracter == '6') {
            return "███          ██████████   ███";
        }

        if (caracter == '7') {
            return "███   ███          ██████████";
        }

        if (caracter == '8') {
            return "██████████   ███          ███";
        }

        if (caracter == '9') {
            return "██████████   ███   ███";
        }

        if (caracter == '-') {
            return "███   ██████████   ███";
        }

        return null;
    }

    static String crearMargesDeLaImatge(int ample) {
        String resultat = "";

        for (int i = 0; i < ample; i++) {
            resultat += "255\n255\n255\n";
        }

        for (int i = 0; i < 2; i++) {
            resultat += resultat;
        }

        return resultat;
    }

    static String crearImatge(String fila, int ample, String filaBuida) {
        String resultat = "P3\n" + ample + " 108\n" + "255\n";

        resultat += filaBuida;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < fila.length(); j++) {
                if (fila.charAt(j) == ' ') {
                    resultat += "255\n255\n255\n";
                } else {
                    resultat += "0\n0\n0\n";
                }
            }
        }

        resultat += filaBuida;

        return resultat;
    }
}