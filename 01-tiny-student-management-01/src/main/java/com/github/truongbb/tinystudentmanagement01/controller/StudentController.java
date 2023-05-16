package com.github.truongbb.tinystudentmanagement01.controller;

import com.github.truongbb.tinystudentmanagement01.model.Student;
import com.github.truongbb.tinystudentmanagement01.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    //    @Autowired
    StudentService studentService;
//
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }

    //    @RequestMapping
    @GetMapping
    public String getStudents(Model model) {
        List<Student> students = studentService.getAlStudents();
        model.addAttribute("danhSachSinhVien", students);
        return "student-list";
    }

    @GetMapping("/create-form")
    public String forwardToCreateForm(Model model) {
        Student student = new Student();

        model.addAttribute("sinhVienToiMuonTaoMoi", student);
        return "create-student";
    }

    @PostMapping
    /**
     * @ModelAttribute trong trường hợp này các data được truyền qua parameter trên URL của request tới controller
     */
    public String createNewStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable("id") String id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String forwardToEditForm(@PathVariable("id") String id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("sinhVienToiMuonCapNhat", student);
        return "edit-student";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("student") Student student) {
        studentService.updateStudent(student);
        return "redirect:/students";
    }


}
