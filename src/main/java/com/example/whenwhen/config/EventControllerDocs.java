package com.example.whenwhen.config;

import com.example.whenwhen.dto.EventRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "[Event API]")
public interface EventControllerDocs {

    @Operation(
            summary = "새 이벤트 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "이벤트 생성 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    ResponseEntity<?> createEvent(EventRequestDto requestDto);

    @Operation(
            summary = "이벤트 코드로 이벤트 세부 정보 검색",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "이벤트 세부 정보 검색 성공",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "이벤트를 찾을 수 없음",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    ResponseEntity<?> getEventByCode(String eventCode);
}