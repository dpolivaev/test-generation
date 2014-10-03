package org.dpolivaev.testgeneration.dsl.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper;
import org.junit.Assert;
import org.junit.rules.TestName;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class CompilationTestGoldenMasterHelper extends CompilationTestHelper{

	private static final String REFERENCES_FOLDER = "test-reference/";
	
	public void assertCompilesToFile(CharSequence source, final TestName testName) throws IOException {
		new File(REFERENCES_FOLDER).mkdirs();
		assertCompilesToFile(source, REFERENCES_FOLDER + testName.getMethodName() + ".java");
	}
	public void assertCompilesToFile(CharSequence source, final String file) throws IOException {
		assertCompilesTo(source, new File(file));
	}

	public void assertCompilesTo(CharSequence source, final File expectedResult) throws IOException {
		if(expectedResult.canRead()){
			String expectedCompilationResult = Resources.toString(expectedResult.toURI().toURL(), Charsets.UTF_8);
			assertCompilesTo(source, expectedCompilationResult);
		}
		else{
			final boolean[] called = {false};
			compile(source, new IAcceptor<CompilationTestHelper.Result>() {
				public void accept(Result r) {
						writeResultToFile(r, expectedResult);
					called[0] = true;
				}
			});
			Assert.assertTrue("Nothing was generated but the expectation was :\n"+expectedResult, called[0]);
		}
	}

	public void assertCompilesTo(CharSequence source, final CharSequence expected) throws IOException {
		final boolean[] called = {false};
		compile(source, new IAcceptor<CompilationTestHelper.Result>() {
			public void accept(Result r) {
				String actual = stringOf(r);
				Assert.assertEquals(removeEndWhiteSpace(expected), removeEndWhiteSpace(actual));
				called[0] = true;
			}

			public String removeEndWhiteSpace(final CharSequence text) {
				return text.toString().trim().replaceAll("[ \t]+(\r?\n)", "$1");
			}
		});
		Assert.assertTrue("Nothing was generated but the expectation was :\n"+expected, called[0]);
	}
	
	private void writeResultToFile(Result r, File outputFile) {
		try(Writer out = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream(outputFile), "UTF-8"))){
		    out.write(stringOf(r));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private String stringOf(Result r) {
		StringBuilder actual = new StringBuilder (r.getSingleGeneratedCode());
	    for(Issue issue : r.getErrorsAndWarnings()){
	    	actual.append(stringOf(issue));
	    }
		return actual.toString();
	}
	private String stringOf(Issue issue) {
		StringBuilder buffer = new StringBuilder(issue.getSeverity().name());
		buffer.append(":").append(issue.getMessage());
		buffer.append(" (");
		URI uriToProblem = issue.getUriToProblem();
		if (uriToProblem != null)
			buffer.append(uriToProblem.lastSegment());
		buffer.append(" line : ").append(issue.getLineNumber()).append(")");
		return buffer.toString();
	}

}
