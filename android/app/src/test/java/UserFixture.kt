import com.foss.foss.model.User

object UserFixture {

    fun createUser(): User = User(
        accessId = "abcdef",
        nickname = "신공학관캣대디",
        level = 450
    )
}
