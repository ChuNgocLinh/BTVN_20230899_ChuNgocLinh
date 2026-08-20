package vn.edu.eaut.lab8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.eaut.lab8.model.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findAllByOrderByIdDesc();

    Optional<Participant> findByStudentCodeIgnoreCase(String studentCode);

    // Tìm kiếm trên nhiều cột để ô tìm kiếm ngoài giao diện có thể lọc theo tên, email, đội hoặc chủ đề.
    @Query("""
            select p from Participant p
            where lower(p.studentCode) like lower(concat('%', :keyword, '%'))
               or lower(p.fullName) like lower(concat('%', :keyword, '%'))
               or lower(p.email) like lower(concat('%', :keyword, '%'))
               or lower(p.teamName) like lower(concat('%', :keyword, '%'))
               or lower(p.hackathonTheme) like lower(concat('%', :keyword, '%'))
            order by p.id desc
            """)
    List<Participant> search(@Param("keyword") String keyword);

    boolean existsByStudentCodeIgnoreCase(String studentCode);

    boolean existsByEmailIgnoreCase(String email);

    @Query("""
            select count(p) > 0 from Participant p
            where lower(p.studentCode) = lower(:studentCode)
              and p.id <> :id
            """)
    boolean existsStudentCodeForOtherId(@Param("studentCode") String studentCode, @Param("id") Long id);

    @Query("""
            select count(p) > 0 from Participant p
            where lower(p.email) = lower(:email)
              and p.id <> :id
            """)
    boolean existsEmailForOtherId(@Param("email") String email, @Param("id") Long id);
}
