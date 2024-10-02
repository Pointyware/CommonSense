package org.pointyware.commonsense.feature.ontology.di

import org.koin.core.module.Module

actual fun ontologyLocalPlatformModule(): Module = ontologyLocalAndroidModule()
