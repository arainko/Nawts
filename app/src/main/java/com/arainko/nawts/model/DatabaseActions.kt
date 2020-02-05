package com.arainko.nawts.model

interface DatabaseActions {
    val deleteAction: DatabaseAction
    val updateAction: DatabaseAction
    val addAction: DatabaseAction
}