package com.PruebasPro.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PruebasPro.app.Entity.*;
import com.PruebasPro.app.Exception.*;
import com.PruebasPro.app.Repository.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "coordinador")
public class ControllerWebCoordinador {
    
    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @GetMapping("/index")
    public String coordinadorIndexTemplate(Model model, HttpSession session) {
        Coordinador coordinador = (Coordinador) session.getAttribute("usuarioLogeado");
        
        if (coordinador != null) {
            model.addAttribute("usuario", coordinador.getUsuario());
            model.addAttribute("nombre", coordinador.getNombre());
        }
        
        return "index-coordinador";
    }
    
    @GetMapping("/login")
    public String coordinadorLoginTemplate(Model model) {
        return "login-coordinador";
    }
    
    @PostMapping("/logear")
    public String coordinadorLogearTemplate(@RequestParam String usuario, @RequestParam String contrasena, Model model, HttpSession session) {
        Coordinador coordinador = null;
        for (Coordinador c : coordinadorRepository.findAll()) {
            if (c.getUsuario().equals(usuario)) {
                coordinador = c;
                break;
            }
        }
        
        if (coordinador != null && coordinador.getContrasena().equals(contrasena)) {
            session.setAttribute("usuarioLogeado", coordinador);
            return "redirect:/coordinador/index";
        } else {
            model.addAttribute("error", true);
            return "login-coordinador";
        }
    }
    
    @GetMapping("/estudiante/crear")
    public String coordinadorCrearTemplate(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "estudiante-form";
    }
    
    @GetMapping("/estudiante/informe/volver")
    public String volverAListaEstudiantes() {
        return "redirect:/coordinador/lista"; 
    }

    @GetMapping("/lista")
    public String asociacionListTemplate(Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "estudiante-lista";
    }

    @GetMapping("/estudiante/edit/{id}")
    public String coordinadorEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("estudiante",
                estudianteRepository.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrada")));
        return "estudiante-form";
    }

    @PostMapping("/estudiante/save")
    public String coordinadorSaveProcess(@ModelAttribute("estudiante") Estudiante estudiante) {
        if (estudiante.getId().isEmpty()) {
            estudiante.setId(null);
        }
        estudianteRepository.save(estudiante);
        return "redirect:/coordinador/index";
    }

    @GetMapping("/estudiante/delete/{id}")
    public String coordinadorDeleteProcess(@PathVariable("id") String id) {
        estudianteRepository.deleteById(id);
        return "redirect:/coordinador/lista";    
    }

    @GetMapping("/estudiante/informe/{id}")
    public String coordinadorInformeTemplate(@PathVariable("id") String id, Model model) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        model.addAttribute("estudiante", estudiante);
        return "informe-estudiante"; 
    }

    @GetMapping("/estudiante/anular/{id}")
    public String coordinadorAnularEstudiante(@PathVariable("id") String id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        // Anular todas las notas estableciendo cada campo en 0
        estudiante.setPuntaje(0);
        estudiante.setComunicacionEscrita(0);
        estudiante.setRazonamientoCuantitativo(0);
        estudiante.setLecturaCritica(0);
        estudiante.setCompetenciasCiudadanas(0);
        estudiante.setIngles(0);
        estudiante.setFormulacionProyectosIngenieria(0);
        estudiante.setPensamientoCientifico(0);
        estudiante.setDiseñoSoftware(0);
        estudiante.setNivelCompetenciasCiudadanas("Anulado");
        estudiante.setNivelComunicacionEscrita("Anulado");
        estudiante.setNivelDeIngles("Anulado");
        estudiante.setNivelDiseñoSoftware("Anulado");
        estudiante.setNivelFormulacionProyectosIngenieria("Anulado");
        estudiante.setNivelLecturaCritica("Anulado");
        estudiante.setNivelPensamientoCientifico("Anulado");
        estudiante.setNivelRazonamientoCuantitativo("Anulado");
        estudiante.setNivelSaberPro("Anulado");
        estudiante.setNivelIngles("Anulado");
        estudiante.setEstado("Anulado");




        // Guardar el estudiante actualizado
        estudianteRepository.save(estudiante);

        return "redirect:/coordinador/lista"; // Redirige a la lista de estudiantes
    }
}
