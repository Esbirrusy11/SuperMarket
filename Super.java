public class Super {

    String nombre;
    int cantidad;
    double precio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Super(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    @Override
    public String toString() {

        double unitPrice = precio / cantidad;

        String precioStr = String.format("%.2f", unitPrice).replace('.', ',');
        String subtotalStr = String.format("%.2f", precio).replace('.', ',');


        return String.format("%-10s %7s %8d %10s", nombre, precioStr, cantidad, subtotalStr);
    }

}
