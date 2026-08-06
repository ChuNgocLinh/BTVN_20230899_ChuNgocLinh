package vn.edu.eaut.lab2;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("===== LAB 2 - MAVEN PROJECT VA DONG GOI JAR =====");

            String studentId = inputRequiredText(scanner, "Nhap ma sinh vien: ");
            String fullName = inputRequiredText(scanner, "Nhap ho ten sinh vien: ");
            double attendanceScore = inputScore(scanner, "diem chuyen can");
            double midtermScore = inputScore(scanner, "diem giua ky");
            double finalScore = inputScore(scanner, "diem cuoi ky");

            Student student = new Student(
                    studentId,
                    fullName,
                    attendanceScore,
                    midtermScore,
                    finalScore);

            double totalScore = GradeCalculator.calculateFinalScore(student);
            String grade = GradeCalculator.classify(totalScore);

            printResult(student, totalScore, grade);
        }
    }

    private static String inputRequiredText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Loi: Gia tri khong duoc de trong.");
        }
    }

    private static double inputScore(Scanner scanner, String label) {
        while (true) {
            try {
                System.out.print("Nhap " + label + ": ");
                double score = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                GradeCalculator.validateScore(score, label);
                return score;
            } catch (NumberFormatException ex) {
                System.out.println("Loi: Diem phai la so.");
            } catch (IllegalArgumentException ex) {
                System.out.println("Loi: " + ex.getMessage());
            }
        }
    }

    private static void printResult(Student student, double totalScore, String grade) {
        System.out.println();
        System.out.println("----- KET QUA HOC PHAN -----");
        System.out.printf("%-15s: %s%n", "Ma SV", student.getStudentId());
        System.out.printf("%-15s: %s%n", "Ho ten", student.getFullName());
        System.out.printf("%-15s: %.2f%n", "Diem tong ket", totalScore);
        System.out.printf("%-15s: %s%n", "Xep loai", grade);
    }
}

