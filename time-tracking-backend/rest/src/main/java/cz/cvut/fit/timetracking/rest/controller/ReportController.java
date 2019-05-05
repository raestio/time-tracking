package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/monthly")
    public ResponseEntity<Object> getMonthlyReports() {

    }

    @GetMapping("/daily")
    public ResponseEntity<Object> getMonthlyReports() {

    }

    @GetMapping("/yearly")
    public ResponseEntity<Object> getMonthlyReports() {

    }

    @GetMapping("/projects")
    public ResponseEntity<Object> getMonthlyReports() {

    }

    @GetMapping("/users")
    public ResponseEntity<Object> getMonthlyReports() {

    }

    @GetMapping("/monthly/me")
    public ResponseEntity<Object> getMonthlyReports() {

    }

    @GetMapping("/daily/me")
    public ResponseEntity<Object> getMonthlyReports() {

    }

    @GetMapping("/yearly/me")
    public ResponseEntity<Object> getMonthlyReports() {

    }
}
