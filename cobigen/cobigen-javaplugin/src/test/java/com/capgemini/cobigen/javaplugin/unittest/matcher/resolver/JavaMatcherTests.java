package com.capgemini.cobigen.javaplugin.unittest.matcher.resolver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.cobigen.extension.to.VariableAssignmentTo;
import com.capgemini.cobigen.javaplugin.matcher.JavaMatcher;

/**
 *
 * @author sholzer (Nov 18, 2015)
 */
public class JavaMatcherTests {

    /**
     * Object under test
     *
     */
    private JavaMatcher javaMatcher;

    /**
     * @throws java.lang.Exception
     *             shouldn't occur
     * @author sholzer (Nov 18, 2015)
     */
    @Before
    public void setUp() throws Exception {
        javaMatcher = new JavaMatcher();
    }

    /**
     * Test method for
     * {@link com.capgemini.cobigen.javaplugin.matcher.JavaMatcher#getResolvedVariables(com.capgemini.cobigen.javaplugin.matcher.JavaMatcher.MatcherType, java.lang.String, java.lang.String, java.util.List)}
     * . Tests if the algorithm handles empty variables correctly
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void resolveEmptyRegexVariable() {
        List<VariableAssignmentTo> variables = new LinkedList<>();
        VariableAssignmentTo rootPackage = new VariableAssignmentTo("regex", "rootPackage", "1");
        VariableAssignmentTo domain = new VariableAssignmentTo("regex", "domain", "3");
        VariableAssignmentTo component = new VariableAssignmentTo("regex", "component", "4");
        VariableAssignmentTo detail = new VariableAssignmentTo("regex", "detail", "5");
        VariableAssignmentTo typeName = new VariableAssignmentTo("regex", "typeName", "6");
        variables.add(rootPackage);
        variables.add(domain);
        variables.add(component);
        variables.add(detail);
        variables.add(typeName);

        String inputValue = "de.tukl.abc.project.standard.datatype.common.api.LongText";
        String regex = "((.+\\.)?([^.]+))\\.(datatype)\\.common\\.api(\\..*)?\\.([^.]+)";

        Map<String, String> result = javaMatcher.getResolvedVariables(null, regex, inputValue, variables);
        assertNull("Expected detail to be null: " + result.toString(), result.get("detail"));

        regex = "((.+\\.)?([^\\.]+))\\.([^\\.]+)\\.dataaccess\\.api(\\..*)?\\.([^\\.]+)Embeddable";
        inputValue = "de.tukl.abc.project.standard.datatype.common.api.someEmbeddable";

        result = javaMatcher.getResolvedVariables(null, regex, inputValue, variables);
        assertNull("Expected detail to be null: " + result.toString(), result.get("detail"));
    }

    /**
     * Test method for
     * {@link com.capgemini.cobigen.javaplugin.matcher.JavaMatcher#getResolvedVariables(com.capgemini.cobigen.javaplugin.matcher.JavaMatcher.MatcherType, java.lang.String, java.lang.String, java.util.List)}
     * . tests if the algorithm handles non empty variables correctly (control-test to
     * {@link #resolveEmptyRegexVariable()})
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void resolveNonEmptyRegexVariable() {
        List<VariableAssignmentTo> variables = new LinkedList<>();
        VariableAssignmentTo rootPackage = new VariableAssignmentTo("regex", "rootPackage", "1");
        VariableAssignmentTo domain = new VariableAssignmentTo("regex", "domain", "3");
        VariableAssignmentTo component = new VariableAssignmentTo("regex", "component", "4");
        VariableAssignmentTo detail = new VariableAssignmentTo("regex", "detail", "5");
        VariableAssignmentTo typeName = new VariableAssignmentTo("regex", "typeName", "6");
        variables.add(rootPackage);
        variables.add(domain);
        variables.add(component);
        variables.add(detail);
        variables.add(typeName);

        String inputValue = "de.tukl.abc.project.standard.datatype.common.api.subpackage.LongText";
        String regex = "((.+\\.)?([^.]+))\\.(datatype)\\.common\\.api(\\..*)?\\.([^.]+)";

        Map<String, String> result = javaMatcher.getResolvedVariables(null, regex, inputValue, variables);
        assertNotNull("Expected detail to be not null: " + result.toString(), result.get("detail"));

        regex = "((.+\\.)?([^.]+))\\.([^.]+)\\.dataaccess\\.api(\\..*)?\\.([^.]+)Embeddable";
        inputValue = "de.tukl.abc.project.standard.datatype.dataaccess.api.my.subpackage.someEmbeddable";

        result = javaMatcher.getResolvedVariables(null, regex, inputValue, variables);
        assertNotNull("Expected detail to be not null: " + result.toString(), result.get("detail"));
    }
}