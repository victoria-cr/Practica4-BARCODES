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

    // Decodifica amb Code11
    static String decode(String s) {
        return "";
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
