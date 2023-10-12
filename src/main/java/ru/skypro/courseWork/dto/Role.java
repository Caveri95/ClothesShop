package ru.skypro.courseWork.dto;

import lombok.Data;
/**
 * Перечисление, представляющее возможные роли для пользователей.
 */

public enum Role {
    /**
     * Роль обычного пользователь.
     */
    USER,

    /**
     * Роль администратора.
     */
    ADMIN
}
