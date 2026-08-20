package vn.edu.eaut.lab8.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ParticipantRequest {

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 30, message = "Mã sinh viên tối đa 30 ký tự")
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 120, message = "Họ tên tối đa 120 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 160, message = "Email tối đa 160 ký tự")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9+\\- ]{8,20}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Trường/đơn vị không được để trống")
    @Size(max = 160, message = "Trường/đơn vị tối đa 160 ký tự")
    private String university;

    @NotBlank(message = "Tên đội không được để trống")
    @Size(max = 120, message = "Tên đội tối đa 120 ký tự")
    private String teamName;

    @NotBlank(message = "Chủ đề không được để trống")
    @Size(max = 120, message = "Chủ đề tối đa 120 ký tự")
    private String hackathonTheme;

    @NotBlank(message = "Vai trò không được để trống")
    @Size(max = 60, message = "Vai trò tối đa 60 ký tự")
    private String role;

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
}
