package com.arainko.nawts.persistence

inline class DatabaseAction(val action: (String, String, Int) -> Unit) {
    operator fun invoke(header: String = "", content: String = "", id: Int) = action(header, content, id)
}