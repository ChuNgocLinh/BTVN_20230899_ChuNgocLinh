package vn.edu.eaut.lab8;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.eaut.lab8.model.Participant;
import vn.edu.eaut.lab8.repository.ParticipantRepository;

import java.util.List;

@SpringBootApplication
public class Lab08SpringBootRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab08SpringBootRestApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(ParticipantRepository repository) {
        return args -> {
            // Seed dữ liệu mẫu có dấu tiếng Việt. Nếu mã SV đã tồn tại thì cập nhật lại,
            // nhờ vậy database MySQL cũ cũng được làm mới khi chạy app.
            List<Participant> participants = List.of(
                    new Participant("SV001", "Nguyễn Minh Anh", "minhanh@eaut.edu.vn", "0981000001", "EAUT", "Byte Builders", "AI cho khuôn viên trường", "Backend"),
                    new Participant("SV002", "Trần Gia Huy", "giahuy@eaut.edu.vn", "0981000002", "EAUT", "Byte Builders", "AI cho khuôn viên trường", "Frontend"),
                    new Participant("SV003", "Lê Hoàng Nam", "hoangnam@eaut.edu.vn", "0981000003", "EAUT", "Data Sparks", "Thành phố thông minh", "Dữ liệu"),
                    new Participant("SV004", "Phạm Ngọc Linh", "ngoclinh@eaut.edu.vn", "0981000004", "EAUT", "Secure Flow", "An toàn tài chính số", "Trưởng nhóm")
            );
            for (Participant sample : participants) {
                Participant participant = repository.findByStudentCodeIgnoreCase(sample.getStudentCode())
                        .orElseGet(Participant::new);
                participant.setStudentCode(sample.getStudentCode());
                participant.setFullName(sample.getFullName());
                participant.setEmail(sample.getEmail());
                participant.setPhone(sample.getPhone());
                participant.setUniversity(sample.getUniversity());
                participant.setTeamName(sample.getTeamName());
                participant.setHackathonTheme(sample.getHackathonTheme());
                participant.setRole(sample.getRole());
                repository.save(participant);
            }
            System.out.println("Lab 8 đã khởi tạo/cập nhật dữ liệu mẫu tiếng Việt trong bảng participants.");
        };
    }
}
