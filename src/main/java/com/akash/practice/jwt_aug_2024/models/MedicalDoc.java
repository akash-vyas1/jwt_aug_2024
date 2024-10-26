package com.akash.practice.jwt_aug_2024.models;

import java.util.ArrayList;
import java.util.List;

import com.akash.practice.jwt_aug_2024.utilities.UniqueId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MedicalDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int uniqueId;
    private int doctorId;
    // private HumanBeing doctor;
    private int patientId;
    private double billAmount;
    private String doctorComments;
    private String checkUpDate;

    public MedicalDoc(int patient, int doctor, double billAmount, String doctorComments,
            String checkUpDate) {
        this.uniqueId = UniqueId.getNextMedicalReportCount();
        this.patientId = patient;
        this.doctorId = doctor;
        this.billAmount = billAmount;
        this.doctorComments = doctorComments;
        this.checkUpDate = checkUpDate;
    }

    public MedicalDoc() {
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patient) {
        this.patientId = patient;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctor) {
        this.doctorId = doctor;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public String getDoctorComments() {
        return doctorComments;
    }

    public void setDoctorComments(String doctorComments) {
        this.doctorComments = doctorComments;
    }

    public String getCheckUpDate() {
        return checkUpDate;
    }

    public void setCheckUpDate(String checkUpDate) {
        this.checkUpDate = checkUpDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setValues(MedicalDoc doc) {
        // this.doctorId = doc.getDoctorId();
        // this.patientId = doc.getPatientId();
        this.billAmount = doc.getBillAmount();
        this.doctorComments = doc.getDoctorComments();
        this.checkUpDate = doc.getCheckUpDate();

    }

    @Override
    public String toString() {
        return "Medical Document [id=" + uniqueId + ", patient ID=" + patientId + ", doctor ID=" + doctorId
                + ", billAmount="
                + billAmount
                + ", doctorComments=" + doctorComments + ", checkUpDate=" + checkUpDate + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // private int doctorId;
        // private int patientId;
        // private double billAmount;
        // private String doctorComments;
        // private String checkUpDate;
        try {
            MedicalDoc tempDoc = (MedicalDoc) obj;
            // boolean isDoctorIdMatching = this.doctorId == tempDoc.getDoctorId();
            // boolean isPatientIdMatching = this.patientId == tempDoc.getPatientId();
            boolean isBillAmountMatching = this.billAmount == tempDoc.getBillAmount();
            boolean isDoctorCommentMatching = this.doctorComments.equals(tempDoc.getDoctorComments());
            boolean isCheckupDateMatching = this.checkUpDate.equals(tempDoc.getCheckUpDate());
            if (/* isDoctorIdMatching && isPatientIdMatching && */ isBillAmountMatching && isDoctorCommentMatching
                    && isCheckupDateMatching) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Can not convert object to human being");
        }
        System.out.println("Can not convert object to human being");
        return false;
    }

    public String getMatchingString(MedicalDoc old, MedicalDoc newUser) {
        List<String> matching = new ArrayList<>();
        List<String> notMatching = new ArrayList<>();

        boolean isDoctorIdMatching = old.getDoctorId() == newUser.getDoctorId();

        if (isDoctorIdMatching) {
            matching.add("Doctor Id");
        } else {
            notMatching.add("Doctor Id");
        }
        boolean isPatientIdMatching = old.getPatientId() == newUser.getPatientId();

        if (isPatientIdMatching) {
            matching.add("Patient Id");
        } else {
            notMatching.add("Patient Id");
        }
        boolean isBillAmountMatching = old.getBillAmount() == newUser.getBillAmount();

        if (isBillAmountMatching) {
            matching.add("Bill amount");
        } else {
            notMatching.add("Bill amount");
        }
        boolean isDoctorCommentMatching = old.getDoctorComments().equals(newUser.getDoctorComments());

        if (isDoctorCommentMatching) {
            matching.add("Doctor comment");
        } else {
            notMatching.add("Doctor comment");
        }

        boolean isCheckupDateMatching = old.getCheckUpDate().equals(newUser.getCheckUpDate());
        if (isCheckupDateMatching) {
            matching.add("Checkup date");
        } else {
            notMatching.add("Checkup date");
        }

        StringBuilder matchingString = new StringBuilder("");
        if (matching.isEmpty()) {
            matchingString.append("All values are updated !");
        } else if (matching.size() == 1) {
            matchingString.append("Only " + matching.get(0) + " is matching.");
        } else {
            for (int i = 0; i < matching.size(); i++) {
                if (i == (matching.size() - 2)) {
                    matchingString.append(matching.get(i));
                } else if (i == (matching.size() - 1)) {
                    matchingString.append(" and ").append(matching.get(i));
                } else {
                    matchingString.append(matching.get(i)).append(", ");
                }
            }
            matchingString.append(" are matching.");
        }
        // System.out.println("1. " + matchingString);

        StringBuilder notMatchingString = new StringBuilder("");
        if (notMatching.isEmpty()) {
            notMatchingString.append("All values are same !");
        } else if (notMatching.size() == 1) {
            notMatchingString.append("Only " + notMatching.get(0) + " is updated !");
        } else {
            for (int i = 0; i < notMatching.size(); i++) {
                if (i == (notMatching.size() - 2)) {
                    notMatchingString.append(notMatching.get(i));
                } else if (i == (notMatching.size() - 1)) {
                    notMatchingString.append(" and ").append(notMatching.get(i));
                    // notMatchingString = notMatchingString + "and " + notMatching.get(i);
                } else {
                    notMatchingString.append(notMatching.get(i)).append(", ");
                    // notMatchingString = notMatchingString + notMatching.get(i);
                }
            }
            notMatchingString.append(" are updated !");
        }
        // System.out.println("2. " + notMatchingString);

        return matchingString.append("\n").append(notMatchingString).toString();

    }

}
