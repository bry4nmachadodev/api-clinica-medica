package med.voll.api.testes.memoria;

public class TestaString {
    public static void main(String[] args) {
        /*String valor1 = "alura";
        *//*String valor2 = "alura";

        System.out.println(valor1 == valor2);

        String valor3 = new String("alura");
        System.out.println(valor1 == valor3);*//*

        valor1 = valor1 + "Programação";
        System.out.println(valor1);

        StringBuilder resultado = new StringBuilder();

        for (int i = 1; i <= 100; i++){
            resultado.append(i).append(" ");
        }

        System.out.println(resultado);*/

        StringBuffer resultado = new StringBuffer();

        var thread1 = new Thread(()->
        {
            for(int i = 1; i <= 100; i++){
                resultado.append(i);
                resultado.append(" ");
            }
        });

        var thread2 = new Thread(()->
        {
            for(int i = 101; i <= 200; i++){
                resultado.append(i);
                resultado.append(" ");
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(resultado);
    }
}
