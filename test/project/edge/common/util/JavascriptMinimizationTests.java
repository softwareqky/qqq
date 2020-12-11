package project.edge.common.util;

import org.junit.Test;

import java.io.File;

import garage.origin.common.util.ProcessUtils;

public class JavascriptMinimizationTests {

    @Test
    public void testMinimizeJavascript() {

        File pwd = new File(System.getProperty("user.dir") + File.separator + "WebContent"
                + File.separator + "js");
        System.out.println(pwd.getAbsolutePath());
        ProcessUtils.runCommand(pwd, null, 1, "java", "-jar", "closure-compiler-v20190121.jar",
                "--js_output_file=main.min.js", "main.js", "websocket.js");
    }
}
