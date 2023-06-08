import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class WebController {

    @RequestMapping(value = ["/"])
    fun index(): String {
        return "register"
    }
    @RequestMapping(value = ["/login"])
    fun login(): String {
        return "login"
    }
}
