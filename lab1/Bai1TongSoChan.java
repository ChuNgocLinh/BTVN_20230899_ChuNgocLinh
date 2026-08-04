package vn.edu.eaut.lab1;

import java.util.Scanner;

public class Bai1TongSoChan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chay(scanner);
        scanner.close();
    }

    public static void chay(Scanner scanner) {
        System.out.println("Bai 1: Tinh S = 2 + 4 + ... + n");
        System.out.print("Nhap n: ");
        int n = scanner.nextInt();
        System.out.println("S = " + So.tongChanDenN(n));
    }
}
