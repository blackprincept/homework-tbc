package com.perullheim.homework.domain.usecase

import com.perullheim.homework.domain.cache.CacheManager
import com.perullheim.homework.domain.cache.PreferenceKeys
import com.perullheim.homework.domain.model.Session
import com.perullheim.homework.domain.repository.SessionRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckSavedSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val cacheManager: CacheManager
) {
    suspend operator fun invoke(): Boolean {
        val cachedToken = cacheManager.observe(key = PreferenceKeys.TOKEN_KEY).first()
        val cachedEmail = cacheManager.observe(key = PreferenceKeys.EMAIL_KEY).first()

        if (cachedToken == null || cachedEmail == null)
            return false

        val session = Session(token = cachedToken, email = cachedEmail)

        sessionRepository.setSession(session)

        return true
    }
}