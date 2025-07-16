package com.example.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Producto;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService service;
    private final CategoriaService cService;

    @GetMapping
    public String mostrarInicio(Model model) {
        model.addAttribute("productos", service.obtenerPrimerosSeis());
        return "public/index";
    }

    @GetMapping("/menu")
    public String verMenu(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        List<Producto> productos = service.obtenerProductosActivosConCategoriaActiva();

        if (buscar != null && !buscar.isBlank()) {
            productos = productos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(buscar.toLowerCase()))
                    .collect(Collectors.toList());
        }

        Map<String, List<Producto>> productosPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(p -> p.getCategoria().getNombre()));

        model.addAttribute("productosPorCategoria", productosPorCategoria);
        model.addAttribute("buscar", buscar);
        return "public/menu";
    }

    @GetMapping("/menu/productos/{id}")
    public String verProducto(@PathVariable Long id, Model model) {

        Producto producto = service.selectOne(id);

        model.addAttribute("producto", producto);

        return "public/producto";

    }

    @GetMapping("/menu/clasicos")
    public String mostrarClasicos(Model model) {

        model.addAttribute("clasicos", service.obtenerClasicos());

        return "public/clasicos";

    }

    @GetMapping("/menu/mas-pedidos")
    public String mostrarMasPedidos(Model model) {

        model.addAttribute("productosTop", service.obtenerTop3Productos());

        return "public/mas-pedidos";

    }

}
