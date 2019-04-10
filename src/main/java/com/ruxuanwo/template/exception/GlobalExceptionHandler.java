package com.ruxuanwo.template.exception;


import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.utils.RequestUtil;
import com.ruxuanwo.template.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * 全局异常处理
 * 一般情况下，方法都有异常处理机制，但不能排除有个别异常没有处理，导致返回到前台，因此在这里做一个异常拦截，统一处理那些未被处理过的异常
 *
 * @author ruxuanwo
 */
@ControllerAdvice
@Controller
@RequestMapping
public class GlobalExceptionHandler extends AbstractErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Value("${server.error.path:${error.path:/error}}")
    private static String errorPath = "/error";

    @Value("${system.config.base-url}")
    private String systemBaseUrl;
    @Value("${system.config.static-url}")
    private String systemStaticUrl;




    /**
     * sql异常
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(NotLoginException.class)
    public Result<String> notLoginException(HttpServletRequest req, HttpServletResponse rsp, Exception ex) {
        LOGGER.error("!!! request uri:{} from {} server exception:{}", req.getRequestURI(), RequestUtil.getIpAddress(req), ex == null ? null : ex);
        return ResultUtil.builderResponse(102, ex == null ? null : ex.getMessage(), null);
    }

    /**
     * sql异常
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public Result<String> sqlException(HttpServletRequest req, HttpServletResponse rsp, Exception ex) {
        LOGGER.error("!!! request uri:{} from {} server exception:{}", req.getRequestURI(), RequestUtil.getIpAddress(req), ex == null ? null : ex);
        return ResultUtil.builderResponse(1002, ex == null ? null : ex.getMessage(), null);
    }


    /**
     * 500错误.
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseBody
    //@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result<String> serverError(HttpServletRequest req, HttpServletResponse rsp, Exception ex) throws Exception {
        LOGGER.error("!!! request uri:{} from {} server exception:{}", req.getRequestURI(), RequestUtil.getIpAddress(req), ex == null ? null : ex);
        return ResultUtil.builderResponse(1002, ex == null ? null : ex.getMessage(), null);
    }


    /**
     * 404的拦截.
     *
     * @param request
     * @param response
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFound(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        LOGGER.error("!!! request uri:{} from {} not found exception:{}", request.getRequestURI(), RequestUtil.getIpAddress(request), ex);
        request.setAttribute("staticUrl",systemStaticUrl);
        request.setAttribute("domain",systemBaseUrl);
        return "/error/404";
    }


    /**
     * 重写/error请求, ${server.error.path:${error.path:/error}} IDEA报红无需处理，作用是获取spring底层错误拦截
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "${server.error.path:${error.path:/error}}")
    public Result<String> handleErrors(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NOT_FOUND) {
            throw new NoHandlerFoundException(request.getMethod(),request.getRequestURL().toString(),new HttpHeaders()) ;
        }
        return ResultUtil.builderResponse(1002, "请求错误", null);
    }


    @Override
    public String getErrorPath() {
        return errorPath;
    }
}
