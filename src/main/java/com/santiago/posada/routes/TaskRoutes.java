package com.santiago.posada.routes;

import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.ToDo;
import com.santiago.posada.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TaskRoutes {

    @Autowired
    private ToDoService service;

    @Bean
    public RouterFunction<ServerResponse> getTasks(){
        return route(GET("route/get/all"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.getTasks(), ToDo.class)));
    }

    @Bean
    public RouterFunction<ServerResponse>  updatetask(){
        return  route(PUT("route/update/{id}/{task}"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.
                            updateTask(request.pathVariable("id"),
                            request.pathVariable("task")), ToDo.class)));

    }
    @Bean
    public RouterFunction<ServerResponse>  addtask(){
        return  route(POST("route/create/{task}"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromPublisher(service.addTask(request.pathVariable("task")), ToDo.class)));

    }

    @Bean
    public RouterFunction<ServerResponse>  deletetask() {
        return RouterFunctions.route().
                DELETE("route/delete/{id}",
                RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        request -> service.deleteTask(request.pathVariable("id")))
                .build();

    }

    //Generar un tres router functions
    //Post para guardar una tarea
    //Put para actualizar
    //Delete para eliminar una tarea.


}
