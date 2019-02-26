@file:kotlin.jvm.JvmMultifileClass
@file:kotlin.jvm.JvmName("DSL")
@file:Suppress("unused", "EnumEntryName", "ReplaceSizeCheckWithIsNotEmpty", "FunctionName")

// MUST BE IN THE GLOBAL (ROOT) PACKAGE

import org.sonatype.maven.polyglot.kotlin.dsl.PomDsl
import org.sonatype.maven.polyglot.kotlin.dsl.Project
import java.io.File
import javax.script.Bindings

/**
 * Configures a Maven project model.
 */
@PomDsl
fun project(nameOrId: String? = null, block: Project.(Project) -> Unit): Project {
    return org.sonatype.maven.polyglot.kotlin.dsl.project(nameOrId, block)
}

/**
 * Invokes the script at the supplied location
 */
fun eval(scriptLocation: String, bindings: Map<String, Any?>): Any? {
    val scriptEngine = bindings["kotlin.script.engine"] as javax.script.ScriptEngine?
    if (scriptEngine != null) {
        val newBindings: Bindings = scriptEngine.createBindings()
        newBindings.putAll(bindings)
        return scriptEngine.eval(File(scriptLocation).reader(), newBindings)
    }
    return null
}