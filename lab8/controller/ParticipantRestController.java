package vn.edu.eaut.lab8.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.eaut.lab8.dto.ParticipantRequest;
import vn.edu.eaut.lab8.model.Participant;
import vn.edu.eaut.lab8.service.ParticipantService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participants")
public class ParticipantRestController {

    private final ParticipantService service;

    public ParticipantRestController(ParticipantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Participant> list(@RequestParam(required = false) String keyword) {
        // GET /api/participants hoặc GET /api/participants?keyword=... dùng để hiển thị/tìm kiếm dữ liệu.
        return service.findAll(keyword);
    }

    @GetMapping("/{id}")
    public Participant detail(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Participant create(@Valid @RequestBody ParticipantRequest request) {
        // POST nhận JSON từ giao diện rồi thêm một thí sinh mới vào MySQL.
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Participant update(@PathVariable Long id, @Valid @RequestBody ParticipantRequest request) {
        // PUT cập nhật bản ghi theo id, dùng khi bấm nút Sửa trên giao diện.
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        // DELETE xóa bản ghi khỏi bảng participants trong MySQL.
        service.delete(id);
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return service.stats();
    }
}
