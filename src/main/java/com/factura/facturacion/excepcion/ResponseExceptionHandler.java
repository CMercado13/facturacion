package com.factura.facturacion.excepcion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.factura.facturacion.modelo.dto.ErrorDTO;
import com.factura.facturacion.utilidades.JsonResponse;
import com.factura.facturacion.utilidades.UtilKeys;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private UtilKeys uKeys;

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<JsonResponse<Object>> manejarTodasExcepciones(Exception ex, WebRequest request) {
		JsonResponse<Object> er = new JsonResponse<>(uKeys.STATUS_ERROR, ex.getMessage(), uKeys.LIST_EMPTY);
		return new ResponseEntity<>(er, HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ErrorDTO> errores = ex.getBindingResult().getAllErrors().stream().map(error -> {
			return new ErrorDTO(error.getDefaultMessage());
		}).collect(Collectors.toList());
		JsonResponse<ErrorDTO> er = new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_ERROR, errores);
		return new ResponseEntity<>(er, HttpStatus.OK);
	}

}
