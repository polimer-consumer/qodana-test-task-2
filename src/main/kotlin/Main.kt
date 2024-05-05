package com.polimerconsumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class Problem(val hash: String, val data: List<String>)
data class BugbanOutput(val problems: List<Problem>)

class BugbanCLI : CliktCommand() {
    private val firstFile: String by option(
        help = "First Bugban analysis path"
    ).required()
    private val secondFile: String by option(
        help = "Second Bugban analysis path"
    ).required()
    private val onlyFirstOutput: String by option(
        help = "Output file for problems only detected in 1st analysis"
    ).required()
    private val onlySecondOutput: String by option(
        help = "Output file for problems only detected in 2nd analysis"
    ).required()
    private val bothOutput: String by option(
        help = "Output file for problems detected in both analyses"
    ).required()

    override fun run() {
        val objectMapper = jacksonObjectMapper()
        val firstFileProblems = readProblems(firstFile, objectMapper)
        val secondFileProblems = readProblems(secondFile, objectMapper)
        val onlyFirst = firstFileProblems.keys - secondFileProblems.keys
        val onlySecond = secondFileProblems.keys - firstFileProblems.keys
        val both = firstFileProblems.keys.intersect(secondFileProblems.keys)

        writeOutput(onlyFirstOutput, onlyFirst, firstFileProblems, objectMapper)
        writeOutput(onlySecondOutput, onlySecond, secondFileProblems, objectMapper)
        writeOutput(bothOutput, both, firstFileProblems, objectMapper)
    }
}

fun readProblems(filePath: String, objectMapper: ObjectMapper): Map<String, Set<String>> {
    val file = File(filePath)
    val output: BugbanOutput = objectMapper.readValue(file)
    return output.problems.associate { it.hash to it.data.toSet() }
}

fun writeOutput(
    filePath: String,
    hashes: Set<String>,
    problemInfo: Map<String, Set<String>>,
    objectMapper: ObjectMapper
) {
    val problems = hashes.map { hash ->
        Problem(hash, problemInfo[hash]?.toList() ?: listOf())
    }
    val output = BugbanOutput(problems)
    objectMapper.writeValue(File(filePath), output)
}

fun main(args: Array<String>) = BugbanCLI().main(args)
