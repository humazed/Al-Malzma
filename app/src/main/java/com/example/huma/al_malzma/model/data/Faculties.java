package com.example.huma.al_malzma.model.data;


//TODO: replace it with JsonAttributes.java
public interface Faculties {

    String TERM_1 = "1";
    String TERM_2 = "2";

    interface Engineering {
        String FACULTY_ENGINEERING = "Engineering";

        interface Departments {
            String DEPARTMENT_PREP = "prep";
            String DEPARTMENT_SYSTEMS_AND_COMPUTER_ENGINEERING = "Systems and computer engineering";
            String DEPARTMENT_PETROL_ENGINEERING = "Petrol engineering";
            String DEPARTMENT_ELECTRICAL_ENGINEERING = "Electrical engineering";
            String DEPARTMENT_ARCHITECTURE_ENGINEERING = "Architecture engineering";
            String DEPARTMENT_CIVIL_ENGINEERING = "Civil engineering";
            String DEPARTMENT_MECHANICAL_ENGINEERING = "Mechanical engineering";
            String DEPARTMENT_URBAN_DESIGN_ENGINEERING = "Urban design engineering";
        }

        interface Grades {
            String GRADE_1 = "1";
            String GRADE_2 = "2";
            String GRADE_3 = "3";
            String GRADE_4 = "4";
        }
    }
}