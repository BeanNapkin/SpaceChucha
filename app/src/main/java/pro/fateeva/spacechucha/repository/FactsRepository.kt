package pro.fateeva.spacechucha.repository

interface FactsRepository {
    fun getFacts(): List<String>
}