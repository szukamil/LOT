package com.task.lot.controller;

import com.task.lot.model.ApiError;
import com.task.lot.model.response.FullStatsResponse;
import com.task.lot.model.UserVisitDTO;
import com.task.lot.model.response.UserVisitStatsResponse;
import com.task.lot.service.UserVisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserVisitService userVisitService;
    @Operation(summary = "Post do dostarczania danych w body - Date data, String ip ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @PostMapping("/save")
    public ResponseEntity post(@RequestBody UserVisitDTO userVisitDTO){
        userVisitService.register(userVisitDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @Operation(summary = "Get do pobierania zestawienia odwiedzin usera z podanego w path varible ip w Stringu, " +
            "zwracanego z paginacja ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserVisitStatsResponse.class))}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @GetMapping("/statistics/{ip}")
    public ResponseEntity<Page<UserVisitStatsResponse>> get(@PathVariable String ip, Pageable pageable){
        return new ResponseEntity<>(userVisitService.getStatsForIp(ip,pageable),HttpStatus.OK);
    }
    @Operation(summary = "Get do pobierania zestawienia wszystkich wizyt userow zwracanych z paginacja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullStatsResponse.class))}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @GetMapping("/statistics")
    public ResponseEntity<Page<FullStatsResponse>> get(Pageable pageable){
        return new ResponseEntity<>(userVisitService.getFullStats(pageable),HttpStatus.OK);
    }
}

