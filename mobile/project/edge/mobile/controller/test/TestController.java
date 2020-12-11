/**
 * 
 */
package project.edge.mobile.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author angel_000
 *         API测试画面。
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String main() {
        return "mobile/test/testApi";
    }
}
