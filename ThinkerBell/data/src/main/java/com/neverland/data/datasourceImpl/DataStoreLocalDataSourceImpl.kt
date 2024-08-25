package com.neverland.data.datasourceImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.neverland.core.utils.LoggerUtil
import com.neverland.data.datasource.DataStoreLocaleDataSource
import com.neverland.domain.enums.NoticeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreLocalDataSourceImpl @Inject constructor(
    private val preferenceStore: DataStore<Preferences>
): DataStoreLocaleDataSource {

    companion object {
        private val EXAMPLE_KEY = stringPreferencesKey("example_key")
        private val RECENT_SEARCH_WORDS_KEY = stringPreferencesKey("recent_search_words")
        private val CATEGORY_ORDER_KEY = stringPreferencesKey("category_order_key")
    }

    override suspend fun clearData(): Result<Boolean> {
        LoggerUtil.d("clearData 호출")

        return try {
            preferenceStore.edit { preferences ->
                preferences.clear()
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.success(false)
        }
    }

    override suspend fun saveExampleData(value: String): Result<Boolean> {
        LoggerUtil.d("saveExampleData 호출")

        return try {
            preferenceStore.edit { preferences ->
                preferences[EXAMPLE_KEY] = value
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.success(false)
        }
    }

    override fun readExampleData(): Flow<String?> {
        LoggerUtil.d("readExampleData 호출")

        return preferenceStore.data
            .map { preferences ->
                preferences[EXAMPLE_KEY]
            }
    }

    override suspend fun saveRecentSearchWord(word: String): Result<Boolean> {
        LoggerUtil.d("saveRecentSearchWord 호출")

        return try {
            preferenceStore.edit { preferences ->
                val currentWords = preferences[RECENT_SEARCH_WORDS_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
                currentWords.remove(word) // 중복 제거
                currentWords.add(0, word) // 가장 뒤에 추가
                if (currentWords.size > 9) {
                    currentWords.removeAt(currentWords.size - 1) // 가장 오래된 항목 제거
                }
                preferences[RECENT_SEARCH_WORDS_KEY] = currentWords.joinToString(",")
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.success(false)
        }
    }

    override suspend fun deleteRecentSearchWord(word: String): Result<Boolean> {
        LoggerUtil.d("deleteRecentSearchWord 호출")

        return try {
            preferenceStore.edit { preferences ->
                val currentWords = preferences[RECENT_SEARCH_WORDS_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
                currentWords.remove(word) // 해당 단어 제거
                preferences[RECENT_SEARCH_WORDS_KEY] = currentWords.joinToString(",")
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.success(false)
        }
    }

    override fun readRecentSearchWords(): Flow<List<String>> {
        LoggerUtil.d("readRecentSearchWords 호출")

        return preferenceStore.data
            .map { preferences ->
                preferences[RECENT_SEARCH_WORDS_KEY]?.split(",")?.filter { it.isNotEmpty() } ?: emptyList()
            }
    }

    override suspend fun saveCategoryOrder(list: List<NoticeType>): Result<Boolean> {
        LoggerUtil.d("saveRecentSearchWord 호출")

        return try {
            preferenceStore.edit { preferences ->
                preferences[CATEGORY_ORDER_KEY] = list.joinToString(",")
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.success(false)
        }
    }

    override fun readCategoryOrder(): Flow<List<NoticeType>> {
        LoggerUtil.d("readCategoryOrder 호출")

        return preferenceStore.data
            .map { preferences ->
                val data = preferences[CATEGORY_ORDER_KEY]?.split(",")?.filter { it.isNotEmpty() } ?: emptyList()
                data.map { NoticeType.valueOf(it) }
            }
    }
}