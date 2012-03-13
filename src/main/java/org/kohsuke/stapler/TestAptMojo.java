package org.kohsuke.stapler;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.CompilationFailureException;

import java.lang.reflect.Field;

/**
 * @goal apt-test-compile
 * @phase test-compile
 * @requiresDependencyResolution test
 * @author Kohsuke Kawaguchi
 */
public class TestAptMojo extends TestCompilerMojo {
    public void execute() throws MojoExecutionException, CompilationFailureException {
        // overwrite the compilerId value. This seems to be the only way to
        //do so without touching the copied files.
        setField("compilerId", "stapler-apt");

        super.execute();
    }

    private void setField(String name, String value) {
        try {
            Field field = AbstractCompilerMojo.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(this, value);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e); // impossible
        } catch (IllegalAccessException e) {
            throw new AssertionError(e); // impossible
        }
    }
}
