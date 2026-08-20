package vn.edu.eaut.lab8.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "participants")
public class Participant {

    // Entity này ánh xạ trực tiếp với bảng participants trong MySQL.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, unique = true, length = 30)
    private String studentCode;

    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 160)
    private String university;

    @Column(name = "team_name", nullable = false, length = 120)
    private String teamName;

    @Column(name = "hackathon_theme", nullable = false, length = 120)
    private String hackathonTheme;

    @Column(nullable = false, length = 60)
    private String role;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    public Participant() {
    }

    public Participant(String studentCode, String fullName, String email, String phone, String university, String teamName, String hackathonTheme, String role) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.university = university;
        this.teamName = teamName;
        this.hackathonTheme = hackathonTheme;
        this.role = role;
    }

    @PrePersist
    void onCreate() {
        // Tự gán thời điểm đăng ký khi thêm mới, không bắt người dùng nhập tay.
        if (registeredAt == null) {
            registeredAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getHackathonTheme() {
        return hackathonTheme;
    }

    public void setHackathonTheme(String hackathonTheme) {
        this.hackathonTheme = hackathonTheme;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}
