package vn.edu.eaut.lab1;

import java.util.Scanner;

public class Bai5Fibonacci {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chay(scanner);
        scanner.close();
    }

    public static void chay(Scanner scanner) {
        System.out.println("Bai 5: Hien thi n so Fibonacci dau tien");
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();
        System.out.println(So.dayFibonacci(n));
    }
}
