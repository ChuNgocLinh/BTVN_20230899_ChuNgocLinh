package vn.edu.eaut.lab1;

import java.util.Scanner;

public class Bai2TongNghichDao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chay(scanner);
        scanner.close();
    }

    public static void chay(Scanner scanner) {
        System.out.println("Bai 2: Tinh S = 1 + 1/2 + ... + 1/n");
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();
        System.out.printf(java.util.Locale.US, "S = %.4f%n", So.tongNghichDao(n));
    }
}
