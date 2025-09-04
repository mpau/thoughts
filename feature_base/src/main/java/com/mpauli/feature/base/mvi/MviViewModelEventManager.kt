package com.mpauli.feature.base.mvi

import com.mpauli.base.util.Procedure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.reflect.KClass

class MviViewModelEventManager(private val coroutinesScope: CoroutineScope) {

    val handlers = mutableMapOf<KClass<out Any>, MutableList<Procedure<*>>>()

    inline fun <reified T : Any> addHandler(noinline handler: Procedure<T>) {
        Timber.d("addHandler(${T::class}, $handler)")

        addHandler(
            clazz = T::class,
            handler = handler
        )
    }

    fun <T : Any> addHandler(clazz: KClass<T>, handler: Procedure<T>) {
        synchronized(handlers) {
            val typeHandlers = handlers.getOrPut(clazz) { mutableListOf() }
            typeHandlers.add(handler as Procedure<*>)
        }
    }

    @Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
    internal inline fun <T : Any> queue(event: T) {
        Timber.d("queue($event)")

        coroutinesScope.launch {
            handlers[event::class]?.forEach { procedure ->
                Timber.d("Handling $event by $procedure")

                (procedure as? Procedure<T>)?.invoke(event)
            }
        }
    }
}
