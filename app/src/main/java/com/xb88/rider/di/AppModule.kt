package com.xb88.rider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.xb88.rider.data.local.DatabaseSeeder
import com.xb88.rider.data.local.XB88Database
import com.xb88.rider.data.local.dao.BikeProfileDao
import com.xb88.rider.data.local.dao.ServiceTaskDao
import com.xb88.rider.data.repository.BikeRepositoryImpl
import com.xb88.rider.data.repository.ServiceTaskRepositoryImpl
import com.xb88.rider.domain.repository.BikeRepository
import com.xb88.rider.domain.repository.ServiceTaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        databaseProvider: Provider<XB88Database>,
        @ApplicationScope scope: CoroutineScope,
    ): XB88Database = Room.databaseBuilder(context, XB88Database::class.java, XB88Database.NAME)
        .addCallback(DatabaseSeeder(databaseProvider, scope))
        .build()

    @Provides
    fun provideBikeProfileDao(db: XB88Database): BikeProfileDao = db.bikeProfileDao()

    @Provides
    fun provideServiceTaskDao(db: XB88Database): ServiceTaskDao = db.serviceTaskDao()

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("xb88_settings") }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindBikeRepository(impl: BikeRepositoryImpl): BikeRepository

    @Binds
    abstract fun bindServiceTaskRepository(impl: ServiceTaskRepositoryImpl): ServiceTaskRepository
}
