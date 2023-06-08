package com.example.crud;

import com.example.crud.model.StudentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
        //빈 객체 주입됨
    }

    @GetMapping("/create-view")
    public String createView() {
        return "create";
    }

    @PostMapping("/create")
    public String create(
            @RequestParam("name") String name,
            @RequestParam("email") String email) {
        System.out.println(name);
        System.out.println(email);
        StudentDTO newStudent = studentService.createStudent(name, email);
        System.out.println(newStudent.toString());
        return "redirect:/home"; //중복 데이터 입력 불가
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("studentList", studentService.readStudentAll());
        return "home";
    }
}
