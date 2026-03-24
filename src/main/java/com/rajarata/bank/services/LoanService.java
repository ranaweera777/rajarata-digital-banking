package com.rajarata.bank.services;

import com.rajarata.bank.models.loans.Loan;
import com.rajarata.bank.models.loans.LoanApplication;
import com.rajarata.bank.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class LoanService {

    private List<Loan> loans;
    private List<LoanApplication> pendingApplications;

    public LoanService() {
        this.loans = new ArrayList<>();
        this.pendingApplications = new ArrayList<>();
    }

public LoanApplication applyForLoan(Customer customer, double amount, int termMonths, String purpose){
    LoanApplication application = new LoanApplication(generateApplicationId(),customer,amount,termMonths,purpose);
       pendingApplications.add(application);
       System.out.println("Loan application submitted: " + application.getApplicationId()
    );
    return application;
}
//modify loan and loan application classes
public Loan approveLoan(LoanApplication application, double interestRate){
     application.approve();
     Loan loan = new Loan(
        generateLoanId(),
            application.getCustomer(),
            application.getAmount(),
            interestRate,
            application.getTermMonths()
     );

     loans.add(loan);
     pendingApplications.remove(application);
     
//modify loan and loan application classes
     loan.generateRepaymentSchedule();
     System.out.println("Loan approved: " + loan.getLoanId());
     return loan;
}

public void rejection(LoanApplication application, String reason){
    application.reject(reason);
pendingApplications.remove(application);
        System.out.println("Loan rejected: " + application.getApplicationId() + " Reason: " + reason);
}

public double calculateMonthlyPayment(double principle,double annualInterestRate, int termMonths){
    double monthlyInterestRate = annualInterestRate/100/12;
    return(principle * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, termMonths))/  (Math.pow(1 + monthlyInterestRate, termMonths) - 1);
}
public double calculatePenalty(Loan loan,int daysLate){
    double penaltyRate = 0.05;
    return loan.getMonthlyPayment() * penaltyRate * daysLate;
}

private String generateApplicationId(){
    return "APP-" + System.currentTimeMillis();
}
  
private String generateLoanId() {
        return "LOAN" + System.currentTimeMillis();
    }

    public List<Loan> getLoans() { return new ArrayList<>(loans); }
    public List<LoanApplication> getPendingApplications() { return new ArrayList<>(pendingApplications); }   

}


