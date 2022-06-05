// https://en.wikipedia.org/wiki/Code_93

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Code93 {
    static String caracters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%∦∬⊘⊕*";

    static String[] grossors = new String[]{
            "131112", "111213", "111312", "111411", "121113", "121212", "121311", "111114", "131211", "141111", "211113", "211212",
            "211311", "221112", "221211", "231111", "112113", "112212", "112311", "122112", "132111", "111123", "111222", "111321",
            "121122", "131121", "212112", "212211", "211122", "211221", "221121", "222111", "112122", "112221", "122121", "123111",
            "121131", "311112", "311211", "321111", "112131", "113121", "211131", "121221", "312111", "311121", "122211", "111141"
    };

    static String encodeExtended(char c) {
        if (c == 'a') return "⊕A"; if (c == 'b') return "⊕B"; if (c == 'c') return "⊕C"; if (c == 'd') return "⊕D";
        if (c == 'e') return "⊕E"; if (c == 'f') return "⊕F"; if (c == 'g') return "⊕G"; if (c == 'h') return "⊕H";
        if (c == 'i') return "⊕I"; if (c == 'j') return "⊕J"; if (c == 'k') return "⊕K"; if (c == 'l') return "⊕L";
        if (c == 'm') return "⊕M"; if (c == 'n') return "⊕N"; if (c == 'o') return "⊕O"; if (c == 'p') return "⊕P";
        if (c == 'q') return "⊕Q"; if (c == 'r') return "⊕R"; if (c == 's') return "⊕S"; if (c == 't') return "⊕T";
        if (c == 'u') return "⊕U"; if (c == 'v') return "⊕V"; if (c == 'w') return "⊕W"; if (c == 'x') return "⊕X";
        if (c == 'y') return "⊕Y"; if (c == 'z') return "⊕Z"; if (c == ',') return "∦L"; if (c == '*') return "∦J";
        return null;
    }

    // Codifica emprant Code93
    static String encode(String str) {
        char ck1 = calcularCK1(str);
        char ck2 = calcularCK2(str + ck1);

        str = "*" + str + ck1 + ck2 + "*";

        String resultat = "";

        for (int i = 0; i < str.length(); i++) {
            char caracter = str.charAt(i);
            int index = cercarELIndexDelCaracter(caracter, caracters);
            String g = grossors[index];
            resultat += dibuixarGrossors(g);
        }

        return resultat + "█";
    }

    private static char calcularCK1(String str) {
        StringBuilder strInvertit = new StringBuilder(str);
        str = strInvertit.reverse().toString();
        int resultat = 0;
        int contador = 1;
        for (int i = 0; i < str.length(); i++) {
            char caracter = str.charAt(i);
            if (contador > 20) {
                contador = 1;
            }
            resultat += ((contador) * cercarELIndexDelCaracter(caracter, caracters));
            contador++;
        }
        resultat = resultat % 47;
        // passar de l'índex al caracter, cercar el caracter donant l'índex?
        return caracters.charAt(resultat);
    }

    private static char calcularCK2(String s) {
        StringBuilder strInvertit = new StringBuilder(s);
        s = strInvertit.reverse().toString();
        int resultat = 0;
        int contador = 1;
        for (int i = 0; i < s.length(); i++) {
            char caracter = s.charAt(i);
            if (contador > 15) {
                contador = 1;
            }
            resultat += ((contador) * cercarELIndexDelCaracter(caracter, caracters));
            contador++;
        }
        resultat = resultat % 47;
        // passar de l'índex al caracter, cercar el caracter donant l'índex?
        return caracters.charAt(resultat);
    }

    private static int cercarELIndexDelCaracter(char caracter, String string) {
        for (int i = 0; i < string.length(); i++) {
            if (caracter == string.charAt(i)) {
                return i;
            }
        }
        return -1;
    }

    private static String dibuixarGrossors(String g) {
        boolean linia = true;
        String resultat = "";
        for (int i = 0; i < g.length(); i++) {
            int gruix = g.charAt(i) - '0';
            if (linia) {
                for (int j = 0; j < gruix; j++) {
                    resultat += "█";
                }
            } else {
                for (int j = 0; j < gruix; j++) {
                    resultat += " ";
                }
            }
            linia = !linia;
        }
        return resultat + "";
    }


    // Descodifica emprant Code93
    static String decode(String str) {
        // Quitar espais
        str = str.trim();

        List<String> grossors = calcularGrossors(str);

        String ajustarGrossors = ajustarGrossors(grossors);

        String resultatTotal = agruparGrossors(ajustarGrossors);

        String resultat = crearResultatFinal(resultatTotal);

        return resultat;
    }

    static List<String> calcularGrossors(String str) {
        int posicio = 0;
        List<String> resultat = new ArrayList<>();
        while (posicio < str.length() - 1) {
            char c = str.charAt(posicio);
            int contador = 1;
            posicio++;
            while (str.charAt(posicio) == c) {
                contador++;
                if (posicio != str.length() - 1) {
                    posicio++;
                }
                else {
                    break;
                }
            }
            resultat.add(contador + "");
        }
        if (str.charAt(str.length() - 1) == '█' && str.charAt(str.length() - 2) == ' ') {
            resultat.add("1");
        }
        return resultat;
    }

    private static String ajustarGrossors(List<String> s) {
        int minim = tamanyBarra(s);
        String resultat = "";
        for (int i = 0; i < s.size(); i++) {
            resultat += Integer.parseInt(s.get(i)) / minim;
        }
        return resultat;
    }

    private static int tamanyBarra(List<String> s) {
        int comparador = 10000;
        for (int i = 0; i < s.size(); i++) {
            if (Integer.parseInt(s.get(i)) < comparador) {
                comparador = Integer.parseInt(s.get(i));
            }
        }
        return comparador;
    }

    private static String agruparGrossors(String grossors) {
        String resultat = "";
        String codi = "";
        int contador = 0;
        for (int i = 0; i < grossors.length(); i++) {
            if (contador < 6) {
                //coger núms de grossors y posar-los en codi
                codi += "" + grossors.charAt(i);
                contador++;
            } else {
                char c = cercarGrossorsICaracters(codi);
                resultat += c;
                codi = "";
                contador = 0;
                i--;
            }
        }
        return resultat;
    }

    private static char cercarGrossorsICaracters(String codi) {
        int resultat = 0;
        for (int i = 0; i < grossors.length; i++) {
            if (codi.equals(grossors[i])) {
                resultat = i;
            }
        }
        return caracters.charAt(resultat);
    }

    private static String crearResultatFinal(String resultatTotal) {
        System.out.println(resultatTotal);
//        if (compararCK(resultatTotal)) {
//            return null;
//        }
        String resultat = "";
        for (int i = 1; i < resultatTotal.length() - 3; i++) {
            if (resultatTotal.charAt(i) == '⊕') {
                i++;
                resultat += resultatTotal.toLowerCase(Locale.ROOT).charAt(i);
            } else {
                resultat += resultatTotal.charAt(i);
            }
        }
        System.out.println(resultat);
        return resultat;
    }

//    private static boolean compararCK(String resultatTotal) {
//        return false;
//    }


    // Decodifica una imatge. La imatge ha d'estar en format "ppm"
    public static String decodeImage(String str) {
        return "";
    }


    // Genera imatge a partir de barcode code93
    // Unitat barra mínima: 3 pixels
    // Alçada: 100px
    // Marges: vertical: 5px, horizontal: 15px
    /*public static String generateImage(String s) {
        String resultat;
        String barcode = barcodeMatch(s);
        String fila = "               " + barcode + "               ";
        int ample = fila.length();
        String filaBuida = ferMarges(ample);
        System.out.println(crearImatge(filaBuida, fila, ample));
        resultat = crearImatge(filaBuida, fila, ample);
        return resultat.trim();
    }

    static String crearImatge(String filaBuida, String fila, int ample) {
        String resultat = "P3\n" + ample + " 108\n" + "255\n";
        resultat += filaBuida;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < fila.length(); j++) {
                if (fila.charAt(j) == ' ') resultat += "255\n255\n255\n";
                else resultat += "0\n0\n0\n";
            }

        }
        resultat += filaBuida;

        return resultat;
    }

    static String ferMarges(int ample) {
        String resultat = "";
        for (int i = 0; i < ample; i++) {
            resultat += "255\n255\n255\n";
        }
        for (int i = 0; i < 2; i++) {
            resultat += resultat;
        }
        return resultat;
    }

    static String barcodeMatch(String s) {
        String resultat = "";
        for (int i = 0; i < s.length(); i++) {
            resultat += dibuixaCodiBarres(s.charAt(i));
            resultat += "   ";
        }
        return resultat.trim();
    }

    static String dibuixaCodiBarres(char c) {
        if (c == '*') {
            return "███   ██████████          ███";
        }
        if (c == '0') {
            return "███   ███   ██████████";
        }
        if (c == '1') {
            return "██████████   ███   ██████████";
        }
        if (c == '2') {
            return "███          ███   ██████████";
        }
        if (c == '3') {
            return "██████████          ███   ███";
        }
        if (c == '4') {
            return "███   ██████████   ██████████";
        }
        if (c == '5') {
            return "██████████   ██████████   ███";
        }
        if (c == '6') {
            return "███          ██████████   ███";
        }
        if (c == '7') {
            return "███   ███          ██████████";
        }
        if (c == '8') {
            return "██████████   ███          ███";
        }
        if (c == '9') {
            return "██████████   ███   ███";
        }
        if (c == '-') {
            return "███   ██████████   ███";
        }
        return null;
    }*/
}
