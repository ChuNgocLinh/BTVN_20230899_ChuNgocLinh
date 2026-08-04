package vn.edu.eaut.lab1;

import java.util.Scanner;

public class Bai4TamGiac {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chay(scanner);
        scanner.close();
    }

    public static void chay(Scanner scanner) {
        System.out.println("Bai 4: Kiem tra va phan loai tam giac");
        System.out.print("Nhap a: ");
        double a = scanner.nextDouble();
        System.out.print("Nhap b: ");
        double b = scanner.nextDouble();
        System.out.print("Nhap c: ");
        double c = scanner.nextDouble();
        System.out.println(So.loaiTamGiac(a, b, c));
    }
}
