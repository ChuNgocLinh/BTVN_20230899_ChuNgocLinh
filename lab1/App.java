package vn.edu.eaut.lab1;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            hienThiMenu();
            System.out.print("Chon bai tap: ");
            choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> Bai1TongSoChan.chay(scanner);
                    case 2 -> Bai2TongNghichDao.chay(scanner);
                    case 3 -> Bai3SoNguyenTo.chay(scanner);
                    case 4 -> Bai4TamGiac.chay(scanner);
                    case 5 -> Bai5Fibonacci.chay(scanner);
                    case 0 -> System.out.println("Ket thuc chuong trinh.");
                    default -> System.out.println("Lua chon khong hop le!");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Loi: " + ex.getMessage());
            }
            System.out.println();
        } while (choice != 0);
        scanner.close();
    }

    private static void hienThiMenu() {
        System.out.println("========== LAB 1 - JAVA SE CONSOLE ==========");
        System.out.println("1. Tinh S = 2 + 4 + ... + n");
        System.out.println("2. Tinh S = 1 + 1/2 + ... + 1/n");
        System.out.println("3. Kiem tra so nguyen to");
        System.out.println("4. Kiem tra va phan loai tam giac");
        System.out.println("5. Hien thi n so Fibonacci dau tien");
        System.out.println("0. Thoat");
    }
}
