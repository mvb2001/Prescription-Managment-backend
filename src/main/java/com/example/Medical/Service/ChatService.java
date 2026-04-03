package com.example.Medical.Service;

import com.example.Medical.model.Patient;
import com.example.Medical.model.Prescription;
import com.example.Medical.Repository.PatientRepository;
import com.example.Medical.Repository.PrescriptionRepository;
import com.example.Medical.Utils.ChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    private static final int MAX_HISTORY_MESSAGES = 20;

    private final Map<String, Deque<Map<String, String>>> conversationHistory = new ConcurrentHashMap<>();

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private AIService aiService;

    public String handleQuery(String query, String doctorEmail) {

        String intent = ChatUtils.detectIntent(query);

        if (intent.equals("PATIENT_QUERY")) {
            return handlePatientQuery(query, doctorEmail);
        } else {
            Deque<Map<String, String>> history = conversationHistory.computeIfAbsent(doctorEmail, key -> new LinkedList<>());

            history.addLast(Map.of("role", "user", "content", query));
            trimHistory(history);

            String assistantReply = aiService.askAI(new ArrayList<>(history), query);

            history.addLast(Map.of("role", "assistant", "content", assistantReply));
            trimHistory(history);

            return assistantReply;
        }
    }

    private void trimHistory(Deque<Map<String, String>> history) {
        while (history.size() > MAX_HISTORY_MESSAGES) {
            history.removeFirst();
        }
    }

    private String handlePatientQuery(String query, String doctorEmail) {

        String nic = ChatUtils.extractNIC(query);

        if (nic == null) {
            return "Please provide a valid 12-digit NIC for patient-specific records. For general medical advice, ask the question without requesting patient records.";
        }

        Patient patient = patientRepo
                .findByNicAndDoctorEmail(nic, doctorEmail)
                .orElse(null);

        if (patient == null) {
            return "Patient not found or access denied.";
        }

        List<Prescription> prescriptions =
                prescriptionRepo.findByPatient_NicAndDoctorEmail(nic, doctorEmail);

        return formatResponse(patient, prescriptions);
    }

    private String formatResponse(Patient patient, List<Prescription> prescriptions) {

        StringBuilder res = new StringBuilder();

        res.append("👤 Patient: ")
           .append(patient.getFirstName())
           .append(" ")
           .append(patient.getLastName())
           .append("\nNIC: ")
           .append(patient.getNic())
           .append("\n\n");

        if (prescriptions.isEmpty()) {
            res.append("No prescriptions found.");
            return res.toString();
        }

        for (Prescription p : prescriptions) {
            res.append("📅 Date: ").append(p.getCreatedAt()).append("\n");
            res.append("BP: ").append(p.getBpLevel()).append("\n");
            res.append("FBS: ").append(p.getFbsLevel()).append("\n");
            res.append("Temp: ").append(p.getBodyTemperature()).append("\n");
            res.append("Medicines Given: ").append(p.getMedicineIssue()).append("\n");
            res.append("Medicines To Get: ").append(p.getMedicineToGet()).append("\n");
            res.append("Notes: ").append(p.getNote()).append("\n");
            res.append("----------------------------------\n");
        }

        return res.toString();
    }
}