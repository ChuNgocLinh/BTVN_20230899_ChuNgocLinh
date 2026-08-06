package vn.edu.eaut.lab2;

public class Student {
    private final String studentId;
    private final String fullName;
    private final double attendanceScore;
    private final double midtermScore;
    private final double finalScore;

    public Student(String studentId, String fullName,
            double attendanceScore, double midtermScore, double finalScore) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.attendanceScore = attendanceScore;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public double getAttendanceScore() {
        return attendanceScore;
    }

    public double getMidtermScore() {
        return midtermScore;
    }

    public double getFinalScore() {
        return finalScore;
    }
}

