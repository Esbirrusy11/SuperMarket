import java.io.*;
import java.util.*;

public class Main {
    static Set<Super> Productos=new HashSet<>();
    static Map<String,Double> pros=new HashMap<>();
    static Map<String, Super> compraMap = new LinkedHashMap<>();
    static double totalConDescuento = 0;
    static double totalOriginal = 0;
    static boolean aplicoDescuento = false;

    public static void main(String[] args) {

        Super avena=new Super("Avena",1,2.21);
        Super garbanzos=new Super("Garbanzos",1,2.21);
        Super tomate=new Super("Tomate",1,1.59);
        Super jengibre=new Super("Jengibre",1,3.13);
        Super quinoa=new Super("Quinoa",1,4.50);
        Super guisantes=new Super("Guisantes",1,1.60);
        Productos.add(avena);
        Productos.add(garbanzos);
        Productos.add(tomate);
        Productos.add(jengibre);
        Productos.add(quinoa);
        Productos.add(guisantes);

        pros.put("avena",2.21);
        pros.put("garbanzos",2.21);
        pros.put("tomate",1.59);
        pros.put("jengibre",3.13);
        pros.put("quinoa",4.50);
        pros.put("guisantes",1.60);

        boolean menu=true;

        do {
            System.out.println();
            ImprimirMenu();
            Scanner sc=new Scanner(System.in);

            try {
                int i= sc.nextInt();

                switch (i){
                    case 0:
                        System.out.println("Gracias");
                        menu=false;
                        break;
                    case 1:
                       compraMap= hacerCompra();
                        break;
                    case 2:
                        generarTicket(compraMap);
                        break;
                    case 3:
                       nuevoProducto();
                        break;
                    case 4:
                        imprimirProductos();
                        break;
                    case 5:
                        if (Productos.isEmpty()){
                            System.out.println("No hay productos registrados");
                        }else {
                            System.out.println("¿Cuál es el nombre del producto que quieres eliminar?");
                            String nombre= sc.next();
                            if (Productos.contains(buscarProducto(nombre))) {
                                Productos.remove(buscarProducto(nombre));
                                System.out.println("El producto se ha eliminado correctamente");
                            }else {
                                System.out.println("El nombre no pertenece a ningún producto");

                            }
                        }
                        break;
                    case 6:
                        cambiarPrecio();
                        break;
                    case 7:
                        cargarProductosDesdeArchivo();
                        break;
                    case 8:
                        imprimirProductosOrdenadosPorPrecio();
                        break;
                    case 9:
                        listarProductosPorNombre(true);
                        break;
                    case 10:
                        filtrarProductosPorPrecio(2.0,5.0);
                        break;
                    default:
                        System.out.println("Elige una opción válida");
                        System.out.println();
                }
            }catch (InputMismatchException e){
                System.out.println("Error elige una opción válida");
                ImprimirMenu();
                System.out.println();
                sc.nextLine();
            }
        }while (menu);
        System.out.println(" ");

    }
    public static Map<String, Super> hacerCompra() {
        Scanner sc = new Scanner(System.in);
        compraMap.clear();
        boolean boo = true;
        String proOriginal;
        int cant;

        do {
            System.out.println();
            System.out.println("Productos disponibles:");
            for (Super e : Productos) {
                System.out.println(e.nombre + " - Precio: " + e.precio);
            }
            System.out.println();
            System.out.print("Producto: ");
            proOriginal = sc.nextLine();
            String pro = proOriginal.toLowerCase();

            if (pro.equals("fin")) {
                boo = false;
            } else if (!pros.containsKey(pro)) {
                System.out.println("Este producto no está en el supermercado.");
            } else {
                boolean encontrado = false;
                for (Super s : Productos) {
                    if (s.nombre.equalsIgnoreCase(proOriginal)) {
                        System.out.print("Cantidad: ");
                        cant = sc.nextInt();
                        sc.nextLine();

                        if (compraMap.containsKey(s.nombre)) {

                            Super existente = compraMap.get(s.nombre);
                            existente.cantidad += cant;
                            existente.precio += s.precio * cant;
                        } else {

                            Super nuevo = new Super(s.nombre, cant, s.precio * cant);
                            compraMap.put(s.nombre, nuevo);
                        }

                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    System.out.println("Error: el producto no se encontró.");
                }
            }
        } while (boo);

        System.out.print("Introduzca código de descuento (INTRO si no tiene ninguno): ");
        String codigo = sc.nextLine();

        totalOriginal = 0;
        for (Super s : compraMap.values()) {
            totalOriginal += s.precio;
        }

        totalConDescuento = totalOriginal;
        aplicoDescuento = false;

        System.out.println();
        System.out.println("Compra Realizada");
        System.out.println();
        System.out.printf("%-10s %7s %8s %10s\n", "Producto", "Precio", "Cantidad", "Subtotal");
        System.out.println("--------------------------------------------------");

        for (Super S:compraMap.values()){
            System.out.println(S);
        }

        if (codigo.equalsIgnoreCase("PROMOECO")) {
            double descuento = totalOriginal * 0.10;
            totalConDescuento = totalOriginal - descuento;
            aplicoDescuento = true;
            System.out.println("Descuento aplicado: " + String.format("%.2f", descuento).replace('.', ','));
        }

        System.out.println("TOTAL: " + String.format("%.2f", totalOriginal).replace('.', ','));
        if (aplicoDescuento) {
            System.out.println("TOTAL con descuento: " + String.format("%.2f", totalConDescuento).replace('.', ','));
        }

        return compraMap;
    }


    public static void ImprimirMenu(){
        System.out.println("0.Salir");
        System.out.println("1.Hacer la Compra");
        System.out.println("2.Imprimir Ticket");
        System.out.println("3.Añadir Producto");
        System.out.println("4.Imprimir Productos");
        System.out.println("5.Eliminar Producto");
        System.out.println("6.Cambiar el precio a un Producto");
        System.out.println("7.Cargar Productos desde archivo");
        System.out.println("8.Ordenar Productos por Precio (Mayor a Menor)");
        System.out.println("9.Ordenar Productos por Nombre (A-Z=true Z-A=false)");
        System.out.println("10.Filtrar productos por Precio");

    }
    public static void generarTicket(Map<String, Super> compraMap) {
        if (compraMap.isEmpty()) {
            System.out.println("La compra está vacía, no hay nada que guardar.");
            return;
        }

        try (PrintWriter writer = new PrintWriter("ticket.txt")) {
            writer.printf("%-10s %8s %10s %12s\n", "Producto", "Precio", "Cantidad", "Subtotal");
            writer.println("----------------------------------------------------------");

            for (Super s : compraMap.values()) {
                writer.println(s.toString());
            }

            writer.println("----------------------------------------------------------");

            if (aplicoDescuento) {
                writer.println("TOTAL sin descuento: " + String.format("%.2f", totalOriginal).replace('.', ','));
                writer.println("TOTAL con descuento: " + String.format("%.2f", totalConDescuento).replace('.', ','));
            } else {
                writer.println("TOTAL: " + String.format("%.2f", totalOriginal).replace('.', ','));
            }

            System.out.println("Ticket guardado correctamente.");

        } catch (FileNotFoundException e) {
            System.out.println("Error guardando ticket: " + e.getMessage());
        }
    }


    public static void nuevoProducto(){
        Scanner sc = new Scanner(System.in);

        System.out.println("¿Cuál es el nombre del producto?");
        String nombre = sc.nextLine();
        System.out.println("¿Cuál es el precio del producto?");
        double precio = sc.nextDouble();
        sc.nextLine();
        pros.put(nombre,precio);
        Super s=new Super(nombre,1,precio);
        Productos.add(s);
        System.out.println("El producto fue añadido con éxito");
    }

    public static void imprimirProductos(){
        for (Super s: Productos){
            System.out.println(s.nombre + " - Precio: " + s.precio);
        }
    }

    public static Super buscarProducto(String numero){
        for (Super conta:Productos){
            if (conta.getNombre().equalsIgnoreCase(numero)){
                return conta;
            }
        }
        return null;
    }

    public static void cambiarPrecio() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el nombre del producto cuyo precio quieres cambiar: ");
        String nombreProducto = sc.nextLine();
        boolean encontrado = false;

        for (Super s : Productos) {
            if (s.nombre.equalsIgnoreCase(nombreProducto)) {
                System.out.print("Introduce el nuevo precio: ");
                try {
                    double nuevoPrecio = sc.nextDouble();
                    sc.nextLine();

                    if (nuevoPrecio < 0) {
                        System.out.println("El precio no puede ser negativo.");
                        return;
                    }

                    s.precio = nuevoPrecio;
                    System.out.println("Precio actualizado correctamente.");
                    encontrado = true;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Debes introducir un número.");
                    encontrado=true;
                    sc.nextLine();
                }
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Producto no encontrado.");
        }
    }

    public static void cargarProductosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ticket2.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    double precio;

                    try {
                        precio = Double.parseDouble(partes[1].trim().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        System.out.println("Precio inválido en la línea: " + linea);
                        continue;
                    }

                    Super productoF = new Super(nombre, 0, precio);
                    Productos.add(productoF);
                    pros.put(nombre.toLowerCase(), precio);
                } else {
                    System.out.println("Línea mal formada: " + linea);
                }
            }
            System.out.println("Productos cargados correctamente desde el archivo.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void imprimirProductosOrdenadosPorPrecio() {

        List<Super> productosOrdenados = new ArrayList<>(Productos);

        productosOrdenados.sort((p1, p2) -> Double.compare(p2.precio, p1.precio));

        System.out.printf("%-10s %10s\n", "Producto", "Precio");


        for (Super producto : productosOrdenados) {
            String precioStr = String.format("%.2f", producto.precio).replace('.', ',');
            System.out.printf("%-10s %10s\n", producto.nombre, precioStr);
        }
    }

    public static void listarProductosPorNombre(boolean ascendente) {
        List<Super> lista = new ArrayList<>(Productos);

        if (ascendente) {
            lista.sort(Comparator.comparing(s -> s.nombre.toLowerCase()));
        } else {
            lista.sort(Comparator.comparing((Super s) -> s.nombre.toLowerCase()).reversed());
        }

        System.out.println("Productos ordenados por nombre (" + (ascendente ? "A–Z" : "Z–A") + "):");
        for (Super s : lista) {
            System.out.println(s.nombre + " - Precio: " + String.format("%.2f", s.precio).replace('.', ','));
        }
    }

    public static void filtrarProductosPorPrecio(double min, double max) {
        System.out.println("Productos entre " + min + "€ y " + max + "€:");

        boolean encontrado = false;

        for (Super s : Productos) {
            double precioUnitario = s.precio / s.cantidad;

            if (precioUnitario >= min && precioUnitario <= max) {
                System.out.println(s);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos en ese rango de precios.");
        }
    }


}
