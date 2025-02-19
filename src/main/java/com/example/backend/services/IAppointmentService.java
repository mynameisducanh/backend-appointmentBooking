package com.example.backend.services;

import com.example.backend.dtos.AppointmentDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.responses.AppointmentResponses;

import java.util.List;

public interface IAppointmentService {
    AppointmentResponses createAppointment(AppointmentDTO appointmentDTO) throws Exception;
    AppointmentResponses getAppointment(Integer id) throws DataNotFoundException;
    AppointmentResponses updateAppointment(Integer id,AppointmentDTO appointmentDTO) throws DataNotFoundException;

    void deleteApp(Integer id) throws DataNotFoundException;
    List<AppointmentResponses> getAllApp(Integer user_id);
}
