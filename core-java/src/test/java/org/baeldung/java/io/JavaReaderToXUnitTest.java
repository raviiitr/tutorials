package org.baeldung.java.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.FileWriteMode;

@SuppressWarnings("unused")
public class JavaReaderToXUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int DEFAULT_SIZE = 1500000;

    // tests - sandbox

    // tests - Reader to String

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoStringV1_thenCorrect() throws IOException {
        final StringReader reader = new StringReader("With Java 1");
        int intValueOfChar;
        String targetString = "";
        while ((intValueOfChar = reader.read()) != -1) {
            targetString += (char) intValueOfChar;
        }
        reader.close();
    }

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoStringV2_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader("With Java 1");
        final char[] arr = new char[8 * 1024];
        final StringBuilder buffer = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(arr, 0, arr.length)) != -1) {
            buffer.append(arr, 0, numCharsRead);
        }
        initialReader.close();
        final String targetString = buffer.toString();
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = CharSource.wrap("With Google Guava").openStream();
        final String targetString = CharStreams.toString(initialReader);
        initialReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader("With Apache Commons");
        final String targetString = IOUtils.toString(initialReader);
        initialReader.close();
    }

    // tests - Reader WRITE TO File

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoFile_thenCorrect() throws IOException {
        final File sourceFile = new File("src/test/resources/sourceFile.txt");
        sourceFile.createNewFile();

        final Reader initialReader = new FileReader(sourceFile);
        final char[] buffer = new char[(int) sourceFile.length()];
        initialReader.read(buffer);
        initialReader.close();

        final File targetFile = new File("src/test/resources/targetFile.txt");
        targetFile.createNewFile();

        final Writer targetFileWriter = new FileWriter(targetFile);
        targetFileWriter.write(buffer);
        targetFileWriter.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoFile_thenCorrect() throws IOException {
        final Reader initialReader = CharSource.wrap("IDDQD").openStream();
        final File targetFile = new File("src/test/resources/targetFile.txt");
        com.google.common.io.Files.touch(targetFile);
        final CharSink charSink = com.google.common.io.Files.asCharSink(targetFile, Charset.defaultCharset(), FileWriteMode.APPEND);
        charSink.writeFrom(initialReader);
        initialReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoFile_thenCorrect() throws IOException {
        final Reader initialReader = new CharSequenceReader("CharSequenceReader extends Reader");
        final File targetFile = new File("src/test/resources/targetFile.txt");
        FileUtils.touch(targetFile);
        final byte[] buffer = IOUtils.toByteArray(initialReader);
        FileUtils.writeByteArrayToFile(targetFile, buffer);
        initialReader.close();
    }

    // tests - Reader to byte[]

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoByteArray_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader("With Java");

        final char[] charArray = new char[8 * 1024];
        final StringBuilder builder = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(charArray, 0, charArray.length)) != -1) {
            builder.append(charArray, 0, numCharsRead);
        }
        final byte[] targetArray = builder.toString().getBytes();

        initialReader.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoByteArray_thenCorrect() throws IOException {
        final Reader initialReader = CharSource.wrap("With Google Guava").openStream();

        final byte[] targetArray = CharStreams.toString(initialReader).getBytes();

        initialReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoByteArray_thenCorrect() throws IOException {
        final StringReader initialReader = new StringReader("With Commons IO");

        final byte[] targetArray = IOUtils.toByteArray(initialReader);

        initialReader.close();
    }

}