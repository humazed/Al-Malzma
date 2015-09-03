package com.example.huma.al_malzma.persistence;


public interface JsonAttributes {
    String NAME = "name";

    String FACULTIES = "faculties";
    String DEPARTMENTS = "departments";
    String GRADES = "grades";
    String TERM_0 = "term_0";
    String TERM_1 = "term_1";
    String TERM_2 = "term_2";
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
        int GRADE_0 = 0; //no grades
        int GRADE_1 = 1;
        int GRADE_2 = 2;
        int GRADE_3 = 3;
        int GRADE_4 = 4;
        int GRADE_5 = 5;
        int GRADE_6 = 6;
        int GRADE_7 = 7;
    }


}