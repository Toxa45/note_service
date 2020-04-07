package note.service.exception;

import org.hibernate.exception.DataException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class SampleExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(SampleException.class)
    public ResponseEntity<Object> handle(SampleException e ) {
        return new ResponseEntity<Object>(new ApiError( HttpStatus.INTERNAL_SERVER_ERROR,  e.getMessage(), e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handle(HttpMessageNotReadableException ex ){
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR,  "Ошибка валидации данных", ex), HttpStatus.INTERNAL_SERVER_ERROR);
      }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR,  "Искаженный запрос JSON ", ex), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex ){
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR,  "Исключение SQL", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException ex ){
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR, "Произошло не определенное исключение", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataException.class)
    public ResponseEntity<Object> handleDataException(DataException ex ) {
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка данных", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex ) throws Exception {
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR, "Произошло не определенное исключение", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value=RuntimeException.class)
    public ResponseEntity<Object>  handle(RuntimeException ex  )
    {
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка во время выполнения", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value=NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex )
    {
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR, "Исключение нулевого указателя", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value=ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex )
    {
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR, "Индекс вне массива", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value=ArithmeticException.class)
    public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex)
    {
        return new ResponseEntity<Object>(new ApiError(  HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка арифметичекой операции", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value=NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex )
    {
        return new ResponseEntity<Object>(new ApiError( HttpStatus.INTERNAL_SERVER_ERROR, "Исключение числового формата", ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ApiError(status, "Обработчик не найден", ex), HttpStatus.NOT_FOUND);
    }

}