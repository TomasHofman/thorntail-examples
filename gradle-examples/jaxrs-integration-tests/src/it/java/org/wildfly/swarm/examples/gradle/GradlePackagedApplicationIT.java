package org.wildfly.swarm.examples.gradle;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.wildfly.swarm.it.AbstractIntegrationTest;

/**
 * Simple integration test.
 */
public class GradlePackagedApplicationIT extends AbstractIntegrationTest {

    @Test
    public void testIt() {
        String text = getUrlContents("http://localhost:8181/api");
        assertThat(text).contains("Hello!");
    }
}
