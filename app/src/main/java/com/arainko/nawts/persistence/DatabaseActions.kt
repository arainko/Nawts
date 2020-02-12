package com.arainko.nawts.persistence

interface DatabaseActions {
    val deleteAction: DatabaseAction
    val updateAction: DatabaseAction
    val addAction: DatabaseAction
}