package com.example.demo.controlador;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Controlador {

    @GetMapping("/clasicos")
    public String clasicos() {
        return "clasicos";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/espress-caliente")
    public String espressCaliente() {
        return "espress-caliente";
    }

    @GetMapping("/espress-frio")
    public String espressFrio() {
        return "espress-frio";
    }

    @GetMapping("/formulario")
    public String formulario() {
        return "formulario";
    }

    @GetMapping("/frap-matcha")
    public String frapMatcha() {
        return "frap-matcha";
    }

    @GetMapping("/frap-mocha")
    public String frapMocha() {
        return "frap-mocha";
    }

    @GetMapping("/frap-vainilla")
    public String frapVainilla() {
        return "frap-vainilla";
    }

    @GetMapping
    public String inicio (){
        return "index";
    }
    
    @GetMapping("/keke-chocolate")
    public String kekeChocolate() {
        return "keke-chocolate";
    }

    @GetMapping("/mas-pedidos")
    public String masPedidos() {
        return "mas-pedidos";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/muffin-naranja")
    public String muffinNaranja() {
        return "muffin-naranja";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/sandwich")
    public String sandwich() {
        return "sandwich";
    }
}