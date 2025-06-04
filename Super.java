public class Super {

    String nombre;
    int cantidad;
    double precio;


    public Super(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return
                "Nombre= " + nombre +
                " Cantidad= " + cantidad +
                " Precio= " + precio;
    }
}
