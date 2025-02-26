package com.abc.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.abc.Transaction;
import com.abc.utilities.enums.AccountType;
import com.abc.utilities.enums.TransactionType;

/**
 * Maxi Savings Account - inherits from Account class.
 */
public class MaxiSavingsAccount extends Account {
    private final BigDecimal LOW_ANNUAL_INTEREST_RATE = BigDecimal.valueOf(0.001);
    private final BigDecimal HIGH_ANNUAL_INTEREST_RATE = BigDecimal.valueOf(0.05);

    /**
     * Initialises a new Maxi Savings Account instance object
     */
    public MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    /**
     * Gets the current date and time
     * @return an immutable date-time object
     */
    private LocalDate getCurrentDate() {
        LocalDate currentDateTime = LocalDate.now();
        return currentDateTime;
    }

    /**
     * Calculates the number of days between the current date and the last date of withdrawal
     * @param transactionDate date of transaction
     * @return true if there are 10 or less days between the date of last withdrawal and the current date, false if more than 10 days
     */
    private boolean isWithinTenDays(LocalDate transactionDate) {
        long days = ChronoUnit.DAYS.between(getCurrentDate(), transactionDate);
        return !(days < -10);
    }

    /**
     * Checks if any withdrawals have occurred within the past ten days
     * @param transactions a list of withdrawals and/or deposits, their amounts and transaction dates
     * @return true if a withdrawal has occured within the last ten days of the current date, false if not
     */
    private boolean isWithdrawalWithinLastTenDays(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransactionDate();
            if (isWithinTenDays(transactionDate) && transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
                return true;
            }    
        }

        return false;
    }

    /*
     * Calculates the interest earned for a Maxi Savings Account
     */
    @Override
    public BigDecimal getAnnualInterestRate() {
        BigDecimal annualInterestRate = isWithdrawalWithinLastTenDays(transactions) ? LOW_ANNUAL_INTEREST_RATE : HIGH_ANNUAL_INTEREST_RATE;
        return annualInterestRate;
    }
}


