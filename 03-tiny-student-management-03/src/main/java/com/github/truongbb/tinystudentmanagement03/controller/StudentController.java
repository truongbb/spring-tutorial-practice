package com.github.truongbb.tinystudentmanagement03.controller;

import com.github.truongbb.tinystudentmanagement03.dto.RegionDto;
import com.github.truongbb.tinystudentmanagement03.model.StudentCreateModel;
import com.github.truongbb.tinystudentmanagement03.model.StudentUpdateModel;
import com.github.truongbb.tinystudentmanagement03.service.RegionService;
import com.github.truongbb.tinystudentmanagement03.service.StudentService;
import com.github.truongbb.tinystudentmanagement03.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
        return "student-list";
    }

    @GetMapping("/create-form")
    public String forwardToCreateForm(Model model) {
        StudentCreateModel studentCreateModel = new StudentCreateModel();
        model.addAttribute("sinhVienMuonTaoMoi", studentCreateModel);
        return "create-student";
    }

    @PostMapping
    /** ==> không cần dùng @ModelAttribute khi class của attribute là model
     * @ModelAttribute trong trường hợp này các data được truyền qua parameter trên URL của request tới controller
     */
    public String createNewStudent(@ModelAttribute("sinhVienMuonTaoMoi") @Valid StudentCreateModel sinhVienMuonTaoMoi, Errors errors) {
//    public String createNewStudent(@Valid Student sinhVien, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "create-student";
        } else {
            studentService.saveStudent(sinhVienMuonTaoMoi);
            return "redirect:/students";
        }
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
