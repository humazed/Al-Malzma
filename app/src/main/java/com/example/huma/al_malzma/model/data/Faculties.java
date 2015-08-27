package com.example.huma.al_malzma.model.data;


public interface Faculties {

    interface Engineering {
        public static final String FACULTY_ENGINEERING = "engineering";

        interface Departments {
            String DEPARTMENT_PREP = "prep";
            String DEPARTMENT_SYSTEMS_AND_COMPUTER_ENGINEERING = "systems and computer engineering";
            String DEPARTMENT_PETROL_ENGINEERING = "petrol engineering";
            String DEPARTMENT_ELECTRICAL_ENGINEERING = "electrical engineering";
            String DEPARTMENT_ARCHITECTURE_ENGINEERING = "architecture engineering";
            String DEPARTMENT_CIVIL_ENGINEERING = "civil engineering";
            String DEPARTMENT_MECHANICAL_ENGINEERING = "mechanical engineering";
            String DEPARTMENT_URBAN_DESIGN_ENGINEERING = "urban design engineering";
            String DEPARTMENT_ERROR = "error";
        }

        interface Grades {
            String GRADE_1 = "1";
            String GRADE_2 = "2";
            String GRADE_3 = "3";
            String GRADE_4 = "4";
            String GRADE_ERROR = "error";
        }
    }
}