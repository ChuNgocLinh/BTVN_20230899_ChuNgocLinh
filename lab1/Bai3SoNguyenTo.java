package vn.edu.eaut.lab1;

import java.util.Scanner;

public class Bai3SoNguyenTo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chay(scanner);
        scanner.close();
    }

    public static void chay(Scanner scanner) {
        System.out.println("Bai 3: Kiem tra so nguyen to");
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();
        if (So.laSoNguyenTo(n)) {
            System.out.println(n + " la so nguyen to.");
        } else {
            System.out.println(n + " khong phai la so nguyen to.");
        }
    }
}
