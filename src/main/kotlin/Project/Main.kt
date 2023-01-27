package Project

data class Transaction(
    val operationType: String,
    val timestamp: String,
    val value: Double
): Comparable<Transaction> {
    override fun compareTo(other: Transaction): Int {
        val timestampComparison = other.timestamp.compareTo(timestamp)
        val equalOperationType = (other.operationType == operationType)
        val equalValue = (other.value == value)
        if (timestampComparison == 0 && equalOperationType && equalValue)
            return 0
        else if (timestampComparison == 0 && equalOperationType)
            return 1
        else if (timestampComparison == 0 && equalValue)
            return 1
        return when (timestampComparison) {
            1 -> -1
            -1 -> 1
            else -> 0
        }
    }
}

data class Account(
    val accountHolder: String,
    val agencyId: String,
    val accountId: String,
    val bank: String,
    var balance: Double,
    val transactions: MutableSet<Transaction>
)

class BankSystem {
    private val accounts = HashMap<String, Account>()

    fun returnAccount(accountHolder: String): Account? {
        return accounts[accountHolder]
    }

    fun addAccount(accountHolder: String, account: Account) {
        accounts[accountHolder] = account
    }

    fun addTransaction(accountHolder: String, transaction: Transaction) {
        val transactionAdded = accounts[accountHolder]?.transactions?.add(transaction)

        if (transactionAdded == true)
            updateBalance(accountHolder, transaction)
    }

    fun printAccounts() {
        accounts.forEach {
            println("*******")
            println("Account holder: ${it.value.accountHolder}")
            println("Agency ID: ${it.value.agencyId}")
            println("Account ID: ${it.value.accountId}")
            println("Bank: ${it.value.bank}")
            println("Balance: $ ${it.value.balance}")
            it.value.transactions.forEach { it2 ->
                println(it2)
            }
            println()
        }
    }

    private fun updateBalance(accountHolder: String, transaction: Transaction) {
        if (transaction.operationType == "SAQUE") {
            accounts[accountHolder]!!.balance -= transaction.value
        } else if (transaction.operationType == "DEPOSITO") {
            accounts[accountHolder]!!.balance += transaction.value
        }
    }
}

fun main() {
    val bankSystem = BankSystem()
    val csvData = readCsv("operacoes.csv")

    csvData.forEach {
        val agencyId = it[0]
        val accountId = it[1]
        val bank = it[2]
        val accountHolder = it[3]
        val operationType = it[4]
        val timestamp = it[5]
        val value = it[6].toDouble()

        val accountExist = (bankSystem.returnAccount(accountHolder) != null)
        if (!accountExist) {
            val account = Account(accountHolder, agencyId, accountId, bank, 0.0, sortedSetOf())
            bankSystem.addAccount(accountHolder, account)
        }
        val transaction = Transaction(operationType, timestamp, value)
        bankSystem.addTransaction(accountHolder, transaction)
    }

    bankSystem.printAccounts()
}