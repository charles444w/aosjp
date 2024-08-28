package jcp.apps.t

data class TransactionDataModel(
    val id: Int,
    val transactionData: String? = "1-1-1",
    val description: String? = "desc",
    val category: String? = "cate",
    val debit: Int? = 1,
    val credit: Int? = 2
) {
}