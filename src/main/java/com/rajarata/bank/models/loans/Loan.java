package com.rajarata.bank.models.loans;

import com.rajarata.bank.models.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    private String LoanId;
    private Customer customer;
    private double principleAmount;
    private double interestRate;
    private int termMonts;
    private double monthlyPayment;
    private double remainBalance;
    private LocalDate startDate;
    private LocalDate nextDueDate;
    private String status;
    private List<LoanPayment> paymentHistory;
    private List<RepaymentSchedule> repaymentSchedule;



    public Loan(String loanId, double principleAmount, Customer customer,double interestRate,int termMonths) {
        this.LoanId = loanId;
        this.principleAmount = principleAmount;
        this.customer = customer;
        this.interestRate = interestRate;
        this.termMonts = termMonths;
        this.remainBalance = principleAmount;
        this.startDate = LocalDate.now();
        this.status = "ACTIVE";
        this.paymentHistory = new ArrayList<>();
        this.repaymentSchedule = new ArrayList<>();
        calculateMonthlyPayment();

    }
    private void calculateMonthlyPayment(){
        double monthlyRate = interestRate / 100 / 12 ;
        this.monthlyPayment = (principleAmount * monthlyRate *Math.pow(1 + monthlyRate, termMonts)) / (Math.pow(1 + monthlyRate, termMonts) - 1);
    }

    public void generateRepaymentSchedule(){
        double balance = principleAmount;
        double monthlyRate = interestRate / 100 / 12;
        LocalDate dueDate = startDate.plusMonths(1);

        for(int month = 1; month <= termMonts; month++){
            double interestPayment = balance * monthlyRate;
            double prin
        }


    }


  
}
