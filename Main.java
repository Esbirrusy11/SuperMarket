import java.util.*;

public class Main {
    static Set<Super> Productos=new HashSet<>();
    static Map<String,Double> pros=new HashMap<>();
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

        pros.put("Avena",2.21);
        pros.put("Garbanzos",2.21);
        pros.put("Tomate",1.59);
        pros.put("Jengibre",3.13);
        pros.put("Quinoa",4.50);
        pros.put("Guisantes",1.60);
        hacerCompra();
    }
    public static void hacerCompra(){
        Scanner sc=new Scanner(System.in);
        boolean boo=true;
        String pro;
        int cant;
        ArrayList<Super> array=new ArrayList<>();
        do {
            for (Super e: Productos){
                System.out.println(e.nombre+ " "+ e.precio);
            }
            System.out.println();
            System.out.println("Producto: ");
             pro=sc.nextLine();

            if (pro.equalsIgnoreCase("fin")){
                boo=false;
            }
            else if (!pros.containsKey(pro)) {
                System.out.println("Este producto no  está en el supermercado.");
                break;
            } else {
                for (Super s : Productos) {
                    if (s.nombre.equalsIgnoreCase(pro)) {
                        System.out.println("Cantidad: ");
                        cant=sc.nextInt();
                        double precio=s.precio*cant;
                        Super com=new Super(pro,cant,precio);
                        array.add(com);
                        sc.nextLine();
                    }
                 }
            }
        }while (boo);
        for (Super Ar :array){
            System.out.println(Ar);
        }

    }
}
