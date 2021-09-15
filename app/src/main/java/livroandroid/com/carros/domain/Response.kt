package livroandroid.com.carros.domain

data class Response( val id: Long, val status: String, val url: String) {
    fun isOk() = "Created".equals(status, ignoreCase = true)
}