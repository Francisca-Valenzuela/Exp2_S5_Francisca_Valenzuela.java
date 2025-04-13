/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp2_s5_francisca_valenzuela.java;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author IVI
 */
public class Exp2_S5_Francisca_Valenzuela {

    /**
     * @param args the command line arguments
     */
   
// Variables estáticas
    static int entradasVendidas = 0;
    static double totalIngresos = 0;
    static int totalDescuentosAplicados = 0;
    static final String nombreTeatro = "Teatro Moro";
    static final int capacidadTotal = 12;
    static ArrayList<Entrada> entradasVendidasLista = new ArrayList<>();
    static int entradasEliminadas = 0;

    public static void main(String[] args) {
        
        //Asientos disponibles
        Scanner scanner = new Scanner(System.in);
        String[][] asientos = {
                { "A1", "A2", "A3", "A4" },
                { "B1", "B2", "B3", "B4" },
                { "C1", "C2", "C3", "C4" }
        };

        boolean continuar = true;
        
        //Menú principal
        while (continuar) {
            System.out.println("\n===================================");
            System.out.println("  BIENVENIDO AL " + nombreTeatro.toUpperCase());
            System.out.println("===================================");
            System.out.println("\nMenu:");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Consultar asientos disponibles");
            System.out.println("3. Cancelar una compra");
            System.out.println("4. Ver promociones");
            System.out.println("5. Buscar entradas vendidas");
            System.out.println("6. Salir");
            System.out.print("\nSeleccione una opción: ");
            
            int opcion = 0;
            boolean opcionValida = false;

            // Validación para la opción del menú
            while (!opcionValida) {
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    if (opcion >= 1 && opcion <= 6) {
                        opcionValida = true;
                    } else {
                        System.out.println("Opción inválida. Por favor, ingrese 1 y 6.");
                        System.out.print("Elija una opción: ");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número (1 y 6).");
                    System.out.print("Elija una opción: ");
                    scanner.next(); 
                }
                scanner.nextLine(); 
            }
            

            switch (opcion) {
                case 1:
                    comprarEntrada(scanner, asientos);
                    break;
                case 2:
                    mostrarAsientos(asientos);
                    break;
                case 3:
                    cancelarCompra(scanner, asientos);
                    break;
                case 4:
                    mostrarPromociones();
                    break;
                case 5:
                    buscarEntradas(scanner);
                    break;
                case 6:
                    mostrarResumen();
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }
    
    // Método para comprar una entrada
    static void comprarEntrada(Scanner scanner, String[][] asientos) {
        mostrarAsientos(asientos);

        String zonaElegida;
        int zonaIndice = -1;
        double precioBase = 0;
        // Elegir zona (A, B o C)
        while (true) {
            
            System.out.print("\nSeleccione zona (A, B, C): ");
            zonaElegida = scanner.nextLine().toUpperCase();
            if (zonaElegida.equals("A")) {
                zonaIndice = 0;
                precioBase = 5000;
                break;
            } else if (zonaElegida.equals("B")) {
                zonaIndice = 1;
                precioBase = 4000;
                break;
            } else if (zonaElegida.equals("C")) {
                zonaIndice = 2;
                precioBase = 3000;
                break;
            } else {
                System.out.println("Zona inválida. Intente nuevamente.");
            }
        }
        
        //Selección del asiento
        int numAsiento = 0;
        while (true) {
            System.out.print("Ingrese número de asiento (1-4): ");
            if (scanner.hasNextInt()) {
                numAsiento = scanner.nextInt();
                if (numAsiento >= 1 && numAsiento <= 4) break;
                else System.out.println("Asiento inválido. Debe ser entre 1 y 4.");
            } else {
                System.out.println("\nEntrada inválida.");
                scanner.next();
            }
        }
        scanner.nextLine();
        
        // Validar si el asiento está disponible
        if (asientos[zonaIndice][numAsiento - 1] != null) {
            String asientoSeleccionado = asientos[zonaIndice][numAsiento - 1];
            asientos[zonaIndice][numAsiento - 1] = null;
            
            // Validar edad del cliente (entre 1 y 120)            
            int edad = -1;
            while (edad < 1 || edad > 120) {
                System.out.print("\nIngrese su edad: ");
                if (scanner.hasNextInt()) {
                    edad = scanner.nextInt();
                    if (edad < 1 || edad > 120) {
                        System.out.println("Edad fuera de rango. Intente nuevamente.");
                    }
                } else {
                    System.out.println("Dato inválido. Por favor ingrese un número.");
                    scanner.next(); 
                }
            }
            scanner.nextLine(); 
            
            
          
            
            // Aplicar descuento según edad
            double descuento = 0.0;
            String tipoDescuento = "No aplica";

            if (edad <= 18) {
                descuento = 0.10;
                tipoDescuento = "Estudiante (10%)";
                totalDescuentosAplicados++;
            } else if (edad >= 60) {
                descuento = 0.15;
                tipoDescuento = "Tercera Edad (15%)";
                totalDescuentosAplicados++;
            }
            
            // Calcular precio final y guardar entrada
            double precioFinal = precioBase - (precioBase * descuento);
            entradasVendidas++;
            totalIngresos += precioFinal;

            entradasVendidasLista.add(new Entrada(asientoSeleccionado, zonaElegida, precioFinal, tipoDescuento));
            
            // Mostrar resumen de la compra
            System.out.println("\n===== RESUMEN DE COMPRA =====");
            System.out.println("\nAsiento: " + asientoSeleccionado);
            System.out.printf("Precio base: $%.0f\n", precioBase);
            System.out.println("Descuento: " + tipoDescuento);
            System.out.printf("Total a pagar: $%.0f\n", precioFinal);
            
        } else {
            System.out.println("\nEl asiento ya está ocupado.");
        }
    }
    
    // Mostrar todos los asientos disponibles o ocupados
    static void mostrarAsientos(String[][] asientos) {
        System.out.println("\nAsientos disponibles:");
        for (int i = 0; i < asientos.length; i++) {
            char zona = (char) ('A' + i);
            System.out.print("Zona " + zona + ": ");
            for (int j = 0; j < asientos[i].length; j++) {
                if (asientos[i][j] != null) {
                    System.out.print("[" + asientos[i][j] + "] ");
                } else {
                    System.out.print("[XX] ");
                }
            }
            System.out.println();
        }
    }
    
    // Cancelar una compra 
    static void cancelarCompra(Scanner scanner, String[][] asientos) {
        System.out.print("Ingrese el código del asiento a cancelar (Ej: B2): ");
        String asientoCancelar = scanner.nextLine().toUpperCase();
        boolean cancelado = false;

        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                String nombreAsiento = "" + (char) ('A' + i) + (j + 1);
                if (asientos[i][j] == null && nombreAsiento.equals(asientoCancelar)) {
                    asientos[i][j] = nombreAsiento;
                    entradasVendidas--;
                    cancelado = true;

                    for (int k = 0; k < entradasVendidasLista.size(); k++) {
                        Entrada e = entradasVendidasLista.get(k);
                        if (e.numero.equals(asientoCancelar)) {
                            totalIngresos -= e.precioFinal;
                            entradasVendidasLista.remove(k);
                            entradasEliminadas++;
                            break;
                        }
                    }


                    System.out.println("El Asiento " + asientoCancelar + " ha sido cancelado.");
                }
            }
        }

        if (!cancelado) {
            System.out.println("El asiento no está ocupado o ya está disponible.");
        }
    }
    
