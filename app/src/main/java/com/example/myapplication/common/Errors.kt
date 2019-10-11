package com.example.myapplication.common

sealed class CustomException(message: String) : Exception(message)

object UnexpectedException : CustomException("Неизвестная ошибка")

object NoInternetConnection : CustomException("Отсутствует подключение к интернету")

object NoActiveAccount : CustomException("Требуется подтверждение аккаунта администратором")

object AlreadyAuthorized : CustomException("Пользователь был авторизован на другом устройстве")

object PageNotFound : CustomException("Страница не найдена")

object InvalidPinCode : CustomException("Неверный пин код")
