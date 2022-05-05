// Consultar taula https://en.wikipedia.org/wiki/Barcode#Linear_barcodes
// Code11: https://en.wikipedia.org/wiki/Code_11

// Generadors de codis:
//     https://barcode.tec-it.com/en/Code11
//     https://www.free-barcode-generator.net/code-11/
//     https://products.aspose.app/barcode/generate

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
        String codi = "";
        String resultat = "";
        for (int i = 0; i < s.length(); i++) {
            char caracter = s.charAt(i);
            if (caracter == ' ' && codi.equals("")) {
                continue;
            }

            if (i == s.length()-1) {
                codi += "0";
            } else {
                char seguentCaracter = s.charAt(i+1);
                if (caracter == seguentCaracter) {
                    codi += "1";
                    i++;
                } else {
                    codi += "0";
                }
            }

            if (codi.length() == 5) {
                resultat += (codificarCaracter(codi));
                codi = "";
            }
        }
        return resultat;
    }

    private static String codificarCaracter(String codi) {
        String resultat = "";
        if (codi.equals("00110")) {
            resultat += "*";
        }

        if (codi.equals("00001")) {
            resultat += "0";
        }

        if (codi.equals("10001")) {
            resultat += "1";
        }

        if (codi.equals("01001")) {
            resultat += "2";
        }

        if (codi.equals("11000")) {
            resultat += "3";
        }

        if (codi.equals("00101")) {
            resultat += "4";
        }

        if (codi.equals("10100")) {
            resultat += "5";
        }

        if (codi.equals("01100")) {
            resultat += "6";
        }

        if (codi.equals("00011")) {
            resultat += "7";
        }

        if (codi.equals("10010")) {
            resultat += "8";
        }

        if (codi.equals("10000")) {
            resultat += "9";
        }

        if (codi.equals("00100")) {
            resultat += "-";
        }

        return resultat;
    }

    // Decodifica una imatge. La imatge ha d'estar en format "ppm"
    public static String decodeImage(String str) {
        return "";
    }

    // Genera imatge a partir de codi de barres
    // Alçada: 100px
    // Marges: vertical 4px, horizontal 8px
    public static String generateImage(String s) {
        return "";
    }
}
