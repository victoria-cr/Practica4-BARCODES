// https://en.wikipedia.org/wiki/Code_93

public class Code93 {
    static String caracters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%????*";

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
        String resultat = "";
        for (int i = 0; i < str.length(); i++) {
            char caracter = str.charAt(i);
            cercarIndexCaracter(caracter, caracters);
            System.out.println(cercarIndexCaracter(caracter, caracters));
        }
        return resultat;
    }

    private static int cercarIndexCaracter(char caracter, String string) {
        for (int i = 0; i < string.length(); i++) {
            if (caracter == string.charAt(i)) {
                return i;
            }
        }
        return -1;
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
