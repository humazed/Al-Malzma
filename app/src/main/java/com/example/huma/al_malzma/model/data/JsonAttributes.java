package com.example.huma.al_malzma.model.data;


public interface JsonAttributes {
    String NAME = "name";

    String FACULTIES = "faculties";
    String DEPARTMENTS = "departments";
    String GRADES = "grades";

    String TERM_0 = "0";
    String TERM_1 = "1";
    String TERM_2 = "2";

    String SUBJECTS = "subjects";

    interface Universities {

        interface AlAzharCairo {
            String UNIVERSITY_AL_AZHAR_CAIRO = "Al-Azhar-cairo";

            interface Faculties {

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
                }

            }
        }
    }

    interface Grades {
        String GRADE_0 = "0"; //no grades
        String GRADE_1 = "1";
        String GRADE_2 = "2";
        String GRADE_3 = "3";
        String GRADE_4 = "4";
        String GRADE_5 = "5";
        String GRADE_6 = "6";
        String GRADE_7 = "7";
    }


}