/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package t3_progobjetos;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author crist
 */
public class T3_ProgObjetos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("EXCEPCIONES");
        try{
        int a=8;
        int b=0;
        System.out.println("---excepcion de dividir entre 0---");
        int rpta=a/b;
        System.out.println("El resultado es: "+rpta);//catch lo que es la excepcion o error
        }catch(ArithmeticException e)
        {
            System.out.println("division entre cero");
        }
        System.out.println("Fin del programa");
        int dato=0;
        int sw=0;
        Scanner sc = new Scanner(System.in);
        System.out.println("---excepcion de ingresar un caracter en un entero---");
        while(sw==0)
        {
            System.out.println("Ingrese un entero");
            try{
                dato = sc.nextInt();
                sw=1;
            }
            catch(InputMismatchException exception)
            {
                System.out.println("Error");
                sw=0;
                //sc.nextLine();
                sc.next();
            }
        }
        System.out.println("El número ingresado es: "+ dato);
        System.out.println("---COLECCIONES---");
        System.out.println("");
        ArrayList<String> nombres=new ArrayList();
        nombres.add("Juan");
        nombres.add("Rosa");
        nombres.add("Maria");
        
        System.out.println("El objeto de la posicion 1 es: "+nombres.get(1));
        
        System.out.println("Eliminando a la posicion 1:");
        nombres.remove(1);
        nombres.set(1, "Ana");
        System.out.println("recorriendo los opbjetos de la coleccion");
        for(int i=0; i<nombres.size();i++)
        {
            System.out.println(nombres.get(i));
        }
        
        System.out.println("--SOBRECARGA---");
        sumar (4,5);
        sumar (4,5,6);
        sumar (4,5,4,4);
        multiplicar(4,5);
        multiplicar(4,5,6);
        multiplicar(4,5,6,7);
    }
    
    public static void sumar (int a, int b) {
    System.out.println("La suma es: "+(a+b));
    }
    public static void sumar(int a, int b, int c) {
    System.out.println("La suma es: "+(a+c+c));
    }
    public static void sumar(int a, int b, int c, int d) {
    System.out.println("La suma es: "+(a+b+c+d));
    }
    public static void multiplicar(int a, int b)
    {
    System.out.println("MULTIPLICACION");
    System.out.println("La multiplicacion es: "+(a*b));
    }
    public static void multiplicar(int a, int b, int c)
    {
    System.out.println("La multiplicacion es: "+(a*b*c));
    }
    public static void multiplicar(int a, int b, int c, int d)
    {
    System.out.println("La multiplicacion es: "+(a*b*c*d));
    }
}
