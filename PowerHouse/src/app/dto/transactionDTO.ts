export class TransactionDTO {
    transactionId!: number
    dependentId!: string
    creditAccount!: string
    debitAccount!: string
    transactionAmount!: number
    transactionDate!: string
    status!: string
    transactionType!: string
    remark!: string
}