    // Mostrar promociones vigentes
    static void mostrarPromociones() {
        System.out.println("\n==== PROMOCIONES ====");
        System.out.println("\nEstudiantes: 10% de descuento");
        System.out.println("Tercera edad: 15% de descuento");
        System.out.println("Compra de 3 o más entradas en una misma sesión: 5% adicional de descuento (no acumulable).");

    }
    
    // Buscar entrada vendida por número de asiento
    static void buscarEntradas(Scanner scanner) {
        String codigo = "";
        boolean formatoValido = false;

        // Validación del formato del asiento
        while (!formatoValido) {
            System.out.println("\n==== BUSCADOR ENTRADAS VENDIDAS  ====");
            System.out.print("\nIngrese número de asiento a buscar (Ej: A1): ");
            codigo = scanner.nextLine().toUpperCase();  

            // Verificar que el código tenga exactamente dos caracteres
            if (codigo.length() == 2) {
                char letra = codigo.charAt(0);
                char numero = codigo.charAt(1);

                if (Character.isLetter(letra) && Character.isDigit(numero)) {
                    formatoValido = true;  
                } else {
                    System.out.println("\nFormato inválido. El formato debe ser una letra seguida de un número (Ej: A1).");
                }
            } else {
                System.out.println("\nFormato inválido. El formato debe ser una letra seguida de un número (Ej: A1).");
            }
        }

        // Buscar la entrada en la lista
        for (Entrada e : entradasVendidasLista) {
            if (e.numero.equals(codigo)) {
                System.out.println("\nEntrada encontrada:");
                System.out.println("Asiento: " + e.numero);
                System.out.println("Zona: " + e.ubicacion);
                System.out.println("Precio final: $" + e.precioFinal);
                System.out.println("Descuento: " + e.tipoDescuento);
                return;
            }
        }
        System.out.println("\nNo se encontró la entrada con ese número.");
    }

    

    
    // Mostrar resumen final de ventas
    static void mostrarResumen() {
        System.out.println("\n===== RESUMEN FINAL =====");
        System.out.println("Entradas vendidas: " + entradasVendidas);
        System.out.printf("Total ingresos: $%.0f\n", totalIngresos);
        System.out.println("Descuentos aplicados: " + totalDescuentosAplicados);
        System.out.println("Entradas Eliminadas: " + entradasEliminadas); 
    }
}

class Entrada {
    String numero;
    String ubicacion;
    double precioFinal;
    String tipoDescuento;

    public Entrada(String numero, String ubicacion, double precioFinal, String tipoDescuento) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.precioFinal = precioFinal;
        this.tipoDescuento = tipoDescuento;
    }
}