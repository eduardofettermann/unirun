package br.unisinos.unirun.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(formatter);
        String path = request.getRequestURI();
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

        logger.error("ResponseStatusException at {}: {} - Status: {}, Reason: {}",
                path, timestamp, status, ex.getReason(), ex);

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("timestamp", timestamp);
        mav.addObject("status", status.value());
        mav.addObject("error", status.getReasonPhrase());
        mav.addObject("message", ex.getReason() != null ? ex.getReason() : "An error occurred");
        mav.addObject("path", path);
        mav.setStatus(status);

        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(formatter);
        String path = request.getRequestURI();

        logger.error("Unexpected exception at {}: {} - {}",
                path, timestamp, ex.getMessage(), ex);

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("timestamp", timestamp);
        mav.addObject("status", 500);
        mav.addObject("error", "Internal Server Error");
        mav.addObject("message", "An unexpected error occurred. Please try again later.");
        mav.addObject("path", path);
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return mav;
    }
}
