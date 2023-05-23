package com.github.truongbb.tinystudentmanagement04.controller;

import com.github.truongbb.tinystudentmanagement04.dto.RegionDto;
import com.github.truongbb.tinystudentmanagement04.entity.Student;
import com.github.truongbb.tinystudentmanagement04.model.StudentCreateModel;
import com.github.truongbb.tinystudentmanagement04.model.StudentUpdateModel;
import com.github.truongbb.tinystudentmanagement04.service.RegionService;
import com.github.truongbb.tinystudentmanagement04.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    StudentService studentService;

    RegionService regionService;

    @GetMapping
    public String getStudents(Model model) {
        List<Student> studentModels = studentService.getAlStudents();
        model.addAttribute("danhSachSinhVien", studentModels);

        model.addAttribute("sinhVienMuonTaoMoi", new StudentCreateModel());

        return "student-list";
    }

    @PostMapping
    /** ==> không cần dùng @ModelAttribute khi class của attribute là model
     * @ModelAttribute trong trường hợp này các data được truyền qua parameter trên URL của request tới controller
     */
    public String createNewStudent(@ModelAttribute("sinhVienMuonTaoMoi") @Valid StudentCreateModel sinhVienMuonTaoMoi) {
        studentService.saveStudent(sinhVienMuonTaoMoi);
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable("id") String id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String forwardToUpdateForm(@PathVariable("id") String id, Model model) {
        StudentUpdateModel studentCreateModel = studentService.findById(id);
        model.addAttribute("sinhVienToiMuonCapNhat", studentCreateModel);
//        model.addAttribute("regionData", regionService.getAll());  // --> set data vào đây hoặc dùng @ModelAttribute như bên dưới cũng được
        return "update-student";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("sinhVienToiMuonCapNhat") @Valid StudentUpdateModel studentUpdateModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-student";
        }
        studentService.updateStudent(studentUpdateModel);
        return "redirect:/students";
    }

    @ModelAttribute("regionData")
    public List<RegionDto> getAllRegions() {
        return regionService.getAll();
    }


}
