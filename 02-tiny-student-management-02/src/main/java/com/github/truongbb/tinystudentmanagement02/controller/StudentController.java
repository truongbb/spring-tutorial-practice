package com.github.truongbb.tinystudentmanagement02.controller;

import com.github.truongbb.tinystudentmanagement02.model.StudentModel;
import com.github.truongbb.tinystudentmanagement02.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    StudentService studentService;

    @GetMapping
    public String getStudents(Model model) {
        List<StudentModel> studentModels = studentService.getAlStudents();
        model.addAttribute("danhSachSinhVien", studentModels);
        return "student-list";
    }

    @GetMapping("/create-form")
    public String forwardToCreateForm(Model model) {
        StudentModel studentModel = new StudentModel();

        model.addAttribute("sinhVienToiMuonTaoMoi", studentModel);
        return "create-student";
    }

    @PostMapping
    /**
     * @ModelAttribute trong trường hợp này các data được truyền qua parameter trên URL của request tới controller
     */
    public String createNewStudent(@ModelAttribute("sinhVienToiMuonTaoMoi") @Valid StudentModel studentModel, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "create-student";
        } else {
            studentService.saveStudent(studentModel);
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
        StudentModel studentModel = studentService.findById(id);
        model.addAttribute("sinhVienToiMuonCapNhat", studentModel);
        return "update-student";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("sinhVienToiMuonCapNhat") @Valid StudentModel studentModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update-student";
        }
        studentService.updateStudent(studentModel);
        return "redirect:/students";
    }


}
