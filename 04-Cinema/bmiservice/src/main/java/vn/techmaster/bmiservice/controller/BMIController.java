package vn.techmaster.bmiservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.bmiservice.request.BMIRequest;
import vn.techmaster.bmiservice.response.BMIResult;
import vn.techmaster.bmiservice.service.HealthService;

@RestController
@RequestMapping("/")
public class BMIController {
  @Autowired
  private HealthService healthService;


  @GetMapping("bmi/{height}/{weight}")
  public ResponseEntity<BMIResult> calculateBMI(@PathVariable String height, @PathVariable String weight) {
    float fheight;
    float fweight;
    try {
      fheight = Float.parseFloat(height);
      fweight = Float.parseFloat(weight);
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
    BMIRequest bmiRequest = new BMIRequest(fheight, fweight);
    return ResponseEntity.ok(healthService.calculateBMI(bmiRequest));
  }

  @GetMapping("bmi2/{height}/{weight}")
  public ResponseEntity<BMIResult> calculateBMI2(@PathVariable String height, @PathVariable String weight) {
    float fheight;
    float fweight;
    try {
      fheight = Float.parseFloat(height);
      fweight = Float.parseFloat(weight);
    } catch (NumberFormatException e) {
      return ResponseEntity.ok(new BMIResult(-1, "Bad number format"));
    }
    BMIRequest bmiRequest = new BMIRequest(fheight, fweight);
    return ResponseEntity.ok(healthService.calculateBMI(bmiRequest));
  }


}


