package me.sunpeng.Interceptor;

import lombok.extern.slf4j.Slf4j;
import me.sunpeng.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author sp
 * @date 2021-11-16 17:11
 */
@Slf4j
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
     * <p>
     * 返回值：
     * true表示继续流程（如调用下一个拦截器或处理器）
     * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器
     * 此时我们需要通过response来产生响应；
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(System.currentTimeMillis());
        String uri = request.getRequestURI();
        Map paramMap = request.getParameterMap();
        log.info("用户访问地址:{},来路地址:{},请求参数:{}",uri, StringUtils.getIp(request));
        return super.preHandle(request, response, handler);
    }
}
