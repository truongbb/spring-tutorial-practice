# tiny studentModel management 03

Basic validation in Spring boot 2.x + thymeleaf + validation + working with enum

Working with enum in thymeleaf: 
    - in create-student.html -> use thymeleaf support
    - in update-student.html -> use @ModelAttribute & mapping manually

Validation:
    - Using standard validation
    - Custom annotation for validation (in validation package)
    - @ControllerAdvice & @ExceptionHandler

## Tech stack
- HTML, CSS, Bootstrap
- Jdk 17
- Lombok
- Object mapper
- Spring boot 2.x:
    - Spring web MVC
    - Thymeleaf

## Basic features:

- List of studentModels
- Add new a studentModel
- Update a studentModel
- Delete a studentModel (with confirmation)

## Notes:
- No bootstrap popup use

## References:
[1] https://www.baeldung.com/thymeleaf-enums

[2] https://www.baeldung.com/spring-mvc-custom-validator

[3] https://levunguyen.com/laptrinhspring/2020/04/19/su-dung-validation-trong-spring/