package coroutines.exercises

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class BestStudentUseCase(
    private val repo: StudentsRepository
) {
    suspend fun getBestStudent(semester: String): Student {
        val student = coroutineScope {
            val studentIds = repo.getStudentIds(semester)
            if (studentIds.isEmpty()) {
                throw IllegalStateException("No students in the semester")
            }
            val students = studentIds.map { id ->
                async {
                    repo.getStudent(id)
                }
            }
            return@coroutineScope students.maxByOrNull { it.await().result }!!.await()
        }
        return student
    }
}

data class Student(val id: Int, val result: Double, val semester: String)

interface StudentsRepository {
    suspend fun getStudentIds(semester: String): List<Int>
    suspend fun getStudent(id: Int): Student
}
