package com.example.whenwhen.config;

import com.example.whenwhen.dto.ApiResponseWrapper;
import com.example.whenwhen.dto.EventRequestDto;
import com.example.whenwhen.dto.EventResponseDto;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[Event API]")
public interface EventControllerDocs {

    @Operation(
            summary = "Create a new event",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "이벤트 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponseWrapper.class,
                                            subTypes = {EventResponseDto.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "유효성 검증 실패",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                                "success": false,
                                                "error": {
                                                    "code": "VALIDATION_ERROR",
                                                    "message": "Invalid input data"
                                                }
                                            }
                                            """))
                    )
            }
    )
    ResponseEntity<?> createEvent(EventRequestDto requestDto);

    @Operation(
            summary = "Retrieve event by code",
            description = "이벤트 코드를 사용하여 이벤트 세부 정보를 검색합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "이벤트 세부 정보 검색 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponseWrapper.class,
                                            subTypes = {EventResponseDto.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "이벤트를 찾을 수 없음",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                              "success": false,
                                              "error": {
                                                "code": "EVENT_CODE_NOT_EXISTS",
                                                "message": "존재하지 않는 이벤트 코드입니다."
                                              }
                                            }
                                            """))
                    )
            }
    )
    ResponseEntity<?> getEventByCode(String eventCode);
}