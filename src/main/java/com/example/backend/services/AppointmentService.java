package com.example.backend.services;

import com.example.backend.dtos.AppointmentDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.models.Appointment;
import com.example.backend.models.Doctor;
import com.example.backend.models.Patient;
import com.example.backend.repositories.AppointmentRepository;
import com.example.backend.repositories.DoctorRepository;
import com.example.backend.repositories.PatientRepository;
import com.example.backend.responses.AppointmentResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentResponses createAppointment(AppointmentDTO appointmentDTO) throws DataNotFoundException {
        // Debugging logs
        System.out.println("Creating appointment with DTO: " + appointmentDTO);

        // Tìm kiếm bệnh nhân
        Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy bệnh nhân"));

        // Tìm kiếm bác sĩ
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy bác sĩ"));

        // Tạo cuộc hẹn mới
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(appointmentDTO.getAppointmentDate())
                .appointmentTime(appointmentDTO.getAppointmentTime())
                .status(appointmentDTO.getStatus() != null ? appointmentDTO.getStatus() : "scheduled")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Lưu cuộc hẹn và trả về response
        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponses.class);
    }
    @Override
    public AppointmentResponses getAppointment(Integer id) throws DataNotFoundException {
        // Tìm kiếm cuộc hẹn theo ID
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy cuộc hẹn"));

        return modelMapper.map(appointment, AppointmentResponses.class);
    }

    @Override
    public AppointmentResponses updateAppointment(Integer id, AppointmentDTO appointmentDTO) throws DataNotFoundException {
        // Tìm kiếm cuộc hẹn theo ID
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy cuộc hẹn"));

        // Cập nhật thông tin cuộc hẹn nếu có
        if (appointmentDTO.getAppointmentDate() != null) {
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        }
        if (appointmentDTO.getAppointmentTime() != null) {
            appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        }
        if (appointmentDTO.getStatus() != null) {
            appointment.setStatus(appointmentDTO.getStatus());
        }
        appointment.setUpdatedAt(LocalDateTime.now());

        // Lưu cuộc hẹn đã cập nhật và trả về response
        appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponses.class);
    }

    @Override
    public void deleteApp(Integer id) throws DataNotFoundException {
        // Kiểm tra sự tồn tại của cuộc hẹn
        if (!appointmentRepository.existsById(id)) {
            throw new DataNotFoundException("Không tìm thấy cuộc hẹn");
        }

        // Xóa cuộc hẹn
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentResponses> getAllApp(Integer userId) {
        // Lấy danh sách cuộc hẹn của bệnh nhân
        List<Appointment> appointments = appointmentRepository.findByPatientUserId(userId);

        // Chuyển đổi danh sách cuộc hẹn sang danh sách response
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponses.class))
                .collect(Collectors.toList());
    }
}
