package spring.generic.server;

import org.springframework.web.bind.annotation.*;

/**
 * Created by gadiel on 11/10/2016.
 */
@RestController
@RequestMapping("/farewell")
public class rest2 {

    @RequestMapping(value ="/sayBye", method = RequestMethod.GET)
    public String sayHi(@RequestParam(value="name", defaultValue="World") String name) {
        return "Bye "+name;
        //example: http://localhost:8080/farewell/sayBye?name=ido
    }

    @RequestMapping(value = "Seeya/{name}", method = RequestMethod.GET)
    String readBookmark(@PathVariable String name) {
        return "Seeya "+name;
        //examaple: http://localhost:8080/farewell/Seeya/ido
    }

}
