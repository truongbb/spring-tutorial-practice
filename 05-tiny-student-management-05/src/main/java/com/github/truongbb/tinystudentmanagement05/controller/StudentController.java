package com.github.truongbb.tinystudentmanagement05.controller;

import com.github.truongbb.tinystudentmanagement05.entity.Student;
import com.github.truongbb.tinystudentmanagement05.model.request.StudentCreateRequest;
import com.github.truongbb.tinystudentmanagement05.model.request.StudentSearchRequest;
import com.github.truongbb.tinystudentmanagement05.model.request.StudentUpdateRequest;
import com.github.truongbb.tinystudentmanagement05.model.response.RegionResponse;
import com.github.truongbb.tinystudentmanagement05.service.RegionService;
import com.github.truongbb.tinystudentmanagement05.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping
public class StudentController {

    StudentService studentService;

    RegionService regionService;

    @GetMapping("/students")
    public String getStudents(Model model) {
        List<Student> studentModels = studentService.getAlStudents();
        model.addAttribute("danhSachSinhVien", studentModels);

        model.addAttribute("sinhVienMuonTaoMoi", new StudentCreateRequest());

        return "student-list";
    }

    @PostMapping("/students")
    /** ==> không cần dùng @ModelAttribute khi class của attribute là model
     * @ModelAttribute trong trường hợp này các data được truyền qua parameter trên URL của request tới controller
     */
    public String createNewStudent(@ModelAttribute("sinhVienMuonTaoMoi") @Valid StudentCreateRequest sinhVienMuonTaoMoi) {
        studentService.saveStudent(sinhVienMuonTaoMoi);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable("id") String id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String forwardToUpdateForm(@PathVariable("id") String id, Model model) {
        StudentUpdateRequest studentCreateModel = studentService.findById(id);
        model.addAttribute("sinhVienToiMuonCapNhat", studentCreateModel);
//        model.addAttribute("regionData", regionService.getAll());  // --> set data vào đây hoặc dùng @ModelAttribute như bên dưới cũng được
        return "update-student";
    }

    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute("sinhVienToiMuonCapNhat") @Valid StudentUpdateRequest studentUpdateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-student";
        }
        studentService.updateStudent(studentUpdateRequest);
        return "redirect:/students";
    }

    @ModelAttribute("regionData")
    public List<RegionResponse> getAllRegions() {
        return regionService.getAll();
    }

    ////////////////////////////////////////////////////////////////////

    @GetMapping("/api/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.findByIdVer2(id));
    }

    @PutMapping("/api/v1/students/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody @Valid StudentUpdateRequest studentUpdateRequest) {
        studentUpdateRequest.setId(id);
        studentService.updateStudent(studentUpdateRequest);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/api/v1/students")
    public ResponseEntity<?> search(StudentSearchRequest request) {
//        studentUpdateRequest.setId(id);
//        studentService.updateStudent(studentUpdateRequest);
        return ResponseEntity.ok(null);
    }

}
