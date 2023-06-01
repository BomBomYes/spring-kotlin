package com.tdmnsExamlpe.bankApplication.datasource.mock

import com.tdmnsExamlpe.bankApplication.datasource.BankDataSource
import com.tdmnsExamlpe.bankApplication.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockBankDataSource: BankDataSource {

    val banks = mutableListOf(
        Bank("Q2R2", 4.2, 1),
        Bank("PQ03", 4.9, 0),
        Bank("2ST1", 0.0, 1)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    
    override fun retrieveBank(accountNumber: String): Bank = banks.firstOrNull() { it.accountNumber == accountNumber }
        ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
    
    override fun createBank(bank: Bank): Bank {
        if (banks.any {it.accountNumber == bank.accountNumber}) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exist")
        }
        banks.add(bank)

        return bank
    }
    
    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull() { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

        banks.remove(currentBank)
    }
}