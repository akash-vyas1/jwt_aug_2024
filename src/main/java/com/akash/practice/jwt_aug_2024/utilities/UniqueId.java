package com.akash.practice.jwt_aug_2024.utilities;

import org.springframework.stereotype.Component;

@Component
public class UniqueId {

    private UniqueId() {
    }

    static int userCount;
    static int medicalReportCount;

    public static int getNextUserCount() {
        return ++userCount;
    }

    public static int getNextMedicalReportCount() {
        return ++medicalReportCount;
    }

}
