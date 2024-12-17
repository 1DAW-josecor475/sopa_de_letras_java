import java.util.Random;
import java.util.Scanner;

public class Sopa_de_Letras {
    public static void main(String[] args) {
        char[][] sopa = new char[10][10];
        generarSopa(sopa);
        visualizarSopa(sopa);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Ingrese una palabra a buscar (o escriba 'salir' para terminar): ");
            String palabra = scanner.nextLine();
            if (palabra.equalsIgnoreCase("salir")) {
                break;
            }

            boolean encontrada = buscarHorizontalNormal(sopa, palabra) ||
                    buscarHorizontalInvertida(sopa, palabra) ||
                    buscarVerticalNormal(sopa, palabra) ||
                    buscarVerticalInvertida(sopa, palabra);

            if (encontrada) {
                System.out.println("¡Palabra encontrada y tachada en la sopa de letras!");
                visualizarSopa(sopa);
            } else {
                System.out.println("Palabra no encontrada.");
            }
        }
        scanner.close();
    }
    public static void generarSopa(char[][] sopa) {
        Scanner sc = new Scanner(System.in);
        System.out.println("* Introduzca cuatro palabras: ");

        for (int i = 0; i < 4; i++) {
            String palabra = sc.nextLine();
            colocarPalabraAleatoria(sopa, palabra, i);
        }

        Random random = new Random();
        for (int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa[i].length; j++) {
                if (sopa[i][j] == '\0') {
                    sopa[i][j] = (char) ('A' + random.nextInt(26));
                }
            }
        }
    }
    public static void visualizarSopa(char[][] sopa) {
        for (int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa[i].length; j++) {
                System.out.print(sopa[i][j] + "  ");
            }
            System.out.println();
        }
    }
    public static boolean buscarHorizontalNormal(char[][] sopa, String palabra) {
        for (int i = 0; i < sopa.length; i++) {
            for (int j = 0; j < sopa[i].length - palabra.length(); j++) {
                boolean encontrada = true;
                while (encontrada) {
                    for (int k = 0; k < palabra.length(); k++) {
                        if (sopa[i][j + k] != palabra.charAt(k)) {
                            encontrada = false;
                        }
                    }
                    if (encontrada) {
                        for (int k = 0; k < palabra.length(); k++) {
                            sopa[i][j + k] = '-';
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean buscarHorizontalInvertida(char[][] sopa, String palabra) {
        for (int i = 0; i < sopa.length; i++) {
            for (int j = palabra.length() - 1; j < sopa[i].length; j++) {
                boolean encontrada = true;
                while (encontrada) {
                    for (int k = palabra.length() - 1; k > 0; k--) {
                        if (sopa[i][j] != palabra.charAt(k)) {
                            encontrada = false;
                        }
                    }
                    if (encontrada) {
                        for (int k = 0; k < palabra.length(); k++) {
                            sopa[i][j + k] = '-';
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean buscarVerticalNormal(char[][] sopa, String palabra) {
        for (int i = 0; i < sopa.length - palabra.length(); i++) {
            for (int j = 0; j < sopa[i].length; j++) {
                boolean encontrada = true;
                while (encontrada) {
                    for (int k = 0; k < palabra.length(); k++) {
                        if (sopa[i + k][j] != palabra.charAt(k)) {
                            encontrada = false;
                        }
                    }
                    if (encontrada) {
                        for (int k = 0; k < palabra.length(); k++) {
                            sopa[i + k][j] = '-';
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean buscarVerticalInvertida(char[][] sopa, String palabra) {
        for (int i = palabra.length() - 1; i < sopa.length - palabra.length(); i++) {
            for (int j = 0; j < sopa[i].length; j++) {
                boolean encontrada = true;
                while (encontrada) {
                    for (int k = 0; k < palabra.length(); k++) {
                        if (sopa[i + k][j] != palabra.charAt(k)) {
                            encontrada = false;
                        }
                    }
                    if (encontrada) {
                        for (int k = 0; k < palabra.length(); k++) {
                            sopa[i + k][j] = '-';
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static void colocarPalabraAleatoria(char[][] sopa, String palabra, int direccion) {
        Random random = new Random();
        boolean colocada = false;
        while (!colocada) {
            int fila = random.nextInt(10);
            int columna = random.nextInt(10);
            colocada = intentarColocarPalabra(sopa, palabra, direccion, fila, columna);
        }
    }

    public static boolean intentarColocarPalabra(char[][] sopa, String palabra, int direccion, int fila, int columna) {
        int longitud = palabra.length();
        switch (direccion) {
            case 0: // Horizontal normal
                if (columna + longitud > sopa[0].length) return false;
                for (int i = 0; i < longitud; i++) {
                    if (sopa[fila][columna + i] != '\0') return false;
                }
                for (int i = 0; i < longitud; i++) {
                    sopa[fila][columna + i] = palabra.charAt(i);
                }
                break;

            case 1: // Horizontal invertida
                if (columna - longitud < -1) return false;
                for (int i = 0; i < longitud; i++) {
                    if (sopa[fila][columna - i] != '\0') return false;
                }
                for (int i = 0; i < longitud; i++) {
                    sopa[fila][columna - i] = palabra.charAt(i);
                }
                break;

            case 2: // Vertical normal
                if (fila + longitud > sopa.length) return false;
                for (int i = 0; i < longitud; i++) {
                    if (sopa[fila + i][columna] != '\0') return false;
                }
                for (int i = 0; i < longitud; i++) {
                    sopa[fila + i][columna] = palabra.charAt(i);
                }
                break;

            case 3: // Vertical invertida
                if (fila - longitud < -1) return false;
                for (int i = 0; i < longitud; i++) {
                    if (sopa[fila - i][columna] != '\0') return false;
                }
                for (int i = 0; i < longitud; i++) {
                    sopa[fila - i][columna] = palabra.charAt(i);
                }
                break;
        }
        return true;
    }
}

