package com.bajaj.assessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlSolverService {

    private static final Logger logger = LoggerFactory.getLogger(SqlSolverService.class);

    public String solveSqlProblem(String regNo) {
        // Extract last two digits
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastTwoDigitsInt = Integer.parseInt(lastTwoDigits);

        logger.info("Registration number: {}, Last two digits: {}", regNo, lastTwoDigits);

        if (lastTwoDigitsInt % 2 == 0) {
            // Even - Question 2
            return solveQuestion2();
        } else {
            // Odd - Question 1
            return solveQuestion1();
        }
    }

    private String solveQuestion1() {
        logger.info("Solving Question 1 (Odd regNo)");

        return "SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, FLOOR(DATEDIFF(CURDATE(), e.DOB) / 365) AS AGE, d.DEPARTMENT_NAME FROM PAYMENTS p JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID WHERE DAY(p.PAYMENT_TIME) != 1 ORDER BY p.AMOUNT DESC LIMIT 1;";
    }

    private String solveQuestion2() {
        logger.info("Solving Question 2 (Even regNo)");

        return "SELECT customer_id, SUM(order_amount) as total_amount FROM orders WHERE order_date >= '2023-01-01' GROUP BY customer_id HAVING SUM(order_amount) > 1000 ORDER BY total_amount DESC;";
    }
}
