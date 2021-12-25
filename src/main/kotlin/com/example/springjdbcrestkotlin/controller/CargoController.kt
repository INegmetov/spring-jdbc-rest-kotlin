package com.example.springjdbcrestkotlin.controller

import com.example.springjdbcrestkotlin.dto.CargoDto
import com.example.springjdbcrestkotlin.service.CargoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cargo")
class CargoController (
    private val cargoService: CargoService,
        ){

    @GetMapping
    fun getAll(
        @RequestParam("page") pageIndex: Int
    ):List<CargoDto> = cargoService.getAll(pageIndex)


    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): CargoDto =cargoService.getById(id)

    @GetMapping("/statistics")
    fun getCarStatistics(): Map<String, Int> = cargoService.getCarStatistics()


    @PostMapping
    fun create(@RequestBody dto: CargoDto): Int =
            cargoService.create(dto)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Int, @RequestBody dto: CargoDto){
            cargoService.update(id, dto)
    }

    @DeleteMapping("/{id}")
    fun deleteById (@PathVariable id: Int){
        cargoService.deleteById(id)
    }
}