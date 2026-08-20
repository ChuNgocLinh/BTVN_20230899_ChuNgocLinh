package vn.edu.eaut.lab8.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eaut.lab8.dto.ParticipantRequest;
import vn.edu.eaut.lab8.exception.DuplicateResourceException;
import vn.edu.eaut.lab8.exception.ResourceNotFoundException;
import vn.edu.eaut.lab8.model.Participant;
import vn.edu.eaut.lab8.repository.ParticipantRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParticipantService {

    private final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Participant> findAll(String keyword) {
        // Nếu không có từ khóa thì trả toàn bộ danh sách, có từ khóa thì gọi query tìm kiếm.
        if (keyword == null || keyword.isBlank()) {
            return repository.findAllByOrderByIdDesc();
        }
        return repository.search(keyword.trim());
    }

    @Transactional(readOnly = true)
    public Participant findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thí sinh có id = " + id));
    }

    public Participant create(ParticipantRequest request) {
        // Kiểm tra trùng mã SV/email trước khi lưu để dữ liệu trong MySQL không bị lặp.
        validateUnique(request, null);
        Participant participant = new Participant();
        copyData(request, participant);
        return repository.save(participant);
    }

    public Participant update(Long id, ParticipantRequest request) {
        Participant participant = findById(id);
        validateUnique(request, id);
        copyData(request, participant);
        return repository.save(participant);
    }

    public void delete(Long id) {
        Participant participant = findById(id);
        repository.delete(participant);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> stats() {
        List<Participant> participants = repository.findAll();
        long total = participants.size();
        long teams = participants.stream()
                .map(Participant::getTeamName)
                .filter(value -> value != null && !value.isBlank())
                .map(value -> value.trim().toLowerCase(Locale.ROOT))
                .distinct()
                .count();
        Map<String, Long> byTheme = participants.stream()
                .collect(Collectors.groupingBy(Participant::getHackathonTheme, Collectors.counting()));
        Map<String, Object> result = new HashMap<>();
        result.put("totalParticipants", total);
        result.put("totalTeams", teams);
        result.put("totalThemes", byTheme.size());
        result.put("themes", byTheme);
        return result;
    }

    private void validateUnique(ParticipantRequest request, Long ignoredId) {
        String studentCode = normalize(request.getStudentCode());
        String email = normalize(request.getEmail());
        boolean duplicatedCode = ignoredId == null
                ? repository.existsByStudentCodeIgnoreCase(studentCode)
                : repository.existsStudentCodeForOtherId(studentCode, ignoredId);
        boolean duplicatedEmail = ignoredId == null
                ? repository.existsByEmailIgnoreCase(email)
                : repository.existsEmailForOtherId(email, ignoredId);

        if (duplicatedCode) {
            throw new DuplicateResourceException("Mã sinh viên đã tồn tại: " + studentCode);
        }
        if (duplicatedEmail) {
            throw new DuplicateResourceException("Email đã tồn tại: " + email);
        }
    }

    private void copyData(ParticipantRequest request, Participant participant) {
        // Gom phần copy dữ liệu vào một hàm để create và update dùng chung.
        participant.setStudentCode(normalize(request.getStudentCode()));
        participant.setFullName(normalize(request.getFullName()));
        participant.setEmail(normalize(request.getEmail()));
        participant.setPhone(normalize(request.getPhone()));
        participant.setUniversity(normalize(request.getUniversity()));
        participant.setTeamName(normalize(request.getTeamName()));
        participant.setHackathonTheme(normalize(request.getHackathonTheme()));
        participant.setRole(normalize(request.getRole()));
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
