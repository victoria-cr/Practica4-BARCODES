// https://en.wikipedia.org/wiki/Code_93

public class Code93 {
    static String caracters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%∦∬⊘⊕*";

    static String[] grossors = new String[]{
            "131112",
            "111213",
            "111312",
            "111411",
            "121113",
            "121212",
            "121311",
            "111114",
            "131211",
            "141111",
            "211113",
            "211212",
            "211311",
            "221112",
            "221211",
            "231111",
            "112113",
            "112212",
            "112311",
            "122112",
            "132111",
            "111123",
            "111222",
            "111321",
            "121122",
            "131121",
            "212112",
            "212211",
            "211122",
            "211221",
            "221121",
            "222111",
            "112122",
            "112221",
            "122121",
            "123111",
            "121131",
            "311112",
            "311211",
            "321111",
            "112131",
            "113121",
            "211131",
            "121221",
            "312111",
            "311121",
            "122211",
            "111141"
    };

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
            resultat += dibuixaGrossors(g);
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

    private static String dibuixaGrossors(String g) {
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

    // Decodifica emprant Code93
    static String decode(String str) {
        return "";
    }

    // Decodifica una imatge. La imatge ha d'estar en format "ppm"
    public static String decodeImage(String str) {
        return "";
    }

    // Genera imatge a partir de barcode code93
    // Unitat barra mínima: 3 pixels
    // Alçada: 100px
    // Marges: vertical: 5px, horizontal: 15px
    public static String generateImage(String s) {
        return "";
    }
}
