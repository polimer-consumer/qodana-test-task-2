# Bugban Analysis Tool

This is a Kotlin-based command-line tool that processes Bugban analysis JSON output files. It uses the Clikt library to handle command-line inputs and the Jackson library for JSON processing.
Example input and output files are in **test/resources** directory.

## Running the Application

To run the application, use the following command in the terminal. Replace the placeholder paths with the actual paths to input and output JSON files.

```bash
./gradlew run -PappArgs="--first-file,/path/to/first.json,--second-file,/path/to/second.json,--only-first-output,/path/to/firstOutput.json,--only-second-output,/path/to/secondOutput.json,--both-output,/path/to/bothOutput.json"
