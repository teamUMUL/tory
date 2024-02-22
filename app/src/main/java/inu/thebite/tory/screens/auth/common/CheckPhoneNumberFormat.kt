package inu.thebite.tory.screens.auth.common

fun isPhoneNumberFormattedCorrectly(phoneNumber: String): Boolean {
    // 정규 표현식 패턴: 010으로 시작하고, 중간에 -가 포함되며, 총 11자리 숫자와 2개의 -를 포함합니다.
    val pattern = "^010-\\d{4}-\\d{4}$".toRegex()
    return pattern.matches(phoneNumber)
}