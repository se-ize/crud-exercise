package com.example.crud;

import com.example.crud.model.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    //복수의 StudentDTO를 담는 멤버변수(필드)
    private final List<StudentDTO> studentList = new ArrayList<>();
    private Long nextId = 1L;


    //새로운 StudentDTO를 생성하는 메소드
    public StudentDTO createStudent(String name, String email) {
        StudentDTO newStudent = new StudentDTO(
                nextId, name, email
        );
        nextId++;
        studentList.add(newStudent);
        return newStudent;
    }

    public List<StudentDTO> readStudentAll() {
        return studentList;
    }
}
