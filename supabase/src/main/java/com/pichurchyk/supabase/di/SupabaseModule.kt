package com.pichurchyk.supabase.di

import com.pichurchyk.supabase.supabaseClient
import io.github.jan.supabase.SupabaseClient
import org.koin.dsl.module

val supabaseModule = module {
    single<SupabaseClient> { supabaseClient }
}