package com.example.crud;

import com.example.crud.model.StudentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public String read(@PathVariable("id") Long id, Model model) {
        System.out.println(id);
        model.addAttribute(
                "student",
                studentService.readStudent(id)
        );
        return "read";
    }

    @GetMapping("/{id}/update-view")
    public String updateView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.readStudent(id));
        return "update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @RequestParam("name") String name, @RequestParam("email") String email) {
        // service 활용
        studentService.updateStudent(id, name, email);
        // **상세보기 페이지로 redirect**
        return String.format("redirect:/%s", id);
    }

    @GetMapping("/{id}/delete-view")
    public String deleteView(@PathVariable("id") Long id, Model model) {
        StudentDTO studentDTO = studentService.readStudent2(id);
        model.addAttribute("student", studentDTO);
        return "delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        studentService.deleteStudent2(id);
        return "redirect:/home";
    }
}
